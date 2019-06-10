package Entity;

import Engine.*;

import java.awt.*;
import java.util.Random;

public class ManaShard extends GameObject {

    private Random r = new Random();

    private boolean followPlayer = false;
    private boolean clamping = true;
    /*private float rotationAngle = 0;
    private float rotationSpeed = 0.1f;
    private float moveSpeed = 5;*/
    private Game game;
    private Handler handler;
    private int originalX;
    private int originalY;
    public ManaShard(int x, int y, ID id, SpriteSheet ss, SpriteSheet il, boolean illuminate,  int levelOfIlumination, Handler handler, Game game) {
        super(x, y, id, ss, il, illuminate, levelOfIlumination);
        this.handler = handler;
        this.game = game;
        originalX = x;
        originalY = y;

    }

    private void light() {
        game.lightMap[x / 32][y / 32] = 3;
        game.light(x, y);
    }

    @Override
    public void tick() {

        if(x > 64 * 32 || y > 32 * 32 || x <= 0 || y <= 0)
            handler.object.remove(this);
        light();
        for(int i = 0; i < handler.object.size(); ++i) {
            GameObject element = handler.object.get(i);
            if(element.getId() == ID.Player && game.mana < 100) {
                if (Math.abs(element.getX() - this.getX()) <= 128 && Math.abs(element.getY() - this.getY()) <= 128) {
                    followPlayer = true;
                    clamping = false;
                }
                else {
                    followPlayer = false;
                }
            }
        }

        if(followPlayer == true && game.mana < 100) {
            GameObject element;
            for(int i = 0; i < handler.object.size(); ++i){
                element = handler.object.get(i);
                if(element.getId() == ID.Player){
                    double angleToTarget = Math.atan2(element.getY() - y, element.getX() - x);

                    if (rotationAngle > angleToTarget)
                        rotationAngle -= (rotationAngle-angleToTarget) * rotationSpeed;
                    if (rotationAngle < angleToTarget)
                        rotationAngle += angleToTarget * rotationSpeed;

                    x += Math.cos(rotationAngle) * moveSpeed;
                    y += Math.sin(rotationAngle)  * moveSpeed;
                    moveSpeed += 0.01f;

                }

            }

        }

        if(clamping == true) {
            if(Math.abs(x - originalX) <= 4 && Math.abs(y - originalY) <= 4) {
                x += r.nextInt(1 - -1);
                y += r.nextInt( 1 - -1);
            }
            else {
                x = originalX;
                y = originalY;
            }
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.fillOval(x, y, 8, 8);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 8, 8);
    }
}
