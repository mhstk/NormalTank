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
    public boolean camerafixedX, camerafixedY;
    private static PlayerTank playerTank = new PlayerTank("Tank-under.png", "Tank-top.png","Tank-Bullet.png");
    private EnemyTank enemyTank = new EnemyTank(500,500,"Tank-under.png", "Tank-top.png","Tank-Bullet.png");
    private Turret turret = new Turret(100,100,0.0,"Tank-under.png", "Tank-top.png");
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean mouseUP, mouseDOWN, mouseRIGHT, mouseLEFT;
    public double rad = 0;
    public double rad2 = 0;
    private boolean mousePress;
    private boolean mouseMoved;
    public int mouseX, mouseY;
    private static int count = 1;
    private long timeLastShotGun = 0;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    int[][] maps = new int[100][100];
    int originX = 0;
    int originY = 0;

    public GameState() {

        camerafixedX = false;
        camerafixedY = false;
        locX = 100;
        locY = 100;
        diam = 32;
        gameOver = false;
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

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                maps[i][j] = 0;
            }
        }

        for (int i=0 ; i<10 ; i++){
            maps[i][0] = 1;
        }
        maps[1][1] = 2;
        maps[1][2] = 2;
        maps[1][3] = 2;
        maps[1][4] = 2;
    }

    public static Point tankPosition(){
        return new Point(playerTank.getX(),playerTank.getY());
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
turret.act();
        if (keyUP) {
            originY += 4;
            //enemy tank should'nt move
            enemyTank.positionY += 4;
        }
        if (keyDOWN) {
            originY -= 4;
            enemyTank.positionY -= 4;
        }
        if (keyLEFT) {
            originX -= 4;
            enemyTank.positionX += 4;
        }
        if (keyRIGHT) {
            originX += 4;
            enemyTank.positionX -= 4;
        }

        if (mouseY <= 200 && playerTank.positionY<700){
            mouseUP = true;
            mouseDOWN = false;
        }
        if (1080 - mouseY <=200 && playerTank.positionY>200){
            mouseDOWN = true;
            mouseUP = false;
        }
        if (mouseX <= 200 && playerTank.positionX<1400){
            mouseLEFT = true;
            mouseRIGHT = false;
        }
        if (1920 - mouseX <=200 && playerTank.positionX>200){
            mouseRIGHT = true;
            mouseLEFT = false;
        }
        if (!(playerTank.positionY<700)){
            mouseUP = false;
        }
        if (!(playerTank.positionY>200)){
            mouseDOWN = false;
        }
        if (!(playerTank.positionX<1400)){
            mouseLEFT = false;
        }
        if (!(playerTank.positionX>200)){
            mouseRIGHT = false;
        }
        if (mouseUP) {
            playerTank.positionY+=5;
            enemyTank.positionY+=5;
            originY += 5;
        }
        if (mouseDOWN) {
            playerTank.positionY-=5;
            enemyTank.positionY-=5;
            originY -= 5;
        }
        if (mouseLEFT) {
            playerTank.positionX+=5;
            enemyTank.positionX+=5;
            originX -= 5;
        }
        if (mouseRIGHT) {
            playerTank.positionX-=5;
            enemyTank.positionX-=5;
            originX += 5;
        }

        if (originX < 0) {
            camerafixedX = true;
            if (mouseRIGHT){
                playerTank.positionX+=5;
                enemyTank.positionX+=5;
            }
            if (mouseLEFT){
                playerTank.positionX-=5;
                enemyTank.positionX-=5;
            }

            originX = 0;
        }else {
            camerafixedX = false;
        }
        if (originY < 0) {
            camerafixedY = true;
            if (mouseUP){
                playerTank.positionY-=5;
                enemyTank.positionY-=5;
            }
            if (mouseDOWN){
                playerTank.positionY+=5;
                enemyTank.positionY+=5;
            }

            originY = 0;
        }else {
            camerafixedY = false;
        }


        if (mousePress && playerTank.checkMouseLoc()) {
            Long now = System.nanoTime();
            if ((now - timeLastShotGun) / 1000000000.0 > playerTank.getDifTimeBullet()) {
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
    public EnemyTank getEnemyTank(){ return enemyTank;}
}

