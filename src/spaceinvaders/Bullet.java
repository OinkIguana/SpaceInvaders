package spaceinvaders;

import java.awt.Graphics;

/**
 * Bullet
 * @author Cameron Eldridge
 */
public class Bullet {
    private boolean playerBullet = false;
    public Sprite sprite = new Sprite("bullet.png", 1, 12, 24);
    public int x, y;
    public Bullet(int xx, int yy) {
        x = xx;
        y = yy;
    }
    public Bullet(int xx, int yy, boolean p) {
        playerBullet = p;
        x = xx;
        y = yy;
    }
    
    public void step() {
        //Move up or down
        if(playerBullet) {
            y -= 16;
        } else {
            y += 16;
        }
    }
    public void draw(Graphics g) {
        sprite.draw(g, x, y, 0, false, playerBullet);
    }
    public boolean fromPlayer() {
        return playerBullet;
    }
}
