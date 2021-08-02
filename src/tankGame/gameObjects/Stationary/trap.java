package tankGame.gameObjects.Stationary;

import tankGame.gameLoader;
import tankGame.gameObjects.Moveable.Tank;
import tankGame.gameObjects.gameObject;

import java.awt.image.BufferedImage;

//trap class makes tank take damage upon collision
public class trap extends gameObject {
    public trap(int x, int y, BufferedImage img) {
        super(x, y, img);
    }


    @Override
    public void resolveCollision(gameObject o) {
        if(this.getHitbox().intersects(o.getHitbox())) {
            //if collided with a tank then damage the tank every 40 ticks
            if(o instanceof Tank && gameLoader.tickCount%40==0) {
                ((Tank) o).takeDamage();
                System.out.println("HP: " + ((Tank) o).getHealth());
            }
        }
    }

    @Override
    public void update(){
        super.setHitbox(this.getX(),this.getY());
    }
}
