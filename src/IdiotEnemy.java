import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class IdiotEnemy extends Moving {

    public IdiotEnemy(int positionX, int positionY, String imageFileBody) {
        super(null);
        try {
            image = ImageIO.read(new File(imageFileBody));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        angelBody = 0;
        new Thread(() -> {
            while (true) {
                if (isInArea()) move();
                else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
//        while (this.isAlive()) {
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
//        }

        if (positionX == GameState.tankPosition().x &&
                positionY == GameState.tankPosition().y) {
            GameState.getTank().reduceHealth();
//            this.destroy();
            System.out.println(GameState.getTank().health);
        }
    }
}
