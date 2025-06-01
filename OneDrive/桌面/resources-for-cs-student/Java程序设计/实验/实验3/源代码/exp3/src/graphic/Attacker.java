package graphic;

import java.util.Random;

public class Attacker extends Missile{
    private int crossUp = 0;
    private int crossDown = 0;
    private double tanofup = 0;
    private double tanofdown = 0;

    private final int pivot;
    public Attacker(int x, int y, int speed) {
        super(x, y, speed, java.awt.Color.BLUE);
        Random random = new Random();
        int number = random.nextInt(200);
        this.pivot = y;
        if(pivot > 200 && pivot < 500) {
            if (number > 170)
                this.crossUp = 1;
            else if (number < 30)
                this.crossDown = 1;
            this.tanofup = Math.tan(y / 1280.0);
            this.tanofdown = Math.tan((630 - y) / 1280.0);
        }
    }

    @Override
    public void move(int horizontal, int vertical) {
        super.move(horizontal, vertical);
        if(this.crossUp == 1){
            this.y = pivot - (int)(this.x * this.tanofup);
        }
        else if(this.crossDown == 1){
            this.y = pivot + (int)(this.x * this.tanofdown);
        }
    }
}
