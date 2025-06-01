package ClientApplication;

import ServerUtility.Receiver;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * 客户端可视化界面类，用于显示用户界面并处理用户交互
 */
public class Visualization extends JFrame{

    private static Client client; // 客户端对象
    private boolean shouldPause = false; // 暂停标志位
    private final JButton buttonLogin = new JButton("进入聊天室"); // 登录按钮
    private final JButton buttonLogout = new JButton("退出聊天室"); // 退出按钮
    private final JTextField fieldIP = new JTextField("127.0.0.1"); // IP 地址输入框
    private final JTextField fieldPort = new JTextField("2333"); // 端口号输入框
    private final JTextField fieldNickname = new JTextField("Bush"); // 昵称输入框
    private final JTextArea areaContent = new JTextArea(); // 消息输入框
    private final JPanel mainPanel; // 主面板
    private final JButton buttonSend = new JButton("发送"); // 发送按钮
    private final JButton pauseButton = new JButton("暂停"); // 暂停按钮
    private static final Logger logger = Logger.getLogger(Receiver.class.getName()); // 日志记录器

    /**
     * 构造函数，初始化客户端可视化界面
     */
    public Visualization() {
        setSize(800, 600);
        setTitle("客户端");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        // 头部面板，包含 IP、端口号和昵称输入框以及登录、退出按钮
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 5));
        panel.setBounds(0, 0, 800, 30);
        panel.add(new JLabel("IP: "));
        panel.add(fieldIP);
        panel.add(new JLabel("端口: "));
        panel.add(fieldPort);
        panel.add(new JLabel("昵称: "));
        panel.add(fieldNickname);
        panel.add(buttonLogin);
        panel.add(buttonLogout);
        add(panel);

        // 主面板，用于显示聊天内容
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 1));
        mainPanel.setBounds(0, 30, 800, 500);
        add(mainPanel);

        // 底部面板，包含消息输入框、发送按钮和暂停按钮
        JPanel footer = new JPanel();
        footer.setLayout(null);
        footer.setBounds(0, 530, 800, 35);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 1));
        contentPanel.setBounds(0, 0, 650, 30);
        contentPanel.add(areaContent);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1));
        buttonPanel.setBounds(650, 0, 100, 30);
        buttonPanel.add(buttonSend);
        JPanel pausePanel = new JPanel();
        pausePanel.setLayout(new GridLayout(1, 1));
        pausePanel.setBounds(750, 0, 50, 30);
        pausePanel.add(pauseButton);
        footer.add(contentPanel);
        footer.add(buttonPanel);
        footer.add(pausePanel);
        add(footer);

        // 注册按钮点击事件监听器
        buttonLogin.addActionListener(e -> {
            String ip = fieldIP.getText();
            String port = fieldPort.getText();
            String nickname = fieldNickname.getText();
            if (ip.isEmpty() || port.isEmpty() || nickname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "IP, 端口号和昵称不能为空。");
            } else {
                client = new Client(ip, Integer.parseInt(port), nickname);
                String message = "[ Server ]：" + nickname + " 进入了聊天室。";
                client.setMessageToSend(message);
            }
        });
        buttonLogout.addActionListener(e -> {
            // 处理退出聊天室按钮点击事件
            client.setMessageToSend("[ Server ]：" + client.getNickname() + " 离开了聊天室。");
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                logger.severe("异常发生：" + ex.getMessage());
            }
            client.setMessageToSend("exit");
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                logger.severe("异常发生：" + ex.getMessage());
            }
            client.setFlagContinue(false);
            JOptionPane.showMessageDialog(this, "成功退出聊天室。");
            client.clearMessageList();
        });
        buttonSend.addActionListener(e -> {
            // 处理发送消息按钮点击事件
            String content = areaContent.getText();
            if (content.isEmpty()) {
                JOptionPane.showMessageDialog(this, "发送内容不能为空。");
            } else {
                LocalDateTime currentTime = LocalDateTime.now();
                String timeString = currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond();
                content = "[ " + client.getNickname() + ": " + timeString + " ]: " + content;
                client.setMessageToSend(content);
            }
        });
        pauseButton.addActionListener(e -> {
            // 处理暂停按钮点击事件
            if(client != null)
                shouldPause = !shouldPause;
        });
        // 刷新主面板线程，实现动态刷新消息显示
        Thread refreshThread = new Thread(() -> {
            while (true) {
                if(!shouldPause){
                    refreshMainPanel();
                }
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    logger.severe("异常发生：" + e.getMessage());
                }
            }
        });
        refreshThread.start();
    }

    /**
     * 刷新主面板，动态显示消息
     */
    private void refreshMainPanel() {
        SwingUtilities.invokeLater(() -> {
            if (client != null && client.getMessageListSize() > 0) {
                // 创建一个新的面板用于显示消息
                JPanel messagePanel = new JPanel();
                messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS)); // 使用垂直布局

                // 添加消息到面板中
                for (int i = 0; i < client.getMessageListSize(); i++) {
                    String message = client.getMessageList().get(i);
                    JLabel label = new JLabel(message);
                    messagePanel.add(label);
                }

                // 创建一个 JScrollPane 并将消息面板放入其中
                JScrollPane scrollPane = new JScrollPane(messagePanel);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 只显示垂直滚动条

                // 设置 JScrollPane 的大小与 mainPanel 一致
                scrollPane.setPreferredSize(mainPanel.getSize());

                // 移除旧的消息面板并添加新的 JScrollPane
                mainPanel.removeAll();
                mainPanel.add(scrollPane);

                // 让滚动条保持在底部
                JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());

                revalidate(); // 重新布局
                repaint(); // 重新绘制
                System.gc();
            }
        });
    }

}
