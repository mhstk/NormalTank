import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

public class EnemyTank extends Tank {

    public EnemyTank(int positionX, int PositionY, String imageFileBody, String imageFileGun, String bulletImageAddress) {
        super(imageFileBody, imageFileGun, bulletImageAddress);
        this.positionX = positionX;
        this.positionY = positionY;
        speed = 8;
    }

    private boolean isInArea() {
        int playerTankX = GameState.tankPosition().x;
        int playerTankY = GameState.tankPosition().y;
        double distance = Math.pow(positionX - playerTankX, 2) + Math.pow(positionY - playerTankY, 2);
        return Math.sqrt(distance) > 500 && Math.sqrt(distance) < 1000;
    }

    public void move() {
        if (isInArea()) {
            angelGun = Math.atan2(GameState.tankPosition().y - (positionY), GameState.tankPosition().x - (positionX));
            if (GameState.tankPosition().x > positionX) {
                positionX += 4;
                if (positionX > GameState.tankPosition().x) positionX = GameState.tankPosition().x;
            } else if (GameState.tankPosition().x < positionX) {
                positionX -= 4;
                if (positionX < GameState.tankPosition().x) positionX = GameState.tankPosition().x;
            }
            if (GameState.tankPosition().y > positionY) {
                positionY += 4;
                if (positionY > GameState.tankPosition().y) positionY = GameState.tankPosition().y;
            } else if (GameState.tankPosition().y < positionY) {
                positionY -= 4;
                if (positionY < GameState.tankPosition().y) positionY = GameState.tankPosition().y;
            }
            angelBody = Math.atan2(GameState.tankPosition().y - (positionY), GameState.tankPosition().x - (positionX));
        } else return;
    }

    public double getAngelBody() {
        return angelBody;
    }

    public int getX() {
        return positionX;
    }

    public BufferedImage getBodyImage() {
        return image;
    }

    public int getY() {
        return positionY;
    }
}