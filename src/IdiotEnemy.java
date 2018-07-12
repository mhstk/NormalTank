import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

public class IdiotEnemy extends Moving {

    private boolean seed = false;
    private double angelBody;
    private boolean down,up,left,right;
    private boolean isFirstImage = true;
    private long lastTimeImageChanged = 0;


    public IdiotEnemy(int positionX, int positionY) {
        super(null);
        this.positionY = positionY;
        this.positionX = positionX;
        try {
            image = ImageIO.read(new File("idiotEnemy1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        angelBody = 0;
    }

    public void isInArea() {
        int playerTankX = GameState.tankPosition().x;
        int playerTankY = GameState.tankPosition().y;
        double distance = Math.pow(positionX - playerTankX, 2) + Math.pow(positionY - playerTankY, 2);
        if (Math.sqrt(distance) < 1500){
            seed = true;
        }
    }

    @Override
    public void move() {
        System.out.println("Seed" + seed);
        long now = System.nanoTime();
        if ((now - lastTimeImageChanged) / 1000000000.0 > 0.08) {
            if (isFirstImage) {
                try {
                    image = ImageIO.read(new File("idiotEnemy2.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isFirstImage = false;
            } else {
                try {
                    image = ImageIO.read(new File("idiotEnemy1.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isFirstImage = true;
            }
            lastTimeImageChanged = now;
        }
        if (seed) {
            if (GameState.tankPosition().x > positionX) {
                positionX += 4;
                if (GameLoop.state.camerafixedX) {
                    positionX += 4;
                }
                if (positionX > GameState.tankPosition().x) positionX = GameState.tankPosition().x;
                right = true;
                left = false;
                moveRight();
            } else if (GameState.tankPosition().x < positionX) {
                positionX -= 4;
                if (GameLoop.state.camerafixedX) {
                    positionX -= 4;
                }
                if (positionX < GameState.tankPosition().x) positionX = GameState.tankPosition().x;
                left = true;
                right = false;
                moveLeft();
            } else {
                right = false;
                left = false;
            }
            if (GameState.tankPosition().y > positionY) {
                positionY += 4;
                if (GameLoop.state.camerafixedY) {
                    positionY += 4;
                }
                if (positionY > GameState.tankPosition().y) positionY = GameState.tankPosition().y;
                down = true;
                up = false;
                moveDown();

            } else if (GameState.tankPosition().y < positionY) {
                positionY -= 4;
                if (GameLoop.state.camerafixedY) {
                    positionY -= 4;
                }
                if (positionY < GameState.tankPosition().y) positionY = GameState.tankPosition().y;
                up = true;
                down = false;
                moveUp();
            } else {
                up = false;
                down = false;
            }

//        if (up || down || left || right) {
//            long now = System.nanoTime();
//            if ((now - lastTimeImageChanged) / 1000000000.0 > 0.08) {
//                if (isFirstImage) {
//                    try {
//                        image = ImageIO.read(new File("Tank-under2.png"));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    isFirstImage = false;
//                } else {
//                    try {
//                        image = ImageIO.read(new File("Tank-under.png"));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    isFirstImage = true;
//                }
//                lastTimeImageChanged = now;
//            }
//        }
        }

    }
    public void moveUp() {
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

    public void moveDown() {
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

    public void moveLeft() {
        if (!(angelInRange(angelBody) == 0) && !(down) && !(up)) {
            if ((angelInRange(angelBody) > 0 && angelInRange(angelBody) < 180)) {
                angelBody += 10;
            }
            if (angelInRange(angelBody) > 180 && angelInRange(angelBody) < 360) {
                angelBody -= 10;
            }
        }

    }

    public void moveRight() {
        if (!(angelInRange(angelBody) == 180) && !(down) && !(up)) {

            if ((angelInRange(angelBody) > 0 && angelInRange(angelBody) < 180)) {
                angelBody -= 10;
            }
            if (angelInRange(angelBody) > 180 && angelInRange(angelBody) < 360) {
                angelBody += 10;

            }
        }
    }
    public double angelInRange(double angel) {

        if (angel % 360 < 0) {
            return 360 + angel % 360;
        } else {
            return angel % 360;
        }
    }

    public void drawBody(Graphics2D g2d , GameState state , AffineTransform oldTrans){
        AffineTransform atBody = g2d.getTransform();
        atBody.rotate(Math.toRadians(angelBody), positionX + image.getWidth() / 2, positionY + image.getHeight() / 2);
        g2d.setTransform(atBody);
        g2d.drawImage(image, positionX, positionY, null);
        g2d.setTransform(oldTrans);
    }

    public Rectangle getBounds(){
        return  new Rectangle(positionX , positionY , image.getWidth(),image.getHeight()) ;
    }

    public double getAngelBody() {
        return angelBody;
    }
}
