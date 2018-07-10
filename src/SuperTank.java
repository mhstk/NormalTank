import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class SuperTank extends Moving {
    protected BufferedImage gunImage;
    protected double angelGun;
    protected double angelBody;

    public SuperTank(String imageFileBody , String imageFileGun , String bulletImageAddress){
        super(bulletImageAddress);
        try {
            image = ImageIO.read(new File(imageFileBody));
            gunImage = ImageIO.read(new File(imageFileGun));
        } catch (IOException e) {
            e.printStackTrace();
        }
        angelBody = 0;
        angelGun = 0;
    }
    @Override
    public void move() {

    }

    public Rectangle getBounds(){
        return  new Rectangle(positionX , positionY , 180,134) ;
    }
}
