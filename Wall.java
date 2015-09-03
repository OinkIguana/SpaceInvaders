package spaceinvaders;

import java.awt.Graphics;

/**
 * Defensive walls
 * @author Cameron Eldridge
 */
public class Wall {
    public Sprite sprite = new Sprite("wall.png", 4, 20, 20);
    public int x = 0, y = 0, imageIndex = 0;
    private int health = 4;

    public Wall(int xx, int yy) {
        x = xx;
        y = yy;
    }

    public void step() {} //Does nothing
    public void getHit() {health--;} //Takes damage
    public boolean destroyed(){return (health == 0);} //Dies
    public boolean bulletCollision(Bullet b) {
        return b.x + b.sprite.w > x && b.x < x + sprite.w &&
                b.y < y + sprite.h && b.y + b.sprite.h > y;
    }
    public void draw(Graphics g) {
        sprite.draw(g, x, y, 4 - health);
    }
}
