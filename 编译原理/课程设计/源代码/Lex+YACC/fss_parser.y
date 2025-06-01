%{ 
//To the header of fss_parser.tab.c 

#include <stdio.h>      
#include <stdlib.h>     
#include <string.h>     
#include <errno.h>      
#include <unistd.h>     
#include "string_list.h" // 自定义的头文件，用于 StringList 类型 (存储选项或路径列表)
// 这个头文件将由 "bison -d fss_parser.y" 命令生成
#include "fss_parser.tab.h" // 包含由 Bison 生成的头文件。
                            // 它定义了词法单元 (token) 的编号 (TOKEN_PWD 等)
                            // 以及 YYSTYPE 联合体 (需要 StringList 类型定义)。
                            // -d 选项告诉 Bison 生成这个头文件。

int yylex(void); // 声明词法分析器函数 yylex()，在lex.yy.c中，获取下一个词法单元。

void yyerror(const char *s); // 声明解析器错误报告函数 yyerror()，在C代码区实现。

// 来自词法分析器 (lex.yy.c) 的全局变量
extern char* yylval_lexeme; // 声明一个外部全局变量，它指向词法分析器匹配到的最后一个词法单元的文本，在fss_lexer.l中。
extern int yylineno;        // 声明外部全局变量，表示词法分析器当前的行号。由 Flex/Lex 维护。
extern FILE *yyin;          // 声明外部全局变量，表示词法分析器的输入流。由 Flex/Lex 使用。

// 语义/执行器存根 (stub) 和辅助函数的前向声明，在C代码区实现
int str_ends_with(const char *str, const char *suffix); // 检查字符串是否以特定后缀结尾

// 为每个命令声明配对的 语义检查 和 执行 函数
void semantic_check_pwd(); void execute_pwd();
void semantic_check_ls(StringList* options, const char* path); void execute_ls(StringList* options, const char* path);
void semantic_check_cd(const char* path); void execute_cd(const char* path);
void semantic_check_chmod(const char* mode, const char* path); void execute_chmod(const char* mode, const char* path);
void semantic_check_mkdir(StringList* options, const char* path); void execute_mkdir(StringList* options, const char* path);
void semantic_check_rm(StringList* options, StringList* paths); void execute_rm(StringList* options, StringList* paths);
void semantic_check_cp(StringList* options, StringList* sources, const char* destination); void execute_cp(StringList* options, StringList* sources, const char* destination);
void semantic_check_mv(StringList* options, StringList* sources, const char* destination); void execute_mv(StringList* options, StringList* sources, const char* destination);
void semantic_check_read(const char* path); void execute_read(const char* path);
void semantic_check_write(StringList* options, const char* path, const char* content); void execute_write(StringList* options, const char* path, const char* content);
void semantic_check_source(const char* path); void execute_source(const char* path);
void semantic_check_help(); void execute_help();
void semantic_check_exit(); void execute_exit();

%} // C 定义区段结束

/* YACC/Bison 语义值类型定义 */
// %union 定义了 YYSTYPE 联合体，用于存储 词法单元 和 非终结符 的语义值。
%union {
    char* sval;            // 用于单个字符串值 (路径、选项、字面量)。
    StringList* list_sval; // 用于字符串列表。StringList 类型通过 string_list.h 导入。
}

/* 词法单元声明：类型 (如果有) 和编号 */
// %token 声明终结符 (由词法分析器返回的词法单元)
// 从257开始，0-255分配给ASCII字符
%token TOKEN_PWD 257             // PWD 命令关键字
%token TOKEN_LS 258              // LS 命令关键字
%token TOKEN_CD 259              // CD 命令关键字
%token TOKEN_MKDIR 260           // MKDIR 命令关键字
%token TOKEN_RM 261              // RM 命令关键字
%token TOKEN_CP 262              // CP 命令关键字
%token TOKEN_MV 263              // MV 命令关键字
%token TOKEN_READ 264            // READ 命令关键字
%token TOKEN_WRITE 265           // WRITE 命令关键字
%token TOKEN_CHMOD 266           // CHMOD 命令关键字
%token TOKEN_SOURCE 267          // SOURCE 命令关键字
%token TOKEN_HELP 268            // HELP 命令关键字
%token TOKEN_EXIT 269            // EXIT 命令关键字
%token TOKEN_ERROR 273           // 词法分析器返回的错误标记

