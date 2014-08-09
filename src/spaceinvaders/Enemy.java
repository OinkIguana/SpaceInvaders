package spaceinvaders;

import java.awt.Graphics;
/**
 * Enemy parent class
 * @author Cameron Eldridge
 */
public class Enemy {
    //Important variables
    public Sprite sprite;
    public int x = 0, y = 0, imageIndex = 0;
    private double dir = 1;
    private int moveTimer = 15, points;
    
    public void step() {
        //Move slowly, change frame
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
        //Turn around and move closer to the bottom
        dir = -getDir() * (Math.abs(dir) + 0.1);
        y += 8;
        x += 4 * dir;
    }
    public int getDir() {
        //-1 for left, 1 for right
        return dir > 0 ? 1 : -1;
    }
    public void speedUp() {
        //Increase speed regardless of direction
        dir = getDir() * (Math.abs(dir) + 0.1);
    }
    public boolean bulletCollision(Bullet b) {
        return b.x + b.sprite.w > x && b.x < x + sprite.w &&
                b.y < y + sprite.h && b.y + b.sprite.h > y;
    }
    public void setPointValue(int x) {
        points = x;
    }
    public int getPointValue() {
        return points;
    }
}
