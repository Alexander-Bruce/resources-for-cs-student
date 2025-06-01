import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 状态类，表示棋盘的状态
public class State implements Comparable<State> {
    final int[] board;  // 当前状态的棋盘
    final int size = 3;  // 棋盘的大小 (3x3)
    final int g;  // 从起始状态到当前状态的实际代价
    final int h;  // 启发式代价 (当前状态到目标状态的估计距离)
    final State parent;  // 当前状态的父节点 (即上一状态)
    final int zeroPos;  // 空格 (0) 在棋盘中的位置
    private final int[] goalState;  // 目标状态

    // 构造函数，初始化一个状态
    public State(int[] initialBoard, int[] goalState) {
        this.board = Arrays.copyOf(initialBoard, initialBoard.length);  // 复制初始棋盘
        this.g = 0;  // 起始状态到当前状态的代价为 0
        this.parent = null;  // 起始状态没有父节点
        this.goalState = goalState;  // 设置目标状态
        this.zeroPos = findZeroPos();  // 找到空格的位置
        this.h = calculateHeuristic();  // 计算启发式代价 (不在位数)
    }

    // 构造函数，用于创建当前状态的子状态
    private State(int[] board, int g, State parent, int[] goalState) {
        this.board = board;  // 子状态的棋盘
        this.g = g;  // 从起始状态到当前状态的代价
        this.parent = parent;  // 当前状态的父节点
        this.goalState = goalState;  // 目标状态
        this.zeroPos = findZeroPos();  // 找到空格的位置
        this.h = calculateHeuristic();  // 计算启发式代价 (不在位数)
    }

    // 查找棋盘上空格 (0) 的位置
    private int findZeroPos() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                return i;  // 返回空格的位置
            }
        }
        return -1;  // 如果没有找到，返回 -1
    }

    // 计算启发式代价 (不在位数)
    private int calculateHeuristic() {
        int misplaced = 0;  // 错位的方块数
        for (int i = 0; i < board.length; i++) {
            if (board[i] != 0 && board[i] != goalState[i]) {
                misplaced++;  // 如果当前方块和目标状态中的方块不一致，则计数
            }
        }
        return misplaced;  // 返回错位方块的数量
    }

    // 计算 f(n) = g(n) + h(n)
    public int getF() {
        return g + h;
    }

    // 获取 g(n)
    public int getG() {
        return g;
    }

    // 获取 h(n)
    public int getH() {
        return h;
    }

    // 获取父节点
    public State getParent() {
        return parent;
    }

    // 获取当前状态的棋盘
    public int[] getBoard() {
        return board;
    }

    // 判断当前状态是否为目标状态
    public boolean isGoal() {
        return Arrays.equals(this.board, this.goalState);
    }

    // 获取当前状态的邻居状态
    public List<State> getNeighbors() {
        List<State> neighbors = new ArrayList<>();
        int zeroRow = zeroPos / size;  // 空格的行数
        int zeroCol = zeroPos % size;  // 空格的列数

        int[] dRow = {-1, 1, 0, 0};  // 上下左右的行位移
        int[] dCol = {0, 0, -1, 1};  // 上下左右的列位移

        // 遍历上、下、左、右四个方向，生成邻居状态
        for (int i = 0; i < 4; i++) {
            int newRow = zeroRow + dRow[i];
            int newCol = zeroCol + dCol[i];

            // 如果新位置在棋盘内，交换空格并生成新状态
            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                int newZeroPos = newRow * size + newCol;
                int[] newBoard = Arrays.copyOf(this.board, this.board.length);  // 复制当前棋盘

                newBoard[zeroPos] = newBoard[newZeroPos];  // 交换空格与新位置的方块
                newBoard[newZeroPos] = 0;

                neighbors.add(new State(newBoard, this.g + 1, this, this.goalState));  // 将新状态加入邻居列表
            }
        }
        return neighbors;
    }

    // 重写 equals 方法，判断两个状态是否相同
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Arrays.equals(board, state.board);
    }

    // 重写 hashCode 方法
    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    // 实现 Comparable 接口，按 f(n) 排序
    @Override
    public int compareTo(State other) {
        return Integer.compare(this.getF(), other.getF());
    }

    // 输出当前状态的字符串表示
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            sb.append(board[i] == 0 ? " " : board[i]).append(" ");
            if ((i + 1) % size == 0) {
                sb.append("\n");
            }
        }
        sb.append("g=").append(g).append(", h=").append(h).append(", f=").append(getF());
        return sb.toString();
    }
}
