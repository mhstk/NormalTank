import java.awt.*;
import java.util.ArrayList;

public class Collision {
    static GameState state = GameLoop.state;
    private static PlayerTank playerTank = state.getPlayerTank();
    private static ArrayList<EnemyTank> enemyTanks = state.map.enemyTanks;
    private static ArrayList<Turret> turrets = state.map.turrets;
    private static ArrayList<IdiotEnemy> idiotEnemies= state.map.idiotEnemies;
    private static ArrayList<Teazel> teazels = state.map.teazel;
    private static ArrayList<Mine> mines = state.map.mines;

    private static ArrayList<SoftWall> softWalls = state.map.softWalls;
    private static ArrayList<HardWall> hardWalls = state.map.hardWalls;
    private static ArrayList<MashinGun> mashinGuns = state.map.mashinGuns;
    private static ArrayList<TankGun> tankGuns = state.map.tankGuns;
    private static ArrayList<Repair> repaires = state.map.repaires;
    static GameFrame gameFrame = Main.frame;

    public static boolean collisionBullet(Bullet bullet) {
        for (HardWall hardWall : hardWalls) {
            if (CollisionDetection.intersect(bullet.getBounds(), hardWall.getBounds(), bullet.getAngelBody(), 0)) {
                return true;
            }
        }

        for (SoftWall softWall : softWalls) {
            if (CollisionDetection.intersect(bullet.getBounds(), softWall.getBounds(), bullet.getAngelBody(), 0)) {
                {
                    Sound sound = new Sound("softWall.wav", 0);
                    sound.execute();
                    if (softWall.destroy() == 4) {
                        softWalls.remove(softWall);
                    }
                    return true;
                }
            }
        }



//        if (CollisionDetection.intersect(bullet.getBounds() , playerTank.getBounds(),bullet.getAngelBody(),playerTank.angelBody)) {
//            playerTank.toBeInjured();
//            System.out.println("klek");
//            return true;
//        }
            return false;
    }


    public static boolean collisionPlayerTank() {

        for (HardWall hardWall : hardWalls) {
            if (CollisionDetection.intersect(playerTank.getBounds(), hardWall.getBounds(), playerTank.getAngelBody(), 0)) {
                return true;
            }
        }

        for (SoftWall softWall : softWalls) {
            if (CollisionDetection.intersect(playerTank.getBounds(), softWall.getBounds(), playerTank.getAngelBody(), 0)) {
                return true;
            }
        }

        for (Teazel teazel : teazels) {
            if (CollisionDetection.intersect(playerTank.getBounds(), teazel.getBounds(), playerTank.getAngelBody(), 0))
                return true;
        }

        for (EnemyTank enemyTank : enemyTanks){
            if (CollisionDetection.intersect(playerTank.getBounds(), enemyTank.getBounds(), playerTank.getAngelBody(), enemyTank.getAngelBody())) {
                return true;
            }
        }

        for (MashinGun mashinGun : mashinGuns){
            if (CollisionDetection.intersect(playerTank.getBounds(),mashinGun.getBounds(),playerTank.angelBody,0)){
               if (!mashinGun.used())
                mashinGun.changeImage();
                return false;
            }
        }

        for (TankGun tankGun : tankGuns){
            if (CollisionDetection.intersect(playerTank.getBounds(),tankGun.getBounds(),playerTank.angelBody,0)){
                if (!tankGun.used())
                    tankGun.changeImage();
                return false;
            }
        }

        for (Repair repair : repaires){
            if (CollisionDetection.intersect(playerTank.getBounds(),repair.getBounds(),playerTank.angelBody,0)){
                if (!repair.used())
                    repair.changeImage();
                return false;
            }
        }

        for (Mine mine : mines){
            if (mine.isAlive()) {
                if (CollisionDetection.intersect(playerTank.getBounds(), mine.getBounds(), playerTank.getAngelBody(), 0)) {
                    mine.destroy();
                    playerTank.toBeInjured();
                }
            }
        }
        return false;
    }

    public static boolean collisionPlayerBullet(Bullet bullet){
        if (collisionBullet(bullet)) return true;
        else {
            for (IdiotEnemy idiotEnemy : idiotEnemies)
                if (idiotEnemy.isAlive())
                    if (CollisionDetection.intersect(bullet.getBounds(), idiotEnemy.getBounds(), bullet.getAngelBody(), idiotEnemy.getAngelBody())) {

                    idiotEnemy.toBeInjured();
                    idiotEnemies.remove(idiotEnemy);
                        return true;
                    }

            for (EnemyTank enemyTank : enemyTanks){
                if (CollisionDetection.intersect(bullet.getBounds() , enemyTank.getBounds(),bullet.getAngelBody(),enemyTank.angelBody)) {
                    enemyTank.toBeInjured();
                    enemyTanks.remove(enemyTank);
                    return true;
                }
            }

            for (Turret turret : turrets)
            {
                if (CollisionDetection.intersect(bullet.getBounds(), turret.getBounds(),bullet.getAngelBody(),turret.getAngelBody())) {
                    turrets.remove(turret);
                    return true;
                }
            }

            for (Mine mine : mines){
                if (mine.isAlive()&&mine.isVisible()) {
                    if (CollisionDetection.intersect(bullet.getBounds(), mine.getBounds(), bullet.getAngelBody(), 0)) {
                        mine.destroy();
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static boolean CollisionEnemyTank(EnemyTank enemyTank) {

        for (SoftWall softWall : softWalls) {
            if (CollisionDetection.intersect(enemyTank.getBounds(), softWall.getBounds(), enemyTank.getAngelBody(), 0)) {
                return true;
            }
        }
        for (HardWall hardWall : hardWalls) {
            if (CollisionDetection.intersect(enemyTank.getBounds(), hardWall.getBounds(), enemyTank.getAngelBody(), 0)) {
                return true;
            }
        }

        for (Teazel teazel : teazels) {
            if (CollisionDetection.intersect(enemyTank.getBounds(), teazel.getBounds(), enemyTank.getAngelBody(), 0))
                return true;
        }

        if (CollisionDetection.intersect(playerTank.getBounds(), enemyTank.getBounds(), playerTank.getAngelBody(), enemyTank.getAngelBody())) {
            return true;
        }
        return false;
    }


    public static int CollisionIdiotEnemyTank(IdiotEnemy idiotEnemy) {
        for (SoftWall softWall : softWalls) {
            if (CollisionDetection.intersect(idiotEnemy.getBounds(), softWall.getBounds(), idiotEnemy.getAngelBody(), 0)) {
                return 1;
            }
        }
        for (HardWall hardWall : hardWalls) {
            if (CollisionDetection.intersect(idiotEnemy.getBounds(), hardWall.getBounds(), idiotEnemy.getAngelBody(), 0)) {
                return 1;
            }
        }
        for (Teazel teazel : teazels) {
            if (CollisionDetection.intersect(idiotEnemy.getBounds(), teazel.getBounds(), idiotEnemy.getAngelBody(), 0))
                return 1;
        }

        if (CollisionDetection.intersect(playerTank.getBounds(), idiotEnemy.getBounds(), playerTank.getAngelBody(), idiotEnemy.getAngelBody())) {
            return 2;
        }
        return 0;
    }

    public static boolean collisionEnemyBullet(Bullet bullet) {
        return collisionBullet(bullet) || CollisionDetection.intersect(bullet.getBounds(), playerTank.getBounds(), bullet.getAngelBody(), playerTank.angelBody);
    }
}
