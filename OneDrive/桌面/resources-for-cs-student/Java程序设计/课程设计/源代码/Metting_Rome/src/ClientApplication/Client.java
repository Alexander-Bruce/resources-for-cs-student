package ClientApplication;

import ClientUtility.Receiver;
import ClientUtility.Sender;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 * 客户端类，用于连接服务器、发送和接收消息
 */
public class Client extends JFrame {
    private boolean flagContinue = false; // 标志位，表示是否继续运行客户端
    private Socket socket = null; // 客户端 Socket 连接
    private PrintWriter clientToServer = null; // 向服务器发送消息的 PrintWriter
    private BufferedReader serverToClient = null; // 从服务器接收消息的 BufferedReader
    private String messageToSend = null; // 待发送的消息
    private String nickname; // 客户端昵称
    private final CopyOnWriteArrayList<String> messageList = new CopyOnWriteArrayList<>(); // 存储接收到的消息列表
    private static final Logger logger = Logger.getLogger(ServerUtility.Receiver.class.getName()); // 日志记录器

    /**
     * 客户端构造函数，接收服务器 IP 地址、端口号和客户端昵称
     * @param ip 服务器 IP 地址
     * @param port 服务器端口号
     * @param nickname 客户端昵称
     */
    public Client(String ip, int port, String nickname) {
        try {
            // 创建 Socket 连接服务器
            socket = new Socket(ip, port);
            this.nickname = nickname;
            // 初始化输入输出流
            clientToServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            serverToClient = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            flagContinue = true; // 设置标志位为 true，表示客户端继续运行
            // 创建并启动接收和发送消息的线程
            Receiver receiver = new Receiver(this, serverToClient);
            Sender sender = new Sender(this, clientToServer);
            receiver.start();
            sender.start();
            // 弹出欢迎消息对话框
            JOptionPane.showMessageDialog(this, "欢迎来到聊天室，" + nickname + "!");
        } catch (IOException e) {
            // 处理连接服务器异常情况
            logger.severe("连接到服务器时发生异常：" + e.getMessage());
            JOptionPane.showMessageDialog(this, "不能连接到服务器。");
        }
    }

    /**
     * 获取待发送的消息
     * @return 待发送的消息
     */
    public String getMessageToSend() {
        return messageToSend;
    }

    /**
     * 判断客户端是否继续运行
     * @return true 如果客户端继续运行，否则 false
     */
    public boolean isFlagContinue() {
        return flagContinue;
    }

    /**
     * 设置待发送的消息
     * @param messageToSend 待发送的消息
     */
    public void setMessageToSend(String messageToSend) {
        this.messageToSend = messageToSend;
    }

    /**
     * 设置客户端是否继续运行
     * @param flagContinue true 表示客户端继续运行，否则 false
     */
    public void setFlagContinue(boolean flagContinue) {
        this.flagContinue = flagContinue;
    }

    /**
     * 获取客户端昵称
     * @return 客户端昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 向消息列表中添加一条消息
     * @param message 要添加的消息
     */
    public void addMessage(String message) {
        messageList.add(message);
    }

    /**
     * 获取接收到的消息列表
     * @return 接收到的消息列表
     */
    public CopyOnWriteArrayList<String> getMessageList() {
        return messageList;
    }

    /**
     * 获取消息列表的大小
     * @return 消息列表的大小
     */
    public int getMessageListSize() {
        return messageList.size();
    }

    /**
     * 清空消息列表
     */
    public void clearMessageList() {
        messageList.clear();
    }

    /**
     * 主函数入口，启动客户端可视化界面
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        Visualization visualization = new Visualization();
        visualization.setVisible(true);
    }

    /**
     * 获取客户端的 Socket 连接
     * @return 客户端的 Socket 连接
     */
    public Socket getSocket() {
        return socket;
    }
}
