package ServerUtility;

import java.util.HashSet;
import java.util.logging.Logger;

public class Enteroom extends Thread{
    private final HashSet<Link> links;
    private static final Logger logger = Logger.getLogger(Receiver.class.getName());
    public Enteroom(HashSet<Link> links) {
        this.links = links;
    }
    @Override
    public void run(){
        try {
            while (true) {
                // 使用迭代器的 remove() 方法安全删除元素
                links.removeIf(link -> !link.getFlagContinue());
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            logger.severe("连接断开时发生异常：" + e.getMessage());
        }
    }
}
