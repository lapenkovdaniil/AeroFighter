package sample;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class gameSetUp implements Runnable{
    private String title;
    private int width,heigth;
    private Thread thread;
    private boolean running;
    private Graphics g;
    private gameManager manager;
    private Display display;
    public static final int gameWidth = 600,gameHeight = 600;

    public gameSetUp(String title, int width, int heigth){
        this.title = title;
        this.width = width;
        this.heigth = heigth;
    }

    public void init(){
        display = new Display(title,width,heigth);
        loadImage.init();
        manager = new gameManager();
        manager.init();
    }

    public synchronized void start(){
        running = true;
        if (thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }

    private synchronized void stop(){
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick(){
        manager.tick();
    }

    public void render(){
        BufferStrategy buffer = display.getCanvas().getBufferStrategy();
        display.getCanvas().setBackground(Color.WHITE);

        if (buffer == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = buffer.getDrawGraphics();
        g.clearRect(0,0,width,heigth);

        g.drawImage(loadImage.image,0,0,gameWidth,gameHeight,null);
        manager.render(g);
        buffer.show();
        g.dispose();
    }

    public void run() {
        init();
        int fps = 60;
        double TimePerTick = 1000000000 / fps;
        double delta = 0;
        long current = System.nanoTime();
        while (running) {
            delta += (System.nanoTime() - current)/TimePerTick;
            current = System.nanoTime();
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
    }
}
