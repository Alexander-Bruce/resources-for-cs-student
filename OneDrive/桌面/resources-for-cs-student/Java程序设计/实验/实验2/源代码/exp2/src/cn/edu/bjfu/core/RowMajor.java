package cn.edu.bjfu.core;

import java.util.ArrayList;

/*
 * 本类实现行主序的方阵遍历。
 */
public class RowMajor extends SquareTraversal{
    private int initRow = 0, initCol = 0;
    public RowMajor(int size) {
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

    @Override
    public SquarePoint getNext() {
        // 在这里添加代码：
        int currRow, currCol;
        if (currentPath.isEmpty()) {
            currRow = 0;
            currCol = 0;
        } else {
            int lastRow = currentPath.get(currentPath.size() - 1).getRow();
            int lastCol = currentPath.get(currentPath.size() - 1).getCol();
            if (lastCol + 1 < getSize()) {
                currRow = lastRow;
                currCol = lastCol + 1;
            } else {
                if (lastRow + 1 < getSize()) {
                    currRow = lastRow + 1;
                    currCol = 0;
                } else {
                    return null;
                }
            }
        }
        return new SquarePoint(currRow, currCol);
    }
}
