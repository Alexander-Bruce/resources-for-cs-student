package RunnableControl;

import Utilities.Visualization;
public class CollisionRunnable implements Runnable{
    private final Visualization v;
    public CollisionRunnable(Visualization v){
        this.v = v;
    }

    @Override
    public void run() {
        synchronized (v) {
            v.setInterceptCount(v.collisionDetection(v.getInterceptors(), v.getAttackers(), v.getAttackersRight(), v.getInterceptCount()));
        }
    }
}