// 带有语义值的词法单元声明
// <sval> 指定该词法单元的语义值存储在 YYSTYPE 联合体的 sval 成员 (char*) 中。
%token <sval> TOKEN_PATH 270           // 路径标识符
%token <sval> TOKEN_STRING_LITERAL 271 // 字符串字面量
%token <sval> TOKEN_OPTION 272         // 命令选项 (例如 "-l")

/* 非终结符类型声明 */
// %type 声明非终结符 (语法规则的左侧) 的语义值类型。
// <list_sval> 指定该非终结符的语义值类型为 StringList* (存储在 list_sval 成员中)。
%type <list_sval> option_list           // 选项列表 (例如 "-l -a")
%type <list_sval> path_list             // 路径列表 (用于 rm)
%type <list_sval> source_list_for_cp_mv // 源文件/目录列表 (用于 cp, mv)
// <sval> 指定该非终结符的语义值类型为 char* (存储在 sval 成员中)。
%type <sval> optional_path         // 可选的单个路径 (用于 ls)
%type <sval> path_or_string_literal // 可以是路径或字符串字面量

/* 指定语法的起始规则 */
%start program

%% /* 语法规则区段开始 */

program: // 整个程序 (输入) 的顶层规则
    /* empty program is allowed */ // 允许输入为空
    | command_list                // 或者由一个命令列表组成
    ;

command_list: // 定义命令列表
    command                     // 一个命令列表可以是一个命令
    | command_list command      // 或者是一个命令列表后面跟着另一个命令 (递归定义)
    ;

command: // 定义一个命令可以是什么
    pwd_command       // 可以是 pwd 命令
    | ls_command        // 可以是 ls 命令
    | cd_command        // 可以是 cd 命令
    | chmod_command     // 可以是 chmod 命令
    | mkdir_command     // 可以是 mkdir 命令
    | rm_command        // 可以是 rm 命令
    | cp_command        // 可以是 cp 命令
    | mv_command        // 可以是 mv 命令
    | read_command      // 可以是 read 命令
    | write_command     // 可以是 write 命令
    | source_command    // 可以是 source 命令
    | help_command      // 可以是 help 命令
    | exit_command      // 可以是 exit 命令
    ;

option_list: // 定义选项列表
    /* empty: creates an empty list */ // 匹配空的情况 (没有选项)
    { $$ = create_string_list(); }    // 动作：创建一个新的空 StringList，并将其赋值给这个规则的语义值 ($$).
                                      // $$ 指向 yylval.list_sval
    | option_list TOKEN_OPTION       // 匹配一个已有的选项列表，后面跟着一个新的选项词法单元
    {
        // $1 是 option_list 的语义值 (一个 StringList*)
        // yylval_lexeme 是词法分析器为 TOKEN_OPTION 匹配到的文本 (例如 "-l")
        add_to_string_list_copy($1, yylval_lexeme); // 将选项文本的 *副本* 添加到列表中
        $$ = $1; // 将修改后的列表 (右侧 option_list) 赋值给这个规则的语义值 (左侧 option_list)
    }
    ;

path_or_string_literal: // 定义一个可以接受路径或字符串字面量的地方
    TOKEN_PATH                  // 匹配一个路径词法单元
    {
        // yylval_lexeme 是词法分析器匹配到的路径文本
        $$ = strdup(yylval_lexeme); // 动作：创建路径文本的 *副本*，并将指针赋值给规则的语义值 ($$)
                                    // $$ 指向 yylval.sval
        if (!$$) { perror("strdup failed for TOKEN_PATH"); YYABORT; } // 检查 strdup 是否失败，失败则中止解析
    }
    | TOKEN_STRING_LITERAL       // 匹配一个字符串字面量词法单元
    {
        // yylval_lexeme 是词法分析器匹配到的字符串内容 (不含引号)
        $$ = strdup(yylval_lexeme); // 动作：创建字符串内容的 *副本*，并将指针赋值给规则的语义值 ($$)
                                    // $$ 指向 yylval.sval
        if (!$$) { perror("strdup failed for TOKEN_STRING_LITERAL"); YYABORT; } // 检查 strdup
    }
    ;

