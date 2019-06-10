package Entity;
import Engine.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wizard extends GameObject {

    Handler handler;
    Game game;

    Animation anim;

    private BufferedImage[] image = new BufferedImage[3];

    public Wizard(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss, SpriteSheet il, boolean illuminate,  int levelOfIlumination) {
        super(x, y, id, ss, il, illuminate, levelOfIlumination);
        this.handler = handler;
        this.game = game;

        for(int i = 0; i <= 2; i++)
            image[i] = ss.grabImage(i + 1, 1, 32, 48);

        anim = new Animation(1, image);

    }

    @Override
    public void tick() {
        collision();

        x += velX;
        y += velY;

        light();


        if(handler.isUp()) velY = -5;
        else if (!handler.isDown()) velY = 0;

        if(handler.isDown()) velY = 5;
        else if(!handler.isUp()) velY = 0;

        if(handler.isRight()) velX = 5;
        else if (!handler.isLeft()) velX = 0;

        if(handler.isLeft()) velX = -5;
        else if(!handler.isRight()) velX = 0;

        if(handler.isBarrage()) spell = Spells.Barrage;

        if(handler.isHoming()) spell = Spells.Homing;

        if(handler.isNormal()) spell = Spells.Normal;

        if(handler.isWillAbsorb()) {
            for(int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if(tempObject.getId() == ID.Body && getDistance(tempObject, this) <= 128){
                    game.hp += 20;
                    if (game.hp >= 100)
                        game.hp = 100;
                    handler.object.remove(tempObject);
                    handler.setWillAbsorb(false);
                    break;
                }
            }
        }

        anim.runAnimation();

    }


    public boolean place_free(int x, int y, Rectangle myRect, Rectangle otherRect) {

        myRect.x = x;
        myRect.y = y;
        if (myRect.intersects(otherRect))
            return false;
        return true;
    }

    public void light() {
        game.lightMap[x / 32][y / 32] = 3;
        game.light(x, y);
    }

    public void collision() {

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Block) {
                if (!place_free((int) (x + velX), y, getBounds(), tempObject.getBounds()))
                    velX = 0;
                if (!place_free(x, (int) (y + velY), getBounds(), tempObject.getBounds()))
                    velY = 0;
            } else if (tempObject.getId() == ID.Portal) {
                if (!place_free((int) (x + velX), y, getBounds(), tempObject.getBounds()) || !place_free(x, (int) (y + velY), getBounds(), tempObject.getBounds())) {
                    if(!game.isLoading())
                        game.setLoading(true);
                    break;
                }
            }
            /*if (tempObject.getId() == ID.Body) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    if (game.mana < game.maximumMana) {
                        game.mana += 5;
                        if (game.mana >= 100)
                            game.mana = 100;
                        handler.removeObject(tempObject);
                    }
                }
            }*/
            if (tempObject.getId() == ID.Enemy) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    game.hp--;
                }
            }
            if (tempObject.getId() == ID.ManaShard)
                if (getBounds().intersects(tempObject.getBounds()))
                    if (game.mana < game.maximumMana) {
                        game.mana += 5;
                        if (game.mana >= 100)
                            game.mana = 100;
                        handler.removeObject(tempObject);
                    }

        }
    }

    @Override
    public void render(Graphics g) {
        if(velX == 0 && velY == 0)
            g.drawImage(image[0], x, y, null);
        else
            anim.drawAnimation(g, x, y, 0);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 48);
    }
}
