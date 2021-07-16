package tankGame.gameObjects.Moveable;

import tankGame.gameLoader;
import tankGame.gameObjects.Stationary.Wall;
import tankGame.gameObjects.gameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends moveable {
    private boolean collided;


    public Bullet(int x, int y, float angle, BufferedImage img) {
        super(x, y, angle, img,4,4, 7);
        this.collided = false;
    }


    @Override
    public void resolveCollision(gameObject o) {
        //System.out.println("x,y: " + this.getX() + "," + this.getY());
        //System.out.println("hitBox: "+this.getHitbox());
        if(o instanceof Tank && gameLoader.tickCount % 40 == 0) {
            System.out.println("hp: " + ((Tank) o).getHealth());
            ((Tank) o).takeDamage();
            this.collided = true;
        }

        if(o instanceof Wall){
            this.collided = true;
        }
    }

    public boolean isCollided() {
        return collided;
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
