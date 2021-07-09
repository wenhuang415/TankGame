package tankGame.gameObjects.Moveable;

import tankGame.GameConstants;
import tankGame.gameObjects.gameObject;
import tankGame.gameObjects.Moveable.moveable;

import java.awt.image.BufferedImage;

public class Bullet extends moveable {
    private final int R = 4;

    public Bullet(int height, int width, float angle, BufferedImage img) {
        super(height, width, angle, img,4,4);
    }

    @Override
    public void resolveCollision(gameObject o) {

    }

    private void checkBorder() {
        if (this.getX() < 30) {
            this.setX(30);
        }
        if (this.getX() >= GameConstants.GAME_SCREEN_WIDTH - 88) {
            this.setX(GameConstants.GAME_SCREEN_WIDTH - 88);
        }
        if (this.getY() < 40) {
            this.setY(40);
        }
        if (this.getY() >= GameConstants.GAME_SCREEN_HEIGHT - 80) {
            this.setY(GameConstants.GAME_SCREEN_HEIGHT - 80);
        }
    }


    private void moveForwards() {
        this.setVx((int)Math.round(R * Math.cos(Math.toRadians(this.getAngle()))));
        this.setVy((int)Math.round(R * Math.sin(Math.toRadians(this.getAngle()))));
        this.setX(this.getX()+this.getVx());
        this.setY(this.getY()+this.getVy());
        checkBorder();
    }

}
