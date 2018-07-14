import java.awt.*;
import java.io.Serializable;

public abstract class Runner implements Serializable {
    protected int health;
    protected int damage;
    protected int positionX;
    protected int positionY;
    protected Point clientLoc = new Point();
    protected String bulletImageAddress;

    public Runner(String bulletImageAddress, int positionX, int positionY) {
        this.health = 5;
        this.bulletImageAddress = bulletImageAddress;
        this.positionX = positionX;
        this.positionY = positionY;
        clientLoc.x = positionX;
        clientLoc.y = positionY;
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
        return null ;
    }

    public int getX() {
        return positionX;
    }

    public int getY() {
        return positionY;
    }
}
