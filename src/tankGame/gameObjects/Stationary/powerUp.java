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
    public void update(){}


    @Override
    public void resolveCollision(gameObject o) {}

    @Override
    public void drawImage(Graphics g) {
        //only draw if power up is not collected
        if(!super.isDestroyed()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.getImg(), this.getX(), this.getY(), null);
        }
    }

}
