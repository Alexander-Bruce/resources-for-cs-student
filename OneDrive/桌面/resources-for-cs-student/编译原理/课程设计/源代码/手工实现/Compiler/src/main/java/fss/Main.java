package fss;

import fss.common.Token;
import fss.common.TokenType;
import fss.exception.SemanticErrorException;
import fss.exception.SyntaxErrorException;
import fss.executor.CommandExecutor;
import fss.lexer.Lexer;
import fss.parser.Parser;

import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

/**
 * 文件系统脚本主程序入口类
 */
public class Main {

    public static void main(String[] args) {
        // 初始化命令执行器
        CommandExecutor executor = new CommandExecutor();

        // 使用try-with-resources确保Scanner资源自动释放
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                // 显示模式选择菜单
                System.out.println("\nChoose execution mode:");
                System.out.println("  1: Interactive Mode");
                System.out.println("  2: Execute Script File");
                System.out.print("Enter your choice (1 or 2): ");

                String choice = scanner.nextLine().trim();

                if ("1".equals(choice)) {
                    // 进入交互模式
                    runInteractive(executor);
                    break;
                } else if ("2".equals(choice)) {
                    // 进入脚本文件执行模式
                    System.out.print("Enter the path to the script file: ");
                    String scriptPath = scanner.nextLine().trim();

                    if (scriptPath.isEmpty()) {
                        System.err.println("Script path cannot be empty.");
                        continue;
                    }

                    runScriptFile(scriptPath, executor);
                    break;
                } else {
                    System.err.println("Invalid choice. Please enter 1 or 2.");
                }
            }
        }

        System.out.println("\n程序执行结束");
    }

    /**
     * 执行脚本文件
     * @param filePath 脚本文件路径
     * @param executor 命令执行器
     */
    private static void runScriptFile(String filePath, CommandExecutor executor) {
        System.out.println("\n--- Loading script file: " + filePath + " ---");
        try {
            // 读取脚本文件所有行
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            // 执行脚本内容
            executor.executeScriptContent(lines, filePath, 1); // 从第1行开始执行

        } catch (NoSuchFileException e) {
            System.err.println("Error: Script file not found '" + filePath + "'");
        } catch (IOException e) {
            System.err.println("Error reading or accessing script file '" + filePath + "': " + e.getMessage());
        } catch (SecurityException e) {
            System.err.println("Error: Permission denied while accessing script file '" + filePath + "': " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred during script execution: " + e.getMessage());
        } finally {
            System.out.println("--- Main script execution finished ---");
        }
    }

    /**
     * 运行交互式解释器
     * @param executor 命令执行器
     */
    private static void runInteractive(CommandExecutor executor) {

        try (Scanner interactiveScanner = new Scanner(System.in)) {
            System.out.println("\n--- File System Script Interpreter (Interactive) ---");
            System.out.println("Type 'HELP' for commands, 'EXIT' to quit)");

            int lineNumber = 0; // 记录当前行号用于错误提示

            while (!executor.shouldExit()) {
                lineNumber++;
                // 显示提示符，包含当前目录和行号
                String prompt = "[" + executor.getCurrentDirectoryName() + ":" + lineNumber + "] > ";
                System.out.print(prompt);

                String line = interactiveScanner.nextLine();
                if (line == null) break;

                String trimmedLine = line.trim();
                // 跳过空行和注释
                if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) continue;

                // 词法分析
                Lexer lexer = new Lexer(trimmedLine);
                List<Token> tokens = lexer.tokenize();

                // 检查词法分析错误
                boolean lexerError = false;
                for(Token t : tokens) {
                    if (t.type == TokenType.ERROR) {
                        System.err.println("Lexer Error: " + t.value);
                        lexerError = true;
                        break;
                    }
                }
                if(lexerError) continue;

                // 语法分析并执行
                try{
                    Parser parser = new Parser(tokens, executor, lineNumber);
                    parser.parse();
                } catch (SyntaxErrorException | SemanticErrorException e) {
                    continue;
                }

                // 检查退出标志
                if (executor.shouldExit()) {
                    break;
                }
            }
        }
    }
}