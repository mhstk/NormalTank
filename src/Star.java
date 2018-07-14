import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Star {
    int positionX;
    int positionY;
    int i;
    int j;
    private boolean use;
    private BufferedImage image;

    public Star(int positionX, int positionY, int j, int i) {
        this.i = i;
        this.j = j;
        try {
            image = ImageIO.read(new File("star.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.positionX = positionX;
        this.positionY = positionY;
        use = false;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle(positionX + 20, positionY + 20, 100, 100);
    }

    public void changeImage() {
        try {
            image = ImageIO.read(new File("area.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sound sound = new Sound("star.wav",0);
        sound.execute();
        use = true;
    }

    public boolean used() {
        return use;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
