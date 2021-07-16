package tankGame.gameObjects.Stationary;


import tankGame.gameObjects.gameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakWall extends Wall{
    private boolean broken;

    public BreakWall(int x, int y, BufferedImage img) {
       super(x,y,img);
        this.broken = false;
    }

    @Override
    public void resolveCollision(gameObject o) {

    }

    @Override
    public void drawImage(Graphics g) {
        //only draw if wall is not broken
        if(!broken) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.getImg(), this.getX(), this.getY(), null);
        }
    }
}
