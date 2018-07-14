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
    private static ArrayList<Mine> mines ;
    private static ArrayList<Shield> shields ;
    private static Key key;

    private static int mouseX, mouseY;
    private static boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private static boolean mouseUP, mouseDOWN, mouseRIGHT, mouseLEFT;
    private static int cameraSpeed = 4 ;

    public ServerCamera(GameState state) {
        this.state = state;
        playerTank = state.getPlayerTank();
        enemyTanks = state.map.enemyTanks;
        turrets = state.map.turrets;
        idiotEnemies = state.map.idiotEnemies;
        hardWalls = state.map.hardWalls;
        softWalls = state.map.softWalls;
        teazels = state.map.teazel;
        mashinGuns = state.map.mashinGuns;
        tankGuns = state.map.tankGuns;
        rapairs = state.map.repairs;
        stars = state.map.stars;
        mines = state.map.mines;
        shields = state.map.shields;
        key = state.map.key;
        if (GameState.mode == 1){
            coPlayerTank = GameState.coPlayer ;
        }
        cameraSpeed = 4 + (playerTank.speed - 9) ;
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
            originY += (2 * cameraSpeed);
        }
        if (mouseDOWN) {
            originY -= (2 * cameraSpeed);
        }
        if (mouseLEFT) {
            originX -= (2 * cameraSpeed);
        }
        if (mouseRIGHT) {
            originX += (2 * cameraSpeed);
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
            for (Bullet bullet : playerTank.bullets){
                bullet.setPositionY(bullet.getPositionY()+cameraSpeed);
            }
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.y += cameraSpeed;
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionY += cameraSpeed;
                for (Bullet bullet : enemyTank.bullets ){
                    bullet.setPositionY(bullet.getPositionY()+cameraSpeed);
                }
            }
            for (Turret turret : turrets) {
                turret.positionY += cameraSpeed;
                for (Bullet bullet : turret.bullets ){
                    bullet.setPositionY(bullet.getPositionY()+cameraSpeed);
                }
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
            for (Shield shield : shields )
                shield.positionY += cameraSpeed;
            key.positionY+= cameraSpeed;
        }

        if (keyDOWN && !camerafixedY) {
            playerTank.positionY -= cameraSpeed;
            for (Bullet bullet : playerTank.bullets ){
                bullet.setPositionY(bullet.getPositionY()-cameraSpeed);
            }
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.y -= cameraSpeed;
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionY -= cameraSpeed;
                for (Bullet bullet : enemyTank.bullets ){
                    bullet.setPositionY(bullet.getPositionY()-cameraSpeed);
                }
            }
            for (Turret turret : turrets) {
                turret.positionY -= cameraSpeed;
                for (Bullet bullet : turret.bullets ){
                    bullet.setPositionY(bullet.getPositionY()-cameraSpeed);
                }
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
            for (Shield shield : shields )
                shield.positionY -= cameraSpeed;
            key.positionY-= cameraSpeed;
        }

        if (keyLEFT && !camerafixedX) {
            playerTank.positionX += cameraSpeed;
            for (Bullet bullet : playerTank.bullets ){
                bullet.setPositionX(bullet.getPositionX()+cameraSpeed);
            }
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.x += cameraSpeed;
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionX += cameraSpeed;
                for (Bullet bullet : enemyTank.bullets ){
                    bullet.setPositionX(bullet.getPositionX()+cameraSpeed);
                }
            }
            for (Turret turret : turrets) {
                turret.positionX += cameraSpeed;
                for (Bullet bullet : turret.bullets ){
                    bullet.setPositionX(bullet.getPositionX()+cameraSpeed);
                }
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
            for (Shield shield : shields )
                shield.positionX += cameraSpeed;
            key.positionX+= cameraSpeed;
        }

        if (keyRIGHT && !camerafixedX) {
            playerTank.positionX -= cameraSpeed;
            for (Bullet bullet : playerTank.bullets ){
                bullet.setPositionX(bullet.getPositionX()-cameraSpeed);
            }
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.x -= cameraSpeed;
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionX -= cameraSpeed;
                for (Bullet bullet : enemyTank.bullets ){
                    bullet.setPositionX(bullet.getPositionX()-cameraSpeed);
                }
            }
            for (Turret turret : turrets) {
                turret.positionX -= cameraSpeed;
                for (Bullet bullet : turret.bullets ){
                    bullet.setPositionX(bullet.getPositionX()-cameraSpeed);
                }
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
            for (Shield shield : shields )
                shield.positionX -= cameraSpeed;
            key.positionX-= cameraSpeed;
        }

        if (mouseUP && !camerafixedY) {
            playerTank.positionY += (2 * cameraSpeed);
            for (Bullet bullet : playerTank.bullets ){
                bullet.setPositionY(bullet.getPositionY()+2*cameraSpeed);
            }
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.y += (2 * cameraSpeed);
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionY += 2 * cameraSpeed;
                for (Bullet bullet : enemyTank.bullets ){
                    bullet.setPositionY(bullet.getPositionY()+2*cameraSpeed);
                }
            }
            for (Turret turret : turrets) {
                turret.positionY += 2 * cameraSpeed;
                for (Bullet bullet : turret.bullets ){
                    bullet.setPositionY(bullet.getPositionY()+2*cameraSpeed);
                }
            }
            for (IdiotEnemy idiotEnemy : idiotEnemies) {
                idiotEnemy.positionY += 2 * cameraSpeed;
            }
            for (SoftWall softWall : softWalls) {
                softWall.positionY += (2 * cameraSpeed);
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionY += (2 * cameraSpeed);
            }
            for (Teazel teazel : teazels)
                teazel.positionY += 2 * cameraSpeed;
            for (Mine mine : mines)
                mine.positionY += 2 * cameraSpeed;
            for (MashinGun mashinGun : mashinGuns)
                mashinGun.positionY += 2 * cameraSpeed;
            for (TankGun tankGun : tankGuns)
                tankGun.positionY += 2 * cameraSpeed;
            for (Repair repair : rapairs)
                repair.positionY += 2 * cameraSpeed;
            for (Star star : stars )
                star.positionY += 2 * cameraSpeed;
            for (Shield shield : shields )
                shield.positionY += 2*cameraSpeed;
            key.positionY+= 2*cameraSpeed;
        }

        if (mouseDOWN && !camerafixedY) {
            playerTank.positionY -= (2 * cameraSpeed);
            for (Bullet bullet : playerTank.bullets ){
                bullet.setPositionY(bullet.getPositionY()-2*cameraSpeed);
            }
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.y -= (2 * cameraSpeed);
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionY -= 2 * cameraSpeed;
                for (Bullet bullet : enemyTank.bullets ){
                    bullet.setPositionY(bullet.getPositionY()-2*cameraSpeed);
                }
            }
            for (Turret turret : turrets) {
                turret.positionY -= 2 * cameraSpeed;
                for (Bullet bullet : turret.bullets ){
                    bullet.setPositionY(bullet.getPositionY()-2*cameraSpeed);
                }
            }
            for (IdiotEnemy idiotEnemy : idiotEnemies) {
                idiotEnemy.positionY -= 2 * cameraSpeed;
            }
            for (SoftWall softWall : softWalls) {
                softWall.positionY -= (2 * cameraSpeed);
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionY -= (2 * cameraSpeed);
            }
            for (Teazel teazel : teazels)
                teazel.positionY -= 2 * cameraSpeed;
            for (Mine mine : mines)
                mine.positionY -= 2 * cameraSpeed;
            for (MashinGun mashinGun : mashinGuns)
                mashinGun.positionY -= 2 * cameraSpeed;
            for (TankGun tankGun : tankGuns)
                tankGun.positionY -= 2 * cameraSpeed;
            for (Repair repair : rapairs)
                repair.positionY -= 2 * cameraSpeed;
            for (Star star : stars )
                star.positionY -= 2 * cameraSpeed;
            for (Shield shield : shields )
                shield.positionY -= 2*cameraSpeed;
            key.positionY-= 2*cameraSpeed;
        }

        if (mouseLEFT && !camerafixedX) {
            playerTank.positionX += (2 * cameraSpeed);
            for (Bullet bullet : playerTank.bullets ){
                bullet.setPositionX(bullet.getPositionX()+2*cameraSpeed);
            }
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.x += (2 * cameraSpeed);
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionX += 2 * cameraSpeed;
                for (Bullet bullet : enemyTank.bullets ){
                    bullet.setPositionX(bullet.getPositionX()+2*cameraSpeed);
                }
            }
            for (Turret turret : turrets) {
                turret.positionX += 2 * cameraSpeed;
                for (Bullet bullet : turret.bullets ){
                    bullet.setPositionX(bullet.getPositionX()+2*cameraSpeed);
                }
            }
            for (IdiotEnemy idiotEnemy : idiotEnemies) {
                idiotEnemy.positionX += 2 * cameraSpeed;
            }
            for (SoftWall softWall : softWalls) {
                softWall.positionX += (2 * cameraSpeed);
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionX += (2 * cameraSpeed);
            }
            for (Teazel teazel : teazels)
                teazel.positionX += 2 * cameraSpeed;
            for (Mine mine : mines)
                mine.positionX += 2 * cameraSpeed;
            for (MashinGun mashinGun : mashinGuns)
                mashinGun.positionX += 2 * cameraSpeed;
            for (TankGun tankGun : tankGuns)
                tankGun.positionX += 2 * cameraSpeed;
            for (Repair repair : rapairs)
                repair.positionX += 2 * cameraSpeed;
            for (Star star : stars )
                star.positionX += 2 * cameraSpeed;
            for (Shield shield : shields )
                shield.positionX += 2*cameraSpeed;
            key.positionX+= 2*cameraSpeed;
        }

        if (mouseRIGHT && !camerafixedX) {
            playerTank.positionX -= (2 * cameraSpeed);
            for (Bullet bullet : playerTank.bullets ){
                bullet.setPositionX(bullet.getPositionX()-2*cameraSpeed);
            }
            if (GameState.mode == 1) {
                coPlayerTank.clientLoc.x -= (2 * cameraSpeed);
            }
            for (EnemyTank enemyTank :
                    enemyTanks) {
                enemyTank.positionX -= 2 * cameraSpeed;
                for (Bullet bullet : enemyTank.bullets ){
                    bullet.setPositionX(bullet.getPositionX()-2*cameraSpeed);
                }
            }
            for (Turret turret : turrets) {
                turret.positionX -= 2 * cameraSpeed;
                for (Bullet bullet : turret.bullets ){
                    bullet.setPositionX(bullet.getPositionX()-2*cameraSpeed);
                }
            }
            for (IdiotEnemy idiotEnemy : idiotEnemies) {
                idiotEnemy.positionX -= 2 * cameraSpeed;
            }
            for (SoftWall softWall : softWalls) {
                softWall.positionX -= (2 * cameraSpeed);
            }
            for (HardWall hardWall : hardWalls) {
                hardWall.positionX -= (2 * cameraSpeed);
            }
            for (Teazel teazel : teazels)
                teazel.positionX -= 2 * cameraSpeed;
            for (Mine mine : mines)
                mine.positionX -= 2 * cameraSpeed;
            for (MashinGun mashinGun : mashinGuns)
                mashinGun.positionX -= 2 * cameraSpeed;
            for (TankGun tankGun : tankGuns)
                tankGun.positionX -= 2 * cameraSpeed;
            for (Repair repair : rapairs)
                repair.positionX -= 2 * cameraSpeed;
            for (Star star : stars )
                star.positionX -= 2 * cameraSpeed;
            for (Shield shield : shields )
                shield.positionX -= 2*cameraSpeed;
            key.positionX-= 2*cameraSpeed;
        }
    }
}
