package cn.edu.bjfu.core;

/*
 * 本类表示方阵中的一个点。一个点由行和列两个坐标表示。
 * 最上方的行号最小，为0；最左边的列号最小，为0。
 */
public class SquarePoint {
    private int row = 0;
    private int col = 0;
    public SquarePoint() {}
    public SquarePoint(int row, int col) {
        // 在这里添加你的代码
        this.row = row;
        this.col = col;
    }
    public void setRow(int row) {
        // 在这里添加你的代码
        this.row = row;
    }
    public void setCol(int col) {
        // 在这里添加你的代码
        this.col = col;
    }
    public int getRow() {
        // 在这里添加你的代码
        return this.row;
    }
    public int getCol() {
        // 在这里添加你的代码
        return this.col;
    }

    @Override
    public String toString() {
        return "SquarePoint{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}