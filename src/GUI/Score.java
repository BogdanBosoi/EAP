package GUI;

import Engine.Game;
import Engine.Handler;

import javax.swing.*;
import java.awt.*;

public class Score {

    private int score;
    private String levels;
    private JFrame scoreFrame;
    private Game game;
    private Handler handler;

    public String getLevels() {
        return levels;
    }

    private TextField name;
    Menu menu;
    public Score(int score, String levels, Game game, Handler handler, Menu menu) {
        this.score = score;
        this.game = game;
        this.levels = levels;
        this.handler = handler;
        this.menu = menu;
        start();
    }

    public int getScore() {
        return score;
    }

    public JFrame getFrame() {
        return scoreFrame;
    }

    public String getStringName() {
        return name.getText();
    }


    public void start() {

        scoreFrame = new JFrame("Score Frame");
        name = new TextField("Please enter your name");
        name.setBounds(200, 100, 200, 50);
        JButton okButton = new JButton(new GUIAction(this, game, handler, menu));
        okButton.setText("OK");
        okButton.setBounds(200, 300, 135, 60);
        scoreFrame.add(okButton);
        scoreFrame.add(name);
        scoreFrame.setSize(600, 400);
        scoreFrame.setLayout(null);
        scoreFrame.setVisible(true);

    }


}
