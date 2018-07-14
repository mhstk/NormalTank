import java.util.ArrayList;

public class ServerCamera {
    public static boolean camerafixedX = false, camerafixedY = false;
    public static int originX = 0;
    public static int originY = 0;
    static GameState state;
    static PlayerTank playerTank ;

   private static ArrayList<EnemyTank> enemyTanks ;
   private static ArrayList<Turret> turrets ;
   private static ArrayList<IdiotEnemy> idiotEnemies ;
   private static CoPlayerTank coPlayerTank;
   private static ArrayList<HardWall> hardWalls ;
   private static ArrayList<SoftWall> softWalls ;
   private static ArrayList<Teazel> teazels;
   private static ArrayList<MashinGun> mashinGuns;
   private static ArrayList<TankGun> tankGuns ;
   private static ArrayList<Repair> rapairs ;
   private static ArrayList<Star> stars ;

    private static int mouseX, mouseY;
    private static boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private static boolean mouseUP, mouseDOWN, mouseRIGHT, mouseLEFT;
    private static int cameraSpeed = 4;

    public ServerCamera(GameState state) {
        this.state = state;
        playerTank = state.getPlayerTank();
        enemyTanks = state.map.enemyTanks;
        turrets = state.map.turrets;
        idiotEnemies = state.map.idiotEnemies;
        hardWalls = state.map.hardWalls;
        softWalls = state.map.softWalls;
        teazels = state.map.teazel;
        if (GameState.mode == 1){
            coPlayerTank = GameState.coPlayer ;
        }
    }

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
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.y += cameraSpeed;
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionY += cameraSpeed;
            }
            for (Turret turret : turrets) {
                turret.positionY += cameraSpeed;
            }
            for (IdiotEnemy idiotEnemy : idiotEnemies) {
                idiotEnemy.positionY += cameraSpeed;
            }
            for (SoftWall softWall : softWalls) {
                softWall.positionY += cameraSpeed;
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionY += cameraSpeed;
            }
            for (Teazel teazel : teazels)
                teazel.positionY += cameraSpeed;
            for (Mine mine : mines)
                mine.positionY += cameraSpeed;
            for (MashinGun mashinGun : mashinGuns)
                mashinGun.positionY += cameraSpeed;
            for (TankGun tankGun : tankGuns)
                tankGun.positionY += cameraSpeed;
            for (Repair repair : rapairs)
                repair.positionY += cameraSpeed;
            for (Star star : stars )
                star.positionY += cameraSpeed;
        }

        if (keyDOWN && !camerafixedY) {
            playerTank.positionY -= cameraSpeed;
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.y -= cameraSpeed;
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionY -= cameraSpeed;
            }
            for (Turret turret : turrets) {
                turret.positionY -= cameraSpeed;
            }
            for (IdiotEnemy idiotEnemy : idiotEnemies) {
                idiotEnemy.positionY -= cameraSpeed;
            }
            for (SoftWall softWall : softWalls) {
                softWall.positionY -= cameraSpeed;
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionY -= cameraSpeed;
            }
            for (Teazel teazel : teazels)
                teazel.positionY -= cameraSpeed;
            for (Mine mine : mines)
                mine.positionY -= cameraSpeed;
            for (MashinGun mashinGun : mashinGuns)
                mashinGun.positionY -= cameraSpeed;
            for (TankGun tankGun : tankGuns)
                tankGun.positionY -= cameraSpeed;
            for (Repair repair : rapairs)
                repair.positionY -= cameraSpeed;
            for (Star star : stars )
                star.positionY -= cameraSpeed;
        }

        if (keyLEFT && !camerafixedX) {
            playerTank.positionX += cameraSpeed;
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.x += cameraSpeed;
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionX += cameraSpeed;
            }
            for (Turret turret : turrets) {
                turret.positionX += cameraSpeed;
            }
            for (IdiotEnemy idiotEnemy : idiotEnemies) {
                idiotEnemy.positionX += cameraSpeed;
            }
            for (SoftWall softWall : softWalls) {
                softWall.positionX += cameraSpeed;
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionX += cameraSpeed;
            }
            for (Teazel teazel : teazels)
                teazel.positionX += cameraSpeed;
            for (Mine mine : mines)
                mine.positionX += cameraSpeed;
            for (MashinGun mashinGun : mashinGuns)
                mashinGun.positionX += cameraSpeed;
            for (TankGun tankGun : tankGuns)
                tankGun.positionX += cameraSpeed;
            for (Repair repair : rapairs)
                repair.positionX += cameraSpeed;
            for (Star star : stars )
                star.positionX += cameraSpeed;
        }

        if (keyRIGHT && !camerafixedX) {
            playerTank.positionX -= cameraSpeed;
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.x -= cameraSpeed;
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionX -= cameraSpeed;
            }
            for (Turret turret : turrets) {
                turret.positionX -= cameraSpeed;
            }
            for (IdiotEnemy idiotEnemy : idiotEnemies) {
                idiotEnemy.positionX -= cameraSpeed;
            }
            for (SoftWall softWall : softWalls) {
                softWall.positionX -= cameraSpeed;
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionX -= cameraSpeed;
            }
            for (Teazel teazel : teazels)
                teazel.positionX -= cameraSpeed;
            for (Mine mine : mines)
                mine.positionX -= cameraSpeed;
            for (MashinGun mashinGun : mashinGuns)
                mashinGun.positionX -= cameraSpeed;
            for (TankGun tankGun : tankGuns)
                tankGun.positionX -= cameraSpeed;
            for (Repair repair : rapairs)
                repair.positionX -= cameraSpeed;
            for (Star star : stars )
                star.positionX -= cameraSpeed;
        }

        if (mouseUP && !camerafixedY) {
            playerTank.positionY += (1.25 * cameraSpeed);
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.y += (1.25 * cameraSpeed);
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionY += 1.25 * cameraSpeed;
            }
            for (Turret turret : turrets) {
                turret.positionY += 1.25 * cameraSpeed;
            }
            for (IdiotEnemy idiotEnemy : idiotEnemies) {
                idiotEnemy.positionY += 1.25 * cameraSpeed;
            }
            for (SoftWall softWall : softWalls) {
                softWall.positionY += (1.25 * cameraSpeed);
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionY += (1.25 * cameraSpeed);
            }
            for (Teazel teazel : teazels)
                teazel.positionY += 1.25 * cameraSpeed;
            for (Mine mine : mines)
                mine.positionY += 1.25 * cameraSpeed;
            for (MashinGun mashinGun : mashinGuns)
                mashinGun.positionY += 1.25 * cameraSpeed;
            for (TankGun tankGun : tankGuns)
                tankGun.positionY += 1.25 * cameraSpeed;
            for (Repair repair : rapairs)
                repair.positionY += 1.25 * cameraSpeed;
            for (Star star : stars )
                star.positionY += 1.25 * cameraSpeed;
        }

        if (mouseDOWN && !camerafixedY) {
            playerTank.positionY -= (1.25 * cameraSpeed);
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.y -= (1.25 * cameraSpeed);
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionY -= 1.25 * cameraSpeed;
            }
            for (Turret turret : turrets) {
                turret.positionY -= 1.25 * cameraSpeed;
            }
            for (IdiotEnemy idiotEnemy : idiotEnemies) {
                idiotEnemy.positionY -= 1.25 * cameraSpeed;
            }
            for (SoftWall softWall : softWalls) {
                softWall.positionY -= (1.25 * cameraSpeed);
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionY -= (1.25 * cameraSpeed);
            }
            for (Teazel teazel : teazels)
                teazel.positionY -= 1.25 * cameraSpeed;
            for (Mine mine : mines)
                mine.positionY -= 1.25 * cameraSpeed;
            for (MashinGun mashinGun : mashinGuns)
                mashinGun.positionY -= 1.25 * cameraSpeed;
            for (TankGun tankGun : tankGuns)
                tankGun.positionY -= 1.25 * cameraSpeed;
            for (Repair repair : rapairs)
                repair.positionY -= 1.25 * cameraSpeed;
            for (Star star : stars )
                star.positionY -= 1.25 * cameraSpeed;
        }

        if (mouseLEFT && !camerafixedX) {
            playerTank.positionX += (1.25 * cameraSpeed);
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.x += (1.25 * cameraSpeed);
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionX += 1.25 * cameraSpeed;
            }
            for (Turret turret : turrets) {
                turret.positionX += 1.25 * cameraSpeed;
            }
            for (IdiotEnemy idiotEnemy : idiotEnemies) {
                idiotEnemy.positionX += 1.25 * cameraSpeed;
            }
            for (SoftWall softWall : softWalls) {
                softWall.positionX += (1.25 * cameraSpeed);
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionX += (1.25 * cameraSpeed);
            }
            for (Teazel teazel : teazels)
                teazel.positionX += 1.25 * cameraSpeed;
            for (Mine mine : mines)
                mine.positionX += 1.25 * cameraSpeed;
            for (MashinGun mashinGun : mashinGuns)
                mashinGun.positionX += 1.25 * cameraSpeed;
            for (TankGun tankGun : tankGuns)
                tankGun.positionX += 1.25 * cameraSpeed;
            for (Repair repair : rapairs)
                repair.positionX += 1.25 * cameraSpeed;
            for (Star star : stars )
                star.positionX += 1.25 * cameraSpeed;
        }

        if (mouseRIGHT && !camerafixedX) {
            playerTank.positionX -= (1.25 * cameraSpeed);
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.x -= (1.25 * cameraSpeed);
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionX -= 1.25 * cameraSpeed;
            }
            for (Turret turret : turrets) {
                turret.positionX -= 1.25 * cameraSpeed;
            }
            for (IdiotEnemy idiotEnemy : idiotEnemies) {
                idiotEnemy.positionX -= 1.25 * cameraSpeed;
            }
            for (SoftWall softWall : softWalls) {
                softWall.positionX -= (1.25 * cameraSpeed);
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionX -= (1.25 * cameraSpeed);
            }
            for (Teazel teazel : teazels)
                teazel.positionX -= 1.25 * cameraSpeed;
            for (Mine mine : mines)
                mine.positionX -= 1.25 * cameraSpeed;
            for (MashinGun mashinGun : mashinGuns)
                mashinGun.positionX -= 1.25 * cameraSpeed;
            for (TankGun tankGun : tankGuns)
                tankGun.positionX -= 1.25 * cameraSpeed;
            for (Repair repair : rapairs)
                repair.positionX -= 1.25 * cameraSpeed;
            for (Star star : stars )
                star.positionX -= 1.25 * cameraSpeed;
        }
    }
}
