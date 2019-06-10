package GUI;

import Engine.Game;
import Engine.ID;
import Query.DatabaseRow;
import Query.QueryGetData;

import java.awt.*;
import java.util.ArrayList;

public class Highscore {


    Game game;
    Menu menu;
    MenuButton okButton;
    MenuButton upButton, downButton;



    void start() {




    }

    public Highscore(Game game, Menu menu) {
        this.game = game;
        this.menu = menu;
        okButton = new MenuButton(100, 600, ID.OKButton, "OK", game, 0, menu, 60, 20 );
        upButton = new MenuButton(800, 50, ID.navButton, "^", game, 0, menu, 30, 30);
        downButton = new MenuButton(800, 600, ID.navButton, "v", game, 0, menu, 30, 30);
        upButton.loadImage("/nav.png");
        downButton.loadImage("/nav.png");
        menu.buttonHandler.add(okButton);
        menu.buttonHandler.add(upButton);
        menu.buttonHandler.add(downButton);

        start();
    }

    public void render(Graphics g) {

        g.setFont(new Font("Serif", Font.PLAIN, 22));
        g.drawString("Name: ", 20, 100);
        g.drawString ( "Score: ", 400, 100);
        g.drawString ("Levels: ", 500, 100);

        if(game.startEntryNumber < 0)
            game.startEntryNumber = 0;
        if(game.startEntryNumber > menu.databaseRows.size())
            game.startEntryNumber = menu.databaseRows.size();

        for(int i = game.startEntryNumber; i < Math.min(game.startEntryNumber + game.maxEntryNumber, menu.databaseRows.size()); i++) {
            g.drawString(menu.databaseRows.get(i).getName(), 20, (i - game.startEntryNumber + 2) * 75);
            g.drawString(menu.databaseRows.get(i).getScore() + "", 400, (i - game.startEntryNumber + 2) * 75);
            g.drawString(menu.databaseRows.get(i).getLevel(), 500, (i - game.startEntryNumber + 2) * 75);
        }
        upButton.render(g);
        downButton.render(g);
        okButton.render(g);

    }

}
