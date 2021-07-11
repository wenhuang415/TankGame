package tankGame.gameObjects.Moveable;

import tankGame.gameObjects.gameObject;

import java.awt.image.BufferedImage;

abstract class moveable extends gameObject {
    private int vx;
    private int vy;
    private float angle;

    public moveable(int x, int y, float angle, BufferedImage img, int vx, int vy) {
        super(x, y, img);
        this.angle = angle;
        this.vx = vx;
        this.vy = vy;
    }



    //function to get Hitbox for collision detection
    //protected Rectangle getHitbox() {
        //return new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());
    //}

    //setters
    public void setVx(int velocity) {this.vx=velocity;}
    public void setVy(int velocity) {this.vy=velocity;}
    public void setAngle(float angle) {this.angle=angle;}

    //getters
    public int getVx(){return this.vx;}
    public int getVy(){return this.vy;}
    public float getAngle(){return this.angle;}



}
