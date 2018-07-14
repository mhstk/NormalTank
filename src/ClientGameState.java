import java.awt.event.*;
import java.io.IOException;
import java.io.Serializable;


public class ClientGameState extends GameState {
    private CoPlayerTank coPlayer;
    private static int count = 1;
    static Data data;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;


    public ClientGameState(int mode , int level) {
        super(mode , level,"Map.txt");
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();

        Client.getInstance().start();
        Client.getInstance().getData();
        coPlayer = data.coPlayer;
    }

    public void update() {

        System.out.print(coPlayer.up);
        Client.getInstance().sendData(coPlayer);
        Client.getInstance().reset();

        Client.getInstance().getData();
        coPlayer = data.coPlayer;


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

    class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                case (KeyEvent.VK_UP):
                    System.out.println("right here");
                    coPlayer.setKeyUP(true);
                    coPlayer.setKeyDOWN(false);
                    keyUP = true;
                    keyDOWN = false;


                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    coPlayer.setKeyUP(false);
                    coPlayer.setKeyDOWN(true);
                    keyDOWN = true;
                    keyUP = false;


                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    coPlayer.setKeyRIGHT(false);
                    coPlayer.setKeyLEFT(true);
                    keyLEFT = true;
                    keyRIGHT = false;


                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    coPlayer.setKeyRIGHT(true);
                    coPlayer.setKeyLEFT(false);
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
                    System.out.println("nevvvvvvvvvvvvvvvvvvvvvvveeeeeeeer");
                    coPlayer.setKeyUP(false);
                    System.out.println("co Player up " + coPlayer.up);
                    keyUP = false;
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    coPlayer.setKeyDOWN(false);
                    keyDOWN = false;
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    coPlayer.setKeyLEFT(false);
                    keyLEFT = false;
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    coPlayer.setKeyRIGHT(false);
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
                    coPlayer.changeGunTow();
                    count = 2;
                } else {
                    coPlayer.changeGunOne();
                    count = 1;
                }
            } else {
                mouseX = e.getX();
                mouseY = e.getY();
                coPlayer.setMouseX(mouseX);
                coPlayer.setMouseY(mouseY);
                coPlayer.setMousePress(true);
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
            System.out.println(coPlayer);
            coPlayer.setMouseX(mouseX);
            coPlayer.setMouseY(mouseY);
            coPlayer.setMouseMoved(true);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mousePress = false;
            coPlayer.setMousePress(false);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            coPlayer.setMouseX(mouseX);
            coPlayer.setMouseY(mouseY);
        }
    }

}
