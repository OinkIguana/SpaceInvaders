package spaceinvaders;

/**
 * Squid like enemy
 * @author Cameron Eldridge
 */
public class Squid extends Enemy {
    public Squid(int xx, int yy) {
        sprite = new Sprite("squid.png", 2, 48, 32);
        setPointValue(40);
        x = xx;
        y = yy;
    }
}