optional_path: // 定义一个可选的路径 (用于 ls 命令)
    /* empty: represents no path provided */ // 匹配空的情况 (没有提供路径)
    { $$ = NULL; }                         // 动作：将规则的语义值 ($$) 设置为 NULL
                                           // $$ 指向 yylval.sval
    | path_or_string_literal              // 匹配一个路径或字符串字面量
    { $$ = $1; }                           // 动作：将 path_or_string_literal 的语义值 ($1, 即 strdup 返回的 char*) 直接传递给 $$
    ;

path_list: // 定义一个或多个路径的列表 (用于 rm 命令)
    path_or_string_literal             // 列表的第一个元素
    {
        $$ = create_string_list();         // 动作：创建一个新的 StringList
                                           // $$ 指向 yylval.list_sval
        // $1 是 path_or_string_literal 的语义值 (strdup 返回的 char*)
        add_to_string_list_copy($$, $1);   // 将路径的 *副本* 添加到新列表中
        free($1);                         // 释放 $1 指向的由 strdup 分配的内存，因为 add_to_string_list_copy 已创建了其副本
    }
    | path_list path_or_string_literal  // 匹配一个已有的路径列表，后面跟着一个新的路径
    {
        // $1 是 path_list 的语义值 (StringList*)
        // $2 是 path_or_string_literal 的语义值 (strdup 返回的 char*)
        add_to_string_list_copy($1, $2);   // 将新路径的 *副本* 添加到现有列表中
        free($2);                         // 释放 $2 指向的由 strdup 分配的内存
        $$ = $1;                         // 将修改后的列表 ($1) 赋值给 $$
    }
    ;

// --- 具体命令的语法规则和动作 ---

pwd_command:
    TOKEN_PWD                     // 匹配 PWD 关键字
    {                             // 动作：
        printf("PARSED: PWD\n"); // 打印解析成功的消息 (用于调试)
        semantic_check_pwd();    // 调用语义检查函数
        execute_pwd();           // 调用执行函数
    }
    ;

ls_command:
    TOKEN_LS option_list optional_path // 匹配 LS 关键字，后跟选项列表，再后跟可选路径
    {                                  // 动作：
        // $1: TOKEN_LS (无语义值)
        // $2: option_list 的语义值 (StringList*)
        // $3: optional_path 的语义值 (char* 或 NULL)
        printf("PARSED: LS, Options: %d, Path: %s\n", $2->count, $3 ? $3 : "(none)"); // 打印解析信息
        semantic_check_ls($2, $3);     // 调用语义检查
        execute_ls($2, $3);            // 调用执行
        free_string_list($2);          // 释放选项列表占用的内存 (包括列表本身和里面的字符串)
        if ($3) free($3);              // 如果路径存在 (不是 NULL)，释放路径字符串占用的内存
    }
    ;

cd_command:
    TOKEN_CD path_or_string_literal // 匹配 CD 关键字，后跟一个路径
    {                               // 动作：
        // $1: TOKEN_CD (无语义值)
        // $2: path_or_string_literal 的语义值 (char*)
        printf("PARSED: CD, Path: %s\n", $2); // 打印解析信息
        semantic_check_cd($2);      // 调用语义检查
        execute_cd($2);             // 调用执行
        free($2);                   // 释放路径字符串占用的内存
    }
    ;

