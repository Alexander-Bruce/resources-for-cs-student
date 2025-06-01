%{

#include <stdio.h>      
#include <string.h>     
#include <stdlib.h>    

#include "string_list.h"    

#include "fss_parser.tab.h" // 由 Bison/Yacc 生成的头文件，定义了词法单元（token）的常量（如 TOKEN_PWD 等）与 yylval 的类型 YYSTYPE。

// 全局变量，用于存储当前词法单元的文本内容。
// 解析器从这里复制字符串。
char* yylval_lexeme = NULL;

// 用于组装字符串字面量的缓冲区
#define STRING_BUFFER_MAX_LEN 2048  // 字符串缓冲区的最大长度
char string_literal_buffer[STRING_BUFFER_MAX_LEN]; // 存储字符串字面量的缓冲区
int string_literal_pos = 0;           // 当前在字符串缓冲区中的位置

// 重置字符串字面量缓冲区
void string_buffer_reset() {
    string_literal_pos = 0;
    string_literal_buffer[0] = '\0'; // 将缓冲区清空（通过设置第一个字符为 null 终止符）
}

// 向字符串字面量缓冲区追加文本
void string_buffer_append(const char* text) {
    int len = strlen(text); // 获取要追加文本的长度
    if (string_literal_pos + len < STRING_BUFFER_MAX_LEN - 1) { // 检查是否会溢出（-1 是为 null 终止符留空间）
        strcat(string_literal_buffer, text); // 追加文本
        string_literal_pos += len;           // 更新当前位置
    } else {
        fprintf(stderr, "Lexer Error line %d: String literal too long, truncated.\n", yylineno); // 报告错误：字符串太长
        string_literal_buffer[STRING_BUFFER_MAX_LEN - 1] = '\0'; // 确保缓冲区以 null 结尾
        string_literal_pos = STRING_BUFFER_MAX_LEN - 1;         // 更新位置到最大值
    }
}

// 通过复制提供的文本（yytext）来设置全局变量 yylval_lexeme (当前词法单元的文本内容)
void set_global_lexeme_from_yytext(const char* text) {
    if (yylval_lexeme != NULL) { // 如果之前分配过内存
        free(yylval_lexeme);    // 释放旧的内存
        yylval_lexeme = NULL;
    }
    if (text != NULL && text[0] != '\0') { // 如果文本非空
        yylval_lexeme = strdup(text);     // 复制文本到新分配的内存中
        if (yylval_lexeme == NULL) {      // 检查 strdup 是否成功
            fprintf(stderr, "Lexer critical error: out of memory duplicating lexeme (from yytext).\n");
            exit(EXIT_FAILURE);           // 内存不足，严重错误，退出程序
        }
    } else { // 如果文本是 NULL 或空字符串
         yylval_lexeme = strdup("");      // 分配一个空字符串，确保 yylval_lexeme 总是有效
         if (yylval_lexeme == NULL) { // 检查strdup是否成功
            fprintf(stderr, "Lexer critical error: out of memory duplicating empty lexeme (from yytext).\n"); 
            exit(EXIT_FAILURE); // 内存不住，严重错误，程序退出
        }
    }
}

// 通过从 string_literal_buffer (文本缓冲区) 复制内容来设置全局变量 yylval_lexeme
void set_global_lexeme_from_string_buffer(const char* buffer_content) {
    if (yylval_lexeme != NULL) { // 如果之前分配过内存
        free(yylval_lexeme);    // 释放旧的内存
        yylval_lexeme = NULL;
    }
    if (buffer_content != NULL && buffer_content[0] != '\0') { // 如果缓冲区内容非空
        yylval_lexeme = strdup(buffer_content); // 复制内容
        if (yylval_lexeme == NULL) {           // 检查 strdup 是否成功
            fprintf(stderr, "Lexer critical error: out of memory duplicating lexeme (from string buffer).\n");
            exit(EXIT_FAILURE);                // 内存不足，严重错误，退出程序
        }
    } else { // 如果缓冲区内容是 NULL 或空字符串
        yylval_lexeme = strdup("");           // 分配一个空字符串
        if (yylval_lexeme == NULL) {
           fprintf(stderr, "Lexer critical error: out of memory duplicating empty lexeme (from string buffer).\n");
           exit(EXIT_FAILURE);
       }
    }
}
%}

