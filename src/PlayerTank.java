import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Iterator;

public class PlayerTank extends SuperTank implements Serializable {

    public int mouseX, mouseY;
    public double difTimeBullet;
    boolean camerafixedX, camerafixedY;
    protected boolean mousePress;
    protected boolean mouseMoved;
    protected int bulletSpeed;
    protected String shootString;
    private int tankGun;
    private int mashinGun;
    private int firstHealth;
    private int moreSpeed = 0;
    private int moreDamage = 0;


    public PlayerTank(int positionX, int positionY , int level) {
        super("Tank-under.png","Tank-under2.png", "Tank-top.png", "Tank-Bullet.png",positionX,positionY,level);
        speed = 7 + moreSpeed;
        shootString = "heavygun.wav";
        damage = 4 + moreDamage;
        camerafixedX = false;
        camerafixedY = false;
        bulletSpeed = 20 + moreSpeed;
        difTimeBullet = 0.7;
        health = 5 * 4 - level * 4;
        firstHealth = health;
        tankGun = 25;
        mashinGun = 100;
    }

    @Override
    public void move() {
        if (mouseMoved) {
            angelGun = Math.atan2(mouseY - (positionY + 80), mouseX - (positionX + 100));
        }

        if (up) {
            positionY -= speed;
            clientLoc.y -= speed;
            moveUp();
            if (Collision.collisionPlayerTank()) {
                positionY += speed;
                clientLoc.y += speed;
            }
        }

        if (down) {
            positionY += speed;
            clientLoc.y += speed;
            moveDown();
            if (Collision.collisionPlayerTank()) {
                positionY -= speed;
                clientLoc.y -= speed;
            }
        }
        if (left) {
            positionX -= speed;
            clientLoc.x -= speed;
            moveLeft();
            if (Collision.collisionPlayerTank()) {
                positionX += speed;
                clientLoc.x += speed;
            }
        }
        if (right) {
            positionX += speed;
            clientLoc.x += speed;
            moveRight();
            if (Collision.collisionPlayerTank()) {
                positionX -= speed;
                clientLoc.x -= speed;
            }
        }
        if (up || down || right || left) {
            //changeBodyImage();
        }

        positionX = Math.max(positionX, 0);
        positionX = Math.min(positionX, GameFrame.GAME_WIDTH - 205);
        positionY = Math.max(positionY, 0);
        positionY = Math.min(positionY, GameFrame.GAME_HEIGHT - 160);
    }

    public void shoot(int originX, int originY, int destX, int destY) {
        if (getGunNumber() == 1) {
            if (tankGun == 0) {
                Sound sound = new Sound("emptyGun.wav", 0);
                sound.execute();
                return;
            } else {
                changeNumberOfTankGun(-1);
            }
        } else {
            if (mashinGun == 0) {
                Sound sound = new Sound("emptyGun.wav", 0);
                sound.execute();
                return;
            } else changeNumberOfMashinGun(-1);
        }
        Sound sound = new Sound(shootString, 0);
        sound.execute();
        Bullet bullet;
        if (isFirstImage) {
            bullet = new Bullet(originX, originY, destX, destY, 0, bulletSpeed , damage);
        } else {
            bullet = new Bullet(originX, originY, destX, destY, 1, bulletSpeed , damage);
        }
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
            if (Collision.collisionPlayerBullet(bullet)) {
                bullets.remove(bullet);
            }
        }
    }


    public void changeGunTow() {
        bulletImageAddress = "Tank-Bullet3.png";
        damage = 1 + moreDamage;
        shootString = "lightgun.wav";
        bulletSpeed = 25 + moreSpeed;
        difTimeBullet = 0.2;
    isFirstImage = false;
    }

    public void changeGunOne() {
        shootString = "heavygun.wav";
        bulletImageAddress = "Tank-Bullet.png";
        isFirstImage = true;
        damage = 4 + moreDamage;
        bulletSpeed = 20 + moreSpeed;
        difTimeBullet = 0.7;

    }


    public int getGunNumber() {
        if (difTimeBullet == 0.7) {
            return 1;
        } else {
            return 2;
        }
    }

    public void changeNumberOfTankGun(int number) {
        tankGun += number;
    }

    public void changeNumberOfMashinGun(int number) {
        mashinGun += number;
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


    public int getHealth() {
        return health;
    }

    public void refactor() {
        health = firstHealth;
    }

    public int getFirstHealth() {
        return firstHealth;
    }

    public void power() {
        moreSpeed += 2;
        moreDamage += 1;
        damage += moreDamage;
        bulletSpeed += moreSpeed;
        speed += moreSpeed;
    }
}
