package GUI;

import Engine.Game;
import Engine.Handler;
import Query.QueryGetData;
import Query.QueryScore;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GUIAction extends AbstractAction {

    Score score;
    Game game;
    Handler handler;
    Menu menu;


    public GUIAction(Score score, Game game, Handler handler, Menu menu) {
        this.game = game;
        this.score = score;
        this.handler = handler;
        this.menu = menu;
        ImageIcon imageForOne = new ImageIcon(getClass().getResource("/button.png"));
        putValue(LARGE_ICON_KEY, imageForOne);

    }

    public void actionPerformed(ActionEvent evt) {
        game.gameState = 0;
        game.hp = 100;
        game.currentLevel = 0;
        game.score = 0;

        handler.setUp(false);
        handler.setDown(false);
        handler.setLeft(false);
        handler.setRight(false);

        game.scoreLoaded = false;

        game.mana = game.maximumMana;
        new QueryScore(score.getStringName(), score.getScore(), score.getLevels());
        score.getFrame().dispose();
        menu.databaseRows.clear();
        game.startEntryNumber = 0;
        new QueryGetData(menu.databaseRows);


    }
}
