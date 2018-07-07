/*** In The Name of Allah ***/

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 */
public class GameState {

    public int locX, locY, diam;
    public boolean gameOver;

    private static PlayerTank playerTank = new PlayerTank("Tank-under.png", "Tank-top.png","Tank-Bullet.png");
    private EnemyTank enemyTank = new EnemyTank(500,500,"Tank-under.png", "Tank-top.png","Tank-Bullet.png");

    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    public double rad = 0;
    public double rad2 = 0;
    private boolean mousePress;
    private boolean mouseMoved;
    public int mouseX, mouseY;
    private static int count = 1;
    private long timeLastShotGun = 0;
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

    public static Point tankPosition(){
        return new Point(playerTank.getX(),playerTank.getY());
    }
    /**
     * The method which updates the game state.
     */
    public void update() {
        playerTank.move();
        enemyTank.move();
        if (mousePress && playerTank.checkMouseLoc()) {
            Long now = System.nanoTime();
            if ((now - timeLastShotGun) / 1000000000.0 > playerTank.getDifTimeBullet()) {

                playerTank.shoot(playerTank.getGunX(), playerTank.getGunY(), mouseX, mouseY);
                timeLastShotGun = now;

            }
        }
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


    /**
     * The keyboard handler.
     */
    class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                case (KeyEvent.VK_UP):
                    playerTank.setKeyUP(true);
                    playerTank.setKeyDOWN(false);


                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    playerTank.setKeyUP(false);
                    playerTank.setKeyDOWN(true);

                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    playerTank.setKeyRIGHT(false);
                    playerTank.setKeyLEFT(true);

                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    playerTank.setKeyRIGHT(true);
                    playerTank.setKeyLEFT(false);

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
                    playerTank.setKeyUP(false);
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    playerTank.setKeyDOWN(false);
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    playerTank.setKeyLEFT(false);
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    playerTank.setKeyRIGHT(false);
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

            if (e.getButton() == MouseEvent.BUTTON3) {
                if (count == 1) {
                    playerTank.changeGunTow();
                    count = 2;
                } else {
                    playerTank.changeGunOne();
                    count = 1;
                }
            } else {
                mouseX = e.getX();
                mouseY = e.getY();
                playerTank.setMouseX(mouseX);
                playerTank.setMouseY(mouseY);
                playerTank.setMousePress(true);
                mousePress = true;
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            super.mouseMoved(e);
            mouseX = e.getX();
            mouseY = e.getY();
            playerTank.setMouseX(mouseX);
            playerTank.setMouseY(mouseY);
            playerTank.setMouseMoved(true);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mousePress = false;
            playerTank.setMousePress(false);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            playerTank.setMouseX(mouseX);
            playerTank.setMouseY(mouseY);
        }
    }

    public PlayerTank getPlayerTank() {
        return playerTank;
    }
    public EnemyTank getEnemyTank(){ return enemyTank;}
}

