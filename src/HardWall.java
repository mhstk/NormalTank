import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HardWall {
    private BufferedImage image;
    private int positionX;
    private int positionY;

    public HardWall(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        try {
            image = ImageIO.read(new File("softWall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(positionX, positionY, image.getWidth(), image.getHeight());
    }
}
