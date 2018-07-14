import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class CoPlayerTank extends PlayerTank implements Serializable {

    public CoPlayerTank(int positionX, int positionY , int level) {
        super(positionX, positionY , level);
    }

    @Override
    public String toString() {
        return positionX + "<<<" + positionY + ">>>>>>" + angelBody;
    }

    public void drawBody(BufferedImage image, Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        System.out.println(this);
        AffineTransform atBody = g2d.getTransform();
        atBody.rotate(Math.toRadians(angelBody), positionX + image.getWidth() / 2, positionY + image.getHeight() / 2);
        g2d.setTransform(atBody);
        g2d.drawImage(image, positionX, positionY, null);
        g2d.setTransform(oldTrans);
    }

    public void drawBodyServer(BufferedImage image, Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        System.out.println(this);
        AffineTransform atBody = g2d.getTransform();
        atBody.rotate(Math.toRadians(angelBody), clientLoc.x + image.getWidth() / 2, clientLoc.y + image.getHeight() / 2);
        g2d.setTransform(atBody);
        g2d.drawImage(image, clientLoc.x, clientLoc.y, null);
        g2d.setTransform(oldTrans);
    }

}
