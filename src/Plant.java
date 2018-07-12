import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Plant {
    private static BufferedImage image;
    private int positionX;
    private int positionY;

    public Plant(int positionX , int positionY){
        try {
            image= ImageIO.read(new File("plant.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Rectangle getBounds(){
        return new Rectangle(positionX,positionY,image.getWidth(),image.getHeight());
    }

    public static BufferedImage getImage() {
        return image;
    }
}
