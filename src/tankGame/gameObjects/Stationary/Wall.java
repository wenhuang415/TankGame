package tankGame.gameObjects.Stationary;

import tankGame.gameObjects.gameObject;
import java.awt.image.BufferedImage;

/**
 * class representing wall
 */
public class Wall extends gameObject {
    public Wall(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    @Override
    public void resolveCollision(gameObject o) {

    }

}
