package fss.lexer; // 文件系统脚本词法分析器包

import fss.common.Token;
import fss.common.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 词法分析器类，负责将输入字符串解析为Token序列
 */
public class Lexer {
    private final String input;        // 输入字符串
    private int position = 0;         // 当前读取位置
    private int currentLine = 1;      // 当前行号
    private static final Map<String, TokenType> KEYWORDS = new HashMap<>(); // 关键字映射表

    static {
        // 初始化关键字表
        KEYWORDS.put("pwd", TokenType.PWD);
        KEYWORDS.put("ls", TokenType.LS);
        KEYWORDS.put("cd", TokenType.CD);
        KEYWORDS.put("mkdir", TokenType.MKDIR);
        KEYWORDS.put("rm", TokenType.RM);
        KEYWORDS.put("cp", TokenType.CP);
        KEYWORDS.put("mv", TokenType.MV);
        KEYWORDS.put("read", TokenType.READ);
        KEYWORDS.put("write", TokenType.WRITE);
        KEYWORDS.put("help", TokenType.HELP);
        KEYWORDS.put("exit", TokenType.EXIT);
        KEYWORDS.put("chmod", TokenType.CHMOD);
        KEYWORDS.put("source", TokenType.SOURCE);
    }

    public Lexer(String input) {
        this.input = input;
    }

    /**
     * 将输入字符串转换为Token序列
     * @return Token列表
     */
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (position < input.length()) {
            char currentChar = input.charAt(position);

            if (Character.isWhitespace(currentChar)) {
                // 处理空白字符
                if (currentChar == '\n') {
                    currentLine++;
                }
                position++;
            } else if (currentChar == '#') {
                // 处理注释
                skipComment();
            } else if (currentChar == '"') {
                // 处理字符串字面量
                tokens.add(scanStringLiteral());
            } else if (currentChar == '-') {
                // 处理选项(如-r, -f等)
                if (
                        position + 1 < input.length() &&
                        Character.isLetter(input.charAt(position + 1))
                ) {
                    tokens.add(scanOption());
                } else {
                    // 处理路径中的'-'字符
                    tokens.add(scanIdentifierOrPath());
                }

            } else if (isPotentialPathOrIdentifierStart(currentChar)) {
                // 处理命令或路径
                tokens.add(scanIdentifierOrPath());
            } else {
                // 处理非法字符
                tokens.add(new Token(TokenType.ERROR, "Unexpected character: " + currentChar, currentLine));
                position++;
            }
        }

        // 添加文件结束标记
        tokens.add(new Token(TokenType.EOF, "", currentLine));
        return tokens;
    }

    /**
     * 扫描选项Token(如-r, -f等)
     * @return 选项Token
     */
    private Token scanOption() {
        int start = position;
        position++; // 跳过'-'

        // 读取选项字符(如'r', 'f', 'rf'等)
        while (position < input.length() && Character.isLetter(input.charAt(position))) {
            position++;
        }

        String value = input.substring(start, position); // 获取选项值
        return new Token(TokenType.OPTION, value, currentLine);
    }

    /**
     * 扫描标识符或路径Token
     * @return 标识符或路径Token
     */
    private Token scanIdentifierOrPath() {
        int start = position;

        // 读取连续的合法字符
        while (position < input.length() && !Character.isWhitespace(input.charAt(position))
                && input.charAt(position) != '#') {
            if (input.charAt(position) == '"') {
                break;
            }
            position++;
        }
        String value = input.substring(start, position);

        // 检查是否是关键字(命令)
        TokenType type = KEYWORDS.getOrDefault(value.toLowerCase(), TokenType.PATH);

        return new Token(type, value, currentLine);
    }

    /**
     * 跳过注释
     */
    private void skipComment() {
        while (position < input.length() && input.charAt(position) != '\n') {
            position++;
        }
    }

    /**
     * 扫描字符串字面量
     * @return 字符串Token
     */
    private Token scanStringLiteral() {
        // 记录当前行号
        int stringStartLine = currentLine;

        // 用来构建字符串的内容，不能直接用 substring，因为有转义字符
        StringBuilder valueBuilder = new StringBuilder();

        // 跳过开头的引号
        position++;

        while (position < input.length()) {
            char currentChar = input.charAt(position);

            if (currentChar == '"') {
                // 遇到右引号，字符串结束，正常返回
                position++;
                return new Token(TokenType.STRING_LITERAL, valueBuilder.toString(), stringStartLine);

            } else if (currentChar == '\\') {
                // 遇到反斜杠，说明是转义字符，继续处理下一个字符
                position++;

                if (position >= input.length()) {
                    // 如果反斜杠是最后一个字符，那转义肯定是不完整的，报错
                    return new Token(TokenType.ERROR, "Unterminated escape sequence in string literal", currentLine);
                }

                char escapedChar = input.charAt(position);
                switch (escapedChar) {
                    case '"':
                        valueBuilder.append('"');  // 转义双引号
                        break;
                    case '\\':
                        valueBuilder.append('\\'); // 转义反斜杠
                        break;
                    case 'n':
                        valueBuilder.append('\n'); // 换行符
                        break;
                    case 't':
                        valueBuilder.append('\t'); // tab 键
                        break;
                    default:
                        // 遇到未知的转义字符，按原样加进去，同时给个警告
                        valueBuilder.append('\\').append(escapedChar);
                        System.err.println(
                                "Warning: Unrecognized escape sequence '\\" +
                                escapedChar +
                                "' treated as literal characters at line " +
                                currentLine);
                        break;
                }

                position++;

            } else if (currentChar == '\n') {
                // 如果字符串里直接出现了换行符（没有转义），那就把它当成内容，加进去
                valueBuilder.append(currentChar);
                currentLine++;   // 行号也要加一
                position++;      // 继续下一个字符
            } else {
                // 普通字符，直接加到字符串里
                valueBuilder.append(currentChar);
                position++;
            }
        }

        // 能走到这，说明一直没遇到结束的引号，字符串没闭合，报错
        return new Token(TokenType.ERROR, "Unterminated string literal", stringStartLine);
    }


    /**
     * 判断字符是否可以作为路径/标识符的起始字符
     * @param c 待检查字符
     * @return 判断结果
     */
    private boolean isPotentialPathOrIdentifierStart(char c) {
        return Character.isLetterOrDigit(c) || c == '.' || c == '/' || c == '\\' || c == '_' || c == ':';
    }

}