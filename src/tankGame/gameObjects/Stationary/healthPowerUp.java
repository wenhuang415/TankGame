package tankGame.gameObjects.Stationary;

import tankGame.gameObjects.Moveable.Tank;
import tankGame.gameObjects.gameObject;

import java.awt.image.BufferedImage;

/**
 * health powerup class refills tank HP
 */
public class healthPowerUp extends powerUp{
    public healthPowerUp(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void setEffects(Tank t) {
        //healthPowerUp refills tank HP
        t.setHealth(10);
        System.out.println("Tank HP: " + t.getHealth());
    }

}
