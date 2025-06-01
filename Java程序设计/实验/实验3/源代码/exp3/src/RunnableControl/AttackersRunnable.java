package RunnableControl;

import Utilities.Visualization;

public class AttackersRunnable implements Runnable{
    private final Visualization v;
    public AttackersRunnable(Visualization v){
        this.v = v;
    }

    @Override
    public void run() {
        synchronized (v) {
            if (v.getAttackers().size() <= 15)
                v.setGenerateCount(v.randomGenerator(v.getAttackers(), v.getAttackersRight(), v.getSpeed(), v.getGenerateCount()));
            if (v.getAttackersRight().size() <= 15)
                v.setGenerateCount(v.randomGenerator(v.getAttackers(), v.getAttackersRight(), v.getSpeed(), v.getGenerateCount()));
            v.AttackersMove(v.getAttackers(), v.getAttackersRight());
            v.deleteAttakersOutOfBound(v.getAttackers(), v.getAttackersRight());
        }
    }
}
