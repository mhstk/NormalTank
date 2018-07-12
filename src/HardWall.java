import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HardWall {
    private static BufferedImage image;
     int positionX;
     int positionY;

    public HardWall(int positionX, int positionY) {
        try {
            image = ImageIO.read(new File("hardWall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Rectangle getBounds() {
        return new Rectangle(positionX, positionY, 150, 150);
    }

    public static BufferedImage getImage() {
        return image;
    }
}
