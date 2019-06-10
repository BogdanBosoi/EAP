package Entity;
import Engine.*;

import java.awt.*;
import java.util.Random;

public class Bullet extends GameObject {

    private Handler handler;
    private Game game;
    private Random r = new Random();


    public Bullet(int x, int y, ID id, SpriteSheet ss, SpriteSheet il, boolean illuminate,  int levelOfIlumination, Handler handler, int mx, int my, boolean isHoming, Game game) {
        super(x, y, id, ss, il, illuminate, levelOfIlumination);
        this.handler = handler;
        this.game = game;
        velX = (mx - x) / 10;
        velY = (my - y) / 10;
        this.isHoming = isHoming;

    }

    public void light() {
        game.lightMap[x / 32][y / 32] = 3;
        game.light(x, y);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if(x >= 64 * 32 || y >= 32 * 32 || x <= 0 || y <= 0)
            handler.object.remove(this);

        light();

        for(int i = 0; i < handler.object.size(); ++i) {
            GameObject element = handler.object.get(i);

            if(element.getId() == ID.Block)
                if(getBounds().intersects(element.getBounds()))
                    handler.removeObject(this);

            if(element.getId() == ID.Enemy)
                if(isHoming == true)
                    if(Math.abs(element.getX() - this.getX()) <= 128 && Math.abs(element.getY() - this.getY()) <= 128){
                        if(element.getWhoFollow() == null)
                            setWhoFollow(element);
            if(getWhoFollow() != null) {
                double angleToTarget = Math.atan2(getWhoFollow().getY() - y, getWhoFollow().getX() - x);

                if (rotationAngle > angleToTarget)
                    rotationAngle -= (rotationAngle - angleToTarget) * rotationSpeed;
                if (rotationAngle < angleToTarget)
                    rotationAngle += angleToTarget * rotationSpeed;

                x += Math.cos(rotationAngle) * moveSpeed;
                y += Math.sin(rotationAngle) * moveSpeed;
                moveSpeed += 0.01f;
            }

            }


        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillOval(x, y, 8, 8);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 8, 8);
    }
}
