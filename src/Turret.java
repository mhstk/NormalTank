import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Turret {

    protected BufferedImage image;
    protected BufferedImage gunImage;
    protected double angelGun;
    protected double angelBody;
    protected int positionX;
    protected int positionY;
    private boolean isAlive = true;

    public Turret(int positionX, int positionY, double angelBody, String imageFileBody, String imageFileGun) {
        this.positionX = positionX;
        this.positionY = positionY;
        angelGun = Math.toRadians(90 + angelBody);
        System.out.println("First angel is "+angelBody+ "  from "+angelGun);
        this.angelBody = angelBody;
        try {
            image = ImageIO.read(new File(imageFileBody));
            gunImage = ImageIO.read(new File(imageFileGun));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void act() {
                double firstAngel = angelGun;
                angelGun = Math.atan2(GameState.tankPosition().y - (positionY), GameState.tankPosition().x - (positionX));
                if (angelGun<0){
                    angelGun = Math.toRadians(360)+(angelGun%(Math.toRadians(360)));
                }
        System.out.println("now "+angelGun);
                if (angelGun > Math.toRadians(180+angelBody)) {angelGun = firstAngel;
                    System.out.println("Heloo");}
                if (angelGun%Math.abs(Math.toRadians(360)) < Math.toRadians(angelBody)) {angelGun = firstAngel;
                    System.out.println("angel gun "+angelGun+ " angel Body " + angelBody);}
//                if ()
                System.out.println("Angel is " + angelGun);
     }

    public void drawGun(Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        AffineTransform atGun = g2d.getTransform();
        atGun.translate(positionX, positionY);
        atGun.rotate(angelGun, 87, 67);
        g2d.setTransform(atGun);
        g2d.drawImage(state.getTurret().getGunImage(), 0, 0, null);
    }

    public void drawBody(Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        AffineTransform atBody = g2d.getTransform();
        atBody.rotate(Math.toRadians(angelBody), positionX + image.getWidth() / 2, positionY + image.getHeight() / 2);
        g2d.setTransform(atBody);
        g2d.drawImage(image, positionX, positionY, null);
        g2d.setTransform(oldTrans);

    }

    public void isInArea() {
        System.out.println("In area ");
        int playerTankX = GameState.tankPosition().x;
        int playerTankY = GameState.tankPosition().y;
        double distance = Math.pow(positionX - playerTankX, 2) + Math.pow(positionY - playerTankY, 2);
        if (Math.sqrt(distance) < 1000) {
            System.out.println("In if");
            act();
        }
    }

    public BufferedImage getGunImage() {
        return gunImage;
    }
}
