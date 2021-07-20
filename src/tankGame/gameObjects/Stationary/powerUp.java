package tankGame.gameObjects.Stationary;

import tankGame.gameObjects.gameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class powerUp extends gameObject {

    protected boolean collected;

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public boolean isCollected() {
        return collected;
    }

    public powerUp(int x, int y, BufferedImage img) {
        super(x, y, img);
        this.collected = false;
    }

    @Override
    public void drawImage(Graphics g) {
        //only draw if power up is not collected
        if(!collected) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.getImg(), this.getX(), this.getY(), null);
        }
    }

}
