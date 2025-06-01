package cn.edu.bjfu.core;

import javax.swing.plaf.synth.SynthColorChooserUI;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * 这是一个抽象类，对任何一种遍历方阵的方法给出了整体的框架。
 * 已经实现了遍历的关键步骤和打印输出的主要功能。
 * 两个可变的方法需要用户实现，以适应不同的需求。
 */
public abstract class SquareTraversal {
    // 方阵的规模，用size表示方阵的边长：
    private int size = -1;
    // 获取遍历的初始点。使用抽象方法的意义是：强迫用户实现这个方法，而不至于遗漏：
    public abstract SquarePoint getInitPoint();

    // 遍历过程中当前的路径：
    public ArrayList<SquarePoint> currentPath;
    public int getCurrentPathCount(){
        return currentPath.size();
    }
     //ArrayList<SquarePoint> currentPath = new ArrayList<>();
    // 方阵初始化：size表示方阵的大小；initRow和initCol表示路径的起始位置
    public void initiate(int size) {
        // 在这里添加代码：
        this.size = size;
        this.getInitPoint();
    }

    public int getSize() {
        return size;
    }

    // 查找下一个点。
    // 如果存下一个点，则返回这个点；否则返回null。
    // 这个方法交给具体的类按照自己的场景去实现：
    public abstract SquarePoint getNext();

    // 路径推进一步（该步骤的执行之前先要判断是否存在可行的下一步）：
    public void step(SquarePoint nextPoint) {
        if(nextPoint != null) {
            int row = nextPoint.getRow(), col = nextPoint.getCol();
            if (row >= 0 && row < this.size && col >= 0 && col < this.size) currentPath.add(nextPoint);
        }
    }

    @Override
    public String toString() {
        return "SquareTraversal{" +
                "size=" + size +
                ", currentPath=" + currentPath +
                '}';
    }

    public int[][] getCurrMatrix() {
        int[][] matrix = new int[this.size][this.size];
        int lastrow = currentPath.getLast().getRow(), lastcol = currentPath.getLast().getCol();
        for(SquarePoint p: currentPath)
        {
            int row = p.getRow(), col = p.getCol();
            if(row == lastrow && col == lastcol) matrix[row][col] = -1;
            else matrix[row][col] = 1;
        }
        return matrix;
    }

    // 这个方法打印当前路径在整个方阵中的分布情况。
    // 没有遍历过的点用减号表示；已经遍历过的点用加号表示；当前遍历点（最后一个点）用星号表示，以突出其变化：
    public void printCurrentPath() {
        // 在这里添加代码：
        int [][]matrix = getCurrMatrix();
        for(int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                if(matrix[row][col] == 1) System.out.print("+ ");
                else if(matrix[row][col] == -1) System.out.print("* ");
                else System.out.print("- ");
            }
            System.out.println();
        }
    }

    // 用这个方法执行遍历的全过程。每遍历一步，都要打出路径在方阵中的整体分布情况。
    public void traverse() {
        // 在这里添加代码
        while(currentPath.size() != this.size * this.size)
        {
            SquarePoint nextpoint = getNext();
            step(nextpoint);
            printCurrentPath();
        }
    }
}
