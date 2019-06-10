package Engine;

import Entity.Bullet;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.Random;

public class MouseInput extends MouseAdapter {

    private Handler handler;
    private Camera camera;
    private Game game;
    private SpriteSheet ss;
    private SpriteSheet il;
    private Random r = new Random();

    MouseInput(Handler handler, Camera camera, Game game, SpriteSheet ss, SpriteSheet il) {
        this.handler = handler;
        this.camera = camera;
        this.game = game;
        this.ss = ss;
        this.il = il;
    }

    public void mousePressed(MouseEvent e) {
        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());

        for(int i = 0; i < handler.object.size(); ++i) {
            GameObject element = handler.object.get(i);

            if (element.getId() == ID.Player) {
                boolean isHoming = (element.spell == Spells.Homing);
                if(isHoming) {
                    if(game.mana >= 10) {
                        handler.addObject(new Bullet(element.getX() + 16, element.getY() + 24, ID.Bullet, ss, il, true, 3, handler, mx, my, true, game));
                        game.mana -= 10;
                    }
                }
                if(element.spell == Spells.Normal) {
                    if(game.mana >= 5) {
                        handler.addObject(new Bullet(element.getX() + 16, element.getY() + 24, ID.Bullet, ss, il, true, 3, handler, mx, my, false, game));
                        game.mana -= 5;
                    }
                }
                if(element.spell == Spells.Barrage) {
                    if(game.mana >= 15) {
                        for (int j = 0; j <= 3; ++j) {
                            int signX = r.nextInt(2) == 1 ? 1 : -1;
                            int signY = r.nextInt(2) == 1 ? 1 : -1;
                            handler.addObject(new Bullet(element.getX() + 16 + r.nextInt(8) * signX, element.getY() + 24 + r.nextInt(12) * signY, ID.Bullet, ss, il, true, 3, handler, mx, my, false, game));
                        }
                        game.mana -= 15;
                    }
                }


            }
        }
    }

}
