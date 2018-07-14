import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Bullet implements Serializable {
    private int positionX;
    private int positionY;
    private int speed;
    private int destX;
    private int destY;
    public int mode;
    private double angel;

    public Bullet(int positionX, int positionY,int destX , int destY , int mode , int speed) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.destX = destX;
        this.destY = destY;
        this.mode = mode;

        angel = Math.atan2(destY - (positionY ), destX - (positionX ));

        this.speed = speed;
    }


    public void updateLocation(){
        positionX += Math.cos(angel)*speed;
        positionY += Math.sin(angel)*speed;
    }

    public Rectangle getBounds(){
        if (mode == 0){ // heavy gun
            return new Rectangle(positionX , positionY ,45 ,16) ;
        }else { // second gun
            return new Rectangle(positionX , positionY ,35 ,8) ;
        }

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


    public double getAngelBody() {
        return angel;
    }

}
