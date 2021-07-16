package tankGame.gameObjects.Moveable;

import tankGame.GameConstants;
import tankGame.gameLoader;
import tankGame.gameObjects.gameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank extends moveable {
    private int health;
    private ArrayList<Bullet> ammo;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    //private final int R = 2;
    private final float ROTATIONSPEED = 3.0f;


    public Tank(int x, int y, BufferedImage img) {
        super(x, y,0,img,0 ,0 , 2);
        this.ammo = new ArrayList<>();
    }

    //function to limit x coordinate for split screen drawing
    public int xLim(){
        if(this.getX() < 280 ) {
            return 280;
        } else if(this.getX() > 1480){
            return 1480;
        } else {
            return this.getX();
        }
    }
    //function to limit y coordinate for split screen drawing
    public int yLim(){
        if(this.getY() < 200) {
            return 200;
        } else if(this.getY() > 1470){
            return 1470;
        } else {
            return this.getY();
        }
    }


    @Override
    public void resolveCollision(gameObject o){

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
        if (this.ShootPressed && gameLoader.tickCount % 40 == 0) {
            Bullet bullet = new Bullet(this.getX(),this.getY(),this.getAngle(), gameLoader.bulletImage);
            this.ammo.add(bullet);
        }

        //move each bullet forward
        this.ammo.forEach(bullet -> bullet.moveForwards());
    }

    private void rotateLeft() {
        this.setAngle((this.getAngle()- this.ROTATIONSPEED));
    }

    private void rotateRight() {
        this.setAngle(this.getAngle() + this.ROTATIONSPEED);
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.getX(), this.getY());
        rotation.rotate(Math.toRadians(this.getAngle()), this.getImg().getWidth() / 2.0, this.getImg().getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.getImg(), rotation, null);
        this.ammo.forEach(bullet -> bullet.drawImage(g));
        g2d.setColor(Color.CYAN);
        g2d.drawImage(this.getImg(), rotation, null);
        g2d.drawRect(this.getX(),this.getY(),this.getImg().getWidth(),this.getImg().getHeight());
    }

}
