package tankGame.gameObjects.Stationary;


import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Breakable wall is a subclass of wall
 */
public class BreakWall extends Wall{

    public BreakWall(int x, int y, BufferedImage img) {
       super(x,y,img);
    }

    @Override
    public void drawImage(Graphics g) {
        //only draw if wall is not destroyed
        if(!super.isDestroyed()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.getImg(), this.getX(), this.getY(), null);
        }
    }
}