chmod_command:
    TOKEN_CHMOD path_or_string_literal path_or_string_literal // 匹配 CHMOD，后跟模式 (mode)，再后跟路径
    {                                                         // 动作：
        // $1: TOKEN_CHMOD (无语义值)
        // $2: 第一个 path_or_string_literal 的值 (mode, char*)
        // $3: 第二个 path_or_string_literal 的值 (path, char*)
        printf("PARSED: CHMOD, Mode: %s, Path: %s\n", $2, $3); // 打印解析信息
        semantic_check_chmod($2, $3);  // 调用语义检查
        execute_chmod($2, $3);         // 调用执行
        free($2);                     // 释放模式字符串内存
        free($3);                     // 释放路径字符串内存
    }
    ;

mkdir_command:
    TOKEN_MKDIR option_list path_or_string_literal // 匹配 MKDIR，后跟选项列表，再后跟路径
    {                                              // 动作：
        // $1: TOKEN_MKDIR (无语义值)
        // $2: option_list 的值 (StringList*)
        // $3: path_or_string_literal 的值 (path, char*)
        printf("PARSED: MKDIR, Options: %d, Path: %s\n", $2->count, $3); // 打印解析信息
        semantic_check_mkdir($2, $3);   // 调用语义检查
        execute_mkdir($2, $3);          // 调用执行
        free_string_list($2);           // 释放选项列表内存
        free($3);                      // 释放路径字符串内存
    }
    ;

rm_command:
    TOKEN_RM option_list path_list // 匹配 RM，后跟选项列表，再后跟路径列表 (一个或多个路径)
    {                               // 动作：
        // $1: TOKEN_RM (无语义值)
        // $2: option_list 的值 (StringList*)
        // $3: path_list 的值 (StringList*)
        printf("PARSED: RM, Options: %d, Paths: %d\n", $2->count, $3->count); // 打印解析信息
        semantic_check_rm($2, $3);    // 调用语义检查
        execute_rm($2, $3);           // 调用执行
        free_string_list($2);         // 释放选项列表内存
        free_string_list($3);         // 释放路径列表内存
    }
    ;

// 为 cp 和 mv 命令定义源文件列表的规则 (语法与 path_list 相同)
source_list_for_cp_mv:
    path_or_string_literal            // 列表的第一个元素
    {
        $$ = create_string_list();        // 创建新列表
        add_to_string_list_copy($$, $1);  // 添加副本
        free($1);                        // 释放原始 strdup 的内存
    }
    | source_list_for_cp_mv path_or_string_literal // 递归：列表加新元素
    {
        add_to_string_list_copy($1, $2);  // 添加副本到现有列表
        free($2);                        // 释放原始 strdup 的内存
        $$ = $1;                        // 传递列表
    }
    ;

cp_command:
    TOKEN_CP option_list source_list_for_cp_mv path_or_string_literal // 匹配 CP，选项，源列表，目标路径
    {                                                                  // 动作：
        // $1: TOKEN_CP
        // $2: option_list (StringList*)
        // $3: source_list_for_cp_mv (StringList*)
        // $4: path_or_string_literal (destination, char*)
        printf("PARSED: CP, Options: %d, Sources: %d, Dest: %s\n", $2->count, $3->count, $4); // 打印
        semantic_check_cp($2, $3, $4); // 检查
        execute_cp($2, $3, $4);        // 执行
        free_string_list($2);          // 释放选项列表
        free_string_list($3);          // 释放源列表
        free($4);                     // 释放目标路径字符串
    }
    ;

mv_command:
    TOKEN_MV option_list source_list_for_cp_mv path_or_string_literal // 匹配 MV，选项，源列表，目标路径
    {                                                                  // 动作：
        // $1: TOKEN_MV
        // $2: option_list (StringList*)
        // $3: source_list_for_cp_mv (StringList*)
        // $4: path_or_string_literal (destination, char*)
        printf("PARSED: MV, Options: %d, Sources: %d, Dest: %s\n", $2->count, $3->count, $4); // 打印
        semantic_check_mv($2, $3, $4); // 检查
        execute_mv($2, $3, $4);        // 执行
        free_string_list($2);          // 释放选项列表
        free_string_list($3);          // 释放源列表
        free($4);                     // 释放目标路径字符串
    }
    ;

