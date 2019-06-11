package sample;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class gameSetUp implements Runnable{
    private String title;
    private int width;
    private int heigth;
    private Thread thread;
    private boolean running;
    private BufferStrategy buffer;
    private Graphics g;
    private int y;
    private boolean start;
    private gameManager manager;
    private Display display;
    public static final int gameWidth = 400;
    public static final int gameHeight = 400;

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
        buffer = display.getCanvas().getBufferStrategy();
        display.getCanvas().setBackground(Color.ORANGE);

        if (buffer == null){
            display.getCanvas().createBufferStrategy(3);//?


            return;
        }
        g = buffer.getDrawGraphics();
        g.clearRect(0,0,width,heigth);


        g.drawImage(loadImage.image,50,50,gameWidth,gameHeight,null);
        manager.render(g);

        buffer.show();
        g.dispose();

    }

    public void run() {
        init();
        int fps = 50;
        double TimePerTick = 1000000000/fps;
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
