package ServerUtility;

import java.io.PrintWriter;
import java.net.SocketException;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;

public class Sender extends Thread {
    private final Link link; // 链接对象
    private final ConcurrentLinkedDeque<String> messagesToSend = new ConcurrentLinkedDeque<>(); // 待发送消息队列
    private static final Logger logger = Logger.getLogger(Receiver.class.getName()); // 日志记录器

    // 构造函数，接收链接对象
    public Sender(Link link) {
        this.link = link;
    }

    // 广播消息方法，将消息加入待发送队列
    public void broadcastMessage(String message) {
        messagesToSend.add(message);
    }

    // 线程运行方法
    @Override
    public void run() {
        try {
            // 初始化输出流
            PrintWriter serverToClient = new PrintWriter(link.getSocket().getOutputStream(), true);
            serverToClient.println("欢迎来到聊天室。"); // 发送欢迎消息到客户端
            while (link.getFlagContinue()) {
                // 检查是否有待发送消息
                if (!messagesToSend.isEmpty()) {
                    String message = messagesToSend.poll(); // 获取并移除待发送消息
                    serverToClient.println(message); // 发送消息到当前客户端
                }
                Thread.sleep(100); // 每次循环迭代后休眠100毫秒
            }
        } catch (SocketException e) {
            logger.severe("客户端连接异常：" + e.getMessage());
        } catch (Exception e) {
            logger.severe("异常发生：" + e.getMessage());
        }
    }
}
