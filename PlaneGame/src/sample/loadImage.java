package sample;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class loadImage {
    public static BufferedImage image,player,enemy;
    public static void init(){
        image =  imageLoader("/res/cosmos.png");
        player = imageLoader("/res/spacep.png");
        enemy = imageLoader("/res/ufo.png");
    }

    public static BufferedImage imageLoader(String path){
        try {
            return ImageIO.read(loadImage.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
