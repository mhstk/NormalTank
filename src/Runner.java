import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Runner {
    protected int health;
    protected int damage;
    protected int positionX;
    protected int positionY;
    protected BufferedImage image;
    protected String bulletImageAddress;
    private String firstBodyImage;
    private String secondBodyImage;
    private long lastTimeImageChanged;
    private boolean isFirstImage = true;
    protected int level ;

    public Runner(String firstBodyImage , String secondBodyImage ,String bulletImageAddress, int positionX, int positionY , int level) {
        this.health = 5;
        this.bulletImageAddress = bulletImageAddress;
        this.positionX = positionX;
        this.positionY = positionY;
        this.firstBodyImage = firstBodyImage;
        this.secondBodyImage = secondBodyImage;
        this.level = level;
    }

    public void changeBodyImage(){
        long now = System.nanoTime();
        if ((now - lastTimeImageChanged) / 1000000000.0 > 0.08) {
            if (isFirstImage) {
                try {
                    image = ImageIO.read(new File(secondBodyImage));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isFirstImage = false;
            } else {
                try {
                    image = ImageIO.read(new File(firstBodyImage));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isFirstImage = true;
            }
            lastTimeImageChanged = now;
        }
    }


    public void toBeInjured(int damage) {
        this.health -= damage;
    }

    public Rectangle getBounds(){
        return new Rectangle(positionX,positionY,image.getWidth(),image.getHeight());
    }

}
