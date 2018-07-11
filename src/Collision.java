import java.awt.*;
import java.util.ArrayList;

public class Collision {
    static GameState  state = GameLoop.state ;
    static PlayerTank playerTank = state.getPlayerTank();
    static EnemyTank enemyTank = state.getEnemyTank();
    static Turret turret = state.getTurret();
    static IdiotEnemy idiotEnemy = state.getIdiotEnemy();
    static ArrayList<SoftWall> softWalls = state.softWalls;
    static ArrayList<HardWall> hardWalls = state.hardWalls ;
    static GameFrame gameFrame = Main.frame ;

    public static boolean CollisionPlayerTank(){


        for (SoftWall softWall : softWalls){
            if (CollisionDetection.intersect(playerTank.getBounds(),softWall.getBounds(),playerTank.getAngelBody(),0)){
                return true;
            }
        }
        for (HardWall hardWall : hardWalls){
            if (CollisionDetection.intersect(playerTank.getBounds(),hardWall.getBounds(),playerTank.getAngelBody(),0)){
                return true;
            }
        }
        if (CollisionDetection.intersect(playerTank.getBounds(),enemyTank.getBounds(),playerTank.getAngelBody(),enemyTank.getAngelBody())){

            return true ;
        }
        return false ;
    }
}
