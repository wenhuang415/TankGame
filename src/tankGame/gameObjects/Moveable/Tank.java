package tankGame.gameObjects.Moveable;

import tankGame.GameConstants;
import tankGame.Resource;
import tankGame.gameLoader;
import tankGame.gameObjects.Stationary.Wall;
import tankGame.gameObjects.Stationary.powerUp;
import tankGame.gameObjects.gameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank extends moveable {
    private int health;
    private int lives;
    private ArrayList<Bullet> ammo;


    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    private final float ROTATIONSPEED = 3.0f;
    private int fireRate = 50;

    public Tank(int x, int y, BufferedImage img) {
        super(x, y,0,img,0 ,0 , 2);
        this.ammo = new ArrayList<>();
        this.health = 10;
        this.lives = 3;
    }

    //function to reduce tank lives by 1
    public void reduceLife(){
        this.lives--;
        System.out.println("lives: " + this.lives);
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

    //function to get tank hp bar
    public BufferedImage displayHealth(){
        //get health bar
        BufferedImage hp = Resource.getImg("health");
        //crop health bar base on tank hp value
        int width = health % 10;
        if(health < 10) {
            hp = hp.getSubimage(0,0,(60/10) * width,15);
        }
        return hp;
    }

    //function to display amount of lives
    public BufferedImage displayLives(BufferedImage lives) {
        //crop lives image base on how many lives there are left
        switch(this.lives) {
            case 2 :
                lives = lives.getSubimage(0,0, 104,45);
                break;
            case 1 :
                lives = lives.getSubimage(0,0, 52,45);
                break;
        }
        return lives;
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
                //if instance of power up, get the effect of the power up then destory powerup
            } else if((o instanceof powerUp)) {
                ((powerUp) o).setEffects(this);
                o.destroy();
            }
        }

        //resolve ammo collision
        for(int i = 0; i < ammo.size(); i++) {
            Bullet b = ammo.get(i);
            b.resolveCollision(o);
            //if bullet collided with something then remove it
            if(b.isDestroyed()) {
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

    @Override
    public void update() {
        super.setHitbox(this.getX(),this.getY());
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
        if (this.ShootPressed && gameLoader.tickCount % fireRate == 0) {
            Bullet bullet = new Bullet(this.getX(),this.getY(),this.getAngle(), Resource.getImg("bulletImg"));
            this.ammo.add(bullet);
        }

        //move each bullet forward
        this.ammo.forEach(bullet -> bullet.moveForwards());
    }

    //getters
    public int getHealth() {
        return this.health;
    }
    public int getFireRate(){return this.fireRate;}
    public int getLives(){return this.lives;}

    //setters
    public void takeDamage(){this.health--;}
    public void setHealth(int hp) {
        this.health = hp;
    }
    public void setFireRate(int fireRate) { this.fireRate = fireRate;}
    private void rotateLeft() {
        this.setAngle((this.getAngle()- this.ROTATIONSPEED));
    }
    private void rotateRight() {
        this.setAngle(this.getAngle() + this.ROTATIONSPEED);
    }
    public void setLives(int lives) {
        this.lives = lives;
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
