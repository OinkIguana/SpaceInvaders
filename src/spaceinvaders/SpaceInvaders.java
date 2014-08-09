package spaceinvaders;

import javax.swing.JFrame;

/**
 * 
 * @author Cameron Eldridge
 */
public class SpaceInvaders extends JFrame {
    public SpaceInvaders() {
        //Start the game
        add(new Board());
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SpaceInvaders();
    }

    public static void lose() {
        System.exit(0);
    }
}
