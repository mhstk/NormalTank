import java.awt.*;
import java.io.Serializable;

public abstract class MapPart implements Serializable {
    int positionX;
    int positionY;
    Point clientLoc  = new Point() ;

    public MapPart(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        clientLoc.x = positionX;
        clientLoc.y = positionY;
    }

    public Rectangle getBounds() {
        return new Rectangle(positionX, positionY, 150, 150);
    }
}
