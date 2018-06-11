import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class PlayerTank extends Moving {
    private BufferedImage gunImage;
    private double angelBody;
    private double angelGun;
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean mousePress;
    private boolean mouseMoved;
    public int mouseX, mouseY;


    public PlayerTank(String imageFileBody , String imageFileGun ) {
        super();
        positionX=100;
        positionY=100;
        speed = 8;
        try {
            image = ImageIO.read(new File(imageFileBody));
            gunImage = ImageIO.read(new File(imageFileGun));
        } catch (IOException e) {
            e.printStackTrace();
        }
        angelBody = 0;
        angelGun = 0;
        bulletImageAddres = "Tank-Bullet.png";

    }

    @Override
    public void move() {
        if (mouseMoved) {
            angelGun = Math.atan2(mouseY - (positionY + 67), mouseX - (positionX + 87));
        }

        if (keyUP) {
            positionY -= 8;
            moveUp();
        }

        if (keyDOWN) {
            positionY += 8;
            moveDown();
        }
        if (keyLEFT) {
            positionX -= 8;
            moveLeft();
        }
        if (keyRIGHT) {
            positionX += 8;
            moveRight();
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
                System.out.println(angelBody);
            }
            if (angelInRange(angelBody) > 135 && angelInRange(angelBody) < 315) {
                angelBody += 10;

            }

        } else if (keyLEFT) {
            if ((angelInRange(angelBody) > 225 && angelInRange(angelBody) < 360) || (angelInRange(angelBody) >= 0 && angelInRange(angelBody) < 45)) {
                angelBody -= 10;
                System.out.println(angelBody);
            }
            if (angelInRange(angelBody) > 45 && angelInRange(angelBody) < 225) {
                angelBody += 10;

            }

        } else if (!(angelInRange(angelBody) == 90)) {


            if ((angelInRange(angelBody) >= 0 && angelInRange(angelBody) < 90) || (angelInRange(angelBody) > 270 && angelInRange(angelBody) < 360)) {
                angelBody -= 10;
                System.out.println(angelBody);
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
                System.out.println(angelBody);
            }
            if (angelInRange(angelBody) > 135 && angelInRange(angelBody) < 315) {
                angelBody -= 10;

            }

        } else if (keyRIGHT) {
            if ((angelInRange(angelBody) > 225 && angelInRange(angelBody) < 360) || (angelInRange(angelBody) >= 0 && angelInRange(angelBody) < 45)) {
                angelBody += 10;
                System.out.println(angelBody);
            }
            if (angelInRange(angelBody) > 45 && angelInRange(angelBody) < 225) {
                angelBody -= 10;

            }

        } else if (!(angelInRange(angelBody) == 90)) {

            if ((angelInRange(angelBody) >= 0 && angelInRange(angelBody) < 90) || (angelInRange(angelBody) > 270 && angelInRange(angelBody) < 360)) {
                angelBody += 10;
                System.out.println(angelBody);
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
                System.out.println(angelBody);
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
                System.out.println(angelBody);
            }
            if (angelInRange(angelBody) > 180 && angelInRange(angelBody) < 360) {
                angelBody += 10;

            }
        }
    }

    @Override
    public void shoot(int originX, int originY, int destX, int destY) {
        super.shoot(originX, originY, destX, destY);
        Bullet bullet = new Bullet(originX,originY, destX , destY , bulletImageAddres);
        bullets.add(bullet);
    }

    public void updateBullet(){
        Iterator it = bullets.iterator();
        while (it.hasNext()){
            Bullet bullet = (Bullet)it.next();
            bullet.updateLocation();
        }
    }

    public boolean checkMouseLoc(){
        if (mouseX-positionX>-5 && mouseX-positionX<200 && mouseY-positionY>-10 && mouseY-positionY<154){
            return false;
        }else {
            return true;
        }
    }

    public double angelInRange(double angel) {

        if (angel % 360 < 0) {
            return 360 + angel % 360;
        } else {
            return angel % 360;
        }
    }

    public BufferedImage getGunImage() {
        return gunImage;
    }

    public void setGunImage(BufferedImage gunImage) {
        this.gunImage = gunImage;
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

    public int getX(){
        return positionX;
    }
    
    public int getY(){
        return positionY;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public BufferedImage getBodyImage(){
        return image;
    }

    public int getGunX(){
        return positionX + 87 + (int) ( Math.cos(angelGun) * 90.0) ;
    }
    public int getGunY(){
        return positionY + 67 + (int) ( Math.cos(angelGun) * (-10)) + (int) ( Math.sin(angelGun) * 90.0)  ;
    }


}
