import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class SuperTank extends Moving {
    protected BufferedImage gunImage;
    protected int bulletSpeed;
    protected double difTimeBullet;
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
}
