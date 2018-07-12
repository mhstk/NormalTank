import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class EnemyTank extends SuperTank {
    public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = false;
    private long lastTimeImageChanged = 0;
    private boolean isFirstImage = true;
    private long timeLastShotGun = 0;


    public EnemyTank(int positionX, int PositionY, String imageFileBody, String imageFileGun, String bulletImageAddress) {
        super(imageFileBody, imageFileGun, bulletImageAddress);
        this.positionX = positionX;
        speed = 5;
    }

    public void isInArea() {
        int playerTankX = GameState.tankPosition().x;
        int playerTankY = GameState.tankPosition().y;
        double distance = Math.pow(positionX - playerTankX, 2) + Math.pow(positionY - playerTankY, 2);
        if (Math.sqrt(distance) > 400 && Math.sqrt(distance) < 1000) move();
        else if (Math.sqrt(distance) <= 400) changePosition();
    }

    private void changePosition() {
        angelGun = Math.atan2(GameState.tankPosition().y - (positionY), GameState.tankPosition().x - (positionX));
        shoot();
    }


    public void move() {
        angelGun = Math.atan2(GameState.tankPosition().y - (positionY), GameState.tankPosition().x - (positionX));
        if (GameState.tankPosition().x > positionX) {
            positionX += speed;
            right = true;
            left = false;
            if (Collision.CollisionEnemyTank()){
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
            if (Collision.CollisionEnemyTank()){
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
            if (Collision.CollisionEnemyTank()){
                down = false;
                up = true;
                positionY -= speed;
            }
            if (positionY > GameState.tankPosition().y) positionY = GameState.tankPosition().y;
            moveDown();

        } else if (GameState.tankPosition().y < positionY) {
            positionY -= speed;
            if (Collision.CollisionEnemyTank()){
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

        if (up || down || left || right) {
            long now = System.nanoTime();
            if ((now - lastTimeImageChanged) / 1000000000.0 > 0.08) {
                if (isFirstImage) {
                    try {
                        image = ImageIO.read(new File("Tank-under2.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    isFirstImage = false;
                } else {
                    try {
                        image = ImageIO.read(new File("Tank-under.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    isFirstImage = true;
                }
                lastTimeImageChanged = now;
            }
        }
    shoot();
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

    public void drawTankBody(Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        AffineTransform atBody = g2d.getTransform();
        atBody.rotate(Math.toRadians(angelBody), getX() + getBodyImage().getWidth() / 2, getY() + getBodyImage().getHeight() / 2);
        g2d.setTransform(atBody);
        g2d.drawImage(getBodyImage(), getX(), getY(), null);
        g2d.setTransform(oldTrans);
    }

    public void drawTankGun(Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        AffineTransform atGun = g2d.getTransform();
        atGun.translate(getX(), getY());
        atGun.rotate(angelGun, 87, 67);
        g2d.setTransform(atGun);
        g2d.drawImage(state.getPlayerTank().getGunImage(), 0, 0, null);
    }

    public void drawBullet(Graphics2D g2d , GameState state , AffineTransform oldTrans){
        for (Bullet bullet : bullets) {
            AffineTransform atBullet = g2d.getTransform();
            atBullet.translate(bullet.getPositionX(), bullet.getPositionY());
            atBullet.rotate(bullet.getAngel(), 5, 2);
            g2d.setTransform(atBullet);
            g2d.drawImage(bullet.getImage(), 0, 0, null);
            g2d.setTransform(oldTrans);
        }

        updateBullet();

    }
    private void updateBullet() {

        Iterator it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = (Bullet) it.next();
            bullet.updateLocation();
        }
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

    public void shoot(){
        Long now = System.nanoTime();
        if ((now - timeLastShotGun) / 1000000000.0 > 2.7) {
            Bullet bullet = new Bullet(positionX + (87), positionY + (67), GameState.tankPosition().x + (image.getWidth() / 2), GameState.tankPosition().y + (image.getHeight() / 2), "Tank-Bullet.png", 20);
            bullets.add(bullet);
            Sound sound = new Sound("heavygun.wav", 0);
            sound.execute();
            timeLastShotGun = now;
        }
   }
}