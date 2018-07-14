import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Turret extends SuperTank {

    public static final String[] STATUTS = {"UP", "DOWN", "LEFT", "RIGHT"};
    private String status;
    private long timeLastShotGun = 0;

    public Turret(int positionX, int positionY, String status) {
        super("Tank-under.png","Tank-under.png","Tank-top.png","Tank-Bullet.png",positionX,positionY);
        this.status = status;
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
        if (!isPossible(angelGun))
            angelGun = firstAngel;
        Long now = System.nanoTime();
        if ((now - timeLastShotGun) / 1000000000.0 > 2.7) {
            shoot();
            timeLastShotGun = now;
        }
    }

    public void drawGun(BufferedImage image , Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        AffineTransform atGun = g2d.getTransform();
        atGun.translate(positionX, positionY);
        atGun.rotate(angelGun, 87, 67);
        g2d.setTransform(atGun);
        g2d.drawImage(image, 0, 0, null);
    }

    public void isInArea() {
        int playerTankX = GameState.tankPosition().x;
        int playerTankY = GameState.tankPosition().y;
        double distance = Math.pow(positionX - playerTankX, 2) + Math.pow(positionY - playerTankY, 2);
        if (Math.sqrt(distance) < 500) {
            act();
        }
    }

    public void shoot() {
        Bullet bullet = new Bullet(positionX + (87), positionY + (67), GameState.tankPosition().x + (205/ 2), GameState.tankPosition().y + (160 / 2), 0, 20);
        if (isPossible(bullet.getAngelBody())) {
            bullets.add(bullet);
            Sound sound = new Sound("heavygun.wav", 0);
            sound.execute();
        }
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

}
