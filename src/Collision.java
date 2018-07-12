import java.awt.*;
import java.util.ArrayList;

public class Collision {
    static GameState  state = GameLoop.state ;
    static PlayerTank playerTank = state.getPlayerTank();
    static EnemyTank enemyTank = state.getEnemyTank();
    static Turret turret = state.getTurret();
    static IdiotEnemy idiotEnemy = state.getIdiotEnemy();
    static ArrayList<SoftWall> softWalls = state.map.softWalls;
    static ArrayList<HardWall> hardWalls = state.map.hardWalls ;
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


    public static boolean CollisionEnemyTank(){


        for (SoftWall softWall : softWalls){
            if (CollisionDetection.intersect(enemyTank.getBounds(),softWall.getBounds(),enemyTank.getAngelBody(),0)){
                return true;
            }
        }
        for (HardWall hardWall : hardWalls){
//            Graphics2D g2d = (Graphics2D) gameFrame.getGraphics();
//            g2d.fill(hardWall.getBounds());
            if (CollisionDetection.intersect(enemyTank.getBounds(),hardWall.getBounds(),enemyTank.getAngelBody(),0)){
                return true;
            }
        }
        if (CollisionDetection.intersect(playerTank.getBounds(),enemyTank.getBounds(),playerTank.getAngelBody(),enemyTank.getAngelBody())){
            return true ;
        }
        return false ;
    }


    public static int CollisionIdiotEnemyTank(){


        for (SoftWall softWall : softWalls){
            if (CollisionDetection.intersect(idiotEnemy.getBounds(),softWall.getBounds(),idiotEnemy.getAngelBody(),0)){
                return 1;
            }
        }
        for (HardWall hardWall : hardWalls){
//            Graphics2D g2d = (Graphics2D) gameFrame.getGraphics();
//            g2d.fill(hardWall.getBounds());
            if (CollisionDetection.intersect(idiotEnemy.getBounds(),hardWall.getBounds(),idiotEnemy.getAngelBody(),0)){
                return 1;
            }
        }
        if (CollisionDetection.intersect(playerTank.getBounds(),idiotEnemy.getBounds(),playerTank.getAngelBody(),idiotEnemy.getAngelBody())){
            return 2 ;
        }
        return 0 ;
    }
}
