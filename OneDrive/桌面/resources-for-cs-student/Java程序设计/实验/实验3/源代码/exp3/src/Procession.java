import Utilities.Visualization;
import RunnableControl.*;
import javax.swing.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Procession extends Visualization{
    private static final Visualization procession = new Procession();
    private static ScheduledExecutorService scheduler;
    private static final Runnable VisualPageTask = new VisualPageRunnable(procession);
    private static final Runnable AttackersTask = new AttackersRunnable(procession);
    private static final Runnable InterceptorsTask = new InterceptorsRunnable(procession);
    private static final Runnable CollisionTask = new CollisionRunnable(procession);
    private static final ExecutorService executor = Executors.newFixedThreadPool(4);
    private final Timer garbageCollection = new Timer(5000, e -> System.gc());
    public Procession(){
        super();
        // 添加按钮的功能
        getStartButton().addActionListener(e -> {
            // 添加开始按钮的功能
            getStartButton().setEnabled(false);
            getStopButton().setEnabled(true);
            start();
            // 开启垃圾回收
            garbageCollection.start();
        });

        getStopButton().addActionListener(e -> {
            // 添加停止按钮的功能
            getStartButton().setEnabled(true);
            getStopButton().setEnabled(false);
            stop();
            // 关闭垃圾回收
            garbageCollection.stop();
        });

        getInterceptButton().addActionListener(e -> {
            //添加拦截按钮的功能
            if(getInterceptors().size() <= 15)
                InterceptorGenerator(getInterceptors(), getSpeed());
        });

        getAccelerateButton().addActionListener(e -> {
            // 添加加速按钮的功能
            if(getSpeed() < getMaxSpeed()){
                setSpeed(getSpeed() + 1);
                setMissilesSpeeds(getInterceptors(), getAttackers(), getAttackersRight(), getSpeed());
                getDecelerateButton().setEnabled(true);
                getAccelerateButton().setEnabled(getSpeed() != getMaxSpeed());
            }
        });

        getDecelerateButton().addActionListener(e -> {
            // 添加减速按钮的功能
            if(getSpeed() >= getMinSpeed()){
                setSpeed(getSpeed() - 1);
                setMissilesSpeeds(getInterceptors(), getAttackers(), getAttackersRight(), getSpeed());
                getAccelerateButton().setEnabled(true);
                getDecelerateButton().setEnabled(getSpeed() != getMinSpeed());
            }
        });
    }

    public void start(){
        // 开启线程池
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            executor.execute(VisualPageTask);
            executor.execute(AttackersTask);
            executor.execute(InterceptorsTask);
            executor.execute(CollisionTask);
        }, 0, 30, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    public void stop(){
        // 关闭线程池
        scheduler.shutdown();

    }

    public static void main(String[] args) {
        //设置窗口可见
        procession.setVisible(true);
    }
}
