import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Moving extends Runner {
    protected int speed;
    protected BufferedImage secondeBodyImage;

    public Moving(String bulletImageAddress){
        super(bulletImageAddress);
    }

    public Rectangle getBounds(){
        return  new Rectangle(positionX , positionY , image.getWidth(),image.getHeight()) ;
    }

    public abstract void move();
}
