package tankGame.gameObjects.Stationary;

import tankGame.gameObjects.gameObject;
import java.awt.image.BufferedImage;

public class Wall extends gameObject {

    public Wall(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void resolveCollision(gameObject o) {

    }

}
