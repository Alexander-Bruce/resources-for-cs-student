import java.util.*;

// 八数码问题求解器类，包含了 A 算法和宽度优先搜索算法 (BFS) 的实现
public class PuzzleSolver {

    private final int[] goalState;  // 目标状态
    private final Scanner scanner = new Scanner(System.in);  // 用于读取用户输入的扫描器

    // 构造函数，初始化目标状态
    public PuzzleSolver(int[] goalState) {
        this.goalState = goalState;
    }

    // 使用 A 算法求解八数码问题
    public Solution solveAStar(int[] initialState, boolean singleStep) {

        // 优先队列 (Open Set)，用于存储待处理的状态，按 f(n) 排序
        PriorityQueue<State> openSet = new PriorityQueue<>();

        // 已扩展的状态集合 (Closed Set)，避免重复扩展
        Set<State> closedSet = new HashSet<>();

        // 创建起始状态
        State startState = new State(initialState, goalState);
        openSet.add(startState);  // 将起始状态加入 Open Set

        int stepCounter = 0;  // 步数计数器

        System.out.println("\n--- 开始 A 搜索 ---");
        System.out.println("初始状态加入 Open Set:");
        System.out.println(startState);
        System.out.println("Open Set 大小: 1, Closed Set 大小: 0");

        // 如果是单步演示，等待用户输入
        if (singleStep) System.out.println("按 Enter 键开始搜索...");
        if (singleStep) scanner.nextLine();

        // A* 搜索过程
        while (!openSet.isEmpty()) {
            stepCounter++;
            State current = openSet.poll();  // 取出 f(n) 最小的状态

            // 打印当前步骤信息 (单步演示)
            if (singleStep) {
                System.out.println("\n==================== 步骤 " + stepCounter + " ====================");
                System.out.println("从 Open Set 中选择 f(n) 最小的状态进行扩展:");
                System.out.println(current);
                System.out.println("当前 Open Set 大小 (取出后): " + openSet.size());
                System.out.println("当前 Closed Set 大小: " + closedSet.size());
                System.out.println("--------------------------------------------------");
            } else if (stepCounter % 500 == 0) {
                System.out.println("搜索中... 步骤: " + stepCounter + ", Open Set 大小: " + openSet.size() + ", Closed Set 大小: " + closedSet.size() + ", 当前 f=" + current.getF());
            }

            // 如果当前状态是目标状态，返回结果
            if (current.isGoal()) {
                System.out.println("\n--- 找到解! ---");
                System.out.println("在第 " + stepCounter + " 步找到目标状态。");
                System.out.println("总共扩展节点数 (步骤数): " + stepCounter);
                System.out.println("最终 Open Set 大小: " + openSet.size());
                System.out.println("最终 Closed Set 大小: " + closedSet.size());
                List<State> path = reconstructPath(current);  // 还原路径
                return new Solution(path, stepCounter);  // 返回解
            }

            // 将当前状态加入 Closed Set
            closedSet.add(current);
            if (singleStep) {
                System.out.println("将当前状态加入 Closed Set.");
                System.out.println("Closed Set 大小变为: " + closedSet.size());
                System.out.println("生成邻居节点:");
            }

            // 获取当前状态的邻居状态
            List<State> neighbors = current.getNeighbors();
            if (singleStep && neighbors.isEmpty()) System.out.println(" - (无有效邻居生成)");

            // 扩展邻居状态
            for (State neighbor : neighbors) {
                // 如果邻居已经在 Closed Set 中，跳过
                if (closedSet.contains(neighbor)) {
                    if (singleStep) {
                        System.out.println("\n - 邻居: (已在 Closed Set 中，忽略)");
                        System.out.print(neighbor.toString().indent(4));
                    }
                    continue;
                }

                // 如果邻居不在 Open Set 中，将其加入 Open Set
                boolean inOpen = openSet.contains(neighbor);
                if (!inOpen) {
                    openSet.add(neighbor);
                    if (singleStep) {
                        System.out.println("\n - 邻居: (不在 Open Set 或 Closed Set 中，加入 Open Set)");
                        System.out.print(neighbor.toString().indent(4));
                    }
                } else {
                    // 如果邻居已经在 Open Set 中，跳过
                    if (singleStep) {
                        System.out.println("\n - 邻居: (已在 Open Set 中，跳过添加/更新)");
                        System.out.print(neighbor.toString().indent(4));
                    }
                }
            }

            // 单步演示模式下，等待用户输入
            if (singleStep) {
                System.out.println("\n--------------------------------------------------");
                System.out.println("步骤 " + stepCounter + " 结束.");
                System.out.println("当前 Open Set 大小: " + openSet.size());
                System.out.println("当前 Closed Set 大小: " + closedSet.size());
                if (!openSet.isEmpty()) {
                    System.out.println("下一轮将从 Open Set 取出 f(n) 最小的状态 (f=" + openSet.peek().getF() + ")");
                    System.out.println("按 Enter 键进行下一步...");
                    scanner.nextLine();
                } else {
                    System.out.println("Open Set 为空，搜索结束。");
                }
            }
        }

        // 如果搜索结束仍未找到解
        System.out.println("\n--- 未找到解 ---");
        System.out.println("搜索在 " + stepCounter + " 步后停止 (Open Set 为空)。");
        return null;
    }

