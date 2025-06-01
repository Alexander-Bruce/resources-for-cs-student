package ServerApplication;

import ServerUtility.Link;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.Timer;


public class Visualization extends JFrame {
    private static Server server;
    private static final HashSet<Link> links = new HashSet<>();
    private static final JPanel ListDisplay = new JPanel();
    private final Font font = new Font("宋体", Font.PLAIN, 20);
    private static final JPanel []ListForEach = new JPanel[10];
    private static final JLabel []NumberOfList = new JLabel[10];
    private static final JLabel []IPForEachList = new JLabel[10];
    private static final JLabel []PortForEachList = new JLabel[10];
    private static final JButton []OperationForEachList = new JButton[10];
    public Visualization() {
        setSize(800, 600);
        setTitle("服务端管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        //main disposition
        JPanel panel = new JPanel(new GridLayout(1, 4));
        panel.setBounds(0, 0, 800, 100);
        add(panel, BorderLayout.NORTH);

        //panel disposition
        JLabel Title1 = new JLabel("Number");
        JLabel Title2 = new JLabel("IP");
        JLabel Title3 = new JLabel("Port");
        JLabel Title4 = new JLabel("Operation");
        panel.add(Title1);
        panel.add(Title2);
        panel.add(Title3);
        panel.add(Title4);

        //ListDisplay disposition
        ListDisplay.setLayout(new GridLayout(11, 1));
        ListDisplay.setBounds(0, 100, 800, 500);
        add(ListDisplay, BorderLayout.CENTER);

        for (int i = 0; i < 10; i++) {
            ListForEach[i] = new JPanel(new GridLayout(1, 4));

            NumberOfList[i] = new JLabel();
            NumberOfList[i].setFont(font);
            IPForEachList[i] = new JLabel();
            IPForEachList[i].setFont(font);
            PortForEachList[i] = new JLabel();
            PortForEachList[i].setFont(font);
            OperationForEachList[i] = new JButton("Disconnect");
            OperationForEachList[i].setFont(font);
            OperationForEachList[i].setVisible(false);

            ListForEach[i].add(NumberOfList[i]);
            ListForEach[i].add(IPForEachList[i]);
            ListForEach[i].add(PortForEachList[i]);
            ListForEach[i].add(OperationForEachList[i]);

            ListDisplay.add(ListForEach[i]);
        }

        for(JButton button : OperationForEachList) {
            button.addActionListener(e -> {
                for (Link link : links) {
                    if (link.getSocket().getInetAddress().toString().equals(IPForEachList[0].getText())) {
                        link.setFlagContinue(false);
                        links.remove(link);
                        break;
                    }
                }
            });
        }

        setVisible(true);
    }

    public static void main(String[] args) throws Exception{
        Visualization visualization = new Visualization();

        Timer timer = new Timer(3000, e -> {
            int i = 0;

            for (Link link : links) {
                NumberOfList[i].setText(String.valueOf(i + 1));
                IPForEachList[i].setText(link.getSocket().getInetAddress().toString());
                PortForEachList[i].setText(String.valueOf(link.getSocket().getPort()));
                OperationForEachList[i].setText("Disconnect");
                OperationForEachList[i].setVisible(true);
                i++;
                visualization.repaint();
            }

            if (links.isEmpty()) {
                for (int j = 0; j < 10; j++) {
                    NumberOfList[j].setText("");
                    IPForEachList[j].setText("");
                    PortForEachList[j].setText("");
                    OperationForEachList[j].setText("");
                    OperationForEachList[j].setVisible(false);
                    visualization.repaint();
                }
            }
        });
        timer.start();
        server = new Server(links,"chatroom.txt");
    }
}