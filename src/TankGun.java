import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class TankGun implements Serializable {
    private boolean use;
    int positionX;
    int positionY;
    private int i;
    private int j;

    public TankGun(int positionX, int positionY, int j, int i) {
        this.i = i;
        this.j = j;
        this.positionX = positionX;
        this.positionY = positionY;
        use = false;
    }


    public Rectangle getBounds() {
        return new Rectangle(positionX + 20, positionY + 20, 100, 100);
    }

    public void changeImage(){

        use = true;
    }

    public boolean used() {
        return use;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
