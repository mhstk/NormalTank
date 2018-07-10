import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Turret {

    public static final String[] statuses = {"UP", "DOWN", "LEFT", "RIGHT"};
    protected BufferedImage image;
    protected BufferedImage gunImage;
    protected double angelGun;
    protected double angelBody;
    protected int positionX;
    protected int positionY;
    private boolean isAlive = true;
    private String status;
    private double difTimeBullet = 2.7;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private long timeLastShotGun = 0;

    public Turret(int positionX, int positionY, String status, String imageFileBody, String imageFileGun) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.status = status;
        try {
            image = ImageIO.read(new File(imageFileBody));
            gunImage = ImageIO.read(new File(imageFileGun));

        } catch (IOException e) {
            e.printStackTrace();
        }
        setFirstAngel();
    }

    private void setFirstAngel() {
        if (status.equals("UP")) {
            angelGun = Math.toRadians(90);
            angelBody = 0;
        } else if (status.equals("DOWN")) {
            angelGun = Math.toRadians(270);
            angelBody = 180;
        } else if (status.equals("LEFT")) {
            angelGun = Math.toRadians(0);
            angelBody = 90;
        } else if (status.equals("RIGHT")) {
            angelGun = Math.toRadians(180);
            angelBody = 270;
        }
    }

    public void act() {
        double firstAngel = angelGun;
        angelGun = Math.atan2(GameState.tankPosition().y - (positionY), GameState.tankPosition().x - (positionX));
        if (!isPossible(angelGun))       angelGun = firstAngel;

        Long now = System.nanoTime();
        if ((now - timeLastShotGun) / 1000000000.0 > difTimeBullet) {
            shoot();
            System.out.println("11");
            timeLastShotGun = now;
        }

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

    public void shoot() {
        Bullet bullet = new Bullet(positionX + (87), positionY + (67), GameState.tankPosition().x + (image.getWidth() / 2), GameState.tankPosition().y + (image.getHeight() / 2), "Tank-Bullet.png", 20);
        if (isPossible(bullet.getAngel()))
            bullets.add(bullet);
    }

    private boolean isPossible(double angel) {
        switch (status) {
            case "UP":
                return !(angel < Math.toRadians(0)) && !(angel > Math.toRadians(180));
            case "DOWN":
                return !(angel > Math.toRadians(0));
            case "LEFT":
                return !(angel < Math.toRadians(-90)) && !(angel > Math.toRadians(90));
            case "RIGHT":
                return (!(angel > Math.toRadians(0)) || !(angel < Math.toRadians(90))) && (!(angel > Math.toRadians(-90)) || !(angel < Math.toRadians(0)));
        }
        return true;
    }


    public void drawBullets(Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        for (Bullet bullet : bullets) {
            AffineTransform atBullet = g2d.getTransform();
            atBullet.translate(bullet.getPositionX(), bullet.getPositionY());
            atBullet.rotate(bullet.getAngel(), 5, 2);
            g2d.setTransform(atBullet);
            g2d.drawImage(bullet.getImage(), 0, 0, null);
            g2d.setTransform(oldTrans);
        }

        updateBullet();
        System.out.println("Shelik");
    }

    private void updateBullet() {

        Iterator it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = (Bullet) it.next();
            bullet.updateLocation();
        }
    }
}
