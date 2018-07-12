import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class PlayerTank extends SuperTank {
    public int mouseX, mouseY;
    boolean camerafixedX, camerafixedY;
    private BufferedImage firstBodyImage;
    private BufferedImage secondeBodyImage;
    private boolean isFirstImage = true;
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean mousePress;
    private boolean mouseMoved;
    private int bulletSpeed;
    private double difTimeBullet;
    private String shootString;
    private long lastTimeImageChanged;




    public PlayerTank(String imageFileBody, String imageFileGun, String bulletImageAddress) {
        super(imageFileBody, imageFileGun, bulletImageAddress);
        positionX = 100;
        positionY = 600;
        speed = 808;
        positionX = 800;
        positionY = 400;
        speed = 7;
        shootString = "heavygun.wav";
        camerafixedX = false;
        camerafixedY = false;
        angelGun = 0;
        bulletSpeed = 20;
        difTimeBullet = 0.7;
    }


    @Override
    public void move() {
        if (mouseMoved) {
            angelGun = Math.atan2(mouseY - (positionY + 67), mouseX - (positionX + 87));
        }

        if (keyUP  ) {
            positionY -= speed;
            moveUp();
            if (Collision.CollisionPlayerTank()){
                positionY += speed;
            }
        }

        if (keyDOWN ) {
            positionY += speed;
            moveDown();
            if (Collision.CollisionPlayerTank()){
                positionY -= speed;
            }
        }
        if (keyLEFT ) {
            positionX -= speed;
            moveLeft();
            if (Collision.CollisionPlayerTank()){
                positionX += speed;
            }
        }
        if (keyRIGHT ) {
            positionX += speed;
            moveRight();
            if (Collision.CollisionPlayerTank()){
                positionX -= speed;
            }
        }



        if (keyUP || keyDOWN || keyLEFT || keyRIGHT) {
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

        positionX = Math.max(positionX, 0);
        positionX = Math.min(positionX, GameFrame.GAME_WIDTH - 177);
        positionY = Math.max(positionY, 0);
        positionY = Math.min(positionY, GameFrame.GAME_HEIGHT - 134);


    }

    public void moveUp() {
        if (keyRIGHT) {
            if ((angelInRange(angelBody) > 315 && angelInRange(angelBody) < 360) || (angelInRange(angelBody) >= 0 && angelInRange(angelBody) < 135)) {
                angelBody -= 10;
            }
            if (angelInRange(angelBody) > 135 && angelInRange(angelBody) < 315) {
                angelBody += 10;

            }

        } else if (keyLEFT) {
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
        if (keyLEFT) {
            if ((angelInRange(angelBody) > 315 && angelInRange(angelBody) < 360) || (angelInRange(angelBody) >= 0 && angelInRange(angelBody) < 135)) {
                angelBody += 10;
            }
            if (angelInRange(angelBody) > 135 && angelInRange(angelBody) < 315) {
                angelBody -= 10;

            }

        } else if (keyRIGHT) {
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
        if (!(angelInRange(angelBody) == 0) && !(keyDOWN) && !(keyUP)) {
            if ((angelInRange(angelBody) > 0 && angelInRange(angelBody) < 180)) {
                angelBody += 10;
            }
            if (angelInRange(angelBody) > 180 && angelInRange(angelBody) < 360) {
                angelBody -= 10;
            }
        }

    }

    public void moveRight() {
        if (!(angelInRange(angelBody) == 180) && !(keyDOWN) && !(keyUP)) {

            if ((angelInRange(angelBody) > 0 && angelInRange(angelBody) < 180)) {
                angelBody -= 10;
            }
            if (angelInRange(angelBody) > 180 && angelInRange(angelBody) < 360) {
                angelBody += 10;

            }
        }
    }

    @Override
    public void shoot(int originX, int originY, int destX, int destY) {
        Sound sound = new Sound(shootString, 0);
        sound.execute();
        super.shoot(originX, originY, destX, destY);
        Bullet bullet = new Bullet(originX, originY, destX, destY, bulletImageAddress, bulletSpeed);
        bullets.add(bullet);
    }

    public void updateBullet() {
        Iterator it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = (Bullet) it.next();
            bullet.updateLocation();
        }
    }

    public boolean checkMouseLoc() {
        if (mouseX - positionX > -5 && mouseX - positionX < 200 && mouseY - positionY > -10 && mouseY - positionY < 154) {
            return false;
        } else {
            return true;
        }
    }


    public void changeGunTow() {
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
        bulletSpeed = 20;
        difTimeBullet = 0.7;
        try {
            gunImage = ImageIO.read(new File("Tank-top.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double angelInRange(double angel) {

        if (angel % 360 < 0) {
            return 360 + angel % 360;
        } else {
            return angel % 360;
        }
    }

    public void setGunImage(BufferedImage gunImage) {
        this.gunImage = gunImage;
    }

    public int getGunNumber() {
        if (difTimeBullet == 0.7) {
            return 1;
        } else {
            return 2;
        }
    }

    public void drawTankBody(Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        AffineTransform atBody = g2d.getTransform();
        atBody.rotate(Math.toRadians(state.getPlayerTank().getAngelBody()), state.getPlayerTank().getX() + state.getPlayerTank().getBounds().width / 2, state.getPlayerTank().getY() + state.getPlayerTank().getBounds().height/2);
        g2d.setTransform(atBody);
        g2d.drawImage(state.getPlayerTank().getBodyImage(), state.getPlayerTank().getX(), state.getPlayerTank().getY(), null);
        g2d.setTransform(oldTrans);
    }

    public void drawTankGun(Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        AffineTransform atGun = g2d.getTransform();
        atGun.translate(state.getPlayerTank().getX(), state.getPlayerTank().getY());
        atGun.rotate(state.getPlayerTank().getAngelGun(), 100, 80);
        g2d.setTransform(atGun);
        g2d.drawImage(state.getPlayerTank().getGunImage(), 0, 0, null);
        g2d.setTransform(oldTrans);
    }

    public void drawBullets(Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        for (Bullet bullet : state.getPlayerTank().getBullets()) {
            AffineTransform atBullet = g2d.getTransform();
            atBullet.translate(bullet.getPositionX(), bullet.getPositionY());
            atBullet.rotate(bullet.getAngel(), 5, 2);
            g2d.setTransform(atBullet);
            g2d.drawImage(bullet.getImage(), 0, 0, null);
            g2d.setTransform(oldTrans);
        }

        state.getPlayerTank().updateBullet();
    }

    public double getAngelBody() {
        return angelBody;
    }

    public void setAngelBody(double angelBody) {
        this.angelBody = angelBody;
    }

    public double getAngelGun() {
        return angelGun;
    }

    public void setAngelGun(double angelGun) {
        this.angelGun = angelGun;
    }

    public boolean isKeyUP() {
        return keyUP;
    }

    public void setKeyUP(boolean keyUP) {
        this.keyUP = keyUP;
    }

    public boolean isKeyDOWN() {
        return keyDOWN;
    }

    public void setKeyDOWN(boolean keyDOWN) {
        this.keyDOWN = keyDOWN;
    }

    public boolean isKeyRIGHT() {
        return keyRIGHT;
    }

    public void setKeyRIGHT(boolean keyRIGHT) {
        this.keyRIGHT = keyRIGHT;
    }

    public boolean isKeyLEFT() {
        return keyLEFT;
    }

    public void setKeyLEFT(boolean keyLEFT) {
        this.keyLEFT = keyLEFT;
    }

    public boolean isMousePress() {
        return mousePress;
    }

    public void setMousePress(boolean mousePress) {
        this.mousePress = mousePress;
    }

    public boolean isMouseMoved() {
        return mouseMoved;
    }

    public void setMouseMoved(boolean mouseMoved) {
        this.mouseMoved = mouseMoved;
    }

    public int getX() {
        return positionX;
    }

    public int getY() {
        return positionY;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public BufferedImage getBodyImage() {
        return image;
    }

    public int getGunX() {
        return positionX + 100 + (int) (Math.cos(angelGun) * 90.0);
    }

    public int getGunY() {
        return positionY + 80 + (int) (Math.cos(angelGun) * (-10)) + (int) (Math.sin(angelGun) * 90.0);
    }

    public double getDifTimeBullet() {
        return difTimeBullet;
    }

    public void reduceHealth() {
        this.health--;
    }
}
