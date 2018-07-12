import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SoftWall {
    int positionX;
    int positionY;
    int i;
    int j;
    private BufferedImage image;
    private int mood;

    public SoftWall(int positionX, int positionY, int j, int i) {
        mood = 0;
        this.i = i;
        this.j = j;
        try {
            image = ImageIO.read(new File("softWall" + mood + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.positionX = positionX;
        this.positionY = positionY;

    }

    public BufferedImage getImage() {
        return image;
    }


    public Rectangle getBounds() {
        return new Rectangle(positionX, positionY, 150, 150);
    }

    public int destroy() {
        mood++;
        if (mood == 1 || mood == 2 || mood == 3) {
            try {
                image = ImageIO.read(new File("softWall" + mood + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else try {
            image = ImageIO.read(new File("area.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mood;
    }
}