read_command:
    TOKEN_READ path_or_string_literal // 匹配 READ，后跟路径
    {                                 // 动作：
        // $1: TOKEN_READ
        // $2: path_or_string_literal (path, char*)
        printf("PARSED: READ, Path: %s\n", $2); // 打印
        semantic_check_read($2);     // 检查
        execute_read($2);            // 执行
        free($2);                   // 释放路径字符串
    }
    ;

write_command:
    TOKEN_WRITE option_list path_or_string_literal path_or_string_literal // 匹配 WRITE，选项，路径，内容
    {                                                                     // 动作：
        // $1: TOKEN_WRITE
        // $2: option_list (StringList*)
        // $3: path_or_string_literal (path, char*)
        // $4: path_or_string_literal (content, char*)
        printf("PARSED: WRITE, Options: %d, Path: %s, Content: %s\n", $2->count, $3, $4); // 打印
        semantic_check_write($2, $3, $4); // 检查
        execute_write($2, $3, $4);        // 执行
        free_string_list($2);             // 释放选项列表
        free($3);                        // 释放路径字符串
        free($4);                        // 释放内容字符串
    }
    ;

source_command:
    TOKEN_SOURCE path_or_string_literal // 匹配 SOURCE，后跟脚本路径
    {                                   // 动作：
        // $1: TOKEN_SOURCE
        // $2: path_or_string_literal (script path, char*)
        printf("PARSED: SOURCE, Path: %s\n", $2); // 打印
        semantic_check_source($2);     // 检查
        execute_source($2);            // 执行 (这个函数会处理打开文件、切换输入、调用 yyparse 等)
        free($2);                   // 释放脚本路径字符串
    }
    ;

help_command:
    TOKEN_HELP                      // 匹配 HELP 关键字
    {                               // 动作：
        printf("PARSED: HELP\n");  // 打印
        semantic_check_help();    // 检查 (可能为空)
        execute_help();           // 执行 (打印帮助信息)
    }
    ;

exit_command:
    TOKEN_EXIT                      // 匹配 EXIT 关键字
    {                               // 动作：
        printf("PARSED: EXIT\n");  // 打印
        semantic_check_exit();    // 检查 (可能为空)
        execute_exit();           // 执行 (退出程序)
        YYACCEPT;                 // 告诉 Bison 解析成功并立即终止解析过程
    }
    ;

%% /* C 代码区段开始 */
// 这部分 C 代码会直接复制到生成的解析器 C 文件 (fss_parser.tab.c) 的末尾。
// 通常包含辅助函数、错误处理函数 (yyerror) 和 main 函数的实现。

// --- StringList 函数实现 (来自 string_list.h 的原型) ---
StringList* create_string_list() { // 创建并初始化一个 StringList
    StringList* list = (StringList*)malloc(sizeof(StringList)); // 分配列表结构内存
    if (!list) { perror("Failed to allocate StringList"); exit(EXIT_FAILURE); } // 错误处理
    list->count = 0;            // 初始化项目数量为 0
    list->capacity = 4;         // 初始化容量为 4
    list->items = (char**)malloc(list->capacity * sizeof(char*)); // 分配存储字符串指针的数组内存
    if (!list->items) { perror("Failed to allocate StringList items"); free(list); exit(EXIT_FAILURE); } // 错误处理
    return list;
}

void add_to_string_list_copy(StringList* list, const char* item_to_copy) { // 向列表中添加字符串的副本
    if (list->count >= list->capacity) { // 如果列表已满
        list->capacity = (list->capacity == 0) ? 4 : list->capacity * 2; // 容量加倍 (或从 0 设为 4)
        char** new_items = (char**)realloc(list->items, list->capacity * sizeof(char*)); // 重新分配更大的内存
        if (!new_items) { perror("Failed to realloc StringList items"); exit(EXIT_FAILURE); } // 错误处理
        list->items = new_items; // 更新指向项目数组的指针
    }
    // 使用 strdup 创建要添加项的副本，并将指针存入列表
    list->items[list->count] = strdup(item_to_copy);
    if (!list->items[list->count]) { perror("Failed to strdup item for StringList"); exit(EXIT_FAILURE); } // 错误处理
    list->count++; // 增加项目计数
}

