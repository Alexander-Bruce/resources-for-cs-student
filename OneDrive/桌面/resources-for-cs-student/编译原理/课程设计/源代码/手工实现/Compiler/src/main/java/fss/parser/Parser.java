package fss.parser;

import fss.analyzer.SemanticAnalyzer;
import fss.common.Token;
import fss.common.TokenType;
import fss.exception.SyntaxErrorException;
import fss.exception.SemanticErrorException;
import fss.executor.CommandExecutor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 语法解析器类，负责将词法分析器生成的Token序列转换为可执行的命令结构
 * 同时协调语义分析和命令执行流程
 */
public class Parser {
    private final List<Token> tokens;  // 待解析的Token列表
    private int current = 0;          // 当前解析位置索引
    private final CommandExecutor executor;  // 命令执行器引用
    private final SemanticAnalyzer semanticAnalyzer; // 语义分析器实例
    private final int lineNumber;      // 当前行号(用于错误报告)

    /**
     * 构造函数
     * @param tokens 词法分析后的Token列表
     * @param executor 命令执行器实例
     * @param lineNumber 当前行号
     */
    public Parser(List<Token> tokens, CommandExecutor executor, int lineNumber) {
        this.tokens = tokens;
        this.executor = executor;
        this.lineNumber = lineNumber;
        // 初始化语义分析器，传入当前工作目录和执行器引用
        this.semanticAnalyzer =
                new SemanticAnalyzer(executor.getCurrentDirectoryPath(), executor, lineNumber);
    }

    // 支持的命令类型集合
    private static final Set<TokenType> COMMAND_TYPES = Set.of(
            TokenType.PWD, TokenType.LS, TokenType.CD, TokenType.MKDIR,
            TokenType.RM, TokenType.CP, TokenType.MV, TokenType.READ,
            TokenType.WRITE, TokenType.HELP, TokenType.EXIT,
            TokenType.CHMOD, TokenType.SOURCE
    );

    /**
     * 解析命令选项
     * @return 选项集合(如"-r", "-f"等)
     */
    private Set<String> parseOptions() {
        Set<String> options = new HashSet<>();

        while (!isAtEnd() && peek().type == TokenType.OPTION) {
            // 获取当前选项
            Token optionToken = advance();

            if (optionToken.value.length() > 1 && optionToken.value.startsWith("-")) {
                // 处理组合选项如"-rf"拆分为"-r"和"-f"
                for (int i = 1; i < optionToken.value.length(); i++) {
                    options.add("-" + optionToken.value.charAt(i));
                }

            } else {
                options.add(optionToken.value);
            }
        }

        return options;
    }

    /**
     * 主解析方法，负责整个命令的解析流程
     */
    public void parse() {
        if (isAtEnd() || peek().type == TokenType.EOF) { // 处理空输入或仅EOF的情况
            return;
        }

        // 获取当前Token
        Token commandToken = advance(); // 消费命令Token

        try {
            // 验证是否为有效命令类型
            if (!COMMAND_TYPES.contains(commandToken.type)) {
                int line = this.lineNumber > 0 ? this.lineNumber : commandToken.line;

                throw new SyntaxErrorException(
                        "Unexpected token: Expected a command, got " + commandToken.type + " ('" + commandToken.value +"')",
                        line
                );
            }

            // 根据命令类型分发到对应的解析方法
            switch (commandToken.type) {
                case PWD:
                    parsePwd();
                    break;
                case LS:
                    parseLs();
                    break;
                case CD:
                    parseCd();
                    break;
                case CHMOD:
                    parseChmod();
                    break;
                case MKDIR:
                    parseMkdir();
                    break;
                case RM:
                    parseRm();
                    break;
                case CP:
                    parseCp();
                    break;
                case MV:
                    parseMv();
                    break;
                case READ:
                    parseRead();
                    break;
                case WRITE:
                    parseWrite();
                    break;
                case SOURCE:
                    parseSource();
                    break;
                case HELP:
                    parseHelp();
                    break;
                case EXIT:
                    parseExit();
                    break;
                default:
                    // 理论上不会执行到这里，前面的检查已保证
                    int line = this.lineNumber > 0 ? this.lineNumber : commandToken.line;

                    throw new SyntaxErrorException(
                            "Internal Parser Error: Unhandled command type: " + commandToken.type,
                            line
                    );
            }

            // 检查命令参数后是否有非法Token
            if (!isAtEnd() && peek().type != TokenType.EOF) {
                int errorLine = this.lineNumber > 0 ? this.lineNumber : peek().line;
                Token unexpected = peek();

                throw new SyntaxErrorException(
                        "Unexpected token '" + unexpected.value + "' after command arguments.",
                        errorLine
                );
            }

        } catch (SyntaxErrorException e) {
            System.err.println(e.getMessage()); // 打印具体错误信息
            synchronize(); // 尝试恢复以继续解析后续命令
            throw new SyntaxErrorException(e.getMessage());
        } catch(SemanticErrorException e){
            System.err.println(e.getMessage()); // 打印具体错误信息
            synchronize(); // 尝试恢复以继续解析后续命令
            throw new SemanticErrorException(e.getMessage());
        }catch (Exception e) {
            // 捕获解析/分析过程中的运行时异常
            int errorLine = this.lineNumber > 0 ? this.lineNumber : (previous() != null ? previous().line : 1);

            System.err.println(
                    "Runtime Error during parsing/analysis on line " + errorLine + ": " + e.getMessage()
            );

            synchronize();
        }
    }

