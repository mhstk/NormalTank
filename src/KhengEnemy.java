import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class KhengEnemy extends Moving{

    public KhengEnemy(int positionX, int positionY , String imageFileBody){
        super(null);
        try {
            image = ImageIO.read(new File(imageFileBody));
        } catch (IOException e) {
            e.printStackTrace();
        }
        angelBody = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
              while (true){
                  if (isInArea()) move();
                  else {
                      try {
                          Thread.sleep(1000);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                  }
              }
            }
        });
    }

    private boolean isInArea() {
        int playerTankX = GameState.tankPosition().x;
        int playerTankY = GameState.tankPosition().y;
        double distance = Math.pow(positionX - playerTankX, 2) + Math.pow(positionY - playerTankY, 2);
        return Math.sqrt(distance) < 500;
    }

    @Override
    public void move() {
        if (GameState.tankPosition().x > positionX) {
            positionX += 6;
            if (positionX > GameState.tankPosition().x) positionX = GameState.tankPosition().x;
        } else if (GameState.tankPosition().x < positionX) {
            positionX -= 6;
            if (positionX < GameState.tankPosition().x) positionX = GameState.tankPosition().x;
        }
        if (GameState.tankPosition().y > positionY) {
            positionY += 6;
            if (positionY > GameState.tankPosition().y) positionY = GameState.tankPosition().y;
        } else if (GameState.tankPosition().y < positionY) {
            positionY -= 6;
            if (positionY < GameState.tankPosition().y) positionY = GameState.tankPosition().y;
        }

    }
}