void free_string_list(StringList* list) { // 释放 StringList 占用的所有内存
    if (!list) return; // 如果列表指针为空，直接返回
    for (int i = 0; i < list->count; i++) {
        free(list->items[i]); // 释放列表中每个字符串副本占用的内存
    }
    free(list->items); // 释放存储字符串指针的数组内存
    free(list);       // 释放列表结构本身的内存
}

// 辅助函数：检查字符串是否以指定后缀结尾
int str_ends_with(const char *str, const char *suffix) {
    if (!str || !suffix) return 0; // 处理 NULL 指针
    size_t str_len = strlen(str);
    size_t suffix_len = strlen(suffix);
    if (suffix_len > str_len) return 0; // 后缀比字符串长，不可能匹配
    // 比较字符串末尾 suffix_len 长度的部分与后缀是否相同
    return strncmp(str + str_len - suffix_len, suffix, suffix_len) == 0;
}
// --- StringList 函数实现结束 ---

// Bison 调用的错误处理函数 yyerror 的实现
void yyerror(const char *s) {
    // s 是 Bison 提供的通用错误消息 (例如 "syntax error")
    fprintf(stderr, "Parser Error near line %d: %s.", yylineno, s);
    // 尝试提供更多上下文：打印词法分析器最后读取的词法单元
    if (yylval_lexeme && strlen(yylval_lexeme) > 0) {
        // 避免重复打印词法分析器已报告的错误消息
        if (strstr(yylval_lexeme, "Unterminated string literal") == NULL &&
            strstr(yylval_lexeme, "Unexpected character") == NULL ) {
            fprintf(stderr, " Last token read was '%s'.", yylval_lexeme);
        }
    }
    fprintf(stderr, "\n"); // 换行
}

// 语义检查和执行函数的存根 (Stub) 实现
// 这些函数目前只打印信息，实际应用中需要实现具体功能
void semantic_check_pwd() { printf("  SEMANTIC: check_pwd\n"); }
void execute_pwd() { printf("  EXECUTE: pwd\n"); /* 需要实际实现 */ }
void semantic_check_ls(StringList* options, const char* path) {
    printf("  SEMANTIC: check_ls (options: %d, path: %s)\n", options ? options->count : 0, path ? path : ".");
}
void execute_ls(StringList* options, const char* path) {
    printf("  EXECUTE: ls (options: %d, path: %s)\n", options ? options->count : 0, path ? path : ".");
}
// ... (其他命令的语义检查和执行存根，都只打印信息) ...
void semantic_check_cd(const char* path) { printf("  SEMANTIC: check_cd (path: %s)\n", path); }
void execute_cd(const char* path) { printf("  EXECUTE: cd (path: %s)\n", path); }
void semantic_check_chmod(const char* mode, const char* path) { printf("  SEMANTIC: check_chmod (mode: %s, path: %s)\n", mode, path); }
void execute_chmod(const char* mode, const char* path) { printf("  EXECUTE: chmod (mode: %s, path: %s)\n", mode, path); }
void semantic_check_mkdir(StringList* options, const char* path) { printf("  SEMANTIC: check_mkdir (options: %d, path: %s)\n", options ? options->count : 0, path); }
void execute_mkdir(StringList* options, const char* path) { printf("  EXECUTE: mkdir (options: %d, path: %s)\n", options ? options->count : 0, path); }
void semantic_check_rm(StringList* options, StringList* paths) { printf("  SEMANTIC: check_rm (options: %d, paths: %d)\n", options ? options->count : 0, paths ? paths->count : 0); }
void execute_rm(StringList* options, StringList* paths) { printf("  EXECUTE: rm (options: %d, paths: %d)\n", options ? options->count : 0, paths ? paths->count : 0); }
void semantic_check_cp(StringList* options, StringList* sources, const char* destination) { printf("  SEMANTIC: check_cp (options: %d, sources: %d, dest: %s)\n", options ? options->count : 0, sources ? sources->count : 0, destination); }
void execute_cp(StringList* options, StringList* sources, const char* destination) { printf("  EXECUTE: cp (options: %d, sources: %d, dest: %s)\n", options ? options->count : 0, sources ? sources->count : 0, destination); }
void semantic_check_mv(StringList* options, StringList* sources, const char* destination) { printf("  SEMANTIC: check_mv (options: %d, sources: %d, dest: %s)\n", options ? options->count : 0, sources ? sources->count : 0, destination); }
void execute_mv(StringList* options, StringList* sources, const char* destination) { printf("  EXECUTE: mv (options: %d, sources: %d, dest: %s)\n", options ? options->count : 0, sources ? sources->count : 0, destination); }
void semantic_check_read(const char* path) { printf("  SEMANTIC: check_read (path: %s)\n", path); }
void execute_read(const char* path) { printf("  EXECUTE: read (path: %s)\n", path); }
void semantic_check_write(StringList* options, const char* path, const char* content) { printf("  SEMANTIC: check_write (options: %d, path: %s, content: '%.30s...')\n", options ? options->count : 0, path, content); }
void execute_write(StringList* options, const char* path, const char* content) { printf("  EXECUTE: write (options: %d, path: %s, content: '%.30s...')\n", options ? options->count : 0, path, content); }

