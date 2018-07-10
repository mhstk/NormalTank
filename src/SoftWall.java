import java.awt.*;
import java.awt.image.BufferedImage;

public class SoftWall {private BufferedImage image;
    private int positionX;
    private int positionY;

    public SoftWall(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Rectangle getBounds() {
        return new Rectangle(positionX, positionY, image.getWidth(), image.getHeight());
    }
}
