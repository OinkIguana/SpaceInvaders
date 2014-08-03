package spaceinvaders;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
/**
 *
 * @author Cameron Eldridge
 */
public class Sprite {
    public Image img;
    public int imageNumber, w, h;
    public int xOrig = 0, yOrig = 0;
    public Sprite(String imageFile, int num, int width, int height) {
        img = new ImageIcon(imageFile).getImage();
        imageNumber = num;
        w = width;
        h = height;
    }
    public void draw(Graphics g, int x, int y, int n) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, x, y, x + w, y + h, n * w, 0, (n + 1) * w, h, null);
    }
    public void draw(Graphics g, int x, int y, int n, boolean flipX, boolean flipY) {
        Graphics2D g2d = (Graphics2D) g;
        int x2 = x + w, y2 = y + h;
        if(flipX) {
            x2 = x;
            x = x + w;
        }
        if(flipY) {
            y2 = y;
            y = y + h;
        }
        g2d.drawImage(img, x, y, x2, y2, n * w, 0, (n + 1) * w, h, null);
    }
}
