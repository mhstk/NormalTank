/*** In The Name of Allah ***/

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.Serializable;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 */
public class GameState implements Serializable {

    public int locX, locY, diam;
    private static int count = 1 ;
    public boolean gameOver;
    public boolean cameraFixedX, cameraFixedY;
    private static PlayerTank playerTank;
    static CoPlayerTank coPlayer;
    public static int mode;
    public boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    public boolean mouseUP, mouseDOWN, mouseRIGHT, mouseLEFT;
    public int mouseX, mouseY;
    public Map map;
    protected boolean mousePress;
    private long timeLastShotGun = 0;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private int level;
    private CheatCode cheatCode;

    public GameState(int mode , int level,String map) {
        this.level = level;
        playerTank = new PlayerTank(160, 800, level);
        cheatCode = new CheatCode(playerTank);
        cameraFixedX = false;
        cameraFixedY = false;
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
        GameState.mode = mode;
        //
        if (mode != 2) {
            keyHandler = new KeyHandler();
            mouseHandler = new MouseHandler();
        }


        if (mode != 2) {
            this.map = new Map(level);
            this.map.createMap(map);

        }
        if (mode == 1) {
            coPlayer = new CoPlayerTank(60,900 , 0);
            Server.getInstance().start();

            Data data = new Data(playerTank,coPlayer,this.map);


            Server.getInstance().sendData(data);
            Server.getInstance().reset();
        }


    }

    public static Point tankPosition() {
        return new Point(playerTank.positionX, playerTank.positionY);
    }

    public static PlayerTank getTank() {
        return playerTank;
    }

    /**
     * The method which updates the game state.
     */
    public void update() {
        if (playerTank.getHealth() == 0) {
            gameOver = true;
        }
        if (mode == 1) {

            Server.getInstance().getData();
            System.out.println(coPlayer.up);
            System.out.println("up : "+ coPlayer.up + "***" +"down : "+ coPlayer.down + "***" +"left : "+ coPlayer.left + "***" +"right : "+ coPlayer.right );
            coPlayer.move();

//          Client.getInstance().reset();

        }

        playerTank.move();
        for (EnemyTank enemyTank : map.enemyTanks) enemyTank.isInArea();
        for (Turret turret : map.turrets) turret.isInArea();
        for (IdiotEnemy idiotEnemy : map.idiotEnemies) {
            idiotEnemy.isInArea();
            idiotEnemy.move();
        }
        for (Mine mine : map.mines) {
            mine.inArea();
            mine.act();
        }


        ServerCamera serverCamera = new ServerCamera(this);
        serverCamera.updateInfo();
        if (!Collision.collisionPlayerTank()) {
            serverCamera.cameraMove();
        }
        if (mousePress && playerTank.checkMouseLoc()) {
            Long now = System.nanoTime();
            if ((now - timeLastShotGun) / 1000000000.0 > playerTank.difTimeBullet) {
                playerTank.shoot(playerTank.getGunX(), playerTank.getGunY(), mouseX, mouseY);
                timeLastShotGun = now;
                if (playerTank.getGunNumber() == 1) {
                    mousePress = false;
                }

            }
        }

        if (mode == 1){
            Data data = new Data(GameLoop.state.getPlayerTank(),GameState.coPlayer,GameLoop.state.map);
            Server.getInstance().sendData(data);
            Server.getInstance().reset();

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

    public PlayerTank getPlayerTank() {
        return playerTank;
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

                case KeyEvent.VK_Q:

            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    cheatCode.addChar("w");
                case KeyEvent.VK_UP:
                    playerTank.setKeyUP(false);
                    keyUP = false;
                    break;
                case KeyEvent.VK_S:
                    cheatCode.addChar("s");
                case KeyEvent.VK_DOWN:
                    playerTank.setKeyDOWN(false);
                    keyDOWN = false;
                    break;
                case KeyEvent.VK_A:
                    cheatCode.addChar("a");
                case KeyEvent.VK_LEFT:
                    playerTank.setKeyLEFT(false);
                    keyLEFT = false;
                    break;
                case KeyEvent.VK_D:
                    cheatCode.addChar("d");
                case KeyEvent.VK_RIGHT:
                    playerTank.setKeyRIGHT(false);
                    keyRIGHT = false;
                    break;


                case KeyEvent.VK_B:
                    cheatCode.addChar("b");
                    break;
                case KeyEvent.VK_C:
                    cheatCode.addChar("c");
                    break;
                case KeyEvent.VK_E:
                    cheatCode.addChar("e");
                    break;
                case KeyEvent.VK_F:
                    cheatCode.addChar("f");
                    break;
                case KeyEvent.VK_G:
                    cheatCode.addChar("g");
                    break;
                case KeyEvent.VK_H:
                    cheatCode.addChar("h");
                    break;
                case KeyEvent.VK_I:
                    cheatCode.addChar("i");
                    break;
                case KeyEvent.VK_J:
                    cheatCode.addChar("j");
                    break;
                case KeyEvent.VK_K:
                    cheatCode.addChar("k");
                    break;
                case KeyEvent.VK_L:
                    cheatCode.addChar("l");
                    break;
                case KeyEvent.VK_M:
                    cheatCode.addChar("m");
                    break;
                case KeyEvent.VK_N:
                    cheatCode.addChar("n");
                    break;
                case KeyEvent.VK_O:
                    cheatCode.addChar("o");
                    break;
                case KeyEvent.VK_P:
                    cheatCode.addChar("p");
                    break;
                case KeyEvent.VK_Q:
                    cheatCode.addChar("q");
                    break;
                case KeyEvent.VK_R:
                    cheatCode.addChar("r");
                    break;
                case KeyEvent.VK_T:
                    cheatCode.addChar("t");
                    break;
                case KeyEvent.VK_U:
                    cheatCode.addChar("u");
                    break;
                case KeyEvent.VK_V:
                    cheatCode.addChar("v");
                    break;
                case KeyEvent.VK_X:
                    cheatCode.addChar("x");
                    break;
                case KeyEvent.VK_Y:
                    cheatCode.addChar("y");
                    break;
                case KeyEvent.VK_Z:
                    cheatCode.addChar("z");
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
}

