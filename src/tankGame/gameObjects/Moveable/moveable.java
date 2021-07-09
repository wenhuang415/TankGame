package tankGame.gameObjects.Moveable;

import tankGame.gameObjects.gameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract class moveable extends gameObject {
    private int vx;
    private int vy;

    public moveable(int height, int width, float angle, BufferedImage img, int vx, int vy) {
        super(height, width, angle, img);
        this.vx = vx;
        this.vy = vy;
    }

    @Override
    public abstract void resolveCollision(gameObject o);

    //function to get Hitbox for collision detection
    protected Rectangle getHitbox() {
        return new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());
    }

    //setters for velocity
    public void setVx(int velocity) {vx=velocity;}
    public void setVy(int velocity) {vy=velocity;}

    //getters for velocity
    public int getVx(){return this.vx;}
    public int getVy(){return this.vy;}



}
