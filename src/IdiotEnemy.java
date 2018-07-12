import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

public class IdiotEnemy extends Moving {

    private boolean seed = false;
    private double angelBody;
    private boolean down,up,left,right;
    private boolean isFirstImage = true;
    private long lastTimeImageChanged = 0;


    public IdiotEnemy(int positionX, int positionY) {
        super(null);
        this.positionY = positionY;
        this.positionX = positionX;
        try {
            image = ImageIO.read(new File("idiotEnemy1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        angelBody = 0;
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
            //changeBodyImage();
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

    public void moveUp() {
        if (right) {
            if ((angelInRange(angelBody) > 315 && angelInRange(angelBody) < 360) || (angelInRange(angelBody) >= 0 && angelInRange(angelBody) < 135)) {
                angelBody -= 10;
            }
            if (angelInRange(angelBody) > 135 && angelInRange(angelBody) < 315) {
                angelBody += 10;

            }

        } else if (left) {
            if ((angelInRange(angelBody) > 225 && angelInRange(angelBody) < 360) || (angelInRange(angelBody) >= 0 && angelInRange(angelBody) < 45)) {
                angelBody -= 10;
            }
            if (angelInRange(angelBody) > 45 && angelInRange(angelBody) < 225) {
                angelBody += 10;
            }

        } else if (!(angelInRange(angelBody) == 90)) {


            if ((angelInRange(angelBody) >= 0 && angelInRange(angelBody) < 90) || (angelInRange(angelBody) > 270 && angelInRange(angelBody) < 360)) {
                angelBody -= 10;
            }
            if (angelInRange(angelBody) > 90 && angelInRange(angelBody) < 270) {
                angelBody += 10;

            }

        }
    }

    public void moveDown() {
        if (left) {
            if ((angelInRange(angelBody) > 315 && angelInRange(angelBody) < 360) || (angelInRange(angelBody) >= 0 && angelInRange(angelBody) < 135)) {
                angelBody += 10;
            }
            if (angelInRange(angelBody) > 135 && angelInRange(angelBody) < 315) {
                angelBody -= 10;

            }

        } else if (right) {
            if ((angelInRange(angelBody) > 225 && angelInRange(angelBody) < 360) || (angelInRange(angelBody) >= 0 && angelInRange(angelBody) < 45)) {
                angelBody += 10;
            }
            if (angelInRange(angelBody) > 45 && angelInRange(angelBody) < 225) {
                angelBody -= 10;

            }

        } else if (!(angelInRange(angelBody) == 90)) {

            if ((angelInRange(angelBody) >= 0 && angelInRange(angelBody) < 90) || (angelInRange(angelBody) > 270 && angelInRange(angelBody) < 360)) {
                angelBody += 10;
            }
            if (angelInRange(angelBody) > 90 && angelInRange(angelBody) < 270) {
                angelBody -= 10;

            }


        }
    }

    public void moveLeft() {
        if (!(angelInRange(angelBody) == 0) && !(down) && !(up)) {
            if ((angelInRange(angelBody) > 0 && angelInRange(angelBody) < 180)) {
                angelBody += 10;
            }
            if (angelInRange(angelBody) > 180 && angelInRange(angelBody) < 360) {
                angelBody -= 10;
            }
        }

    }

    public void moveRight() {
        if (!(angelInRange(angelBody) == 180) && !(down) && !(up)) {

            if ((angelInRange(angelBody) > 0 && angelInRange(angelBody) < 180)) {
                angelBody -= 10;
            }
            if (angelInRange(angelBody) > 180 && angelInRange(angelBody) < 360) {
                angelBody += 10;

            }
        }
    }
    public double angelInRange(double angel) {

        if (angel % 360 < 0) {
            return 360 + angel % 360;
        } else {
            return angel % 360;
        }
    }

    public void drawBody(Graphics2D g2d , GameState state , AffineTransform oldTrans){
        AffineTransform atBody = g2d.getTransform();
        atBody.rotate(Math.toRadians(angelBody), positionX + image.getWidth() / 2, positionY + image.getHeight() / 2);
        g2d.setTransform(atBody);
        g2d.drawImage(image, positionX, positionY, null);
        g2d.setTransform(oldTrans);
    }

    public Rectangle getBounds(){
        return  new Rectangle(positionX , positionY , image.getWidth(),image.getHeight()) ;
    }

    public double getAngelBody() {
        return angelBody;
    }
}
