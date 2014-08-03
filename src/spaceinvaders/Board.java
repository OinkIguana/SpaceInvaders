package spaceinvaders;

import java.util.Map;
import java.util.HashMap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.font.TextLayout;
import java.awt.font.FontRenderContext;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Cameron Eldridge
 */
public class Board extends JPanel implements ActionListener {

    private Map<Integer, Enemy> enemies = new HashMap<>();
    private Map<Integer, Explosion> explosions = new HashMap<>();
    private Map<Integer, Bullet> bullets = new HashMap<>();
    private Map<Integer, Wall> walls = new HashMap<>();
    private Player player;
    private Timer timer;
    private int score = 0, bonusTimer = (30 * 60), winTimer = 30, loseTimer = -1, wave = 0;

    public Board() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        player = new Player((800 - 52) / 2, (700 - 64));
        addEnemies();
        addWalls();
        timer = new Timer(1000 / 30, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        player.draw(g);
        Font font = new Font("Courier New", Font.PLAIN, 32);
        g.setFont(font);
        g.setColor(Color.WHITE);
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();
        if (loseTimer == -1 && winTimer == 30) {
            for (Enemy obj : enemies.values()) {
                obj.draw(g);
            }
            for (Bullet obj : bullets.values()) {
                obj.draw(g);
            }
            for (Explosion obj : explosions.values()) {
                obj.draw(g);
            }
            for (Wall obj : walls.values()) {
                obj.draw(g);
            }
        } else if (loseTimer > 0) {
            TextLayout layout = new TextLayout("GAME OVER", font, frc);
            g.drawString("GAME OVER", 800 / 2 - (int) layout.getAdvance() / 2, 800 / 2 - 32 / 2);
        } else if (enemies.isEmpty()) {
            TextLayout layout = new TextLayout("WAVE CLEAR", font, frc);
            g.drawString("WAVE CLEAR", 800 / 2 - (int) layout.getAdvance() / 2, 800 / 2 - 32 / 2);
        }
        g.drawString("Score: " + score, 500, 764);
        g.drawLine(0, 700, 800, 700);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (bonusTimer == 0) {
            bonusTimer = -enemies.size();
            enemies.put(enemies.size(), new Bonus());
        } else if (bonusTimer > 0) {
            bonusTimer--;
        }
        player.step();
        if (player.isShooting()) {
            boolean noShoot = false;
            for (Bullet bullet : bullets.values()) {
                if (bullet.fromPlayer()) {
                    noShoot = true;
                    break;
                }
            }
            if (!noShoot) {
                int ind = 0;
                while (bullets.containsKey(ind)) {
                    ind++;
                }
                bullets.put(ind, new Bullet(player.x + player.sprite.w / 2 - 6, player.y, true));
            }
            player.hasShot();
        }
        boolean destroyBonus = false;
        for (Enemy obj : enemies.values()) {
            obj.step();
            if (obj.x < -obj.sprite.w) {
                destroyBonus = true;
            }
        }
        if (destroyBonus) {
            enemies.remove(-bonusTimer);
            bonusTimer = 60 * 30;
        }
        for (int i = 0; i < 11; i++) {
            for (int j = 4; j >= 0; j--) {
                if (enemies.containsKey(11 * j + i)) {
                    if (enemies.get(11 * j + i).getPointValue() < 50) {
                        if (Math.max(Math.floor(Math.random() * 30 * 12) - (55 - enemies.values().size()) / 6, 0) == 0) {
                            int ind = 0;
                            while (bullets.containsKey(ind)) {
                                ind++;
                            }
                            bullets.put(ind,
                                    new Bullet(enemies.get(11 * j + i).x + enemies.get(11 * j + i).sprite.w / 2 - 6,
                                    enemies.get(11 * j + i).y + enemies.get(11 * j + i).sprite.h));
                        }
                    }
                    break;
                }
            }
        }
        for (Explosion obj : explosions.values()) {
            obj.step();
        }
        for (int i : explosions.keySet()) {
            if (explosions.containsKey(i)) {
                if (explosions.get(i).isDone()) {
                    explosions.remove(i);
                    break;
                }
            }
        }
        for (int i : bullets.keySet()) {
            if (bullets.containsKey(i)) {
                bullets.get(i).step();
                if (bullets.get(i).y + bullets.get(i).sprite.h < 0 || bullets.get(i).y + bullets.get(i).sprite.h > 700) {
                    bullets.remove(i);
                } else {
                    for (int j : walls.keySet()) {
                        if (walls.containsKey(j)) {
                            if (walls.get(j).bulletCollision(bullets.get(i))) {
                                bullets.remove(i);
                                walls.get(j).getHit();
                                if (walls.get(j).destroyed()) {
                                    walls.remove(j);
                                }
                                break;
                            }
                        }
                    }
                    if (!bullets.containsKey(i)) {
                        break;
                    }
                    if (bullets.get(i).fromPlayer()) {
                        for (int j : enemies.keySet()) {
                            if (enemies.containsKey(j)) {
                                if (enemies.get(j).bulletCollision(bullets.get(i))) {
                                    bullets.remove(i);
                                    score += enemies.get(j).getPointValue();
                                    explosions.put(j, new Explosion(enemies.get(j).x, enemies.get(j).y));
                                    enemies.remove(j);
                                    break;
                                }
                            }
                        }
                        if (!bullets.containsKey(i)) {
                            for (Enemy obj : enemies.values()) {
                                obj.speedUp();
                            }
                        }
                    } else {
                        if (player.bulletCollision(bullets.get(i))) {
                            player.die();
                            bullets.remove(i);
                            break;
                        }
                    }
                }
                if (!bullets.containsKey(i)) {
                    break;
                }
            }
        }
        int leftMost = 800, rightMost = 0, dir = 0, bottomMost = 0;
        for (Enemy obj : enemies.values()) {
            if (obj.x < leftMost && obj.getPointValue() <= 40) {
                leftMost = obj.x;
            } else if (obj.x > rightMost && obj.getPointValue() <= 40) {
                rightMost = obj.x;
            }
            if (obj.y > bottomMost) {
                bottomMost = obj.y;
            }
            dir = obj.getDir();
        }
        if (player.getLives() < 0 || bottomMost >= 436) {
            if (loseTimer == -1) {
                loseTimer = 60;
            }
            if (loseTimer == 0) {
                SpaceInvaders.lose();
            } else {
                loseTimer--;
            }
        }
        if (enemies.isEmpty() && winTimer == 0) {
            enemies.clear();
            walls.clear();
            wave++;
            winTimer = 30;
            addEnemies(wave * 16);
            addWalls();
            player.setLives(player.getLives() + 1);
        } else if (enemies.isEmpty()) {
            winTimer--;
        }
        if ((leftMost <= 56 / 2 && dir < 0)
                || (rightMost >= 800 - 56 - (56 / 2) && dir > 0)) {
            for (Enemy obj : enemies.values()) {
                obj.turn();
            }
        }
        repaint();
    }

