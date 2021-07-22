package tankGame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Resource {
    private static Map<String, BufferedImage> resources;

    static {
        Resource.resources = new HashMap<>();
        try {
            Resource.resources.put("t1img",read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("tank1.png"))));
            Resource.resources.put("t2img",read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("tank2.png"))));
            Resource.resources.put("breakWall",read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("break.png"))));
            Resource.resources.put("unBreakWall",read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("unbreak.png"))));
            Resource.resources.put("powerUpImg",read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("powerup.png"))));
            Resource.resources.put("powerUpImg2",read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("powerup2.png"))));
            Resource.resources.put("powerUpImg3",read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("powerup3.png"))));
            Resource.resources.put("bulletImg",read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("bullet.png"))));
            Resource.resources.put("t1Lives",read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("t1Lives.png"))));
            Resource.resources.put("t2Lives",read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("t2Lives.png"))));
            Resource.resources.put("health",read(Objects.requireNonNull(gameLoader.class.getClassLoader().getResource("health.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage getImg(String key) {
        return Resource.resources.get(key);
    }
}
