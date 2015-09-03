package spaceinvaders;

/**
 * Bonus enemy
 * @author Cameron Eldridge
 */
public class Bonus extends Enemy {
    public Bonus() {
        //Initialize
        sprite = new Sprite("bonus.png", 2, 96, 42);
        int pts;
        pts = 50 * (int) Math.floor(Math.random() * 5 + 1); 
        //Random score
        setPointValue(pts);
        x = 800;
        y = 20;
    }
    @Override
    public void step() {
        //Move left quickly
        x -= 8;
    }
    @Override
    public void turn() {} //Never turn around
    @Override
    public int getDir() {
        //Always going left
        return -1;
    }
}
