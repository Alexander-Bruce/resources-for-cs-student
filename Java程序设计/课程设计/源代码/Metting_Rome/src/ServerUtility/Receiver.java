package ServerUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.io.File;
import java.util.logging.Logger;

public class Receiver extends Thread {
    private final Link link; // 链接对象
    private BufferedReader reader; // 读取客户端消息的输入流
    private final File file; // 日志文件
    private final Log log; // 日志对象
    // 添加日志记录器
    private static final Logger logger = Logger.getLogger(Receiver.class.getName());

    // 构造函数，接收链接对象并初始化日志相关内容
    public Receiver(Link link) {
        this.link = link;
        file = new File(link.getFilename());
        log = new Log(file);
    }

    // 线程运行方法
    @Override
    public void run() {
        try {
            // 初始化输入流
            reader = new BufferedReader(new InputStreamReader(link.getSocket().getInputStream()));
            String message;
            // 循环读取消息，直到连接关闭或收到退出命令
            while (link.getFlagContinue() && (message = reader.readLine()) != null) {
                // 处理退出命令
                if(message.equals("exit")) {
                    System.out.println("客户端请求退出。");
                    link.setFlagContinue(false);
                    break;
                } else if(!message.isEmpty()){
                    // 广播消息给所有连接
                    System.out.println(message);
                    link.updateAllSenders(message);
                    // 写入日志
                    log.writeLog(message);
                }
            }
        } catch (SocketException e) {
            // 客户端连接异常，可能是客户端关闭了连接或连接中断
            link.setFlagContinue(false);
            logger.warning("客户端关闭：" + e.getMessage());
        } catch (IOException e) {
            logger.severe("IO异常发生：" + e.getMessage());
        } finally {
            try {
                // 关闭资源
                if (reader != null)
                    reader.close();
                link.getSocket().close();
            } catch (IOException e) {
                logger.severe("关闭资源时发生异常：" + e.getMessage());
            }
        }
    }
}
