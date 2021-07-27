package tankGame.gameObjects.Stationary;

import tankGame.gameObjects.Moveable.Tank;

import java.awt.image.BufferedImage;

/**
 * speed powerup increase tank speed
 */
public class speedPowerUp extends powerUp{
    public speedPowerUp(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void setEffects(Tank t) {
        t.setR(t.getR()+1);
        System.out.println("Tank Speed: " + t.getR());
    }

}
