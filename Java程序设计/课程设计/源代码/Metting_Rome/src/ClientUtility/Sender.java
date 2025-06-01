package ClientUtility;

import ClientApplication.Client;
import ServerUtility.Receiver;

import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * 发送器类，用于向服务器发送消息
 */
public class Sender extends Thread {
    private final Client client; // 客户端对象
    private final PrintWriter clientToServer; // 从客户端到服务器的输出流
    private static final Logger logger = Logger.getLogger(Receiver.class.getName()); // 日志记录器

    /**
     * 构造函数，初始化发送器
     * @param client 客户端对象
     * @param clientToServer 从客户端到服务器的输出流
     */
    public Sender(Client client, PrintWriter clientToServer) {
        this.client = client;
        this.clientToServer = clientToServer;
    }

    /**
     * 线程运行方法，循环发送消息到服务器
     */
    @Override
    public void run() {
        // 在循环中发送消息到服务器，直到连接关闭
        while (client.isFlagContinue()) {
            if (client.getMessageToSend() != null){
                clientToServer.println(client.getMessageToSend()); // 发送消息到服务器
                client.setMessageToSend(null); // 重置待发送消息为null
            }
            try {
                Thread.sleep(40); // 每次循环迭代后休眠40毫秒
            } catch (InterruptedException e) {
                logger.severe("异常发生：" + e.getMessage());
            }
        }
    }
}
