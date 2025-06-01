package fss.common;

public enum TokenType {
    // 命令
    PWD, LS, CD, MKDIR, RM, CP, MV, READ, WRITE, CHMOD, SOURCE, HELP, EXIT,

    // 变量
    PATH, STRING_LITERAL,

    // 选项
    OPTION, OCTAL_MODE,

    // 控制
    EOF, // 文件结束

    // 错误
    ERROR // 词法分析错误
}