package Utilities;

import MissileIllustration.Illustration;
import graphic.Attacker;
import graphic.Interceptor;
import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class Visualization extends JFrame implements Illustration {
    private final HashSet<Interceptor> Interceptors = new HashSet<>();
    private final HashSet<Attacker> AttackersRight = new HashSet<>();
    private final HashSet<Attacker> Attackers = new HashSet<>();
    private int interceptCount = 0;
    private int generateCount = 0;
    private int speed = 4;
    private final int maxSpeed = 8;
    private final int minSpeed = 2;
    private final JLabel countLabel;
    private final JLabel timeLabel;
    private final JPanel drawingPanel;
    private final JButton startButton;
    private final JButton stopButton;
    private final JButton interceptButton;
    private final JButton accelerateButton;
    private final JButton decelerateButton;
    private double time = 0;

    public HashSet<Interceptor> getInterceptors() {
        return Interceptors;
    }

    public HashSet<Attacker> getAttackersRight() {
        return AttackersRight;
    }

    public HashSet<Attacker> getAttackers() {
        return Attackers;
    }

    public int getInterceptCount() {return interceptCount % Integer.MAX_VALUE;}

    public void setInterceptCount(int interceptCount) {
        this.interceptCount = interceptCount % Integer.MAX_VALUE;
    }

    public int getGenerateCount() {
        return generateCount;
    }

    public void setGenerateCount(int generateCount) {
        this.generateCount = generateCount;
    }

    public int getMinSpeed() {
        return minSpeed;
    }

    public int getMaxSpeed() {return maxSpeed;}

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public JLabel getCountLabel() { return countLabel; }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public JButton getStartButton() { return startButton; }

    public JButton getStopButton() { return stopButton; }

    public JButton getInterceptButton() {
        return interceptButton;
    }

    public JButton getAccelerateButton() {
        return accelerateButton;
    }

    public JButton getDecelerateButton() {
        return decelerateButton;
    }

    public Visualization() {
        setTitle("红旗拦截导弹实验--221002511_贺庆林");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // 创建绘图区域
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraphics(g);
            }
        };

        // 创建底部面板
        JPanel footer = new JPanel(new GridLayout(1, 7));
        startButton = new JButton("开始");
        stopButton = new JButton("停止");
        interceptButton = new JButton("拦截");
        accelerateButton = new JButton("加速");
        decelerateButton = new JButton("减速");

        // 设置按钮的状态
        startButton.setEnabled(true);
        stopButton.setEnabled(false);

        // 添加计时器
        JPanel timePanel = new JPanel(new GridLayout(1, 2));
        JLabel timeContentLabel = new JLabel("Time: ");
        timeLabel = new JLabel(time/1000 + "s");
        timePanel.add(timeContentLabel);
        timePanel.add(timeLabel);


        // 添加计数器
        JPanel countPanel = new JPanel(new GridLayout(1, 2));
        JLabel contentLabel = new JLabel("count: ");
        countLabel = new JLabel(interceptCount + "/" + generateCount);
        countPanel.add(contentLabel);
        countPanel.add(countLabel);

        // 添加按钮
        footer.add(timePanel);
        footer.add(startButton);
        footer.add(stopButton);
        footer.add(interceptButton);
        footer.add(accelerateButton);
        footer.add(decelerateButton);
        footer.add(countPanel);

        // 将绘图区域添加到中间，底部面板添加到底部
        add(drawingPanel, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

    }
    private void drawGraphics(Graphics g) {
        drawPoint(Interceptors, Attackers, AttackersRight, g);
    }

}
