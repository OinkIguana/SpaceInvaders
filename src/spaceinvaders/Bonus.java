package spaceinvaders;

/**
 *
 * @author Cameron Eldridge
 */
public class Bonus extends Enemy {
    public Bonus() {
        sprite = new Sprite("bonus.png", 2, 96, 42);
        int pts;
        pts = 50 * (int) Math.floor(Math.random() * 5 + 1);
        setPointValue(pts);
        x = 800;
        y = 20;
    }
    @Override
    public void step() {
        x -= 8;
    }
    @Override
    public void turn() {}
    @Override
    public int getDir() {
        return -1;
        
    }
}