    public final void addEnemies() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 11; j++) {
                Enemy e;
                if (i == 0) {
                    e = new Squid((800 / 2 - (11 * 56) / 2) - 56 / 2 + j * 56, i * 48 + 96);
                } else if (i > 2) {
                    e = new Jelly((800 / 2 - (11 * 56) / 2) - 56 / 2 + j * 56, i * 48 + 96);
                } else {
                    e = new Crab((800 / 2 - (11 * 56) / 2) - 56 / 2 + j * 56, i * 48 + 96);
                }
                enemies.put(enemies.size(), e);
            }
        }
    }

    public final void addEnemies(int y) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 11; j++) {
                Enemy e;
                if (i == 0) {
                    e = new Squid((800 / 2 - (11 * 56) / 2) - 56 / 2 + j * 56, y + i * 48 + 96);
                } else if (i > 2) {
                    e = new Jelly((800 / 2 - (11 * 56) / 2) - 56 / 2 + j * 56, y + i * 48 + 96);
                } else {
                    e = new Crab((800 / 2 - (11 * 56) / 2) - 56 / 2 + j * 56, y + i * 48 + 96);
                }
                enemies.put(enemies.size(), e);
            }
        }
    }

    public final void addWalls() {
        int xx = 60, yy = 540;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 3; k++) {
                    if (!(k == 2 && (j == 1 || j == 2))) {
                        walls.put(walls.size(), new Wall(xx + i * 200 + j * 20, yy + k * 20));
                    }
                }
            }
        }
    }

    public class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }
}