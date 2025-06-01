package ServerApplication;

import ServerUtility.Control;
import ServerUtility.Enteroom;
import ServerUtility.Link;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server {
    // 存储所有链接的 HashSet 对象
    private static HashSet<Link> links = new HashSet<>();
    // 全局变量：聊天记录文件名
    private final Enteroom enteroom;
    // 控制线程对象
    private final Control control = new Control();

    // 服务器构造函数，接收链接集合和聊天记录文件名
    public Server(HashSet<Link> links, String filename) throws Exception{
        this.links = links;
        // 创建 Enteroom 线程，用于处理链接
        enteroom = new Enteroom(links);
        // 创建 ServerSocket 对象，监听指定端口
        ServerSocket serverSocket = new ServerSocket(2333);
        // 启动 Enteroom 和 Control 线程
        enteroom.start();
        control.start();
        // 打印服务器启动成功消息
        System.out.println("服务器启动成功");
        // 循环监听客户端连接
        while (true) {
            Socket socket= serverSocket.accept();
            // 打印客户端上线通知
            System.out.println("上线通知： " + socket.getInetAddress() + ":" +socket.getPort());
            // 创建新的链接对象并添加到链接集合中
            links.add(new Link(socket, filename, links));
        }
    }

    // 主函数入口
    public static void main(String[] args) throws Exception{
        // 检查命令行参数是否为空
        if(args.length == 0){
            System.out.println("请输入文件路径.");
        }
        else{
            // 创建文件路径字符串
            StringBuilder stringBuilder = new StringBuilder();
            for(String s: args)
                stringBuilder.append(s);
            String filename = stringBuilder.toString();

            // 检查文件是否存在
            File file = new File(filename);
            if(!file.exists()){
                System.out.println("请输入正确的文件路径。");
            }
            // 检查文件格式是否正确
            else if(!filename.contains(".txt")){
                System.out.println("请输入正确的文件格式。");
            }
            else{
                // 启动服务器
                new Server(links, filename);
            }
        }
    }
}

//java "-Dfile.encoding=UTF8" ServerApplication/Server D:\Java\JavaProject\Metting_Rome\chatroom.txt
