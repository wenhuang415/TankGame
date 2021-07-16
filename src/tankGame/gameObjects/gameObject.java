package tankGame.gameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class gameObject implements collidable{
    private int x;
    private int y;
    private BufferedImage img;
    private Rectangle hitbox;

    public gameObject(int x, int y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle(x,y,this.getImg().getWidth(),this.getImg().getHeight());
    }

    public Rectangle getHitbox() {
        return hitbox.getBounds();
    }

    public void setHitbox(int x, int y) {
        this.hitbox.setLocation(x,y);
    }

    @Override
    public abstract void resolveCollision(gameObject o);

    //getters and setters
    public int getX(){ return this.x; }
    public int getY(){ return this.y; }
    public BufferedImage getImg() {return this.img;}
    public void setImg(BufferedImage img) {this.img = img;}
    public void setX(int x){ this.x = x; }
    public void setY(int y){ this.y = y; }

    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.img, x, y, null);
    }

}
