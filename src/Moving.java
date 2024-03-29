import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public abstract class Moving extends Runner implements Serializable {
    private long lastTimeImageChanged;
    protected boolean isFirstImage = true;
    protected int speed;
    protected double angelBody;
    protected boolean up = false;
    protected boolean down = false;
    protected boolean left = false;
    protected boolean right = false;
    private int count = 0;

    public Moving(String firstBodyImage,String secondBodyImage,String bulletImageAddress,int positionX , int positionY , int level){
        super(bulletImageAddress,positionX,positionY, level);


    }

    public Rectangle getBounds(){
        return  null ;
    }

    public void drawBody(BufferedImage image ,BufferedImage image2 ,BufferedImage destroyed,Graphics2D g2d , GameState state , AffineTransform oldTrans){
        g2d.setTransform(oldTrans);
        AffineTransform atBody = g2d.getTransform();
        atBody.rotate(Math.toRadians(angelBody), positionX + image.getWidth() / 2, positionY + image.getHeight() / 2);
        g2d.setTransform(atBody);
        if (this.isAlive()) {
            long now = System.nanoTime();
            if ((now - lastTimeImageChanged) / 1000000000.0 > 0.08) {
                count++;
                lastTimeImageChanged = now;
            }
            if (count % 2 == 0) {
                g2d.drawImage(image, positionX, positionY, null);
            } else g2d.drawImage(image2, positionX, positionY, null);
        }else {
            g2d.drawImage(destroyed, positionX, positionY, null);
        }
        g2d.setTransform(oldTrans);
    }

    public void drawBodyClient(BufferedImage image ,Graphics2D g2d , GameState state , AffineTransform oldTrans){
        g2d.setTransform(oldTrans);
        AffineTransform atBody = g2d.getTransform();
        atBody.rotate(Math.toRadians(angelBody), clientLoc.x + image.getWidth() / 2, clientLoc.y + image.getHeight() / 2);
        g2d.setTransform(atBody);
        g2d.drawImage(image, clientLoc.x, clientLoc.y, null);
        g2d.setTransform(oldTrans);
    }

    public abstract void move();

    protected void moveUp() {
        if (right) {
            if ((angelInRange(angelBody) > 315 && angelInRange(angelBody) < 360) || (angelInRange(angelBody) >= 0 && angelInRange(angelBody) < 135)) {
                angelBody -= 10;
            }
            if (angelInRange(angelBody) > 135 && angelInRange(angelBody) < 315) {
                angelBody += 10;

            }

        } else if (left) {
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

    protected void moveDown() {
        if (left) {
            if ((angelInRange(angelBody) > 315 && angelInRange(angelBody) < 360) || (angelInRange(angelBody) >= 0 && angelInRange(angelBody) < 135)) {
                angelBody += 10;
            }
            if (angelInRange(angelBody) > 135 && angelInRange(angelBody) < 315) {
                angelBody -= 10;

            }

        } else if (right) {
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

    public void destroy(){
        alive = false ;
    }



    protected void moveLeft() {
        if (!(angelInRange(angelBody) == 0) && !(down) && !(up)) {
            if ((angelInRange(angelBody) > 0 && angelInRange(angelBody) < 180)) {
                angelBody += 10;
            }
            if (angelInRange(angelBody) > 180 && angelInRange(angelBody) < 360) {
                angelBody -= 10;
            }
        }

    }

    protected void moveRight() {
        if (!(angelInRange(angelBody) == 180) && !(down) && !(up)) {

            if ((angelInRange(angelBody) > 0 && angelInRange(angelBody) < 180)) {
                angelBody -= 10;
            }
            if (angelInRange(angelBody) > 180 && angelInRange(angelBody) < 360) {
                angelBody += 10;
            }
        }
    }

    private double angelInRange(double angel) {

        if (angel % 360 < 0) {
            return 360 + angel % 360;
        } else {
            return angel % 360;
        }
    }


    public double getAngelBody() {
        return angelBody;
    }
}
