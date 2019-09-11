package sample;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import static sample.Display.frame;

public class gameManager implements KeyListener {
    private Player player;
    public static ArrayList<Bullet> bullet;
    private ArrayList<Enemy> enemies;
    private long current,delay;
    private int health;
    private int score;
    private boolean start;

    public void init(){
        frame.addKeyListener(this);
        player = new Player((gameSetUp.gameWidth / 2) - 25,gameSetUp.gameHeight - 50);
        player.init();
        bullet = new ArrayList<>();
        enemies = new ArrayList<>();
        current = System.nanoTime();
        delay = 200;
        health = player.getHealth();
        score = 0;
    }

    public void tick(){
        if (start) {
            player.tick();
            int bound = bullet.size();
            for (int i1 = 0; i1 < bound; i1++) {
                bullet.get(i1).tick();
            }
            long breaks = System.nanoTime() - current / 1000000;
            if (breaks > delay) {
                for (int i = 0; i < 1; i++) {
                    Random rand = new Random();
                    int randX = rand.nextInt(700);
                    int randY = rand.nextInt(700);
                    if (health > 0) {
                        enemies.add(new Enemy(randX, -randY));
                    }
                }
                current = System.nanoTime();
            }
            //enemies
            int bound1 = enemies.size();
            for (int i = 0; i < bound1; i++) {
                enemies.get(i).tick();
            }
        }
    }

    public void render(Graphics g) {
        if (start) {
            //player
            player.render(g);
            int bound = bullet.size();
            for (int i1 = 0; i1 < bound; i1++) {
                bullet.get(i1).render(g);
            }
            for (int i = 0; i < bullet.size(); i++)
                if (bullet.get(i).getY() <= 50) {
                    bullet.remove(i);
                    i--;
                }
            //enemies
            int bound1 = enemies.size();
            for (int i1 = 0; i1 < bound1; i1++) {
                if (!(enemies.get(i1).getX() <= 0
                        || enemies.get(i1).getX() >= 555
                        || enemies.get(i1).getY() >= 565)) {
                    if (enemies.get(i1).getY() >= 0) {
                        enemies.get(i1).render(g);
                    }
                }
            }
            for (int i = 0; i < enemies.size(); i++) {
                int ex = enemies.get(i).getX();
                int ey = enemies.get(i).getY();

                int px = player.getX();
                int py = player.getY();

                if (px < ex + 45 && px + 50 > ex &&
                        py < ey + 45 && py + 50 > ey) {
                    enemies.remove(i);
                    i--;
                    health--;
                    if (health <= 0) {
                        enemies.removeAll(enemies);
                        player.setHealth(0);
                        start = false;
                    }
                }

                for (int j = 0; j < bullet.size(); j++) {
                    int bx = bullet.get(j).getX();
                    int by = bullet.get(j).getY();
                    if (ex < bx + 5 && ex + 45 > bx &&
                            ey < by + 5 && ey + 45 > by) {
                        enemies.remove(i);
                        i--;

                        bullet.remove(j);
                        j--;
                        score = score + 5;
                    }

                }
                g.setColor(Color.black);
                g.setFont(new Font("arial", Font.BOLD, 25));
                g.drawString("Score : " + score, 30, 645);
                g.drawString("Health : " + health, 30, 675);
            }
        }else {
            g.setColor(Color.black);
            g.setFont(new Font("arial",Font.PLAIN,33));
            g.drawString("Press enter",215,gameSetUp.gameHeight/2);
            g.setFont(new Font("arial",Font.PLAIN,20));
            g.drawString("Left:      ←",240,gameSetUp.gameHeight/2+100);
            g.drawString("Right:    →",240,gameSetUp.gameHeight/2+125);
            g.drawString("Fire:  Space",240,gameSetUp.gameHeight/2+150);
        }
    }

    public void keyPressed(KeyEvent e) {
        int source = e.getKeyCode();
        if (source == KeyEvent.VK_ENTER){
            start = true;
            init();
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

}
