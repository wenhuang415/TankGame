package tankGame.gameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * abstract class for a gameObject
 * contains x, y coordinates, BufferedImage and a hitbox
 */
public abstract class gameObject{
    private int x;
    private int y;
    private final BufferedImage img;
    private Rectangle hitbox;
    private boolean destroyed;

    public gameObject(int x, int y, BufferedImage img) {
        this.destroyed = false;
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle(x,y,this.getImg().getWidth(),this.getImg().getHeight());
    }

    //two abstract methods that all gameObjects must implement
    public abstract void resolveCollision(gameObject o);
    public abstract void update();

    //getters
    public Rectangle getHitbox() {
        return hitbox.getBounds();
    }
    public boolean isDestroyed() {
        return destroyed;
    }
    public int getX(){ return this.x; }
    public int getY(){ return this.y; }

    //setters
    public void setHitbox(int x, int y) {
        this.hitbox.setLocation(x,y);
    }
    public BufferedImage getImg() {return this.img;}
    public void setX(int x){ this.x = x; }
    public void setY(int y){ this.y = y; }
    //function to set if value of object is destroyed or not
    public void destroy() {
        this.destroyed = true;
    }

    //function to draw game object
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.img, x, y, null);
    }

}
