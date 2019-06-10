package Engine;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    protected double getDistance(GameObject obj1, GameObject obj2) {
        return Math.sqrt((obj1.x - obj2.x) * (obj1.x - obj2.x) + (obj1.y - obj2.y) * (obj1.y - obj2.y));
    }

    protected void lightMechanic(GameObject obj) {
            double distance = getDistance(this, obj);
            if (distance >= 129)
                this.levelOfIlumination = 0;
            else if(distance >= 97 && distance <= 128)
                this.levelOfIlumination = 1;
            else if (distance >= 65 && distance <= 96)
                this.levelOfIlumination = 2;
            else
                this.levelOfIlumination = 3;

        }



    protected int x, y;
    protected float velX = 0, velY = 0;
    protected ID id;
    protected Spells spell = Spells.Normal;
    protected SpriteSheet ss;
    protected SpriteSheet il;
    protected BufferedImage lightImage;
    protected float rotationAngle = 0;
    protected float rotationSpeed = 0.1f;
    protected float moveSpeed = 5;

    protected boolean illuminate;
    protected int levelOfIlumination;   // de la 0 la 3

    public boolean isIlluminate() {
        return illuminate;
    }

    public void setIlluminate(boolean illuminate) {
        this.illuminate = illuminate;
    }

    public int getLevelOfIlumination() {
        return levelOfIlumination;
    }

    public void setLevelOfIlumination(int levelOfIlumination) {
        this.levelOfIlumination = levelOfIlumination;
    }

    public boolean isHoming() {
        return isHoming;
    }

    public void setHoming(boolean homing) {
        isHoming = homing;
    }

    protected GameObject whoFollow = null;
    protected boolean isHoming = false;

    public GameObject getWhoFollow() {
        return whoFollow;
    }

    public void setWhoFollow(GameObject whoFollow) {
        this.whoFollow = whoFollow;
    }

    public GameObject(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public GameObject(int x, int y, ID id, SpriteSheet ss, SpriteSheet il, boolean illuminate, int levelOfIlumination) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.ss = ss;
        this.il = il;
        this.illuminate = illuminate;
        this.levelOfIlumination = levelOfIlumination;
    }
    public void lightTick() {

    }
    public abstract void tick();
    public abstract void render(Graphics g);
    public void lightRender(Graphics g) {

        lightImage = il.grabImage(levelOfIlumination + 1, 1, 32, 32);
        if (!isIlluminate())
            g.drawImage(lightImage, x, y, null);

    }
    public abstract Rectangle getBounds();


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
