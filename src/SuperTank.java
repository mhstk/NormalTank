import javafx.scene.shape.Line;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class SuperTank extends Moving {
    protected double angelGun;
    protected BufferedImage gunImage;
    protected ArrayList<Bullet> bullets ;

    public SuperTank(String firstBodyImage, String secondBodyImage , String imageFileGun , String bulletImageAddress,int positionX,int positionY){
        super(firstBodyImage, secondBodyImage,bulletImageAddress,positionX,positionY);
        try {
            gunImage = ImageIO.read(new File(imageFileGun));
        } catch (IOException e) {
            e.printStackTrace();
        }
        angelBody = 0;
        angelGun = 0;
        bullets = new ArrayList<>();
    }

    @Override
    public void move() {

    }

    public void drawGun(Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        AffineTransform atGun = g2d.getTransform();
        atGun.translate(positionX, positionY);
        atGun.rotate(angelGun, 87, 67);
        g2d.setTransform(atGun);
        g2d.drawImage(state.getPlayerTank().getGunImage(), 0, 0, null);
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
    }

    public void updateBullet() {
        Iterator it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = (Bullet) it.next();
            bullet.updateLocation();
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
