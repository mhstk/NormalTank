import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Runner {
    protected int health;
    protected int damage;
    protected int positionX;
    protected int positionY;
    protected BufferedImage image;
    protected String bulletImageAddress;

    public Runner(String bulletImageAddress, int positionX, int positionY) {
        this.bulletImageAddress = bulletImageAddress;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void setBulletImageAddress(String bulletImageAddress){
        this.bulletImageAddress = bulletImageAddress;
    }

    public void shoot(int originX , int originY , int destX , int destY){

    }

    public int getX() {
        return positionX;
    }

    public int getY() {
        return positionY;
    }
}
