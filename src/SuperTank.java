import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class SuperTank extends Moving {
    protected double angelGun;
    protected BufferedImage gunImage;
    protected CopyOnWriteArrayList<Bullet> bullets ;

    public SuperTank(String firstBodyImage, String secondBodyImage , String imageFileGun , String bulletImageAddress,int positionX,int positionY,int level){
        super(firstBodyImage, secondBodyImage,bulletImageAddress,positionX,positionY, level);
        try {
            gunImage = ImageIO.read(new File(imageFileGun));
        } catch (IOException e) {
            e.printStackTrace();
        }
        angelBody = 0;
        angelGun = 0;
        bullets = new CopyOnWriteArrayList<>();
    }

    public int getGunX() {
        return positionX + 100 + (int) (Math.cos(angelGun) * 90.0);
    }

    public int getGunY() {
        return positionY + 80 + (int) (Math.cos(angelGun) * (-10)) + (int) (Math.sin(angelGun) * 90.0);
    }

    @Override
    public void move() {
    }


    public Rectangle getBounds(){
        return  new Rectangle(positionX , positionY, 150,120) ;
    }

    public void drawGun(Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        AffineTransform atGun = g2d.getTransform();
        atGun.translate(positionX, positionY);
        atGun.rotate(angelGun, 100, 80);
        g2d.setTransform(atGun);
        g2d.drawImage(gunImage, 0, 0, null);
    }


    public void drawBullets(Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        for (Bullet bullet : bullets) {
            AffineTransform atBullet = g2d.getTransform();
            atBullet.translate(bullet.getPositionX(), bullet.getPositionY());
            atBullet.rotate(bullet.getAngelBody(), 5, 2);
            g2d.setTransform(atBullet);
            g2d.drawImage(bullet.getImage(), 0, 0, null);
            g2d.setTransform(oldTrans);
        }
    }

    public void updateBullet() {
        Iterator it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = (Bullet) it.next();
            bullet.updateLocation();
            if (Collision.collisionEnemyBullet(bullet)){
               bullets.remove(bullet);
            }
        }
    }
}
