package tankGame.gameObjects.Stationary;

import tankGame.gameObjects.Moveable.Tank;
import tankGame.gameObjects.gameObject;

import java.awt.image.BufferedImage;

public class healthPowerUp extends powerUp{
    public healthPowerUp(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void resolveCollision(gameObject o) {
        //if gameObject is a tank and hitbox intersects then add health to tank and set collected to true
        if((o instanceof Tank) && (this.getHitbox().intersects(o.getHitbox()))) {
            ((Tank) o).setHealth(10);
            this.collected = true;
            System.out.println("Tank HP: " + ((Tank) o).getHealth());
        }
    }
}
