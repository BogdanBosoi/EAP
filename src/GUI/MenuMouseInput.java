package GUI;

import Engine.Game;
import Engine.ID;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuMouseInput extends MouseAdapter {

    Game game;
    Menu menu;
    Highscore highscore;
    MenuMouseInput(Game game, Menu menu, Highscore highscore) {
        this.game = game;
        this.menu = menu;
        this.highscore = highscore;
    }

    public void mouseClicked(MouseEvent me) {
        Point clicked = me.getPoint();

        for(int i = 0; i < menu.buttonHandler.size(); i++)
        if (menu.buttonHandler.get(i).getBounds().contains(clicked)) {
            if(menu.buttonHandler.get(i).getId() != ID.navButton)
                menu.buttonHandler.get(i).setState(menu.buttonHandler.get(i).gameState);
            else {
                if(menu.buttonHandler.get(i).getLabel().equals("v")) {
                        game.startEntryNumber++;
                } else if (menu.buttonHandler.get(i).getLabel().equals("^")) {
                        game.startEntryNumber--;
                }

            }
        }
    }
}
