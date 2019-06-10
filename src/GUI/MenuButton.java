package GUI;

import Engine.BufferedImageLoader;
import Engine.Game;
import Engine.GameObject;
import Engine.ID;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MenuButton extends GameObject {
    private String text;
    Game game;
    BufferedImage button;
    Menu menu;
    int gameState;
    int textPosX, textPosY;
    public MenuButton(int x, int y, ID id, String text, Game game, int gameState, Menu menu, int textPosX, int textPosY) {
        super(x, y, id);
        this.game = game;
        this.text = text;
        this.menu = menu;
        this.textPosX = textPosX;
        this.textPosY = textPosY;
        BufferedImageLoader loader = new BufferedImageLoader();
        button = loader.loadImage("/button_template.png");
        this.gameState = gameState;
    }

    public void setState(int gameState) {
        game.gameState = gameState;
    }

    public void loadImage(String path) {
        BufferedImageLoader loader = new BufferedImageLoader();
        this.button = loader.loadImage(path);
    }

    public String getLabel() {
        return this.text;
    }


    @Override
    public void tick() {

    }

    public void render(Graphics g) {

        g.drawImage(button, x, y, null);
        g.drawString(text, x + textPosX, y + textPosY);
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 147, 60);
    }
}
