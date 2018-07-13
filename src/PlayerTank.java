import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class PlayerTank extends SuperTank {

    public int mouseX, mouseY;
    public double difTimeBullet;
    boolean camerafixedX, camerafixedY;
    private boolean mousePress;
    private boolean mouseMoved;
    private int bulletSpeed;
    private String shootString;


    public PlayerTank(int positionX, int positionY) {
        super("Tank-under.png","Tank-under2.png", "Tank-top.png", "Tank-Bullet.png",positionX,positionY);
        speed = 7;
        shootString = "heavygun.wav";
        camerafixedX = false;
        camerafixedY = false;
        bulletSpeed = 20;
        difTimeBullet = 0.7;
    }

    @Override
    public void move() {
        if (mouseMoved) {
            angelGun = Math.atan2(mouseY - (positionY + 80), mouseX - (positionX + 100));
        }

        if (up) {
            positionY -= speed;
            moveUp();
            if (Collision.collisionPlayerTank()) {
                positionY += speed;
            }
        }

        if (down) {
            positionY += speed;
            moveDown();
            if (Collision.collisionPlayerTank()) {
                positionY -= speed;
            }
        }
        if (left) {
            positionX -= speed;
            moveLeft();
            if (Collision.collisionPlayerTank()) {
                positionX += speed;
            }
        }
        if (right) {
            positionX += speed;
            moveRight();
            if (Collision.collisionPlayerTank()) {
                positionX -= speed;
            }
        }
        if (up || down || right || left) {
            changeBodyImage();
        }

        positionX = Math.max(positionX, 0);
        positionX = Math.min(positionX, GameFrame.GAME_WIDTH - 205);
        positionY = Math.max(positionY, 0);
        positionY = Math.min(positionY, GameFrame.GAME_HEIGHT - 160);
    }

    public void shoot(int originX, int originY, int destX, int destY) {
        Sound sound = new Sound(shootString, 0);
        sound.execute();
        Bullet bullet = new Bullet(originX, originY, destX, destY, bulletImageAddress, bulletSpeed);
        bullets.add(bullet);
    }

    public boolean checkMouseLoc() {
        if (mouseX - positionX > -5 && mouseX - positionX < 200 && mouseY - positionY > -15 && mouseY - positionY < 180) {
            return false;
        } else {
            return true;
        }
    }

    public void updateBullet() {
        Iterator it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = (Bullet) it.next();
            bullet.updateLocation();
            if (Collision.collisionPlayerBullet(bullet)){
                bullets.remove(bullet);
            }
        }
    }


    public void changeGunTow() {
        bulletImageAddress = "Tank-Bullet3.png";
        shootString = "lightgun.wav";
        bulletSpeed = 25;
        difTimeBullet = 0.2;
        try {
            gunImage = ImageIO.read(new File("Tank-top2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeGunOne() {
        shootString = "heavygun.wav";
        bulletImageAddress = "Tank-Bullet.png";
        bulletSpeed = 20;
        difTimeBullet = 0.7;
        try {
            gunImage = ImageIO.read(new File("Tank-top.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getGunImage() {
        return gunImage;
    }

    public int getGunNumber() {
        if (difTimeBullet == 0.7) {
            return 1;
        } else {
            return 2;
        }
    }

    public void setKeyUP(boolean keyUP) {
        this.up = keyUP;
    }

    public void setKeyDOWN(boolean keyDOWN) {
        this.down = keyDOWN;
    }

    public void setKeyRIGHT(boolean keyRIGHT) {
        this.right = keyRIGHT;
    }

    public void setKeyLEFT(boolean keyLEFT) {
        this.left = keyLEFT;
    }

    public void setMousePress(boolean mousePress) {
        this.mousePress = mousePress;
    }

    public void setMouseMoved(boolean mouseMoved) {
        this.mouseMoved = mouseMoved;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }




    public void drawBody(Graphics2D g2d , GameState state , AffineTransform oldTrans){
        g2d.setTransform(oldTrans);
        AffineTransform atBody = g2d.getTransform();
        atBody.rotate(Math.toRadians(angelBody), positionX + image.getWidth() / 2, positionY + image.getHeight() / 2);
        g2d.setTransform(atBody);
        g2d.drawImage(image, positionX, positionY, null);
        g2d.setTransform(oldTrans);
    }



}
