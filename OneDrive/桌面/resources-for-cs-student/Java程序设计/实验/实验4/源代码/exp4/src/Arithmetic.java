import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Arithmetic extends JFrame {
    private final JComboBox minSize = new JComboBox();
    private final JComboBox maxSize = new JComboBox();
    private final JRadioButton add;
    private final JRadioButton subtract;
    private final JRadioButton multiply;
    private final JRadioButton divide;
    private final JTextField resultType;
    private final JLabel fieldType = new JLabel();
    private final Operation operation = new Operation();
    public Arithmetic() {
        setTitle("算数生成器_计科22-2班贺庆林_221002511");
        setSize(430, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建一个JPanel，并设置布局为GridLayout，4行1列
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        // 创建并添加第一行的组件
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label1_1 = new JLabel("请选择操作数的最小值: ");
        for(int i = 2; i < 10001; i++) minSize.addItem(i);
        JLabel label1_2 = new JLabel("请选择操作数的最大值: ");
        for(int i = 10; i < 10001; i++) maxSize.addItem(i);
        row1.add(label1_1);
        row1.add(minSize);
        row1.add(label1_2);
        row1.add(maxSize);
        panel.add(row1);

        // 创建并添加第二行的组件
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label2_1 = new JLabel("请选择运算类型: ");
        add = new JRadioButton("加法");
        subtract = new JRadioButton("减法");
        multiply = new JRadioButton("乘法");
        divide = new JRadioButton("除法");
        // 创建一个按钮组，并将单选按钮添加到该组中
        add.setSelected(true);
        multiply.setSelected(true);
        row2.add(label2_1);
        row2.add(add);
        row2.add(subtract);
        row2.add(multiply);
        row2.add(divide);
        panel.add(row2);

        // 创建并添加第三行的组件
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton generator = new JButton("生成新算式");
        generator.addActionListener(e -> generateOperation());
        row3.add(generator);
        row3.add(fieldType);
        panel.add(row3);

        // 创建并添加第四行的组件
        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label4_1 = new JLabel("你的运算结果：");
        resultType = new JTextField(10);
        JButton check = new JButton("判断");
        check.addActionListener(e -> checkResult());
        JButton showAnswer = new JButton("显示结果");
        showAnswer.addActionListener(e -> showResult());
        row4.add(label4_1);
        row4.add(resultType);
        row4.add(check);
        row4.add(showAnswer);
        panel.add(row4);

        add(panel);
    }

    // 生成新算式的方法
    private void generateOperation() {
        int min = minSize.getSelectedIndex() + 2;
        int max =  maxSize.getSelectedIndex() + 10;
        ArrayList<Integer> list = new ArrayList<>();
        if(max  > min) {
            if (add.isSelected()) list.add(0);
            if (subtract.isSelected()) list.add(1);
            if (multiply.isSelected()) list.add(2);
            if (divide.isSelected()) list.add(3);
            if(list.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请选择运算类型！", "错误", JOptionPane.ERROR_MESSAGE);
            }
            else {
                Random rand = new Random();
                int operationType =  list.get(rand.nextInt(list.size()));
                String operationTyper;
                operation.setNumber1(min,max);
                operation.setNumber2(min,max);
                if(operationType == 0)operationTyper = "+";
                else if(operationType == 1) operationTyper = "-";
                else if(operationType == 2)operationTyper = "*";
                else {
                    operationTyper = "/";
                    while(operation.getNumber1().intValue() % operation.getNumber2().intValue() != 0){
                        operation.setNumber1(min,max);
                        operation.setNumber2(min,max);
                    }
                }
                operation.setOperationType(operationType);
                fieldType.setText(operation.getNumber1() + " " + operationTyper + " " + operation.getNumber2());
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "最大值必须大于最小值！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showResult() {
        if(operation.getResult() == null){
            JOptionPane.showMessageDialog(null, "请先生成算式！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {
            BigInteger result = operation.getResult();
            JOptionPane.showMessageDialog(null, "运算结果是：" + result.toString(), "结果", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void checkResult() {
        if(operation.getResult() != null && resultType.getText() != null && resultType.getText().equals(operation.getResult() + "")) {
            JOptionPane.showMessageDialog(null, "恭喜你，答对了！", "结果", JOptionPane.INFORMATION_MESSAGE);
        } else if(operation.getResult() != null){
            JOptionPane.showMessageDialog(null, "很遗憾，答错了！", "结果", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "请生成算式", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Arithmetic table = new Arithmetic();
        table.setVisible(true);
    }
}
