package spaceinvaders;

import java.awt.Graphics;
/**
 *
 * @author Cameron Eldridge
 */
public class Enemy {
    public Sprite sprite;
    public int x = 0, y = 0, imageIndex = 0;
    private double dir = 1;
    private int moveTimer = 15, points;
    public void step() {
        moveTimer--;
        if(moveTimer == 0) {
            x += 4 * dir;
            moveTimer = 15;
            imageIndex = (imageIndex + 1) % 2;
        }
    }
    public void draw(Graphics g) {
        sprite.draw(g, x, y, imageIndex);
    }
    public void turn() {
        dir = -getDir() * (Math.abs(dir) + 0.1);
        y += 8;
        x += 4 * dir;
    }
    public int getDir() {
        return dir > 0 ? 1 : -1;
    }
    public void speedUp() {
        dir = getDir() * (Math.abs(dir) + 0.1);
    }
    public boolean bulletCollision(Bullet b) {
        if( b.x + b.sprite.w > x && b.x < x + sprite.w &&
            b.y < y + sprite.h && b.y + b.sprite.h > y) {
            return true;
        } else {
            return false;
        }
    }
    public void setPointValue(int x) {
        points = x;
    }
    public int getPointValue() {
        return points;
    }
}