// source 命令的语义检查实现
void semantic_check_source(const char* path) {
    printf("  SEMANTIC: check_source (path: %s)\n", path);
    // 检查文件是否以 .fss 结尾
    if (!str_ends_with(path, ".fss")) {
        fprintf(stderr, "  SEMANTIC ERROR: Source file '%s' does not have .fss extension. Only .fss files are supported.\n", path);
        // 注意：这里只打印错误，并未中止。更好的做法可能是设置一个错误标志。
        return;
    }
    // 尝试打开文件以检查是否存在和可读
    FILE* test_open = fopen(path, "r");
    if (!test_open) {
        fprintf(stderr, "  SEMANTIC ERROR: Unable to open source file '%s': %s\n", path, strerror(errno));
        return;
    }
    fclose(test_open); // 检查完毕，关闭文件
}

// source 命令的执行实现
void execute_source(const char* path) {
    printf("  EXECUTE: source (path: %s)\n", path);
    // 再次检查后缀 (也可以只在语义检查中做)
    if (!str_ends_with(path, ".fss")) {
        fprintf(stderr, "  EXECUTE ERROR: Cannot source '%s'. Only .fss files are supported.\n", path);
        return;
    }
    // 打开脚本文件
    FILE* script_file = fopen(path, "r");
    if (!script_file) {
        fprintf(stderr, "  EXECUTE ERROR: Failed to open source file '%s': %s\n", path, strerror(errno));
        return;
    }
    printf("  EXECUTE: Sourcing commands from '%s'\n", path);

    // 保存当前的词法分析器输入状态
    FILE* original_yyin = yyin;
    int original_lineno = yylineno;

    // 将词法分析器的输入切换到脚本文件
    yyin = script_file;
    yylineno = 1; // 重置行号为 1

    // 考虑使用 yyrestart(yyin) 如果你的 Flex 版本支持并且需要更干净的状态重置
    // 对于简单情况，直接赋值可能也可以。

    printf("  EXECUTE: Beginning parsing of script '%s'\n", path);
    // *** 核心：递归调用解析器来解析脚本文件 ***
    int result = yyparse();

    // 报告脚本解析结果
    if (result == 0) {
        printf("  EXECUTE: Successfully executed script '%s'\n", path);
    } else {
        fprintf(stderr, "  EXECUTE ERROR: Error parsing script '%s'\n", path);
    }

    // 关闭脚本文件
    fclose(script_file);

    // 恢复词法分析器的原始输入状态
    yyin = original_yyin;
    yylineno = original_lineno;

    // 这里也可以考虑 yyrestart(yyin)。
    printf("  EXECUTE: Returned to main input\n");
}

