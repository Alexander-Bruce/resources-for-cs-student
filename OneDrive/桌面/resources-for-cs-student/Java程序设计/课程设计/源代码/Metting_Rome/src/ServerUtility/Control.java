package ServerUtility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class Control extends Thread {
    private static final BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
    private static final Logger logger = Logger.getLogger(Receiver.class.getName());

    @Override
    public void run() {
        try {
            while (true) {
                String command = keyboardInput.readLine();
                if (command.equals("End")) {
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            logger.severe("服务端关闭时发生异常：" + e.getMessage());
        }
    }
}
