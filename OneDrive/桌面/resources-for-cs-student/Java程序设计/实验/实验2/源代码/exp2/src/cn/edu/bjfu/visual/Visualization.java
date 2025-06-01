package cn.edu.bjfu.visual;

import cn.edu.bjfu.core.*;

import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.awt.*;

public class Visualization extends JFrame implements ActionListener {
    public static final int MIN_SIZE = 3;
    public static final int MAX_SIZE = 13;
    public static final String[] TYPES = {"行主序", "列主序", "对角", "反对角"};
    private int whichType = 0;
    private SquareTraversal squareTraversal = null;

    private boolean isEnd = false;

    JRadioButton radioRowMajor = new JRadioButton("行主序");
    JRadioButton radioColMajor = new JRadioButton("列主序");
    JRadioButton radioDiagonal = new JRadioButton("对角序");
    JRadioButton radioAntiDiagonal = new JRadioButton("反对角序");
    JComboBox boxSize = new JComboBox();
    JTextField fieldType = new JTextField(5);
    JTextField fieldSize = new JTextField(2);
    JButton buttonNew = new JButton("新遍历");
    JButton buttonNext = new JButton("下一步");

    JPanel panelShow = new JPanel();

    public Visualization() {
        // 在这里添加代码：
        super("方阵可视化遍历_计科22-2班_贺庆林_221002511");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new GridLayout(2, 1));
        JPanel panelTraversalType = new JPanel(new FlowLayout());
        panelTraversalType.setBorder(BorderFactory.createTitledBorder("选择遍历方式和遍历大小"));
        ButtonGroup traversalGroup = new ButtonGroup();
        traversalGroup.add(radioRowMajor);
        traversalGroup.add(radioColMajor);
        traversalGroup.add(radioDiagonal);
        traversalGroup.add(radioAntiDiagonal);
        panelTraversalType.add(radioRowMajor);
        panelTraversalType.add(radioColMajor);
        panelTraversalType.add(radioDiagonal);
        panelTraversalType.add(radioAntiDiagonal);
        for (int i = MIN_SIZE; i <= MAX_SIZE; i++) {
            boxSize.addItem(i);
        }
        panelTraversalType.add(new JLabel("遍历大小: "));
        panelTraversalType.add(boxSize);

        JPanel panelSizeInput = new JPanel(new FlowLayout());
        panelSizeInput.setBorder(BorderFactory.createTitledBorder("矩阵大小和遍历类型"));
        panelSizeInput.add(new JLabel("遍历类型: "));
        panelSizeInput.add(fieldType);
        panelSizeInput.add(new JLabel("当前遍历大小: "));
        panelSizeInput.add(fieldSize);

        panelTop.add(panelTraversalType);
        panelTop.add(panelSizeInput);
        add(panelTop, BorderLayout.NORTH);

        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.setBorder(BorderFactory.createTitledBorder("遍历数据可视化"));
        panelCenter.add(panelShow, BorderLayout.CENTER);
        add(panelCenter, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new FlowLayout());
        panelBottom.add(buttonNew);
        panelBottom.add(buttonNext);
        add(panelBottom, BorderLayout.SOUTH);

        buttonNew.addActionListener(this);
        buttonNext.addActionListener(this);

        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        // 在这里添加代码：
        if (e.getSource() == buttonNew) {
            // 新遍历按钮点击事件处理
            Integer size = boxSize.getSelectedIndex() + 3;
            this.isEnd = false;
            fieldSize.setText(size.toString());
            if (radioRowMajor.isSelected()) {
                this.whichType = 1;
                fieldType.setText("行主序");
                squareTraversal = new RowMajor(size);
            } else if (radioColMajor.isSelected()) {
                this.whichType = 2;
                fieldType.setText("列主序");
                squareTraversal = new ColMajor(size);
            } else if (radioDiagonal.isSelected()) {
                this.whichType = 3;
                fieldType.setText("对角序");
                squareTraversal = new Diagonal(size);
            } else if (radioAntiDiagonal.isSelected()) {
                this.whichType = 4;
                fieldType.setText("反对角序");
                squareTraversal = new AntiDiagonal(size);
            }
            displayCurrent();
        } else if (e.getSource() == buttonNext) {
            // 下一步按钮点击事件处理
            displayCurrent();
        }
    }

    private void displayCurrent() {
        if (squareTraversal != null) {
            if(squareTraversal.getCurrentPathCount() < squareTraversal.getSize() * squareTraversal.getSize()) {
                int[][] traversal = squareTraversal.getCurrMatrix();
                panelShow.removeAll();
                panelShow.setLayout(new GridLayout(traversal.length, traversal[0].length, 5 , 5));
                for (int i = 0; i < traversal.length; i++) {
                    for (int j = 0; j < traversal[0].length; j++) {
                        Color color;
                        if (traversal[i][j] == 1) {
                            color = Color.RED;
                        } else if (traversal[i][j] == -1) {
                            color = Color.BLUE;
                        } else {
                            color = Color.GREEN;
                        }
                        JPanel cell = new JPanel();
                        cell.setBackground(color);
                        cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        panelShow.add(cell);
                    }
                }
                SquarePoint p = squareTraversal.getNext();
                squareTraversal.step(p);
                panelShow.revalidate();
                panelShow.repaint();
            }
            else{
                if(this.isEnd == false) {
                    this.isEnd = true;
                    int[][] traversal = squareTraversal.getCurrMatrix();
                    panelShow.removeAll();
                    panelShow.setLayout(new GridLayout(traversal.length, traversal[0].length, 5, 5));
                    for (int i = 0; i < traversal.length; i++) {
                        for (int j = 0; j < traversal[0].length; j++) {
                            Color color;
                            if (traversal[i][j] == 1) {
                                color = Color.RED;
                            } else if(traversal[i][j] == -1){
                                color = Color.BLUE;
                            }else {
                                color = Color.GREEN;
                            }
                            JPanel cell = new JPanel();
                            cell.setBackground(color);
                            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                            panelShow.add(cell);
                        }
                    }
                    panelShow.revalidate();
                    panelShow.repaint();
                }
                else{
                    JOptionPane.showMessageDialog(this, "遍历已经完成！");
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "请先生成遍历！");
        }
    }
    public static void main(String[] args) {
        new Visualization();
    }
}
