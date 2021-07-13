package tankGame.gameObjects.Moveable;

import tankGame.GameConstants;
import tankGame.gameObjects.gameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends moveable {
    private final int R = 7;
    Rectangle hitBox;

    public Bullet(int x, int y, float angle, BufferedImage img) {
        super(x, y, angle, img,4,4);
        this.hitBox = new Rectangle(x,y,img.getWidth(),img.getHeight());
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



    public void moveForwards() {
        this.setVx((int)Math.round(R * Math.cos(Math.toRadians(this.getAngle()))));
        this.setVy((int)Math.round(R * Math.sin(Math.toRadians(this.getAngle()))));
        this.setX(this.getX()+this.getVx());
        this.setY(this.getY()+this.getVy());
        checkBorder();
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.getX(), this.getY());
        rotation.rotate(Math.toRadians(this.getAngle()), this.getImg().getWidth() / 2.0, this.getImg().getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.drawImage(this.getImg(), rotation, null);
        g2d.drawRect(this.getX(),this.getY(),this.getImg().getWidth(),this.getImg().getHeight());
    }

}