    // --- 各命令解析方法 ---

    /**
     * 解析pwd命令(无参数)
     */
    private void parsePwd() {
        // 语法检查：pwd不应带任何参数
        if (!isAtEnd() && peek().type != TokenType.EOF) {
            throw new SyntaxErrorException(
                    "PWD command does not take arguments or options.", getCurrentLine()
            );
        }
        // 语义检查
        semanticAnalyzer.checkPwd();
        // 执行
        executor.executePwd();
    }

    /**
     * 解析ls命令
     */
    private void parseLs() {
        Set<String> options = parseOptions();
        String path = null;
        // 提取可选路径参数
        if (!isAtEnd() && (peek().type == TokenType.PATH || peek().type == TokenType.STRING_LITERAL)) {
            path = advance().value;
        }
        // 语义检查
        semanticAnalyzer.checkLs(options, path);
        // 执行
        executor.executeLs(options, path);
    }

    /**
     * 解析cd命令
     */
    private void parseCd() {
        // cd命令选项应在路径前
        if (peek().type == TokenType.OPTION) {
            throw new SyntaxErrorException(
                    "CD command does not take options before the path.",
                    getCurrentLine()
            );
        }

        Token pathToken = consumePathOrString("Expected directory path after CD.");
        // 语义检查
        semanticAnalyzer.checkCd(pathToken.value);
        // 执行
        executor.executeCd(pathToken.value);
    }

    /**
     * 解析chmod命令
     */
    private void parseChmod() {
        // 格式：chmod 模式 路径
        Token modeToken = consume(
                TokenType.PATH,
                "Expected octal mode (e.g., 755) after CHMOD.",
                getCurrentLine()
        );
        String modeStr = modeToken.value;

        Token pathToken =
                consumePathOrString("Expected file or directory path after mode for CHMOD.");
        String pathStr = pathToken.value;

        // 语义检查(验证路径存在性和模式格式)
        semanticAnalyzer.checkChmod(modeStr, pathStr);
        // 执行(处理实际权限设置和操作系统特定逻辑)
        executor.executeChmod(modeStr, pathStr);
    }

    /**
     * 解析mkdir命令
     */
    private void parseMkdir() {
        Set<String> options = parseOptions();
        Token pathToken = consumePathOrString("Expected directory path after MKDIR.");

        // 语义检查
        semanticAnalyzer.checkMkdir(options, pathToken.value);
        // 执行
        executor.executeMkdir(options, pathToken.value);
    }

    /**
     * 解析rm命令
     */
    private void parseRm() {
        Set<String> options = parseOptions();
        List<String> paths = new ArrayList<>();

        // 必须至少有一个路径参数
        if (!check(TokenType.PATH) && !check(TokenType.STRING_LITERAL) && !check(TokenType.EOF)) {
            throw new SyntaxErrorException(
                    "Expected at least one file/directory path after RM options, or end of command.",
                    getCurrentLine()
            );
        }
        // 收集所有路径参数
        while (check(TokenType.PATH) || check(TokenType.STRING_LITERAL)) {
            paths.add(advance().value);
        }

        if (paths.isEmpty()){
            throw new SyntaxErrorException(
                    "RM command requires at least one path argument.",
                    getCurrentLine()
            );
        }

        // 语义检查
        semanticAnalyzer.checkRm(options, paths);
        // 执行
        executor.executeRm(options, paths);
    }

