import java.awt.*;
import java.awt.image.BufferedImage;

public class Teazel {
    private BufferedImage image;
    public int positionX;
    public int positionY;

    public Teazel(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Rectangle getBounds() {
        return new Rectangle(positionX, positionY, 150, 150);
    }
}
