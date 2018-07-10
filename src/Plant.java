import java.awt.*;
import java.awt.image.BufferedImage;

public class Plant {
    private BufferedImage image;
    private int positionX;
    private int positionY;

    public Plant(int positionX , int positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void draw(Graphics2D g2d){

    }

    public Rectangle getBounds(){
        return new Rectangle(positionX,positionY,image.getWidth(),image.getHeight());
    }
}
