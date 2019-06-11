package sample;

import javax.swing.*;
import java.awt.*;

public class Display {
    private String title;
    private int width;
    private int heigth;
    public static JFrame frame;
    private Canvas canvas;

    public Display(String title, int width, int heigth){
        this.title = title;
        this.width = width;
        this.heigth = heigth;
        createDisplay();
    }
    public void createDisplay(){
        frame = new JFrame(title);
        frame.setSize(width,heigth);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width,heigth));
        canvas.setBackground(new Color(212,154,140));
        canvas.setFocusable(false);
        frame.add(canvas);
        frame.pack();


    }
    public Canvas getCanvas(){
        return canvas;
    }
}
