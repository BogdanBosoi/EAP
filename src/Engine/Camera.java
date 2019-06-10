package Engine;

public class Camera {

    private float x, y;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject object) {

        x += ((object.getX() - x) - 1024 / 2) * 0.05f;          // tweening, sa fac tranzitie ca oamenii
        y += ((object.getY() - y) - 768 / 2) * 0.05f;

        if ( x <= 0 ) x = 0;
        if ( x >= 1024 ) x = 1024;
        if ( y <= 0 ) y = 0;
        if ( y >= 768 ) y = 768;

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }



}
