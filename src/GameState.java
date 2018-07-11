/*** In The Name of Allah ***/

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.util.ArrayList;

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
    private Turret turret = new Turret(700,100,"UP","Tank-under.png", "Tank-top.png");
    private IdiotEnemy idiotEnemy = new IdiotEnemy(700,700);
    public boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    public boolean mouseUP, mouseDOWN, mouseRIGHT, mouseLEFT;
    public ArrayList<Plant> plants = new ArrayList<>() ;
    public ArrayList<SoftWall> softWalls = new ArrayList<>() ;
    public ArrayList<HardWall> hardWalls = new ArrayList<>() ;
    public SoftWall softWall = new SoftWall(300,300);


    public double rad = 0;
    public double rad2 = 0;
    private boolean mousePress;
    private boolean mouseMoved;
    public int mouseX, mouseY;
    private static int count = 1;
    private long timeLastShotGun = 0;
    private KeyHandler keyHandler;
    public  boolean collision = false;
    private MouseHandler mouseHandler;
    int[][] maps = new int[30][30];
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

        try (BufferedReader f = new BufferedReader((new FileReader("Map.txt")))){
            int i = 0;
            while (f.ready()){
                String line = f.readLine();
                String[] lines = line.split(" ");
                for (int j = 0; j < 25; j++) {
                    maps[i][j]=Integer.parseInt(lines[j]);
                    System.out.print(maps[i][j]+" ");
                }
                maps[i][25]= 0;
                maps[i][26] = 0;
                maps[i] [27] = 0;
                maps[i][28] = 0;
                maps[i][29] = 0;
                i++;
                System.out.println(" ");
            }
            for (int j = 0; j < 30; j++) {
                maps[25][j] = 0;
                maps[26][j] = 0;
                maps[27][j] = 0;
                maps[28][j] = 0;
                maps[29][j] = 0;
                maps[29][j] = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0 ; i<30 ; i++){
            for(int j=0 ; j<30 ; j++){
                if (maps[i][j] == 3){
                    softWalls.add(new SoftWall(j*150,i*150));

                }
            }
        }
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

        int lastPosiniotTankX = playerTank.getX();
        int lastPositionTankY = playerTank.getY();


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
            if ((now - timeLastShotGun) / 1000000000.0 > playerTank.getDifTimeBullet()) {
                playerTank.shoot(playerTank.getGunX(), playerTank.getGunY(), mouseX, mouseY);
                timeLastShotGun = now;
                if (playerTank.getGunNumber() == 1){
                    mousePress = false;
                }

            }
        }
//        int difX = playerTank.getX() - lastPosiniotTankX;
//        int difY = playerTank.getY() - lastPositionTankY;


//        while (CollisionDetection.intersect(playerTank.getBounds() , enemyTank.getBounds() , playerTank.getAngelBody() , enemyTank.getAngelBody())) {
//            playerTank.positionX -= difX;
//            playerTank.positionY -= difY;
//            System.out.println("HERDBERB");
//        }
//        if (playerTank.getBounds().intersects(enemyTank.getBounds().getBounds())){
//            collision = true;
//        }else {
//            collision = false;
//        }

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

