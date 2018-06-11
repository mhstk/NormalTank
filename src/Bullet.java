import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bullet {
    private BufferedImage image;
    private int positionX;
    private int positionY;
    private int speed;
    private int destX;
    private int destY;
    private double angel;

    public Bullet(int positionX, int positionY,int destX , int destY , String bulletImageAddress) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.destX = destX;
        this.destY = destY;
        try {
            image = ImageIO.read(new File(bulletImageAddress));
        } catch (IOException e) {
            e.printStackTrace();
        }
        angel = Math.atan2(destY - (positionY ), destX - (positionX ));
        System.out.println(Math.toDegrees(angel));
        speed = 15;
    }

    public void updateLocation(){
        positionX += Math.cos(angel)*speed;
        positionY += Math.sin(angel)*speed;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public double getAngel() {
        return angel;
    }
}
