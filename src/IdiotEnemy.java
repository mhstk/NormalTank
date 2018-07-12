import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class IdiotEnemy extends Moving {
    private boolean seed = false;

 public IdiotEnemy(int positionX, int positionY) {
        super("idiotEnemy1.png","idiotEnemy1.png","",positionX,positionY);
    speed = 4;
 }

    public void isInArea() {
        int playerTankX = GameState.tankPosition().x;
        int playerTankY = GameState.tankPosition().y;
        double distance = Math.pow(positionX - playerTankX, 2) + Math.pow(positionY - playerTankY, 2);
        if (Math.sqrt(distance) < 1000){
            seed = true;
        }
    }

    @Override
    public void move() {
                if (seed) {
            changeBodyImage();
            if (GameState.tankPosition().x > positionX) {
                positionX += speed;
                right = true;
                left = false;
                if (Collision.CollisionIdiotEnemyTank()==1){
                    right = false;
                    left = true;
                    positionX -= speed;
                }
                if (positionX > GameState.tankPosition().x) positionX = GameState.tankPosition().x;
                moveRight();
            } else if (GameState.tankPosition().x < positionX) {
                positionX -= speed;
                left = true;
                right = false;
                if (Collision.CollisionIdiotEnemyTank()==1){
                    right = true;
                    left = false;
                    positionX += speed;
                }
                if (positionX < GameState.tankPosition().x) positionX = GameState.tankPosition().x;
                moveLeft();
            } else {
                right = false;
                left = false;
            }
            if (GameState.tankPosition().y > positionY) {
                positionY += speed;
                down = true;
                up = false;
                if (Collision.CollisionIdiotEnemyTank()==1){
                    down = false;
                    up = true;
                    positionY -= speed;
                }
                if (positionY > GameState.tankPosition().y) positionY = GameState.tankPosition().y;
                moveDown();

            } else if (GameState.tankPosition().y < positionY) {
                positionY -= speed;
                if (Collision.CollisionIdiotEnemyTank()==1){
                    down = true;
                    up = false;
                    positionY += speed;
                }
                if (positionY < GameState.tankPosition().y) positionY = GameState.tankPosition().y;
                up = true;
                down = false;
                moveUp();
            } else {
                up = false;
                down = false;
            }
            if (Collision.CollisionIdiotEnemyTank()==2){
                strike();
            }

    }

    }

    public void strike(){

    }

    public Rectangle getBounds(){
        return  new Rectangle(positionX , positionY , image.getWidth(),image.getHeight()) ;
    }

    public double getAngelBody() {
        return angelBody;
    }
}
