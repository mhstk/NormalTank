import java.awt.*;
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
        this.health = 5;
        this.bulletImageAddress = bulletImageAddress;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void toBeInjured() {
        this.health --;
        System.out.println(health);
    }

    public void setBulletImageAddress(String bulletImageAddress){
        this.bulletImageAddress = bulletImageAddress;
    }

    public void shoot(int originX , int originY , int destX , int destY){

    }

    public Rectangle getBounds(){
        System.out.printf(image.getWidth()+"   ******   "+image.getHeight()+"\n");
        return new Rectangle(positionX,positionY,image.getWidth(),image.getHeight());
    }

    public int getX() {
        return positionX;
    }

    public int getY() {
        return positionY;
    }
}
