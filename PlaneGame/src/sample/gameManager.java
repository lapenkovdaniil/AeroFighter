package sample;

import java.awt.*;
import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class gameManager implements KeyListener {
    private Player player;
    public static ArrayList<Bullet> bullet;
    private ArrayList<Enemy> enemies;
    private long current;
    private long delay;
    private int health;
    private int score;
    private boolean start;

    public void init(){
        Display.frame.addKeyListener(this);
        player = new Player((gameSetUp.gameWidth / 2) + 35 ,gameSetUp.gameHeight);
        player.init();
        bullet = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        current = System.nanoTime();
        delay = 200;
        health = player.getHealth();
        score = 0;


    }
    public void tick(){
       if (start) {
           player.tick();
           for (int i = 0; i < bullet.size(); i++) {
               bullet.get(i).tick();
           }
           long breaks = System.nanoTime() - current / 1000000;
           if (breaks > delay) {
               for (int i = 0; i < 2; i++) {
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
           for (int i = 0; i < enemies.size(); i++) {
               enemies.get(i).tick();
           }
       }

    }
    public void render(Graphics g) {
        if (start) {
            //player
            player.render(g);
            for (int i = 0; i < bullet.size(); i++) {
                bullet.get(i).render(g);
            }
            for (int i = 0; i < bullet.size(); i++) {
                if (bullet.get(i).getY() <= 50) {
                    bullet.remove(i);
                    i--;
                }
            }
            //enemies
            for (int i = 0; i < enemies.size(); i++) {
                if (!(enemies.get(i).getX() <= 50 || enemies.get(i).getX() >= 450 - 45
                        || enemies.get(i).getY() >= 450 - 45)) {
                    if (enemies.get(i).getY() >= 50) {
                        enemies.get(i).render(g);

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
                g.setFont(new Font("arial", Font.BOLD, 40));
                g.drawString("Score : " + score, 70, 500);
                g.drawString("Health : " + health, 70, 540);


            }
        }else {
            g.setColor(Color.black);
            g.setFont(new Font("arial",Font.PLAIN,33));
            g.drawString("Press enter",165,gameSetUp.gameHeight/2+50);
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
