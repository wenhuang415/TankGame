package tankGame;

import tankGame.gameObjects.Moveable.Tank;
import tankGame.gameObjects.Moveable.TankControl;
import tankGame.gameObjects.Stationary.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class gameLoader extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    private Launcher lf;
    private long tick = 0;
    ArrayList<Wall> walls;
    ArrayList<powerUp> powerUps;
    public static BufferedImage bulletImage;
    public static long tickCount = 0;

    //todo bullet types
    //todo tank types
    //todo HUD



    public gameLoader(Launcher lf){
        this.lf = lf;
    }

    //function that plays background music
    public void background(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Clip clip;
                try {
                    //background music code found on https://www.codegrepper.com/code-examples/java/music+loop+java
                    AudioInputStream music = AudioSystem.getAudioInputStream(new File("resources/Music.wav"));
                    clip = AudioSystem.getClip();
                    clip.open(music);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }


    @Override
    public void run() {
        background();
        try {
            this.resetGame();
            while (true) {
                this.tick++;
                this.t1.update(); // update tank
                this.t2.update();
                this.repaint();   // redraw game
                try {
                    //resolve collision with other tank
                    t1.resolveCollision(t2);
                    t2.resolveCollision(t1);
                    //resolve collision with bullets
                    t1.resolveBulletCollision(t2);
                    t2.resolveBulletCollision(t1);
                    //resolve bullet collision with wall
                    this.walls.forEach(wall -> t1.resolveBulletCollision(wall));
                    this.walls.forEach(wall -> t2.resolveBulletCollision(wall));

                    for(int i = 0; i < walls.size(); i++) {
                        //resolve tank collision with wall
                        t1.resolveCollision(walls.get(i));
                        t2.resolveCollision(walls.get(i));
                        //if BreakWall is destoryed then remove it
                        if((walls.get(i) instanceof BreakWall) && ((BreakWall) walls.get(i)).isBroken() ) {
                            walls.remove(i);
                        }
                    }

                    //resolve collision of powerups
                    for(int i = 0; i<powerUps.size(); i++) {
                        powerUps.get(i).resolveCollision(t1);
                        powerUps.get(i).resolveCollision(t2);
                        //if powerup is collected remove it
                        if(powerUps.get(i).isCollected()) {
                            powerUps.remove(i);
                        }
                    }
                } catch(ConcurrentModificationException e) {
                    System.out.println(e);
                }



                tickCount++;
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                //if health of either tank is 0 then end game
                if((this.t1.getHealth()<1) || (this.t2.getHealth()<1)){
                    this.lf.setFrame("end");
                    return;
                }
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;

        t1.setHealth(10);
        t2.setHealth(10);
        this.t1.setX(300);
        this.t1.setY(300);
        this.t2.setX(1300);
        this.t2.setY(1300);

    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        BufferedImage t1img = null;
        BufferedImage t2img = null;
        BufferedImage breakWall = null;
        BufferedImage unBreakWall = null;
        BufferedImage powerUpImg = null;
        BufferedImage powerUpImg2 = null;
        BufferedImage powerUpImg3 = null;

        powerUps = new ArrayList<>();
        walls = new ArrayList<>();
        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            t1img = read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("tank1.png")));
            t2img = read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("tank2.png")));
            breakWall = read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("break.png")));
            unBreakWall = read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("unbreak.png")));
            powerUpImg = read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("powerup.png")));
            powerUpImg2 = read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("powerup2.png")));
            powerUpImg3 = read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("powerup3.png")));

            //load bullet image
            gameLoader.bulletImage = read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("bullet.png")));
            //load background image

            //load map
            InputStreamReader isr = new InputStreamReader(gameLoader.class.getClassLoader().getResourceAsStream("maps/map1"));
            BufferedReader mapReader = new BufferedReader(isr);

            //read map data
            String row = mapReader.readLine();
            if(row == null) {
                throw new IOException("no data in file");
            }
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);
            for(int curRow = 0; curRow < numRows; curRow++) {
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for(int curCol = 0; curCol < numCols; curCol++) {
                    switch(mapInfo[curCol]) {
                        case "2"://breakable wall
                            this.walls.add(new BreakWall(curCol*32,curRow*32,breakWall));
                            break;
                        case "3"://health power up
                            this.powerUps.add(new healthPowerUp(curCol*32, curRow*32, powerUpImg3));
                            break;
                        case "4"://speed power up
                            this.powerUps.add(new speedPowerUp(curCol*32, curRow*32, powerUpImg2));
                            break;
                        case "5"://rapidfire power up
                            this.powerUps.add(new rapidfirePowerUp(curCol*32, curRow*32, powerUpImg));
                            break;
                        case "8"://unbreakable wall
                        case "9"://border wall
                            this.walls.add(new Wall(curCol*32,curRow*32,unBreakWall));
                            break;
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        t1 = new Tank(300, 300,  t1img);
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);

        t2 = new Tank(1300, 1300,  t2img);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc2);
    }



    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();


        //set background to black to remove trails
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);
        try {
            //paint the walls
            this.walls.forEach(wall -> wall.drawImage(buffer));
            //paint powerups
            this.powerUps.forEach(powerup -> powerup.drawImage(buffer));
            //draw the tanks
            this.t1.drawImage(buffer);
            this.t2.drawImage(buffer);
        } catch (ConcurrentModificationException e) {
            System.out.println(e);
        }


        BufferedImage leftHalf = world.getSubimage(t1.xLim()-(GameConstants.GAME_SCREEN_WIDTH/4),t1.yLim()-(GameConstants.GAME_SCREEN_HEIGHT/3),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightHalf = world.getSubimage(t2.xLim()-(GameConstants.GAME_SCREEN_WIDTH/4),t2.yLim()-(GameConstants.GAME_SCREEN_HEIGHT/3),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage minimap = world.getSubimage(0,0,GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);

        g2.drawImage(leftHalf,0,0,null);
        g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2 + 4,0,null);
        g2.scale(.1,.1);
        g2.drawImage(minimap, GameConstants.WORLD_WIDTH*2,GameConstants.WORLD_HEIGHT*2+1200,null);
        //g2.drawImage(world,0,0,null);
    }
}
