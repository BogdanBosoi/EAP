package Engine;

import java.awt.*;
import java.util.ArrayList;

public class Handler {

    public ArrayList<GameObject> object = new ArrayList<>();

    private boolean up = false, down = false, right = false, left = false;
    public GameObject wizardPosition;
    private boolean normal = true;
    private boolean homing = false;
    private boolean barrage = false;


    private boolean willAbsorb = false;

    public boolean isWillAbsorb() {
        return willAbsorb;
    }

    public void setWillAbsorb(boolean willAbsorb) {
        this.willAbsorb = willAbsorb;
    }

    public boolean isNormal() {
        return normal;
    }

    void setNormal(boolean normal) {
        this.normal = normal;
    }

    public boolean isHoming() {
        return homing;
    }

    void setHoming(boolean homing) {
        this.homing = homing;
    }

    public boolean isBarrage() {
        return barrage;
    }

    void setBarrage(boolean barrage) {
        this.barrage = barrage;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void tick() {
        for(int i = 0; i < object.size(); ++i) {
            GameObject tempObject = object.get(i);

            tempObject.tick();
            tempObject.lightTick();
        }
    }

    public void render(Graphics g) {

        for(int i = 0; i < object.size(); ++i) {
            GameObject tempObject = object.get(i);

            tempObject.render(g);
        }

    }

    public void addObject(GameObject tempObject) {
        object.add(tempObject);
    }

    public void removeObject(GameObject tempObject) {
        object.remove(tempObject);
    }

}
