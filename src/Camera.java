import java.util.ArrayList;

public class Camera {
    public static boolean camerafixedX = false, camerafixedY = false;
    private static boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private static boolean mouseUP, mouseDOWN, mouseRIGHT, mouseLEFT;
    static GameState state = GameLoop.state;
    static PlayerTank playerTank = state.getPlayerTank();
    static EnemyTank enemyTank = state.getEnemyTank();
    static Turret turret = state.getTurret();
    static IdiotEnemy idiotEnemy = state.getIdiotEnemy();
    static ArrayList<HardWall> hardWalls = state.hardWalls ;
    static ArrayList<SoftWall> softWalls = state.softWalls;
    public static int originX = 0;
    public static int originY = 0;
    private static int cameraSpeed = 4;
    static int mouseX, mouseY;

    public static void updateInfo() {
        keyUP = state.keyUP;
        keyDOWN = state.keyDOWN;
        keyLEFT = state.keyLEFT;
        keyRIGHT = state.keyRIGHT;

        mouseUP = state.mouseUP;
        mouseDOWN = state.mouseDOWN;
        mouseLEFT = state.mouseLEFT;
        mouseRIGHT = state.mouseRIGHT;

        mouseX = state.mouseX;
        mouseY = state.mouseY;

    }

    public static void cameraMove() {


        if (keyUP) {
            originY += cameraSpeed;
        }

        if (keyDOWN) {
            originY -= cameraSpeed;
        }

        if (keyLEFT) {
            originX -= cameraSpeed;
        }

        if (keyRIGHT) {
            originX += cameraSpeed;
        }

        if (mouseY <= 200 && playerTank.positionY < 700) {
            mouseUP = true;
            mouseDOWN = false;
        }
        if (1080 - mouseY <= 200 && playerTank.positionY > 200) {
            mouseDOWN = true;
            mouseUP = false;
        }
        if (mouseX <= 200 && playerTank.positionX < 1400) {
            mouseLEFT = true;
            mouseRIGHT = false;
        }
        if (1920 - mouseX <= 200 && playerTank.positionX > 200) {
            mouseRIGHT = true;
            mouseLEFT = false;
        }
        if (!(playerTank.positionY < 700)) {
            mouseUP = false;
        }
        if (!(playerTank.positionY > 200)) {
            mouseDOWN = false;
        }
        if (!(playerTank.positionX < 1400)) {
            mouseLEFT = false;
        }
        if (!(playerTank.positionX > 200)) {
            mouseRIGHT = false;
        }

        if (mouseUP) {
            originY += (1.25 * cameraSpeed);
        }
        if (mouseDOWN) {
            originY -= (1.25 * cameraSpeed);
        }
        if (mouseLEFT) {
            originX -= (1.25 * cameraSpeed);
        }
        if (mouseRIGHT) {
            originX += (1.25 * cameraSpeed);
        }


        if (originX < 0 || originX > 2300) {
            camerafixedX = true;
            if (originX < 0) originX = 0;
            if (originX > 2300) originX = 2300;
        } else {
            camerafixedX = false;
        }
        if (originY < 0 || originY > 3100) {
            camerafixedY = true;
            if (originY < 0) originY = 0;
            if (originY > 3100) originY = 3100;
        } else {
            camerafixedY = false;
        }


        if (keyUP && !camerafixedY) {
            playerTank.positionY += cameraSpeed;
            enemyTank.positionY += cameraSpeed;
            turret.positionY += cameraSpeed;
            idiotEnemy.positionY += cameraSpeed;
            for (SoftWall softWall : softWalls) {
                softWall.positionY += cameraSpeed;
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionY += cameraSpeed;
            }
        }
        if (keyDOWN && !camerafixedY) {
            playerTank.positionY -= cameraSpeed;
            enemyTank.positionY -= cameraSpeed;
            turret.positionY -= cameraSpeed;
            idiotEnemy.positionY -= cameraSpeed;
            for (SoftWall softWall : softWalls) {
                softWall.positionY -= cameraSpeed;
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionY -= cameraSpeed;
            }
        }
        if (keyLEFT && !camerafixedX) {
            playerTank.positionX += cameraSpeed;
            enemyTank.positionX += cameraSpeed;
            turret.positionX += cameraSpeed;
            idiotEnemy.positionX += cameraSpeed;
            for (SoftWall softWall : softWalls) {
                softWall.positionX += cameraSpeed;
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionX += cameraSpeed;
            }
        }
        if (keyRIGHT && !camerafixedX) {
            playerTank.positionX -= cameraSpeed;
            enemyTank.positionX -= cameraSpeed;
            turret.positionX -= cameraSpeed;
            idiotEnemy.positionX -= cameraSpeed;
            for (SoftWall softWall : softWalls) {
                softWall.positionX -= cameraSpeed;

            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionX -= cameraSpeed;
            }
        }
        if (mouseUP && !camerafixedY) {
            playerTank.positionY += (1.25 * cameraSpeed);
            enemyTank.positionY += (1.25 * cameraSpeed);
            turret.positionY += (1.25 * cameraSpeed);
            idiotEnemy.positionY += (1.25 * cameraSpeed);
            for (SoftWall softWall : softWalls) {
                softWall.positionY += (1.25 * cameraSpeed);
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionY += (1.25 * cameraSpeed);
            }
        }
        if (mouseDOWN && !camerafixedY) {
            playerTank.positionY -= (1.25 * cameraSpeed);
            enemyTank.positionY -= (1.25 * cameraSpeed);
            turret.positionY -= (1.25 * cameraSpeed);
            idiotEnemy.positionY -= (1.25 * cameraSpeed);
            for (SoftWall softWall : softWalls) {
                softWall.positionY -= (1.25 * cameraSpeed);
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionY -= (1.25 * cameraSpeed);
            }
        }
        if (mouseLEFT && !camerafixedX) {
            playerTank.positionX += (1.25 * cameraSpeed);
            enemyTank.positionX += (1.25 * cameraSpeed);
            turret.positionX += (1.25 * cameraSpeed);
            idiotEnemy.positionX += (1.25 * cameraSpeed);
            for (SoftWall softWall : softWalls) {
                softWall.positionX += (1.25 * cameraSpeed);
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionX += (1.25 * cameraSpeed);
            }
        }
        if (mouseRIGHT && !camerafixedX) {
            playerTank.positionX -= (1.25 * cameraSpeed);
            enemyTank.positionX -= (1.25 * cameraSpeed);
            turret.positionX -= (1.25 * cameraSpeed);
            idiotEnemy.positionX -= (1.25 * cameraSpeed);
            for (SoftWall softWall : softWalls) {
                softWall.positionX -= (1.25 * cameraSpeed);
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionX -= (1.25 * cameraSpeed);
            }
        }
//        System.out.println("originY  : " + originY);
//        System.out.println("originX  : " + originX);

    }
}
