package Engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class BufferedImageLoader {

    private BufferedImage image;

    public BufferedImage loadImage(String path) {
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage loadImageFromFile(String path) {
        try {
            File input = new File(path);
            try {
                image = ImageIO.read(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }

}
