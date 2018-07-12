import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Teazel {
    private static BufferedImage image;
    public int positionX;
    public int positionY;

    public Teazel(int positionX, int positionY) {
        try {
            image = ImageIO.read(new File("sim.png"));
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
