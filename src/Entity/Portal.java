package Entity;

import Engine.Game;
import Engine.GameObject;
import Engine.ID;
import Engine.SpriteSheet;

import java.awt.*;

public class Portal extends GameObject {
    private Game game;
    public Portal(int x, int y, ID id, SpriteSheet ss, SpriteSheet il, boolean illuminate, int levelOfIlumination, Game game) {
        super(x, y, id, ss, il, illuminate, levelOfIlumination);
        this.game = game;
    }

    @Override
    public void tick() {
        game.lightMap[x / 32][y / 32] = 0;
        game.light(x, y);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(x, y, 32, 32);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
