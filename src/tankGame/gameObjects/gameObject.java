package tankGame.gameObjects;

import java.awt.image.BufferedImage;

public abstract class gameObject implements collidable{
    private int x;
    private int y;
    private final int width;
    private final int height;
    private float angle;
    private BufferedImage img;

    public gameObject(int height, int width, float angle, BufferedImage img) {
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.img = img;
    }

    @Override
    public abstract void resolveCollision(gameObject o);

    //getters and setters
    public int getX(){ return this.x; }
    public int getY(){ return this.y; }
    public int getWidth(){ return this.width; }
    public int getHeight(){ return this.height; }
    public float getAngle() { return this.angle;}
    public BufferedImage getImg() {return this.img;}
    public void setAngle(float angle) {this.angle = angle;}
    public void setX(int x){ this.x = x; }
    public void setY(int y){ this.y = y; }

}
