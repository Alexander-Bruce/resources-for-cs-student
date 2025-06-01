import java.util.InputMismatchException;
import java.util.Scanner;

// 主类，程序入口，处理用户输入并调用相应的求解方法
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // 创建扫描器，用于读取用户输入

        // 定义一个默认目标状态
        int[] GOAL_STATE = {1, 2, 3, 4, 5, 6, 7, 8, 0};

        // 获取初始状态
        int[] initialState = getState(scanner, 1);  // 获取初始状态，1表示为初始状态
        if (initialState == null) {
            scanner.close(); // 退出前关闭scanner
            return;  // 如果输入的初始状态无效，则退出程序
        }

        // 获取目标状态 (用户的输入将覆盖上面定义的 GOAL_STATE)
        GOAL_STATE = getState(scanner, 2);  // 获取目标状态，2表示为目标状态
        // 检查目标状态输入是否有效
        if (GOAL_STATE == null) {
            scanner.close(); // 退出前关闭scanner
            return; // 如果输入的目标状态无效，则退出程序
        }

        // 打印初始状态和目标状态
        System.out.println("初始状态:");
        printBoard(initialState);
        System.out.println("目标状态:");
        printBoard(GOAL_STATE); // 此时 GOAL_STATE 存储的是用户输入的目标

        // --- 核心修改：检查初始状态和用户定义的目标状态的可解性 (逆序数奇偶性) ---
        // isSolvable 方法判断的是逆序数是否为偶数
        boolean initialHasEvenInversions = PuzzleSolver.isSolvable(initialState);
        boolean targetHasEvenInversions = PuzzleSolver.isSolvable(GOAL_STATE); // 使用用户输入的目标状态

        // 两个状态互相可达的条件是它们的逆序数奇偶性相同
        if (initialHasEvenInversions != targetHasEvenInversions) {
            System.out.println("\n错误：初始状态和目标状态的逆序数奇偶性不同，无法互相到达！");
            scanner.close();
            return;  // 如果奇偶性不同，则退出程序
        } else {
            // System.out.println("\n初始状态和目标状态的逆序数奇偶性相同，理论上可达。"); // 可以选择性地打印更详细信息
            System.out.println("\n此初始状态理论上可达目标状态。"); // 保持和原来类似的肯定性提示
        }
        // --- 修改结束 ---


        int choice = 0;  // 用户选择的搜索算法
        // 提供用户选择算法的界面
        while (choice != 1 && choice != 2) {
            System.out.println("\n请选择搜索算法:");
            System.out.println("1: A 算法 (启发函数: 不在位数)");
            System.out.println("2: 宽度优先搜索 (BFS)");
            System.out.print("输入选择 (1 或 2): ");
            try {
                choice = scanner.nextInt();  // 读取用户输入的选择
                if (choice != 1 && choice != 2) {
                    System.out.println("无效选择，请输入 1 或 2。");
                }
            } catch (InputMismatchException e) {
                System.out.println("无效输入，请输入数字 1 或 2。");
                scanner.next();  // 清除错误输入
            }
        }

        int mode = 0;  // 用户选择的演示模式
        // 提供用户选择演示模式的界面
        while (mode != 1 && mode != 2) {
            System.out.println("\n请选择演示模式:");
            System.out.println("1: 连续演示");
            System.out.println("2: 单步演示");
            System.out.print("输入选择 (1 或 2): ");
            try {
                mode = scanner.nextInt();  // 读取用户输入的选择
                if (mode != 1 && mode != 2) {
                    System.out.println("无效选择，请输入 1 或 2。");
                }
            } catch (InputMismatchException e) {
                System.out.println("无效输入，请输入数字 1 或 2。");
                scanner.next();  // 清除错误输入
            }
        }
        boolean singleStep = (mode == 2);  // 判断是否为单步演示模式
        scanner.nextLine();  // 清除换行符

        // 创建 PuzzleSolver 实例时，传入用户定义的目标状态 GOAL_STATE
        PuzzleSolver solver = new PuzzleSolver(GOAL_STATE);
        PuzzleSolver.Solution solution = null;  // 存储求解结果
        long startTime = System.currentTimeMillis();  // 记录开始时间

        // 根据用户选择的算法执行搜索
        if (choice == 1) {
            solution = solver.solveAStar(initialState, singleStep);  // 调用 A* 算法
        } else {
            solution = solver.solveBFS(initialState, singleStep);  // 调用 BFS 算法
        }

        long endTime = System.currentTimeMillis();  // 记录结束时间

        // --- 显示结果 ---
        if (solution != null) {
            System.out.println("\n--- 搜索完成 ---");
            System.out.println("搜索算法: " + (choice == 1 ? "A*" : "BFS")); // A* 显示更规范
            System.out.println("执行时间: " + (endTime - startTime) + " ms");
            System.out.println("找到的路径长度 (步数): " + (solution.path.size() - 1));  // 路径长度等于步骤数，包含起始状态
            System.out.println("扩展/出队节点总数: " + solution.nodesExpanded);

            // 打印解决方案路径
            System.out.println("\n解决方案路径:");
            for (int i = 0; i < solution.path.size(); i++) {
                System.out.println("\n步骤 " + i + ":");
                printBoard(solution.path.get(i).getBoard());  // 打印每一步的棋盘状态
            }
        } else {
            // 如果执行到这里，意味着虽然奇偶性检查通过，但搜索算法未能找到解
            // （对于理论可解的问题，这通常是算法实现问题或资源限制）
            System.out.println("\n尽管状态理论上可达，但搜索未能找到解决方案。");
            System.out.println("执行时间: " + (endTime - startTime) + " ms");
        }

        scanner.close();  // 关闭扫描器
    }

    // 获取用户输入的棋盘状态，返回一个包含 9 个数字的数组
    private static int[] getState(Scanner scanner, Integer Status) {
        int[] board = new int[9];  // 用于存储棋盘状态
        boolean inputValid = false;  // 标志输入是否有效

        while (!inputValid) {
            System.out.println("\n请输入八数码的" + (Status == 1 ? "初始" : "目标") + "状态 (9个数字, 用空格或逗号分隔, 0代表空格):");
            System.out.println("例如: 2 8 3 1 6 4 7 0 5");
            System.out.print("输入: ");
            String line = scanner.nextLine().trim();  // 读取用户输入并去掉首尾空格
            String[] parts = line.split("[\\s,]+");  // 使用空格或逗号分隔输入的数字

            // 检查输入是否包含 9 个数字
            if (parts.length != 9) {
                System.out.println("错误：需要输入 9 个数字。您输入了 " + parts.length + " 个。");
                continue;
            }

            try {
                boolean[] seen = new boolean[9];  // 用于检查数字是否重复
                boolean zeroFound = false;  // 标志是否包含数字 0
                boolean formatError = false;  // 标志是否格式错误

                // 检查输入的每个数字是否有效
                for (int i = 0; i < 9; i++) {
                    int num = Integer.parseInt(parts[i]);  // 将字符串转换为整数
                    if (num < 0 || num > 8) {  // 检查数字是否在 0 到 8 之间
                        System.out.println("错误：数字必须在 0 到 8 之间。发现无效数字: " + num);
                        formatError = true;
                        break;
                    }
                    if (seen[num]) {  // 检查是否有重复的数字
                        System.out.println("错误：数字不能重复。发现重复数字: " + num);
                        formatError = true;
                        break;
                    }
                    seen[num] = true;
                    if (num == 0) {  // 如果输入中有 0，表示空格
                        zeroFound = true;
                    }
                    board[i] = num;
                }

                if (formatError) continue;  // 如果输入格式错误，则继续要求输入

                if (!zeroFound) {  // 如果输入中没有 0，表示输入无效
                    System.out.println("错误：输入中必须包含数字 0 (代表空格)。");
                    continue;
                }

                inputValid = true;  // 输入有效，跳出循环

            } catch (NumberFormatException e) {
                System.out.println("错误：输入包含非数字字符。请只输入数字和分隔符。");
            }
        }
        return board;  // 返回合法的棋盘状态
    }

    // 打印棋盘状态，格式化输出
    public static void printBoard(int[] board) {
        if (board == null) {
            System.out.println("无效的棋盘状态。");
            return;
        }
        int size = (int) Math.sqrt(board.length);  // 计算棋盘的大小 (3x3)
        if (size * size != board.length) {
            System.out.println("棋盘大小无效。");
            return;
        }
        System.out.println("---------");
        // 打印每一行
        for (int i = 0; i < board.length; i++) {
            System.out.print("|" + (board[i] == 0 ? " " : board[i]) + " ");
            if ((i + 1) % size == 0) {  // 每行结束时换行
                System.out.println("|");
                System.out.println("---------");
            }
        }
    }
}
