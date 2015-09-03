package spaceinvaders;

/**
 * Jellyfish like enemy
 * @author Cameron Eldridge
 */
public class Jelly extends Enemy {
    public Jelly(int xx, int yy) {
        sprite = new Sprite("jelly.png", 2, 48, 32);
        setPointValue(10);
        x = xx;
        y = yy;
    }
}
