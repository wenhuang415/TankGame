package tankGame.gameObjects.Moveable;

import tankGame.gameObjects.Stationary.BreakWall;
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
    public void update() {
        super.setHitbox(this.getX(),this.getY());
    }

    @Override
    public void resolveCollision(gameObject o) {
        if(this.getHitbox().intersects(o.getHitbox())){
            if(o instanceof Tank) {
                System.out.println("Tank HP: " + ((Tank) o).getHealth());
                ((Tank) o).takeDamage();
                this.collided = true;
            }
            if(o instanceof Wall){
                if(o instanceof BreakWall){
                    ((BreakWall)o).Destroy();
                }
                this.collided = true;
            }
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
