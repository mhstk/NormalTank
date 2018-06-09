/*** In The Name of Allah ***/

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.security.Key;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState {

    public int locX, locY, diam;
    public boolean gameOver;

    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    public double rad = 0;
    public double rad2 = 0;
    public double rad3 = 0;
    private boolean mousePress;
    private boolean mouseMoved;
    public int mouseX, mouseY;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    public GameState() {
        locX = 100;
        locY = 100;
        diam = 32;
        gameOver = false;
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        //
        mousePress = false;

        mouseX = 0;
        mouseY = 0;
        //
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
    }

    /**
     * The method which updates the game state.
     */
    public void update() {
        if (mouseMoved) {
            rad = Math.atan2(mouseY - (locY + 67), mouseX - (locX + 87));
        }

        if (keyUP) {
            locY -= 8;
            moveUp();
        }

        if (keyDOWN) {
            locY += 8;
            moveDown();
        }
        if (keyLEFT) {
            locX -= 8;
            moveLeft();
        }
        if (keyRIGHT) {
            locX += 8;
            moveRight();
        }

        locX = Math.max(locX, 0);
        locX = Math.min(locX, GameFrame.GAME_WIDTH-177);
        locY = Math.max(locY, 0);
        locY = Math.min(locY, GameFrame.GAME_HEIGHT-134);
    }


    public KeyListener getKeyListener() {
        return keyHandler;
    }

    public MouseListener getMouseListener() {
        return mouseHandler;
    }

    public MouseMotionListener getMouseMotionListener() {
        return mouseHandler;
    }

    public void moveUp() {
        if (keyRIGHT) {
            if ((angelInRange(rad2) > 315 && angelInRange(rad2) < 360) || (angelInRange(rad2) >= 0 && angelInRange(rad2) < 135)) {
                rad2 -= 10;
                System.out.println("up");
                System.out.println(rad2);
            }
            if (angelInRange(rad2) > 135 && angelInRange(rad2) < 315) {
                rad2 += 10;

            }

        } else if (keyLEFT) {
            if ((angelInRange(rad2) > 225 && angelInRange(rad2) < 360) || (angelInRange(rad2) >= 0 && angelInRange(rad2) < 45)) {
                rad2 -= 10;
                System.out.println(rad2);
            }
            if (angelInRange(rad2) > 45 && angelInRange(rad2) < 225) {
                rad2 += 10;

            }

        } else if (!(angelInRange(rad2) == 90)) {


            if ((angelInRange(rad2) >= 0 && angelInRange(rad2) < 90) || (angelInRange(rad2) > 270 && angelInRange(rad2) < 360)) {
                rad2 -= 10;
                System.out.println(rad2);
            }
            if (angelInRange(rad2) > 90 && angelInRange(rad2) < 270) {
                rad2 += 10;

            }

        }
    }

    public void moveDown() {
        if (keyLEFT) {
            if ((angelInRange(rad2) > 315 && angelInRange(rad2) < 360) || (angelInRange(rad2) >= 0 && angelInRange(rad2) < 135)) {
                rad2 += 10;
                System.out.println(rad2);
            }
            if (angelInRange(rad2) > 135 && angelInRange(rad2) < 315) {
                rad2 -= 10;

            }

        } else if (keyRIGHT) {
            if ((angelInRange(rad2) > 225 && angelInRange(rad2) < 360) || (angelInRange(rad2) >= 0 && angelInRange(rad2) < 45)) {
                System.out.println("right");
                rad2 += 10;
                System.out.println(rad2);
            }
            if (angelInRange(rad2) > 45 && angelInRange(rad2) < 225) {
                rad2 -= 10;

            }

        } else if (!(angelInRange(rad2) == 90)) {

            if ((angelInRange(rad2) >= 0 && angelInRange(rad2) < 90) || (angelInRange(rad2) > 270 && angelInRange(rad2) < 360)) {
                rad2 += 10;
                System.out.println(rad2);
            }
            if (angelInRange(rad2) > 90 && angelInRange(rad2) < 270) {
                rad2 -= 10;

            }


        }
    }

    public void moveLeft() {
        if (keyDOWN) {
            if ((angelInRange(rad2) > 315 && angelInRange(rad2) < 360) || (angelInRange(rad2) >= 0 && angelInRange(rad2) < 135)) {
                rad2 += 10;
                System.out.println(rad2);
            }
            if (angelInRange(rad2) > 135 && angelInRange(rad2) < 315) {
                rad2 -= 10;

            }

        } else if (keyUP) {
            if ((angelInRange(rad2) > 225 && angelInRange(rad2) < 360) || (angelInRange(rad2) >= 0 && angelInRange(rad2) < 45)) {
                rad2 -= 10;
                System.out.println(rad2);
            }
            if (angelInRange(rad2) > 45 && angelInRange(rad2) < 225) {
                rad2 += 10;

            }

        } else if (!(angelInRange(rad2) == 0)) {
            if ((angelInRange(rad2) > 0 && angelInRange(rad2) < 180)) {
                rad2 += 10;
                System.out.println(rad2);
            }
            if (angelInRange(rad2) > 180 && angelInRange(rad2) < 360) {
                rad2 -= 10;

            }
        }

    }

    public void moveRight() {
        if (keyUP) {
            if ((angelInRange(rad2) > 315 && angelInRange(rad2) < 360) || (angelInRange(rad2) > 0 && angelInRange(rad2) < 135)) {
                rad2 -= 10;
                System.out.println(rad2);
            }
            if (angelInRange(rad2) > 135 && angelInRange(rad2) < 315) {
                rad2 += 10;

            }

        } else if (keyDOWN) {
            if ((angelInRange(rad2) > 225 && angelInRange(rad2) < 360) || (angelInRange(rad2) > 0 && angelInRange(rad2) < 45)) {
                rad2 += 10;
                System.out.println(rad2);
            }
            if (angelInRange(rad2) > 45 && angelInRange(rad2) < 225) {
                rad2 -= 10;

            }

        } else {
            if (!(angelInRange(rad2) == 180)) {

                if ((angelInRange(rad2) > 0 && angelInRange(rad2) < 180)) {
                    rad2 -= 10;
                    System.out.println(rad2);
                }
                if (angelInRange(rad2) > 180 && angelInRange(rad2) < 360) {
                    rad2 += 10;

                }
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


    /**
     * The keyboard handler.
     */
    class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                case (KeyEvent.VK_UP):
                    keyUP = true;
                    keyDOWN = false;


                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    keyDOWN = true;
                    keyUP = false;

                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    keyLEFT = true;
                    keyRIGHT = false;

                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = true;
                    keyLEFT = false;

                    break;
                case KeyEvent.VK_ESCAPE:
                    gameOver = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    keyUP = false;
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    keyDOWN = false;
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    keyLEFT = false;
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = false;
                    break;
            }
        }

    }

    /**
     * The mouse handler.
     */
    class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            mousePress = true;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            super.mouseMoved(e);
            mouseX = e.getX();
            mouseY = e.getY();
            mouseMoved = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mousePress = false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }
}