// Flex 选项
%option noyywrap  // 调用 yywrap() 函数。当输入结束时，yylex() 会直接返回 0，处理其他输入。
                  // 如果没有这个选项，需要在文件末尾提供一个 yywrap() 函数。
%option yylineno  // 告诉 Flex 自动维护一个全局变量 yylineno，表示当前的行号。
%option caseless  // 使模式匹配不区分大小写。例如 "pwd" 规则也会匹配 "PWD" 或 "Pwd"。

// 独占式起始条件声明
// 当进入 STRING_STATE 状态时，只有标记有 <STRING_STATE> 前缀的规则才会被匹配。
%x STRING_STATE

/* 正则表达式定义 (别名) */
EOL             (\r\n|\n|\r)         // 定义 EOL (End Of Line) 为 Windows (CRLF), Unix (LF), 或旧 Mac (CR) 换行符
WHITESPACE      [ \t\f]+             // 定义 WHITESPACE 为一个或多个空格、制表符、换页符
COMMENT_START   "#"                   // 定义 COMMENT_START 为井号 "#"

OPTION_HEAD     "-"                   // 定义 OPTION_HEAD 为连字符 "-"
OPTION_CHARS    [a-zA-Z]+             // 定义 OPTION_CHARS 为一个或多个大小写字母
OPTION          {OPTION_HEAD}{OPTION_CHARS} // 定义 OPTION 为一个连字符后跟一个或多个字母 (例如 -l, -a)

/* 允许 ., .., ./path, /abs/path, file.txt, path-with-hyphens, ~user/path */
PATH_IDENTIFIER [a-zA-Z0-9_/\.\-~]+   // 定义 PATH_IDENTIFIER 为包含字母、数字、下划线、斜杠、点、连字符、波浪号的序列

%%
// 规则段：这里的每一行都是一个 "模式 动作" 对。
// Flex 会尝试匹配最长的可能字符串。如果多个规则匹配相同的最长字符串，则使用第一个定义的规则。

{WHITESPACE}        { /* Action: Skip */ } // 匹配空白字符。动作是空的，表示忽略这些字符。
{EOL}               { /* Action: Skip, yylineno automatically updated */ } // 匹配行尾符。忽略。yylineno 会自动更新。
{COMMENT_START}[^\r\n]* { /* Action: Skip comment line */ } // 匹配以 "#" 开头的注释行，直到行尾。忽略。
                                                           // [^\r\n]* 表示匹配任意数量的非换行符字符。

\"                  { // 匹配一个双引号 " (字符串的开始)
                        string_buffer_reset(); // 重置字符串缓冲区
                        BEGIN(STRING_STATE);   // 进入 STRING_STATE 状态
                    }

<STRING_STATE>\"    { // 在 STRING_STATE 状态下，匹配一个双引号 " (字符串的结束)
                        BEGIN(INITIAL);       // 回到初始状态 (INITIAL 是默认状态)
                        set_global_lexeme_from_string_buffer(string_literal_buffer); // 将缓冲区内容设置到 yylval_lexeme
                        return TOKEN_STRING_LITERAL; // 返回字符串字面量词法单元
                    }

<STRING_STATE>{EOL} { // 在 STRING_STATE 状态下，匹配一个行尾符 (处理多行字符串)
                        // 通过追加换行符来处理多行字符串，词法分析器会跟踪 yylineno。
                        string_buffer_append(yytext); // 将换行符 (yytext 包含匹配到的文本) 追加到缓冲区
                    }

<STRING_STATE><<EOF>> { // 在 STRING_STATE 状态下，如果遇到文件结束符 (End Of File)
                        BEGIN(INITIAL); // 回到初始状态
                        fprintf(stderr, "Lexer Error line %d: Unterminated string literal.\n", yylineno); // 报告错误：未终止的字符串
                        // yylval.sval (来自解析器的 %union) 不能在这里直接设置
                        // 我们将设置 yylval_lexeme 并返回 TOKEN_ERROR
                        set_global_lexeme_from_string_buffer("Unterminated string literal"); // 设置错误信息到 yylval_lexeme
                        return TOKEN_ERROR; // 返回错误词法单元，让解析器处理
                    }

