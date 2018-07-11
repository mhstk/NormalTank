import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Map {
    private int[][] map;
    public ArrayList<HardWall> hardWalls;
    public ArrayList<SoftWall> softWalls;
    public ArrayList<Plant> plants;
    public ArrayList<Teazel> teazels;

    public Map(){
        map = new int[35][35];
        hardWalls = new ArrayList<>();

    }

    public void createMAp(String fileAddress){
        try (BufferedReader f = new BufferedReader((new FileReader(fileAddress)))){
            int i = 0;
            while (f.ready()){
                String line = f.readLine();
                String[] lines = line.split(" ");
                for (int j = 0; j < 25; j++) {
                    map[i][j]=Integer.parseInt(lines[j]);
                    if (map[i][j] == 1){
                        hardWalls.add(new HardWall(i*150,j*150));
                    }
                    else if (map[i][j] == 2){
                        plants.add(new Plant(i*150,j*150));
                    }
                    else if (map[i][j] == 3){
                        softWalls.add(new SoftWall(i*150,j*150));
                    } else if (map[i][j] == 4){
                        teazels.add(new Teazel(i*150,j*150));
                    }
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void drawWalls(){

    }

    public void drawPlants(Graphics2D g2d,GameState state,AffineTransform oldTrans){
        for (Plant plant:
            plants ) {
//            plant.draw(g2d, state, oldTrans);
        }
    }
}
