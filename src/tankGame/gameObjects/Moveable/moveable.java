package tankGame.gameObjects.Moveable;

import tankGame.GameConstants;
import tankGame.gameObjects.gameObject;

import java.awt.image.BufferedImage;

abstract class moveable extends gameObject {
    private int vx;
    private int vy;
    private float angle;
    private int R;

    public moveable(int x, int y, float angle, BufferedImage img, int vx, int vy, int R) {
        super(x, y, img);
        this.angle = angle;
        this.vx = vx;
        this.vy = vy;
        this.R = R;
    }

    protected void moveForwards() {
        this.setVx((int)Math.round(this.getR() * Math.cos(Math.toRadians(this.getAngle()))));
        this.setVy((int)Math.round(this.getR() * Math.sin(Math.toRadians(this.getAngle()))));
        this.setX(this.getX()+this.getVx());
        this.setY(this.getY()+this.getVy());
        checkBorder();
        //System.out.println("x: "+this.getX()+"\t,y: " + this.getY());
        //updates hitbox of moveable object
        setHitbox(this.getX(),this.getY());
    }

    protected void moveBackwards() {
        this.setVx((int)Math.round(this.getR() * Math.cos(Math.toRadians(this.getAngle()))));
        this.setVy((int)Math.round(this.getR() * Math.sin(Math.toRadians(this.getAngle()))));
        this.setX(this.getX()-this.getVx());
        this.setY(this.getY()-this.getVy());
        checkBorder();
        //updates hitbox of moveable object
        setHitbox(this.getX(),this.getY());
    }

    private void checkBorder() {
        if (this.getX() < 30) {
            this.setX(30);
        }
        if (this.getX() >= GameConstants.WORLD_WIDTH - 88) {
            this.setX(GameConstants.WORLD_WIDTH - 88);
        }
        if (this.getY() < 40) {
            this.setY(40);
        }
        if (this.getY() >= GameConstants.WORLD_HEIGHT - 80) {
            this.setY(GameConstants.WORLD_HEIGHT - 80);
        }
    }



    //setters
    public void setVx(int velocity) {this.vx=velocity;}
    public void setVy(int velocity) {this.vy=velocity;}
    public void setAngle(float angle) {this.angle=angle;}

    //getters
    public int getVx(){return this.vx;}
    public int getVy(){return this.vy;}
    public float getAngle(){return this.angle;}
    public int getR() {return this.R;}



}
