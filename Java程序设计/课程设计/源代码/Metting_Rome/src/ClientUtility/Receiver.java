package ClientUtility;

import ClientApplication.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 * 接收器类，用于接收服务器发送的消息并处理
 */
public class Receiver extends Thread {
    private final Client client; // 客户端对象
    private final BufferedReader serverToClient; // 从服务器到客户端的输入流
    private static final Logger logger = Logger.getLogger(ServerUtility.Receiver.class.getName()); // 日志记录器

    /**
     * 构造函数，初始化接收器
     * @param client 客户端对象
     * @param serverToClient 从服务器到客户端的输入流
     */
    public Receiver(Client client, BufferedReader serverToClient) {
        this.client = client;
        this.serverToClient = serverToClient;
    }

    /**
     * 线程运行方法，接收服务器发送的消息并处理
     */
    @Override
    public void run() {
        String message;
        try {
            // 循环接收消息直到连接关闭或标志位设置为false
            while (client.isFlagContinue() && (message = serverToClient.readLine()) != null) {
                // 处理接收到的消息并添加到消息列表
                client.addMessage(message);
            }
        } catch (SocketException e) {
            // 处理服务器异常关闭
            System.out.println("连接关闭。");
            client.setFlagContinue(false); // 设置连接标志为false
        } catch (IOException e) {
            // 处理IO异常
            System.out.println("连接关闭，程序退出。");
            client.setFlagContinue(false); // 设置连接标志为false
        } finally {
            try {
                // 关闭连接
                client.getSocket().close(); // 手动关闭socket
                serverToClient.close();
            } catch (IOException ex) {
                logger.severe("连接关闭时发生异常：" + ex.getMessage());
            }
        }
    }

}
