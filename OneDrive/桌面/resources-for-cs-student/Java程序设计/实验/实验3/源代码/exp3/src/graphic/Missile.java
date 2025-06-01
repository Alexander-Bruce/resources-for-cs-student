package graphic;

import java.awt.*;

public abstract class Missile{
    protected int x;
    protected int y;
    protected int speed;
    private final Color color;
    public Missile(int x, int y, int speed, Color color) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.color = color;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Color getColor() {
        return color;
    }

    public void move(int horizontal, int vertical) {
        this.x = this.x + this.speed * horizontal;
    }
}
