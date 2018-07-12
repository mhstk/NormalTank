import java.awt.*;
import java.util.ArrayList;

public class Collision {
    static GameState state = GameLoop.state;
    static PlayerTank playerTank = state.getPlayerTank();
    static ArrayList<EnemyTank> enemyTanks = state.map.enemyTanks;
    static ArrayList<Turret> turrets = state.map.turrets;
    static ArrayList<IdiotEnemy> idiotEnemies= state.map.idiotEnemies;
    static ArrayList<Teazel> teazels = state.map.teazel;
    static ArrayList<SoftWall> softWalls = state.map.softWalls;
    static ArrayList<HardWall> hardWalls = state.map.hardWalls;
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

        for (EnemyTank enemyTank : enemyTanks){
            if (CollisionDetection.intersect(bullet.getBounds() , enemyTank.getBounds(),bullet.getAngelBody(),enemyTank.angelBody)) {
                enemyTank.toBeInjured();
                return true;
            }
        }

//        for (IdiotEnemy idiotEnemy : idiotEnemies) {
//            if (idiotEnemy.isAlive()) {
//                if (CollisionDetection.intersect(bullet.getBounds(), idiotEnemy.getBounds(), bullet.getAngelBody(), idiotEnemy.getAngelBody())) {
//                    idiotEnemy.toBeInjured();
//                }
//                return true;
//            }
//        }

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
}
