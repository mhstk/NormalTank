import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
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

    public Bullet(int positionX, int positionY,int destX , int destY , String bulletImageAddress , int speed) {
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

        this.speed = speed;
    }


    public void updateLocation(){
        positionX += Math.cos(angel)*speed;
        positionY += Math.sin(angel)*speed;
    }

    public Rectangle getBounds(){
        return new Rectangle(positionX , positionY , image.getWidth(),image.getHeight()) ;
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

    public double getAngelBody() {
        return angel;
    }

}
