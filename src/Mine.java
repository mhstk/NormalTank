import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Mine extends Runner {
    private boolean alive ;
    private boolean visible;
    private int count = 0;
    private long lastTimeImageChanged;

    public Mine(int positionX, int positionY,int level) {
        super("" , positionX, positionY,level);
        damage = 4;
        health = 1;
        alive = true;
        visible =false;
    }

    public void inArea(){
        int playerTankX = GameState.tankPosition().x;
        int playerTankY = GameState.tankPosition().y;
        double distance = Math.pow(positionX - playerTankX, 2) + Math.pow(positionY - playerTankY, 2);
        if (Math.sqrt(distance) < 300){
            visible = true;
            if (alive) {
                Sound sound = new Sound("mine.wav", 0);
                sound.execute();
            }
        } else {
            visible = false;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(positionX+20,positionY+20, 110, 100);
    }

    public void act(){
        if (visible && alive){
            //changeBodyImage();
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isVisible() {
        return visible;
    }

    public void drawBody(BufferedImage image , BufferedImage image2 , Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        AffineTransform atBody = g2d.getTransform();
        atBody.rotate(Math.toRadians(0), positionX + image.getWidth() / 2, positionY + image.getHeight() / 2);
        g2d.setTransform(atBody);
        long now = System.nanoTime();
        if ((now - lastTimeImageChanged) / 1000000000.0 > 0.08) {
            count ++;
            lastTimeImageChanged = now;
        }
        if (count%2 == 0){
            g2d.drawImage(image,positionX,positionY,null);
        } else  g2d.drawImage(image2,positionX,positionY,null);
        g2d.setTransform(oldTrans);
    }

    public void destroy() {
        alive = false;
        Sound sound1 = new Sound("MineBoom.wav",0);
        sound1.execute();
    }
}
