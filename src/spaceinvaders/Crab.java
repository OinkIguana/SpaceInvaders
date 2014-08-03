package spaceinvaders;

/**
 *
 * @author Cameron Eldridge
 */
public class Crab extends Enemy {
    public Crab(int xx, int yy) {
        sprite = new Sprite("crab.png", 2, 48, 32);
        setPointValue(20);
        x = xx;
        y = yy;
    }
}
