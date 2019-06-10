package Engine;

// Aici imi fac fereastra

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;


class Window{

    Window(int width, int height, String title, Game game) {

        JFrame frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));


        frame.add(game);
        frame.setResizable(false);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
           public void windowClosed(java.awt.event.WindowEvent windowEvent) {
               System.exit(1);
           }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
