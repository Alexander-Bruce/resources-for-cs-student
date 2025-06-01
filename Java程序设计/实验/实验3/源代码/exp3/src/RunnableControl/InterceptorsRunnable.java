package RunnableControl;

import Utilities.Visualization;

public class InterceptorsRunnable implements Runnable{
    private final Visualization v;

    public InterceptorsRunnable(Visualization v){
        this.v = v;
    }

    @Override
    public void run() {
        synchronized (v) {
            v.InterceptorsMove(v.getInterceptors());
            v.deleteInterceptorsOutOfBound(v.getInterceptors());
        }
    }
}
