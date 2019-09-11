package sample;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.lang.System.*;

public class Player implements KeyListener {
    private int x,y;
    private boolean left,right;
    private boolean fire;
    private long current,delay;
    private int health;
    public  Player(int x,int y){
        this.x = x;
        this.y = y;
    }

    public void init(){
        Display.frame.addKeyListener(this);
        current = nanoTime();
        delay = 100;
        health = 3;
    }

    public void tick(){
        if (!(health <= 0)) {
            if (left) {
                if (x >= 2) {
                    x -= 5;
                }
            }
            if (right) {
                if (x <= 547) {
                    x += 5;
                }
            }
            if (fire) {
                long breaks = nanoTime() - (current / 100000000);
                if (breaks > delay) {
                    gameManager.bullet.add(new Bullet(x + 23, y));
                }
                current = nanoTime();
            }
        }
    }

    public void render(Graphics g){
        if (!(health <= 0)) {
            g.drawImage(loadImage.player, x, y, 50, 50,null);
        }
    }

    public void keyPressed(KeyEvent e){
        int source = e.getKeyCode();
        if (source == KeyEvent.VK_LEFT){
            left = true;
        }
        if (source == KeyEvent.VK_RIGHT){
            right = true;
        }
        if (source == KeyEvent.VK_SPACE){
            fire = true;
        }
    }
    public void keyReleased(KeyEvent e){
        int source = e.getKeyCode();
        if (source == KeyEvent.VK_LEFT){
            left = false;
        }
        if (source == KeyEvent.VK_RIGHT){
            right = false;
        }
        if (source ==KeyEvent.VK_SPACE){
            fire = false;
        }
    }
    public void keyTyped(KeyEvent e){

    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getHealth(){
        return health;
    }
    public void setHealth(int health){
        this.health = health;
    }
}