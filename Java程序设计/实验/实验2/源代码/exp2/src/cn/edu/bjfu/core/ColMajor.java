package cn.edu.bjfu.core;

import java.util.ArrayList;

/*
 * 本类实现列主序的方阵遍历方式。
 */
public class ColMajor extends SquareTraversal{
    private int initRow = 0, initCol = 0;
    public ColMajor(int size) {
        // 在这里添加代码：
        super.initiate(size);
    }

    @Override
    public SquarePoint getInitPoint() {
        // 在这里添加代码：
        this.initRow = 0;
        this.initCol = 0;
        this.currentPath = new ArrayList<>();
        currentPath.add(new SquarePoint(initRow, initCol));
        return new SquarePoint(initRow, initCol);
    }

    @Override public SquarePoint getNext() {
        int currRow, currCol;
        if (currentPath.isEmpty()) {
            currRow = 0;
            currCol = 0;
        } else {
            int lastRow = currentPath.get(currentPath.size() - 1).getRow();
            int lastCol = currentPath.get(currentPath.size() - 1).getCol();
            if (lastRow + 1 < getSize()) {
                currCol = lastCol;
                currRow = lastRow + 1;
            } else {
                if (lastCol + 1 < getSize()) {
                    currCol = lastCol + 1;
                    currRow = 0;
                } else {
                    // 已经遍历完所有点，返回 null 表示遍历结束
                    return null;
                }
            }
        }
        return new SquarePoint(currRow, currCol);
    }
}
