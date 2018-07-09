import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Turret {

    protected BufferedImage image;
    protected BufferedImage gunImage;
    protected double angelGun;
    protected double angelBody;
    public void Turret(double angelBody, String imageFileBody , String imageFileGun){
        angelGun = 0;
        this.angelBody = angelBody;

        try {
            image = ImageIO.read(new File(imageFileBody));
            gunImage = ImageIO.read(new File(imageFileGun));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
