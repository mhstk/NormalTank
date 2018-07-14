import javafx.print.PageLayout;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {

//    public ArrayList<HardWall> hardWalls;
//    public ArrayList<SoftWall> softWalls;
//    public ArrayList<Plant> plants;
//    public ArrayList<Teazel> teazel;
    PlayerTank playerTank ;
      CoPlayerTank coPlayer ;
//    public ArrayList<IdiotEnemy> idiotEnemies;
//    public ArrayList<EnemyTank> enemyTanks;
////    //    public ArrayList<Mine> mines;
//    public ArrayList<Turret> turrets;
     Map map ;

    public Data(PlayerTank playerTank, CoPlayerTank coPlayer, Map map) {
        this.playerTank = playerTank;
        this.coPlayer = coPlayer;
        this.map = map;
    }

    public Data() {
    }

    public void updateInfo(){

         map = GameLoop.state.map ;
//        hardWalls = GameLoop.state.map.hardWalls ;
//        System.out.println(hardWalls);
//        softWalls = GameLoop.state.map.softWalls ;
//        plants = GameLoop.state.map.plants ;
//        teazel = GameLoop.state.map.teazel ;
//        enemyTanks = GameLoop.state.map.enemyTanks ;
//        idiotEnemies = GameLoop.state.map.idiotEnemies ;
//        turrets = GameLoop.state.map.turrets ;
          playerTank = GameLoop.state.getPlayerTank();
          coPlayer = GameState.coPlayer;
    }
}
