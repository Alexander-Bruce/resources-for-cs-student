package ServerUtility;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.logging.Logger;

public class Log {
    private static FileWriter writer = null;
    private static final Logger logger = Logger.getLogger(Receiver.class.getName());
    public Log(File file){
        try {
            writer = new FileWriter(file, true);
        } catch (IOException e) {
            logger.severe("文件打开时发生异常：" + e.getMessage());
        }
    }

    public void closeWriter() {
        try {
            writer.close();
        } catch (IOException e) {
            logger.severe("文件关闭时发生异常：" + e.getMessage());
        }
    }

    public FileWriter getWriter(){
        return writer;
    }

    public void writeLog(String message) {
        try {
            writer.write(message + "\n");
            writer.flush(); // 确保写入到磁盘
        } catch (IOException e) {
            logger.severe("写入日志时发生异常：" + e.getMessage());
        }
    }
}
