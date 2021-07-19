package tankGame.gameObjects.Moveable;

import tankGame.GameConstants;
import tankGame.gameLoader;
import tankGame.gameObjects.Stationary.Wall;
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
        this.health = 50;
    }

    //function to limit x coordinate for split screen drawing
    public int xLim(){
        //limit of how far to render to the left and right of screen
        int limit = GameConstants.GAME_SCREEN_WIDTH/4;
        if(this.getX() < limit ) {
            return limit;
        } else if(this.getX() > GameConstants.WORLD_WIDTH-limit-10){
            return GameConstants.WORLD_WIDTH-limit-10;
        } else {
            return this.getX();
        }
    }

    //function to limit y coordinate for split screen drawing
    public int yLim(){
        //limit of how far to render to the top and bottom of screen
        int limit = GameConstants.GAME_SCREEN_HEIGHT/3;
        if(this.getY() < limit) {
            return limit;
        } else if(this.getY() > GameConstants.WORLD_HEIGHT-limit-280){
            return GameConstants.WORLD_HEIGHT-limit-280;
        } else {
            return this.getY();
        }
    }

    //getter for health
    public int getHealth() {
        return health;
    }

    @Override
    public void resolveCollision(gameObject o){
        //check if there is a collision
        if(this.getHitbox().intersects(o.getHitbox())) {
            //if tank collide with another tank or wall prevent it from moving foreward
            if((o instanceof Wall) || (o instanceof Tank)) {
                //move the tank forward if tank is moving backwards
                if(this.DownPressed) {
                    this.setX(this.getX()+this.getVx());
                    this.setY(this.getY()+this.getVy());
                    //move the tank backward if tank is moving forwards
                } else if(this.UpPressed) {
                    this.setX(this.getX()-this.getVx());
                    this.setY(this.getY()-this.getVy());
                }
            }
        }
    }

    //function to resolve collsion of all bullets in ammo arrayList
    public void resolveBulletCollision(gameObject o) {
        for(int i = 0; i < ammo.size(); i++) {
            Bullet b = ammo.get(i);
            b.resolveCollision(o);
            //if bullet collided with something then remove it
            if(b.isCollided()) {
                ammo.remove(i);
                b = null;
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
        if (this.ShootPressed && gameLoader.tickCount % 30 == 0) {
            Bullet bullet = new Bullet(this.getX(),this.getY(),this.getAngle(), gameLoader.bulletImage);
            this.ammo.add(bullet);
        }

        //move each bullet forward
        this.ammo.forEach(bullet -> bullet.moveForwards());
    }

    public void takeDamage(){this.health--;}

    public void setHealth(int hp) {
        this.health = hp;
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
