package Entity;

import Engine.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject {

    private BufferedImage image;
    private Handler handler;

    public Block(int x, int y, ID id, SpriteSheet ss, SpriteSheet il, boolean illuminate,  int levelOfIlumination, Handler handler) {
        super(x, y, id, ss, il, illuminate, levelOfIlumination);
        this.handler = handler;
        image = ss.grabImage(5, 2, 32, 32);
    }

    @Override
    public void tick() {
        /*for(int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.isIlluminate())
                this.lightMechanic(tempObject);
        }*/
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(image, x, y, null);
        //this.lightRender(g);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

}
