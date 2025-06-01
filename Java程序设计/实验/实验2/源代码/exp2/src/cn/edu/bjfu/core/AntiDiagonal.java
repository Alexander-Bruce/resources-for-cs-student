package cn.edu.bjfu.core;

import java.util.ArrayList;

/*
 * 本类实现反对角方式的方阵遍历：
 */
public class AntiDiagonal extends SquareTraversal{

    private int initRow = 0, initCol = 0, flag = 0;
    private int currStartRow, curStartCol;
    public AntiDiagonal(int size) {
        // 在这里添加代码：
        super.initiate(size);
    }

    @Override
    public SquarePoint getInitPoint() {
        // 在这里添加代码：
        this.initRow = 0;
        this.initCol = 0;
        this.currStartRow = initRow;
        this.curStartCol = initCol;
        this.currentPath = new ArrayList<>();
        currentPath.add(new SquarePoint(initRow, initCol));
        return new SquarePoint(initRow, initCol);
    }

    @Override
    public SquarePoint getNext() {
        int lastcol = currentPath.getLast().getCol(), lastrow = currentPath.getLast().getRow();
        int difference = lastrow - lastcol;
        int row = 0, col = 0;
        if(lastcol - 1 >=0 && lastcol - 1 < this.getSize() && lastrow + 1 >= 0 && lastrow + 1 < this.getSize()){
            row = lastrow + 1;
            col = lastcol - 1;
        }
        else{
            this.getStartingRow(difference);
            col = this.curStartCol;
            row = this.currStartRow;
        }
        // 1 2 3
        // 4 5 6
        // 7 8 9
        // 1 2 4 3 5 7 6 8 9
        // 1 (0,0)
        // 2 (0,1)
        // 3 (0,2)
        // 4 (1,0）
        // 5 (1,1）
        // 6 (1,2)
        // 7 (2,0)
        // 8 (2,1)
        // 9 (2,2)
        // (0,0) (0,1) (1,0）(0,2) (1,1）(2,0) (1,2) (2,1) (2,2)
        // row = 0, 0
        // 0,0 1,-1 row = 0, col = 1
        // 0,1 1,0 1,-1 row = 0, col = 2
        // 0,2 1,1 2,0 3,-1 row = 1, col = 2
        // 1,2 2,1 3,0  row = 2, col = 2
        // 2，2
        return new SquarePoint(row, col);
    }

    private void getStartingRow(int difference) {
        if(difference == this.getSize() - 1 && this.flag == 0){
            this.flag = 1;
            this.currStartRow  = this.currStartRow + 1;
        }
        else if(flag == 1)
        {
            this.currStartRow = this.currStartRow + 1;
        }
        else{
            this.currStartRow = 0;
            this.curStartCol = this.curStartCol + 1;
        }
    }
}
