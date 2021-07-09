package tankGame.gameObjects.Moveable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import tankGame.GameConstants;
import tankGame.gameObjects.Moveable.Bullet;
import tankGame.gameObjects.Moveable.moveable;
import tankGame.gameObjects.gameObject;

public class Tank extends moveable {
    private int health;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    private final int R = 2;
    private final float ROTATIONSPEED = 3.0f;


    public Tank(int height, int width, float angle, BufferedImage img) {
        super(height, width, angle, img, 0, 0);
    }



    @Override
    public void resolveCollision(gameObject o){
        //if colliding object is a bullet then lose health
        if(o instanceof Bullet) {
            Rectangle targetHitbox = ((Bullet) o).getHitbox();
            //if intersects set velocity to 0 and pushes position back until it doesn't intersect
            if(this.getHitbox().intersects(targetHitbox)) {
                this.health -= 10;
            }
        }
    }

    void toggleShootPressed() {this.ShootPressed = true; }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleShootPressed() { this.ShootPressed = false;}

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed) {
            this.shoot();
        }
    }

    private void rotateLeft() {
        this.setAngle(this.getAngle()- this.ROTATIONSPEED);
    }

    private void rotateRight() {
        this.setAngle(this.getAngle() + this.ROTATIONSPEED);
    }

    private void moveBackwards() {
        this.setVx((int)Math.round(R * Math.cos(Math.toRadians(this.getAngle()))));
        this.setVy((int)Math.round(R * Math.sin(Math.toRadians(this.getAngle()))));
        this.setX(this.getX()-this.getVx());
        this.setY(this.getY()-this.getVy());
        checkBorder();
    }

    private void moveForwards() {
        this.setVx((int)Math.round(R * Math.cos(Math.toRadians(this.getAngle()))));
        this.setVy((int)Math.round(R * Math.sin(Math.toRadians(this.getAngle()))));
        this.setX(this.getX()+this.getVx());
        this.setY(this.getY()+this.getVy());
        checkBorder();
    }

    private void shoot() { }

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




    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.getX(), this.getY());
        rotation.rotate(Math.toRadians(this.getAngle()), this.getImg().getWidth() / 2.0, this.getImg().getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.getImg(), rotation, null);
    }

}
