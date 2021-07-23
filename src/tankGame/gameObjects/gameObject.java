package tankGame.gameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

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

    public abstract void resolveCollision(gameObject o);
    public abstract void update();

    public Rectangle getHitbox() {
        return hitbox.getBounds();
    }

    public void setHitbox(int x, int y) {
        this.hitbox.setLocation(x,y);
    }



    public void destroy() {
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    //getters and setters
    public int getX(){ return this.x; }
    public int getY(){ return this.y; }
    public BufferedImage getImg() {return this.img;}
    public void setX(int x){ this.x = x; }
    public void setY(int y){ this.y = y; }

    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.img, x, y, null);
    }

}
