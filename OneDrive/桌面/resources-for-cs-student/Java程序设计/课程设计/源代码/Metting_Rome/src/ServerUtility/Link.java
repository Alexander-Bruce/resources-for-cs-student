package ServerUtility;

import java.net.Socket;
import java.util.HashSet;

public class Link {
    private final Socket socket; // 与客户端的套接字连接
    private boolean flagContinue; // 连接状态标志，表示连接是否继续
    private final Receiver receiver; // 接收器，用于接收客户端发送的消息
    private final Sender sender; // 发送器，用于向客户端发送消息
    private final String filename; // 连接相关的文件名
    private static HashSet<Link> links; // 存储所有连接的哈希集合，静态成员使得所有连接共享同一集合

    // 获取文件名
    public String getFilename() {
        return filename;
    }

    // 获取连接状态
    public boolean getFlagContinue() {
        return flagContinue;
    }

    // 获取套接字
    public Socket getSocket() {
        return socket;
    }

    // 设置连接状态
    public void setFlagContinue(boolean flagContinue) {
        this.flagContinue = flagContinue;
    }

    // 构造方法，初始化连接并启动接收和发送消息的线程
    public Link(Socket socket, String filename, HashSet<Link> links) {
        flagContinue = true; // 初始连接状态为继续
        this.socket = socket;
        this.filename = filename;
        this.links = links;
        receiver = new Receiver(this); // 创建接收器实例
        sender = new Sender(this); // 创建发送器实例
        receiver.start(); // 启动接收器线程
        sender.start(); // 启动发送器线程
        System.out.println("新连接建立"); // 输出连接建立消息
    }

    // 获取发送器
    public Sender getSender() {
        return sender;
    }

    // 更新所有连接的发送器，向所有连接广播消息
    public void updateAllSenders(String primMessage) {
        for (Link link : links) {
            link.getSender().broadcastMessage(primMessage);
        }
    }

}
