package tankGame.gameObjects.Stationary;

import tankGame.gameObjects.Moveable.Tank;
import tankGame.gameObjects.gameObject;

import java.awt.image.BufferedImage;

/**
 * class representing wall
 */
public class Wall extends gameObject {
    public Wall(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    @Override
    public void resolveCollision(gameObject o) {
        //check if hitbox collides
        if(this.getHitbox().intersects(o.getHitbox())) {
            if(o instanceof Tank) {
                //move the tank forward if tank is moving back
                if(((Tank) o).isDownPressed()) {
                    o.setX(o.getX()+((Tank) o).getVx());
                    o.setY(o.getY()+((Tank) o).getVy());
                    //move the tank back if the tank is moving forwards
                } else if(((Tank) o).isUpPressed()) {
                    o.setX(o.getX()-((Tank) o).getVx());
                    o.setY(o.getY()-((Tank) o).getVy());
                }
            }
        }
    }

}
