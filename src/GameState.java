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
    public boolean cameraFixedX, cameraFixedY;
    private static PlayerTank playerTank = new PlayerTank(160,1600);
    private EnemyTank enemyTank = new EnemyTank(160,-300);
    private Turret turret;
    private IdiotEnemy idiotEnemy = new IdiotEnemy(2080,0);
    public boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    public boolean mouseUP, mouseDOWN, mouseRIGHT, mouseLEFT;
    private boolean mousePress;
    public int mouseX, mouseY;
    private static int count = 1;
    private long timeLastShotGun = 0;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    public Map map;

    public GameState() {
        cameraFixedX = false;
        cameraFixedY = false;
        locX = 100;
        locY = 100;
        diam = 32;
        gameOver = false;
        this.turret  = new Turret(700,100,"UP");
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        mouseUP = false;
        mouseDOWN = false;
        mouseRIGHT = false;
        mouseLEFT = false;
        //
        mousePress = false;

        mouseX = 800;
        mouseY = 400;
        //
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();

        map = new Map();
        map.createMap("Map.txt");

    }

    public static Point tankPosition(){
        return new Point(playerTank.positionX,playerTank.positionY);
    }

    public static PlayerTank getTank() {
        return playerTank;
    }

    /**
     * The method which updates the game state.
     */
    public void update() {
        playerTank.move();
        enemyTank.isInArea();
        turret.isInArea();
        idiotEnemy.isInArea();
        idiotEnemy.move();

        Camera.updateInfo();
        if (!Collision.CollisionPlayerTank()) {
            Camera.cameraMove();
        }
        if (mousePress && playerTank.checkMouseLoc()) {
            Long now = System.nanoTime();
            if ((now - timeLastShotGun) / 1000000000.0 > playerTank.difTimeBullet) {
                playerTank.shoot(playerTank.getGunX(), playerTank.getGunY(), mouseX, mouseY);
                timeLastShotGun = now;
                if (playerTank.getGunNumber() == 1){
                    mousePress = false;
                }

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

    public Turret getTurret() {
        return turret;
    }

    public IdiotEnemy getIdiotEnemy() {
    return idiotEnemy;
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
                    keyUP = true;
                    keyDOWN = false;


                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    playerTank.setKeyUP(false);
                    playerTank.setKeyDOWN(true);
                    keyDOWN = true;
                    keyUP = false;


                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    playerTank.setKeyRIGHT(false);
                    playerTank.setKeyLEFT(true);
                    keyLEFT = true;
                    keyRIGHT = false;


                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    playerTank.setKeyRIGHT(true);
                    playerTank.setKeyLEFT(false);
                    keyRIGHT = true;
                    keyLEFT = false;


                    break;
                case KeyEvent.VK_ESCAPE:
                    gameOver = true;
                    Main.SOUND.cancel();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    playerTank.setKeyUP(false);
                    keyUP = false;
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    playerTank.setKeyDOWN(false);
                    keyDOWN = false;
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    playerTank.setKeyLEFT(false);
                    keyLEFT = false;
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    playerTank.setKeyRIGHT(false);
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
            if (e.getX() - mouseX > 12) {
                mouseRIGHT = true;
                mouseLEFT = false;
            } else if (e.getX() - mouseX < -12) {
                mouseLEFT = true;
                mouseRIGHT = false;
            } else {
                mouseRIGHT = false;
                mouseLEFT = false;
            }
            if (e.getY() - mouseY > 12) {
                mouseDOWN = true;
                mouseUP = false;
            } else if (e.getY() - mouseY < -12) {
                mouseUP = true;
                mouseDOWN = false;
            } else {
                mouseUP = false;
                mouseDOWN = false;
            }
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

    public EnemyTank getEnemyTank() {
        return enemyTank;
    }
}

