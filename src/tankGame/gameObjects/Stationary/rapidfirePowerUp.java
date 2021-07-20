package tankGame.gameObjects.Stationary;

import tankGame.gameObjects.Moveable.Tank;
import tankGame.gameObjects.gameObject;

import java.awt.image.BufferedImage;

public class rapidfirePowerUp extends powerUp{
    public rapidfirePowerUp(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void resolveCollision(gameObject o) {
        //if gameObject is a tank and hitbox intersects then add health to tank and set collected to true
        if((o instanceof Tank) && (this.getHitbox().intersects(o.getHitbox()))) {
            ((Tank) o).setFireRate(((Tank) o).getFireRate()-10);
            this.collected = true;
            System.out.println("Tank Firerate: " + ((Tank) o).getFireRate());
        }
    }
}
