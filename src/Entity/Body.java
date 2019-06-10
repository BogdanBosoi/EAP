package Entity;

import Engine.GameObject;
import Engine.Handler;
import Engine.ID;
import Engine.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Body extends GameObject {

    private BufferedImage image;
    private Handler handler;
    public Body(int x, int y, ID id, SpriteSheet ss, SpriteSheet il, boolean illuminate,  int levelOfIlumination, Handler handler) {
        super(x, y, id, ss, il, illuminate, levelOfIlumination);
        this.handler = handler;
        image = ss.grabImage(6, 2, 32, 32);
    }

    @Override
    public void tick() {
        double distance = getDistance(this, handler.wizardPosition);
        if (distance >= 129)
            levelOfIlumination = 0;
        else if(distance >= 97 && distance <= 128)
            levelOfIlumination = 1;
        else if (distance >= 65 && distance <= 96)
            levelOfIlumination = 2;
        else
            levelOfIlumination = 3;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, x, y, null);
        g.drawImage(lightImage, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
