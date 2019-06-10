package Entity;

import Engine.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject {

    private Handler handler;
    Game game;
    Random r = new Random();
    int choose = 0;
    int hp = 100;

    private BufferedImage[] image = new BufferedImage[3];
    private Animation anim;

    public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss, SpriteSheet il, boolean illuminate,  int levelOfIlumination, Game game) {
        super(x, y, id, ss, il, illuminate, levelOfIlumination);
        this.handler = handler;
        this.game = game;
        for(int i = 0; i <= 2; i++)
            image[i] = ss.grabImage(i + 4, 1, 32, 32);

        anim = new Animation(1, image);
    }


    public void lightTick() {
        game.lightMap[x / 32][y / 32] = 3;
        game.light(x, y);

    }
    public boolean place_free(int x, int y, Rectangle myRect, Rectangle otherRect) {

        myRect.x = x;
        myRect.y = y;
        if (myRect.intersects(otherRect))
            return false;
        return true;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        if(x / 32 <= 0 || y / 32 <= 0 || x / 32 >= 63 || y / 32 >= 63)
            handler.removeObject(this);
        lightTick();

        choose = r.nextInt(10);

        for(int i = 0; i < handler.object.size(); ++i) {
            GameObject element = handler.object.get(i);
            if(element.getId() == ID.Block){
                if (!place_free((int) (x + velX), y, getBounds(), element.getBounds()))
                    x-=velX;
                if (!place_free(x, (int) (y + velY), getBounds(), element.getBounds()))
                    y -= velY;
                /*if(getBounds().intersects(element.getBounds())) {
                    x += (velX * 5) * - 1;
                    y += (velY * 5) * - 1;
                    velX *= -1;
                    velY *= -1;*/
                }
                if (choose == 0) {
                    velX = (r.nextInt(2 - -2) + -2);
                    velY = (r.nextInt(2 - -2) + -2);
                }

            /*if (element.getId() == ID.Bullet) {
                if(element.isHoming() == true)
                    if(Math.abs(element.getX() - this.getX()) <= 64 && Math.abs(element.getY() - this.getY()) <= 64){
                        if(element.getWhoFollow() == null)
                            element.setWhoFollow(this);
                    }
                if(element.getWhoFollow() != null) {
                        double angleToTarget = Math.atan2(element.getY() - y, element.getX() - x);

                        if (rotationAngle > angleToTarget)
                            rotationAngle -= (rotationAngle - angleToTarget) * rotationSpeed;
                        if (rotationAngle < angleToTarget)
                            rotationAngle += angleToTarget * rotationSpeed;

                        x += Math.cos(rotationAngle) * moveSpeed;
                        y += Math.sin(rotationAngle) * moveSpeed;
                        moveSpeed += 0.01f;

                    }*/
            if(element.getId() == ID.Bullet)
                if(getBounds().intersects(element.getBounds())) {
                    hp -= 50;
                    handler.removeObject(element);
                }

            }

        anim.runAnimation();
        if (hp <= 0) {
            handler.addObject(new Body(this.getX(), this.getY(), ID.Body, ss, il, false, 0, handler));
            for(int i = 0; i < r.nextInt(5) + 1; i++)
                handler.addObject(new ManaShard(this.getX() + r.nextInt(32), this.getY() + r.nextInt(32), ID.ManaShard, ss, il, true, 3, handler, game));
            handler.removeObject(this);
            game.score += 10;
            game.numberOfEnemies--;
            for(int i = 0; i < handler.object.size(); i++)
                if(handler.object.get(i).getId() == ID.Player)
                    handler.wizardPosition = handler.object.get(i);
        }
    }

    @Override
    public void render(Graphics g) {
        //g.drawImage(image, x, y, null);
        anim.drawAnimation(g, x, y, 0);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x - 16, y - 16, 64, 64);
    }
}
