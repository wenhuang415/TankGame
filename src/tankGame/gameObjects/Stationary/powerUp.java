package tankGame.gameObjects.Stationary;

import tankGame.gameObjects.Moveable.Tank;
import tankGame.gameObjects.gameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * abstract class for powerUps
 */
public abstract class powerUp extends gameObject {


    public powerUp(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    //all powerUp subclasses must implement setEffects()
    public abstract void setEffects(Tank t);

    @Override
    public void update(){
        super.setHitbox(this.getX(),this.getY());
    }


    @Override
    public void resolveCollision(gameObject o) {
        if(this.getHitbox().intersects(o.getHitbox())) {
            if(o instanceof Tank) {
                //update tank effects
                this.setEffects((Tank) o);
                //destroy powerup after its collected
                super.destroy();
            }
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