<STRING_STATE>[^\"\r\n]+ { // 在 STRING_STATE 状态下，匹配一个或多个非双引号、非回车、非换行的字符
                        string_buffer_append(yytext); // 将这些字符追加到缓冲区
                    }

// 关键字的匹配
"pwd"               { set_global_lexeme_from_yytext(yytext); return TOKEN_PWD; }    // 匹配 "pwd"，设置词素，返回 TOKEN_PWD
"ls"                { set_global_lexeme_from_yytext(yytext); return TOKEN_LS; }     // 匹配 "ls"，设置词素，返回 TOKEN_LS
"cd"                { set_global_lexeme_from_yytext(yytext); return TOKEN_CD; }     // 匹配 "cd"，设置词素，返回 TOKEN_CD
"mkdir"             { set_global_lexeme_from_yytext(yytext); return TOKEN_MKDIR; }  // 匹配 "mkdir"，设置词素，返回 TOKEN_MKDIR
"rm"                { set_global_lexeme_from_yytext(yytext); return TOKEN_RM; }     // 匹配 "rm"，设置词素，返回 TOKEN_RM
"cp"                { set_global_lexeme_from_yytext(yytext); return TOKEN_CP; }     // 匹配 "cp"，设置词素，返回 TOKEN_CP
"mv"                { set_global_lexeme_from_yytext(yytext); return TOKEN_MV; }     // 匹配 "mv"，设置词素，返回 TOKEN_MV
"read"              { set_global_lexeme_from_yytext(yytext); return TOKEN_READ; }   // 匹配 "read"，设置词素，返回 TOKEN_READ
"write"             { set_global_lexeme_from_yytext(yytext); return TOKEN_WRITE; }  // 匹配 "write"，设置词素，返回 TOKEN_WRITE
"chmod"             { set_global_lexeme_from_yytext(yytext); return TOKEN_CHMOD; }  // 匹配 "chmod"，设置词素，返回 TOKEN_CHMOD
"source"            { set_global_lexeme_from_yytext(yytext); return TOKEN_SOURCE; } // 匹配 "source"，设置词素，返回 TOKEN_SOURCE
"help"              { set_global_lexeme_from_yytext(yytext); return TOKEN_HELP; }   // 匹配 "help"，设置词素，返回 TOKEN_HELP
"exit"              { set_global_lexeme_from_yytext(yytext); return TOKEN_EXIT; }   // 匹配 "exit"，设置词素，返回 TOKEN_EXIT

{OPTION}            { // 匹配之前定义的 OPTION 正则表达式 (例如 "-l")
                        set_global_lexeme_from_yytext(yytext); // 设置词素
                        return TOKEN_OPTION;                   // 返回选项词法单元
                    }

{PATH_IDENTIFIER}   { // 匹配之前定义的 PATH_IDENTIFIER 正则表达式
                      // 这个规则放在关键字之后，这样 "ls" 会被识别为 TOKEN_LS 而不是 TOKEN_PATH
                        set_global_lexeme_from_yytext(yytext); // 设置词素
                        return TOKEN_PATH;                     // 返回路径词法单元
                    }

<<EOF>>             { // 匹配文件结束符 (End Of File)
                        set_global_lexeme_from_yytext(""); // 确保 yylval_lexeme 有效 (设置为空字符串)
                        return 0; /* yylex() 返回 0 表示标准的文件结束 */
                    }

.                   { // 匹配任何其他未被以上规则匹配的单个字符 (捕获所有)
                        char err_msg_buffer[100];
                        snprintf(err_msg_buffer, sizeof(err_msg_buffer), "Unexpected character: '%s'", yytext); // 创建错误消息
                        set_global_lexeme_from_string_buffer(err_msg_buffer); // 将错误消息设置到 yylval_lexeme
                        return TOKEN_ERROR; // 返回错误词法单元，让解析器处理
                    }

%%