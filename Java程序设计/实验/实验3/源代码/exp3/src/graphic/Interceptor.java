package graphic;

public class Interceptor extends Missile{
    public Interceptor(int x, int y, int speed) {
        super(x, y, speed, java.awt.Color.RED);
    }
    @Override
    public void move(int horizontal, int vertical) {
        super.move(horizontal, vertical);
        this.y = this.y + this.speed * vertical;
    }
}
