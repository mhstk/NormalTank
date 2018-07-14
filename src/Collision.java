import java.util.ArrayList;

public class Collision {
    static GameState state = GameLoop.state;
    private static PlayerTank playerTank = state.getPlayerTank();
    private static ArrayList<EnemyTank> enemyTanks = state.map.enemyTanks;
    private static ArrayList<Turret> turrets = state.map.turrets;
    private static ArrayList<IdiotEnemy> idiotEnemies = state.map.idiotEnemies;
    private static ArrayList<Teazel> teazels = state.map.teazel;
    private static ArrayList<Mine> mines = state.map.mines;
    private static ArrayList<Shield> shields = state.map.shields;
    private static Key key = state.map.key;

    private static ArrayList<SoftWall> softWalls = state.map.softWalls;
    private static ArrayList<HardWall> hardWalls = state.map.hardWalls;
    private static ArrayList<MashinGun> mashinGuns = state.map.mashinGuns;
    private static ArrayList<TankGun> tankGuns = state.map.tankGuns;
    private static ArrayList<Repair> repaires = state.map.repairs;
    private static ArrayList<Star> stars = state.map.stars;

    public static boolean collisionBullet(Bullet bullet) {
        for (HardWall hardWall : hardWalls) {
            if (CollisionDetection.intersect(bullet.getBounds(), hardWall.getBounds(), bullet.getAngelBody(), 0)) {
                Sound sound = new Sound("softwall.wav", 0);
                sound.execute();
                return true;
            }
        }

        for (SoftWall softWall : softWalls) {
            if (CollisionDetection.intersect(bullet.getBounds(), softWall.getBounds(), bullet.getAngelBody(), 0)) {
                {
                    Sound sound = new Sound("softwall.wav", 0);
                    sound.execute();
                    if (softWall.destroy(bullet.getDamage()) == 4) {
                        softWalls.remove(softWall);
                    }
                    return true;
                }
            }
        }
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

        for (EnemyTank enemyTank : enemyTanks) {
            if (enemyTank.isAlive()) {
                if (CollisionDetection.intersect(playerTank.getBounds(), enemyTank.getBounds(), playerTank.getAngelBody(), enemyTank.getAngelBody())) {
                    return true;
                }
            }
        }

        for (MashinGun mashinGun : mashinGuns) {
            if (CollisionDetection.intersect(playerTank.getBounds(), mashinGun.getBounds(), playerTank.angelBody, 0)) {
                if (!mashinGun.used()) {
                    mashinGun.changeImage();
                    playerTank.changeNumberOfMashinGun(25);
                }
                return false;
            }
        }

        for (TankGun tankGun : tankGuns) {
            if (CollisionDetection.intersect(playerTank.getBounds(), tankGun.getBounds(), playerTank.angelBody, 0)) {
                if (!tankGun.used()) {
                    tankGun.changeImage();
                    playerTank.changeNumberOfTankGun(10);
                }
                return false;
            }
        }

        for (Repair repair : repaires) {
            if (CollisionDetection.intersect(playerTank.getBounds(), repair.getBounds(), playerTank.angelBody, 0)) {
                if (!repair.used()) {
                    if (playerTank.getHealth() != playerTank.getFirstHealth()) {
                        playerTank.refactor();
                        repair.changeImage();
                    }
                    return false;
                }
            }
        }

        for (Star star : stars) {
            if (CollisionDetection.intersect(playerTank.getBounds(), star.getBounds(), playerTank.angelBody, 0)) {
                if (!star.used()) {
                        playerTank.power();
                        star.changeImage();
                    return false;
                }
            }
        }

        for (Shield shield : shields) {
            if (CollisionDetection.intersect(playerTank.getBounds(), shield.getBounds(), playerTank.angelBody, 0)) {
                if (!shield.used()) {
                    playerTank.shield();
                    shield.changeImage();
                    return false;
                }
            }
        }


            if (CollisionDetection.intersect(playerTank.getBounds(), key.getBounds(), playerTank.angelBody, 0)) {
                if (!key.used()) {
                    key.changeImage();

                    return false;
                }
            }


        for (Mine mine : mines) {
            if (mine.isAlive()) {
                if (CollisionDetection.intersect(playerTank.getBounds(), mine.getBounds(), playerTank.getAngelBody(), 0)) {
                    mine.destroy();
                    if (playerTank.getShield())playerTank.setShield();
                    else {
                        playerTank.toBeInjured(mine.damage);
                    }
                }
            }
        }
        return false;
    }