    /**
     * 解析cp命令
     */
    private void parseCp() {
        Set<String> options = parseOptions();
        List<String> sourcesAndDest = new ArrayList<>();

        // 收集所有路径参数
        while (check(TokenType.PATH) || check(TokenType.STRING_LITERAL)) {
            sourcesAndDest.add(advance().value);
        }

        // 必须至少有一个源路径和一个目标路径
        if (sourcesAndDest.size() < 2) {
            throw new SyntaxErrorException(
                    "CP requires at least a source and a destination.",
                    getCurrentLine()
            );
        }

        // 分离源路径和目标路径
        String destination = sourcesAndDest.removeLast(); // 最后一个作为目标
        List<String> sources = sourcesAndDest; // 其余作为源

        // 语义检查
        semanticAnalyzer.checkCp(options, sources, destination);
        // 执行
        executor.executeCp(options, sources, destination);
    }

    /**
     * 解析mv命令
     */
    private void parseMv() {
        Set<String> options = parseOptions();
        List<String> sourcesAndDest = new ArrayList<>();

        // 收集所有路径参数
        while (check(TokenType.PATH) || check(TokenType.STRING_LITERAL)) {
            sourcesAndDest.add(advance().value);
        }

        // 必须至少有一个源路径和一个目标路径
        if (sourcesAndDest.size() < 2) {
            throw new SyntaxErrorException(
                    "MV requires at least a source and a destination.",
                    getCurrentLine()
            );
        }

        // 分离源路径和目标路径
        String destination = sourcesAndDest.removeLast(); // 最后一个作为目标
        List<String> sources = sourcesAndDest; // 其余作为源

        // 语义检查
        semanticAnalyzer.checkMv(options, sources, destination);
        // 执行
        executor.executeMv(options, sources, destination);
    }

    /**
     * 解析read命令
     */
    private void parseRead() {
        // read命令不应带选项
        if (peek().type == TokenType.OPTION) {
            throw new SyntaxErrorException(
                    "READ command does not take options.",
                    getCurrentLine()
            );
        }
        Token pathToken =
                consumePathOrString("Expected file path after READ.");
        // 语法检查：确保没有多余参数
        if (!isAtEnd() && peek().type != TokenType.EOF) {
            throw new SyntaxErrorException(
                    "READ command takes only one path argument.",
                    getCurrentLine()
            );
        }

        // 语义检查
        semanticAnalyzer.checkRead(pathToken.value);
        // 执行
        executor.executeRead(pathToken.value);
    }

    /**
     * 解析write命令
     */
    private void parseWrite() {
        Set<String> options = parseOptions();
        Token pathToken =
                consumePathOrString("Expected file path after WRITE options.");

        Token contentToken = consume(
                TokenType.STRING_LITERAL,
                "Expected quoted content string after file path for WRITE.",
                getCurrentLine()
        );

        // 语义检查
        semanticAnalyzer.checkWrite(options, pathToken.value, contentToken.value);
        // 执行
        executor.executeWrite(options, pathToken.value, contentToken.value);
    }

    /**
     * 解析source命令
     */
    private void parseSource() {
        // source命令不应带选项
        if (peek().type == TokenType.OPTION) {
            throw new SyntaxErrorException("SOURCE command does not take options.", getCurrentLine());
        }
        Token pathToken = consumePathOrString("Expected script file path after SOURCE.");
        // 语法检查：确保没有多余参数
        if (!isAtEnd() && peek().type != TokenType.EOF) {
            throw new SyntaxErrorException(
                    "SOURCE command takes only one path argument.",
                    getCurrentLine()
            );
        }
        String scriptPath = pathToken.value;

        // 语义检查(包括递归深度检查)
        semanticAnalyzer.checkSource(scriptPath);
        // 执行
        executor.executeSource(scriptPath, this.lineNumber);
    }

    /**
     * 解析help命令(无参数)
     */
    private void parseHelp() {
        if (!isAtEnd() && peek().type != TokenType.EOF) {
            throw new SyntaxErrorException(
                    "HELP command does not take arguments or options.",
                    getCurrentLine()
            );
        }

        semanticAnalyzer.checkHelp();
        executor.executeHelp();
    }

