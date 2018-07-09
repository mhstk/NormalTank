import java.awt.image.BufferedImage;

public abstract class Moving extends Runner {
    protected int speed;
    protected BufferedImage secondeBodyImage;

    public Moving(String bulletImageAddress){
        super(bulletImageAddress);
    }
    public abstract void move();
}