    public static boolean collisionPlayerBullet(Bullet bullet) {
        if (collisionBullet(bullet)) return true;
        else {
            for (IdiotEnemy idiotEnemy : idiotEnemies)
                if (idiotEnemy.isAlive())
                    if (CollisionDetection.intersect(bullet.getBounds(), idiotEnemy.getBounds(), bullet.getAngelBody(), idiotEnemy.getAngelBody())) {
                        Sound sound = new Sound("BulletToTank.wav", 0);
                        sound.execute();
                        idiotEnemy.toBeInjured(bullet.getDamage());
                        if (idiotEnemy.getHealth() <= 0) {
                            Sound sound2 = new Sound("enemydestroyed.wav", 0);
                            sound2.execute();
                            idiotEnemies.remove(idiotEnemy);
                        }
                        return true;
                    }

            for (EnemyTank enemyTank : enemyTanks) {
                if (enemyTank.isAlive()&&CollisionDetection.intersect(bullet.getBounds(), enemyTank.getBounds(), bullet.getAngelBody(), enemyTank.angelBody)) {
                    enemyTank.toBeInjured(bullet.getDamage());
                    Sound sound = new Sound("BulletToTank.wav", 0);
                    sound.execute();
                    if (enemyTank.getHealth() <= 0) {
                        Sound sound2 = new Sound("enemydestroyed.wav", 0);
                        sound2.execute();
//                        enemyTanks.remove(enemyTank);
                        enemyTank.destroy();
                    }
                    return true;
                }
            }

            for (Turret turret : turrets) {
                if (CollisionDetection.intersect(bullet.getBounds(), turret.getBounds(), bullet.getAngelBody(), turret.getAngelBody())) {
                    turret.toBeInjured(bullet.getDamage());
                    Sound sound = new Sound("BulletToTank.wav", 0);
                    sound.execute();
                    if (turret.getHealth() <= 0) {
                        Sound sound2 = new Sound("enemydestroyed.wav", 0);
                        sound2.execute();
                        turrets.remove(turret);
                    }
                    return true;
                }
            }

            for (Mine mine : mines) {
                if (mine.isAlive() && mine.isVisible()) {
                    if (CollisionDetection.intersect(bullet.getBounds(), mine.getBounds(), bullet.getAngelBody(), 0)) {
                        Sound sound2 = new Sound("enemydestroyed.wav", 0);
                        sound2.execute();
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
            Sound sound = new Sound("BulletToTank.wav", 0);
            sound.execute();
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
            Sound sound = new Sound("BulletToTank.wav", 0);
            sound.execute();
            if (playerTank.getShield()) playerTank.setShield();
            else {
                state.getPlayerTank().toBeInjured(2);
            }
            return 2;
        }
        return 0;
    }

    public static boolean collisionEnemyBullet(Bullet bullet) {
        if (collisionBullet(bullet)) return true;
        else if (CollisionDetection.intersect(bullet.getBounds(), playerTank.getBounds(), bullet.getAngelBody(), playerTank.angelBody)) {
            Sound sound = new Sound("BulletToTank.wav", 0);
            sound.execute();
            if (playerTank.getShield()) playerTank.setShield();
            else {
                playerTank.toBeInjured(bullet.getDamage());
            }
            return true;
        }
        return false;
    }
}
