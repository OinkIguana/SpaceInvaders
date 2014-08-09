package spaceinvaders;

import java.awt.Graphics;

/**
 * Explosion particle after enemies die
 * @author Cameron Eldridge
 */
public class Explosion {
    public Sprite sprite = new Sprite("explosion.png", 1, 52, 32);
    public int x = 0, y = 0, imageIndex = 0;
    private int timer = 8;
    public Explosion(int xx, int yy) {
        x = xx;
        y = yy;
    }
    public void step() {
        //Die after a while
        timer--;
    }
    public boolean isDone() {
        return (timer == 0);
    }
    public void draw(Graphics g) {
        sprite.draw(g, x, y, 0);
    }
}