void semantic_check_help() { printf("  SEMANTIC: check_help\n"); }
// help 命令的执行实现
void execute_help() {
    printf("  EXECUTE: help\n");
    printf("Available commands:\n");
    // 打印所有可用命令及其简要说明
    printf("  pwd                            : Print working directory\n");
    printf("  ls [options] [path]            : List directory contents\n");
    printf("  cd <path>                      : Change directory\n");
    printf("  chmod <mode> <path>            : Change file permissions\n");
    printf("  mkdir [options] <path>         : Create directory\n");
    printf("  rm [options] <paths>...        : Remove files or directories\n");
    printf("  cp [options] <src>... <dest>   : Copy files or directories\n");
    printf("  mv [options] <src>... <dest>   : Move files or directories\n");
    printf("  read <path>                    : Display file contents\n");
    printf("  write [options] <path> <content> : Write content to file\n");
    printf("  source <path_to_fss_script>  : Execute commands from a .fss script file\n");
    printf("  help                           : Display this help message\n");
    printf("  exit                           : Exit the shell\n");
}
void semantic_check_exit() { printf("  SEMANTIC: check_exit\n"); }
// exit 命令的执行实现
void execute_exit() {
    printf("  EXECUTE: exit\n");
    printf("Exiting FSS shell. Goodbye!\n");
    // 在退出前，释放可能由词法分析器最后一次匹配遗留的全局 lexeme 内存
    if (yylval_lexeme) {
        free(yylval_lexeme);
        yylval_lexeme = NULL;
    }
    exit(EXIT_SUCCESS); // 正常退出程序
}

// 程序的主入口点 main 函数
int main(int argc, char *argv[]) {
    // 处理命令行参数
    if (argc > 1) { // 如果提供了命令行参数
        if (strcmp(argv[1], "-") == 0) { // 如果参数是 "-"
            yyin = stdin; // 从标准输入读取
            // 检查标准输入是否连接到终端，如果是，打印提示符
            if (isatty(fileno(stdin))) {
                printf("FSS Shell - Type 'help' for available commands or 'exit' to quit.\n");
            }
        } else { // 如果参数是文件名
            yyin = fopen(argv[1], "r"); // 尝试以只读方式打开文件
            if (!yyin) { // 如果打开失败
                fprintf(stderr, "Error: Cannot open input file '%s': %s\n", argv[1], strerror(errno));
                return EXIT_FAILURE; // 返回错误退出码
            }
            // 文件打开成功，yyin 指向该文件
        }
    } else { // 如果没有提供命令行参数
        yyin = stdin; // 默认从标准输入读取
        // 检查标准输入是否连接到终端，如果是，打印提示符
        if (isatty(fileno(stdin))) {
            printf("FSS Shell - Type 'help' for available commands or 'exit' to quit.\n");
        }
    }

    // *** 启动 Bison 解析器 ***
    // yyparse() 会不断调用 yylex() 获取词法单元，并根据语法规则进行匹配和执行动作。
    // 它会一直运行直到遇到文件结束、调用 YYACCEPT (在 exit_command 中) 或发生无法恢复的语法错误。
    int result = yyparse(); // result 为 0 表示成功，非 0 表示失败。

    // 如果输入源是打开的文件 (不是 stdin)，则关闭它
    if (yyin != stdin && yyin != NULL) {
        fclose(yyin);
    }

    // 在程序结束前，再次尝试释放可能残留的全局 lexeme 内存 (作为最后的保障)
    if (yylval_lexeme) {
        free(yylval_lexeme);
        yylval_lexeme = NULL;
    }

    // 返回解析结果 (0 表示成功，其他值表示错误)
    return result == 0 ? EXIT_SUCCESS : EXIT_FAILURE;
}