    // 使用 BFS 搜索算法求解八数码问题
    public Solution solveBFS(int[] initialState, boolean singleStep) {

        // 队列 (Queue)，用于存储待处理的状态
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();  // 用于存储已访问的状态

        // 创建起始状态
        State startState = new State(initialState, goalState);
        queue.add(startState);  // 将起始状态加入队列
        visited.add(startState);  // 将起始状态加入已访问集合

        int stepCounter = 0;  // 步数计数器

        System.out.println("\n--- 开始 BFS 搜索 ---");
        System.out.println("初始状态加入队列和 Visited Set:");
        System.out.println(startState);
        System.out.println("队列大小: 1, Visited Set 大小: 1");

        // 如果是单步演示，等待用户输入
        if (singleStep) System.out.println("按 Enter 键开始搜索...");
        if (singleStep) scanner.nextLine();

        // BFS 搜索过程
        while (!queue.isEmpty()) {
            stepCounter++;
            State current = queue.poll();  // 从队列中取出队首状态

            // 打印当前步骤信息 (单步演示)
            if (singleStep) {
                System.out.println("\n==================== 步骤 " + stepCounter + " ====================");
                System.out.println("从队列中取出队首状态进行扩展:");
                System.out.println(current);
                System.out.println("当前队列大小 (取出后): " + queue.size());
                System.out.println("当前 Visited Set 大小: " + visited.size());
                System.out.println("--------------------------------------------------");
            } else if (stepCounter % 2000 == 0) {
                System.out.println("搜索中... 步骤: " + stepCounter + ", 队列大小: " + queue.size() + ", Visited Set 大小: " + visited.size() + ", 当前深度 g=" + current.getG());
            }

            // 如果当前状态是目标状态，返回结果
            if (current.isGoal()) {
                System.out.println("\n--- 找到解! ---");
                System.out.println("在第 " + stepCounter + " 步找到目标状态。");
                System.out.println("总共出队/扩展节点数 (步骤数): " + stepCounter);
                System.out.println("最终队列大小: " + queue.size());
                System.out.println("最终 Visited Set 大小: " + visited.size());
                List<State> path = reconstructPath(current);  // 还原路径
                return new Solution(path, stepCounter);  // 返回解
            }

            if (singleStep) {
                System.out.println("生成邻居节点:");
            }

            // 获取当前状态的邻居状态
            List<State> neighbors = current.getNeighbors();
            if (singleStep && neighbors.isEmpty()) System.out.println(" - (无有效邻居生成)");

            // 扩展邻居状态
            for (State neighbor : neighbors) {
                // 如果邻居尚未访问过，加入队列和已访问集合
                if (visited.add(neighbor)) {
                    queue.add(neighbor);
                    if (singleStep) {
                        System.out.println("\n - 邻居: (未访问过，加入队列和 Visited Set)");
                        System.out.print(neighbor.toString().indent(4));
                    }
                } else {
                    // 如果邻居已访问过，跳过
                    if (singleStep) {
                        System.out.println("\n - 邻居: (已在 Visited Set 中，忽略)");
                        System.out.print(neighbor.toString().indent(4));
                    }
                }
            }

            // 单步演示模式下，等待用户输入
            if (singleStep) {
                System.out.println("\n--------------------------------------------------");
                System.out.println("步骤 " + stepCounter + " 结束.");
                System.out.println("当前队列大小: " + queue.size());
                System.out.println("当前 Visited Set 大小: " + visited.size());
                if (!queue.isEmpty()) {
                    System.out.println("下一轮将从队列取出状态 (g=" + queue.peek().getG() + ")");
                    System.out.println("按 Enter 键进行下一步...");
                    scanner.nextLine();
                } else {
                    System.out.println("队列为空，搜索结束。");
                }
            }
        }

        // 如果搜索结束仍未找到解
        System.out.println("\n--- 未找到解 ---");
        System.out.println("搜索在 " + stepCounter + " 步后停止 (队列为空)。");
        return null;
    }

    // 还原从起始状态到目标状态的路径
    private List<State> reconstructPath(State goalState) {
        LinkedList<State> path = new LinkedList<>();
        State current = goalState;
        while (current != null) {
            path.addFirst(current);  // 将当前状态加入路径
            current = current.getParent();  // 回溯父节点
        }
        return path;  // 返回路径
    }

    // 判断初始状态是否可解
    public static boolean isSolvable(int[] board) {
        int inversions = 0;  // 计算逆序对数
        for (int i = 0; i < board.length - 1; i++) {
            for (int j = i + 1; j < board.length; j++) {
                // 计算逆序对
                if (board[i] != 0 && board[j] != 0 && board[i] > board[j]) {
                    inversions++;
                }
            }
        }
        return inversions % 2 == 0;  // 如果逆序对数为偶数，表示状态可解
    }

    // 解的类，包含路径和扩展的节点数
    public static class Solution {
        public final List<State> path;  // 解的路径
        public final int nodesExpanded;  // 扩展的节点数

        public Solution(List<State> path, int nodesExpanded) {
            this.path = path;
            this.nodesExpanded = nodesExpanded;
        }
    }
}
