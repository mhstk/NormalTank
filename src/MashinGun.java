import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class MashinGun implements Serializable {
    int positionX;
    int positionY;
    int i;
    int j;
    private boolean use;

    public MashinGun(int positionX, int positionY, int j, int i) {
        this.i = i;
        this.j = j;
        this.positionX = positionX;
        this.positionY = positionY;
        use = false;
    }


    public Rectangle getBounds() {
        return new Rectangle(positionX + 20, positionY + 20, 100, 100);
    }

    public void changeImage() {

        Sound sound = new Sound("mashingun.wav",0);
        sound.execute();
        use = true;
    }

    public boolean used() {
        return use;
    }
}