    /**
     * 解析exit命令(无参数)
     */
    private void parseExit() {
        if (!isAtEnd() && peek().type != TokenType.EOF) {
            throw new SyntaxErrorException(
                    "EXIT command does not take arguments or options.",
                    getCurrentLine()
            );
        }

        semanticAnalyzer.checkExit();
        executor.executeExit();
    }

    // ========== 辅助方法 ==========

    /**
     * 消费路径或字符串类型的Token
     * @param errorMessage 错误信息
     * @return 消费的Token
     */
    private Token consumePathOrString(String errorMessage) {
        if (check(TokenType.PATH) || check(TokenType.STRING_LITERAL)) {
            return advance();
        }

        throw new SyntaxErrorException(errorMessage, getCurrentLine());
    }

    /**
     * 消费指定类型的Token
     * @param type 期望的Token类型
     * @param errorMessage 错误信息
     * @param line 行号
     * @return 消费的Token
     */
    private Token consume(TokenType type, String errorMessage, int line) {
        if (check(type)) {
            return advance();
        }
        // 调整行号以获得更好的错误报告
        int errorLine = line;

        if (!isAtEnd() && peek() != null) {
            errorLine = this.lineNumber > 0 ? this.lineNumber : peek().line;
        } else if (previous() != null) {
            errorLine = this.lineNumber > 0 ? this.lineNumber : previous().line;
        }

        throw new SyntaxErrorException(errorMessage, errorLine);
    }

    /**
     * 获取当前行号
     * @return 当前行号
     */
    private int getCurrentLine() {
        // 优先使用传入的行号
        if (this.lineNumber > 0) {
            return this.lineNumber;
        }
        // 否则尝试从当前或前一个Token获取
        if (isAtEnd()) {
            return (previous() != null) ? previous().line : 1; // 默认返回1如果没有Token
        } else {
            return peek().line;
        }
    }

    /**
     * 前进到下一个Token
     * @return 前一个Token
     */
    private Token advance() {
        if (!isAtEnd()) {
            current++;
        }

        return previous();
    }

    /**
     * 检查当前Token类型
     * @param type 期望的类型
     * @return 是否匹配
     */
    private boolean check(TokenType type) {
        if (isAtEnd()) {
            return false;
        }

        return peek().type == type;
    }

    /**
     * 查看当前Token而不消费
     * @return 当前Token
     */
    private Token peek() {
        // 确保不越界，如果越界返回EOF Token
        if (current >= tokens.size()) {
            if (!tokens.isEmpty() && tokens.getLast().type == TokenType.EOF) {
                return tokens.getLast();
            }
            return new Token(TokenType.EOF, "", getCurrentLine()); // 防御性编程
        }

        return tokens.get(current);
    }

    /**
     * 获取前一个Token
     * @return 前一个Token或null
     */
    private Token previous() {
        if (current == 0) {
            return null;
        }
        if (current - 1 < tokens.size()) {
            return tokens.get(current - 1);
        }

        return null; // 正常情况下不应发生
    }

    /**
     * 是否到达Token流末尾
     * @return 是否到达末尾
     */
    private boolean isAtEnd() {
        return current >= tokens.size() || (current == tokens.size() - 1 && peek().type == TokenType.EOF);
    }

    /**
     * 错误恢复同步方法
     * 前进到下一个可能命令或EOF位置
     */
    private void synchronize() {
        System.out.println("Attempting error recovery..."); // 调试信息
        if (isAtEnd()) return; // 已在末尾则无需同步

        Token currentToken = advance(); // 消费导致错误的Token

        while (!isAtEnd()) {
            Token peekToken = peek(); // 查看下一个Token

            // 检查是否为命令关键字
            if (COMMAND_TYPES.contains(peekToken.type)) {
                System.out.println("Recovery point found at command: " + peekToken.value);
                return; // 找到新命令的起点
            }

            // 如果遇到EOF则停止
            if (peekToken.type == TokenType.EOF) {
                System.out.println("Recovery point found at EOF.");
                return;
            }

            // 否则消费当前Token继续循环
            System.out.println("Skipping token during recovery: " + peekToken);
            advance();
        }
        System.out.println("Synchronization reached end of tokens.");
    }
}