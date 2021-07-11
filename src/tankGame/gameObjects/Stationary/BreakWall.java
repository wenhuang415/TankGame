package tankGame.gameObjects.Stationary;


import tankGame.gameObjects.gameObject;

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
}
