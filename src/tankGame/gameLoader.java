package tankGame;

import tankGame.gameObjects.Moveable.Tank;
import tankGame.gameObjects.Moveable.TankControl;
import tankGame.gameObjects.Stationary.BreakWall;
import tankGame.gameObjects.Stationary.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class gameLoader extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    private Launcher lf;
    private long tick = 0;
    ArrayList<Wall> walls;
    public static BufferedImage bulletImage;
    public static long tickCount = 0;

    public gameLoader(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            this.resetGame();
            while (true) {
                this.tick++;
                this.t1.update(); // update tank
                this.t2.update();
                this.repaint();   // redraw game
                tickCount++;
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
                if(this.tick > 4000){
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
        this.t1.setX(300);
        this.t1.setY(300);
        this.t2.setX(500);
        this.t2.setY(500);
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
            //load bullet image
            gameLoader.bulletImage = read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("bullet.png")));

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
                        case "3"://powerup type1
                            break;
                        case "4"://powerup type2
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
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_NUMPAD0);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);

        t2 = new Tank(500, 500,  t2img);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_F);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc2);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();

        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);
        this.walls.forEach(wall -> wall.drawImage(buffer));
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);

        BufferedImage leftHalf = world.getSubimage(t1.getX()-(GameConstants.GAME_SCREEN_WIDTH/4),t1.getY()-(GameConstants.GAME_SCREEN_HEIGHT/4),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightHalf = world.getSubimage(t2.getX()-(GameConstants.GAME_SCREEN_WIDTH/4),t2.getY()-(GameConstants.GAME_SCREEN_HEIGHT/4),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage minimap = world.getSubimage(0,0,GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);



        g2.drawImage(leftHalf,0,0,null);
        g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2 + 4,0,null);
        g2.scale(.1,.1);
        g2.drawImage(minimap, GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT,null);
        //g2.drawImage(world,0,0,null);
    }
}
