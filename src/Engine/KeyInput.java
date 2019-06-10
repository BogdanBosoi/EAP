package Engine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Handler handler;

    KeyInput(Handler handler) {
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        for(GameObject tempObject : handler.object) {
            if(tempObject.getId() == ID.Player) {
                if(key == KeyEvent.VK_W) handler.setUp(true);
                if(key == KeyEvent.VK_A) handler.setLeft(true);
                if(key == KeyEvent.VK_S) handler.setDown(true);
                if(key == KeyEvent.VK_D) handler.setRight(true);
                if(key == KeyEvent.VK_Z) {
                    handler.setBarrage(false);
                    handler.setHoming(false);
                    handler.setNormal(true);
                }
                if(key == KeyEvent.VK_X) {
                    handler.setBarrage(false);
                    handler.setHoming(true);
                    handler.setNormal(false);
                }
                if(key == KeyEvent.VK_C) {
                    handler.setBarrage(true);
                    handler.setHoming(false);
                    handler.setNormal(false);
                }
                if(key == KeyEvent.VK_Q) {
                    if(!handler.isWillAbsorb())
                        handler.setWillAbsorb(true);
                }
            }
        }

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        for(GameObject tempObject : handler.object) {
            if(tempObject.getId() == ID.Player) {
                if(key == KeyEvent.VK_W) handler.setUp(false);
                if(key == KeyEvent.VK_A) handler.setLeft(false);
                if(key == KeyEvent.VK_S) handler.setDown(false);
                if(key == KeyEvent.VK_D) handler.setRight(false);
            }
        }

    }


}
