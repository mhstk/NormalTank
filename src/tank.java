import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tank extends Moving {
    protected BufferedImage gunImage;
    protected double angelBody;
    protected double angelGun;
    protected int bulletSpeed;
    protected double difTimeBullet;

    protected Tank(String imageFileBody , String imageFileGun ,String bulletImageAddress){
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
}
