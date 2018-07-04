import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Runner {
    protected int health;
    protected int damage;
    protected int positionX;
    protected int positionY;
    protected BufferedImage image;
    protected ArrayList<Bullet> bullets ;
    protected String bulletImageAddress;

    public Runner(String bulletImageAddress) {
        this.bulletImageAddress = bulletImageAddress;
        bullets = new ArrayList<>();
    }

    public void shoot(int originX , int originY , int destX , int destY){

    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
