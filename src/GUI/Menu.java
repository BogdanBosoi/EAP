package GUI;

import Engine.Game;
import Engine.ID;
import Query.DatabaseRow;
import Query.QueryGetData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Menu {

    MenuButton bPlay, bHighScore, bExit;
    ArrayList<DatabaseRow> databaseRows = new ArrayList<>();
    ArrayList <MenuButton> buttonHandler = new ArrayList<>();
    Game game;
    Highscore highscore;
    // JButton bLevelEditor; ???

    void tick() {

    }

    public void start() {
        new QueryGetData(databaseRows);


        bPlay = new MenuButton(400, 300, ID.Button ,"Play", game, 1, this, 50, 40);
        bHighScore = new MenuButton(400, 450, ID.Button, "Highscore", game, 3, this, 20, 40);
        bExit = new MenuButton (400, 600, ID.Button, "Exit", game, 6, this, 50, 40);

        buttonHandler.add(bPlay);
        buttonHandler.add(bHighScore);
        buttonHandler.add(bExit);

        game.addMouseListener(new MenuMouseInput(game, this, highscore));


    }

    public Menu(Game game, Highscore highscore) {
        this.game = game;
        this.highscore = highscore;
        //start();

    }

    public void render(Graphics g) {

        for(int i = 0; i < buttonHandler.size(); i++)
            if(buttonHandler.get(i).getId() == ID.Button)
                buttonHandler.get(i).render(g);

    }


}
