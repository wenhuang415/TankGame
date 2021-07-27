package tankGame;

import tankGame.gameObjects.Moveable.Tank;
import tankGame.gameObjects.Moveable.TankControl;
import tankGame.gameObjects.Stationary.*;
import tankGame.gameObjects.gameObject;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Objects;

public class gameLoader extends JPanel implements Runnable {
    private BufferedImage world;
    private Launcher lf;
    ArrayList<gameObject> gameObjects;
    public static long tickCount = 0;

    public gameLoader(Launcher lf){
        this.lf = lf;
    }

    //Function to run game state
    @Override
    public void run() {
        try {
            this.resetGame();
            while (true) {
                //get tank from gameObjects array list
                Tank t1 = (Tank)gameObjects.get(0);
                Tank t2 = (Tank)gameObjects.get(1);
                //update all gameObjects
                gameObjects.forEach(gameObject -> gameObject.update());
                this.repaint();   // redraw game
                try {
                    //resolve collisions with tank 1 and all other gameObjects
                    //tank 1 at index 0 so start at index 1
                    for(int i = 1; i < gameObjects.size(); i++) {
                        t1.resolveCollision(gameObjects.get(i));
                        if(gameObjects.get(i).isDestroyed()) gameObjects.remove(i);
                    }
                    //resolve collisions with tank 2 and all other gameObjects
                    for(int i = 0; i < gameObjects.size(); i++) {
                        if(i == 1) i++;//tank2 at index 1 so skip index 1
                        t2.resolveCollision(gameObjects.get(i));
                        if(gameObjects.get(i).isDestroyed()) gameObjects.remove(i);
                    }
                } catch(ConcurrentModificationException e) {
                    System.out.println(e);
                }

                //reset tank position and subtract lives if health drops to 0
                if(t1.getHealth() < 1) {
                    t1.reduceLife();
                    resetTank();
                }
                if(t2.getHealth() < 1) {
                    t2.reduceLife();
                    resetTank();
                }

                tickCount++;
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                //if lives of either tank is 0 then end game
                if((t1.getLives()<1)||(t2.getLives()<1)){
                    this.lf.setFrame("end");
                    return;
                }
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }



    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        //initialize bufferImage for the world
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        //initialize arrayList of all gameObjects created
        gameObjects = new ArrayList<>();

        //initialize tanks and tank controls
        Tank t1 = new Tank(300, 300,  Resource.getImg("t1img"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);

        Tank t2 = new Tank(1300, 1300,  Resource.getImg("t2img"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc2);

        //add Tanks to gameObjects arrayList
        gameObjects.add(t1);
        gameObjects.add(t2);

        try {
            //load map
            InputStreamReader isr = new InputStreamReader(gameLoader.class.getClassLoader().getResourceAsStream("maps/map1"));
            BufferedReader mapReader = new BufferedReader(isr);
            //read map data
            String row = mapReader.readLine();
            if(row == null) {
                throw new IOException("no data in file");
            }//put map data into String[]
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);
            for(int curRow = 0; curRow < numRows; curRow++) {
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for(int curCol = 0; curCol < numCols; curCol++) {
                    switch(mapInfo[curCol]) {//initialize gameObjects base on map info
                        case "2"://breakable wall for value 2
                            this.gameObjects.add(new BreakWall(curCol*32,curRow*32,Resource.getImg("breakWall")));
                            break;
                        case "3"://health power up for value 3
                            this.gameObjects.add(new healthPowerUp(curCol*32, curRow*32, Resource.getImg("powerUpImg3")));
                            break;
                        case "4"://speed power up for value 4
                            this.gameObjects.add(new speedPowerUp(curCol*32, curRow*32, Resource.getImg("powerUpImg2")));
                            break;
                        case "5"://rapidfire power upfor value 5
                            this.gameObjects.add(new rapidfirePowerUp(curCol*32, curRow*32, Resource.getImg("powerUpImg")));
                            break;
                        case "8"://unbreakable wall
                        case "9"://border wall
                            this.gameObjects.add(new Wall(curCol*32,curRow*32,Resource.getImg("unBreakWall")));
                            break;
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }


    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();

        //set background to black to remove trails
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);

        Tank t1 = (Tank)gameObjects.get(0);
        Tank t2 = (Tank)gameObjects.get(1);
        try {
            //add gameObjects to buffer
            this.gameObjects.forEach(gameObject -> gameObject.drawImage(buffer));

        } catch (ConcurrentModificationException e) {
            System.out.println(e);
        }

        //add lives to buffer
        BufferedImage t1Lives = t1.displayLives(Resource.getImg("t1Lives"));
        BufferedImage t2Lives = t2.displayLives(Resource.getImg("t2Lives"));
        //add left half of split screen to buffer image
        BufferedImage leftHalf = world.getSubimage(t1.xLim()-(GameConstants.GAME_SCREEN_WIDTH/4),t1.yLim()-(GameConstants.GAME_SCREEN_HEIGHT/3),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        //add right half of split screen to buffer image
        BufferedImage rightHalf = world.getSubimage(t2.xLim()-(GameConstants.GAME_SCREEN_WIDTH/4),t2.yLim()-(GameConstants.GAME_SCREEN_HEIGHT/3),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        //add minimap to buffer image
        BufferedImage minimap = world.getSubimage(0,0,GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        g2.drawImage(leftHalf,0,0,null);
        g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2 + 4,0,null);
        //draw lives
        g2.drawImage(t1Lives,0, 0, null);
        g2.drawImage(t2Lives,GameConstants.GAME_SCREEN_WIDTH/2, 0, null);
        //draw hp bar of tanks
        g2.drawImage(t1.displayHealth(),0,GameConstants.GAME_SCREEN_HEIGHT-55,null);
        g2.drawImage(t2.displayHealth(),(GameConstants.GAME_SCREEN_WIDTH-80),GameConstants.GAME_SCREEN_HEIGHT-55,null);
        g2.scale(.1,.1);
        g2.drawImage(minimap, GameConstants.WORLD_WIDTH*2,GameConstants.WORLD_HEIGHT*2+1200,null);
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tickCount = 0;
        gameInitialize();
    }

    //function to reset position of tanks after one is destroyed
    private void resetTank(){
        Tank t1 = (Tank)gameObjects.get(0);
        Tank t2 = (Tank)gameObjects.get(1);
        t1.setHealth(10);
        t2.setHealth(10);
        t1.setAngle(0);
        t2.setAngle(180);
        t1.setX(300);
        t1.setY(300);
        t2.setX(1300);
        t2.setY(1300);
        t1.update();
        t2.update();
    }

    //function that plays background music
    public void backgroundMusic(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Clip clip;
                try {
                    //background music code found on https://www.codegrepper.com/code-examples/java/music+loop+java
                    //AudioInputStream music = AudioSystem.getAudioInputStream(new File("resources/Music.wav"));
                    AudioInputStream music = AudioSystem.getAudioInputStream(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("Music.wav")));
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
}