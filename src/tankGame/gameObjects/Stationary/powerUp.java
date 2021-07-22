package tankGame.gameObjects.Stationary;

import tankGame.gameObjects.Moveable.Tank;
import tankGame.gameObjects.gameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class powerUp extends gameObject {


    public powerUp(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    public abstract void setEffects(Tank t);

    @Override
    public void update(){

    }



    @Override
    public void resolveCollision(gameObject o) {
        //if gameObject is a tank and hitbox intersects then add health to tank and set collected to true
        if((o instanceof Tank) && (this.getHitbox().intersects(o.getHitbox()))) {
            this.setEffects((Tank) o);
            super.Destroy();
        }
    }

    @Override
    public void drawImage(Graphics g) {
        //only draw if power up is not collected
        if(!super.isDestroyed()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.getImg(), this.getX(), this.getY(), null);
        }
    }

}
