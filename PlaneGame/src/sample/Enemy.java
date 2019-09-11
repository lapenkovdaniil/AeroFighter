package sample;

import java.awt.*;

public class Enemy {
    private int x,y;

    public Enemy(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void tick(){
        y += 4;
    }
    public void render(Graphics g){
        g.drawImage(loadImage.enemy, x,y,50,50,null);
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
