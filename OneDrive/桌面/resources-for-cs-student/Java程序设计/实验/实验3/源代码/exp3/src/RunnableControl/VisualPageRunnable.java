package RunnableControl;
import Utilities.Visualization;

public class VisualPageRunnable implements Runnable{
    private final Visualization v;
    public VisualPageRunnable(Visualization v){
        this.v = v;
    }

    @Override
    public void run() {
        synchronized (v) {
            v.getInterceptButton().setEnabled(v.getInterceptors().size() <= 15);
            v.setTime((v.getTime() + 30) % Double.MAX_VALUE);
            v.getTimeLabel().setText(v.getTime() / 1000 + "s");
            v.getCountLabel().setText(v.getInterceptCount() + "/" + (v.getGenerateCount() - v.getAttackers().size() - v.getAttackersRight().size()));
            v.repaint();
        }
    }
}
