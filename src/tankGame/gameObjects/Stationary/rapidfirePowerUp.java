package tankGame.gameObjects.Stationary;

import tankGame.gameObjects.Moveable.Tank;
import tankGame.gameObjects.gameObject;

import java.awt.image.BufferedImage;

public class rapidfirePowerUp extends powerUp{
    public rapidfirePowerUp(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void setEffects(Tank t) {
        t.setFireRate(t.getFireRate()-10);
        System.out.println("Tank Firerate: " + t.getFireRate());
    }

}
