package spaceinvaders;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * Player's turret
 * @author Cameron Eldridge
 */
public class Player {
    //Important variables
    public Sprite sprite = new Sprite("player.png", 1, 52, 32),
            spriteDead = new Sprite("playerdied.png", 1, 52, 32);
    public int x = 0, y = 0, imageIndex = 0;
    private int left = 0, right = 0, deadTimer = 0, lives = 2;
    private Boolean shooting = false;

    public Player(int xx, int yy) {
        x = xx;
        y = yy;
    }

    public void step() {
        //Move. Also un-die after a little while
        if (deadTimer > 0 && lives >= 0) {
            deadTimer--;
        }
        if (deadTimer < 30) {
            move();
        } else if (deadTimer == 30) {
            lives--;
        }
    }

    public void move() {
        //Move based on keyboard
        x += (right - left) * 6;
        x = Math.min(Math.max(0, x), 800 - 52); //Limit position
    }

    public void draw(Graphics g) {
        if (deadTimer <= 30) {
            if(deadTimer % 2 == 0) { //Flicker as you revive
                //Draw the player's sprite
                sprite.draw(g, x, y, imageIndex);
            }
        } else {
            //Draw the dead sprite
            spriteDead.draw(g, x, y, imageIndex);
        }
        for (int i = 0; i < lives; i++) {
            //Draw the lives at the bottom
            sprite.draw(g, 32 + 52 * i, 800 - 64, 0);
        }
    }

    public boolean isShooting() {
        return shooting;
    }

    public void hasShot() {
        shooting = false;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int x) {
        lives = x;
    }

    public boolean bulletCollision(Bullet b) {
        return b.x + b.sprite.w > x && b.x < x + sprite.w
                && b.y < y + sprite.h && b.y + b.sprite.h > y;
    }

    public void die() {
        if (deadTimer == 0) {
            deadTimer = 60;
            left = 0;
            right = 0;
            shooting = false;
        }
    }

    public void keyPressed(KeyEvent key) {
        //Get keys for movement and shooting
        if (deadTimer < 30) {
            if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
                right = 1;
            }
            if (key.getKeyCode() == KeyEvent.VK_LEFT) {
                left = 1;
            }
            if (key.getKeyCode() == KeyEvent.VK_SPACE) {
                shooting = true;
            }
        }
    }

    public void keyReleased(KeyEvent key) {
        //Stop moving on release
        if (deadTimer < 30) {
            if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
                right = 0;
            }
            if (key.getKeyCode() == KeyEvent.VK_LEFT) {
                left = 0;
            }
        }
    }
}
