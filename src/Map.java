import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
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
    public ArrayList<Teazel> teazel;
    public ArrayList<IdiotEnemy> idiotEnemies ;
    public ArrayList<EnemyTank> enemyTanks;
//    public ArrayList<Mine> mines;
    public ArrayList<Turret> turrets;
    private BufferedImage area;
    private int wall = 0;

    public Map(){
        map = new int[30][30];

        try {
            area = ImageIO.read(new File("area.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        plants = new ArrayList<>();
        hardWalls = new ArrayList<>();
        softWalls = new ArrayList<>();
        teazel = new ArrayList<>();

        turrets = new ArrayList<>();
        enemyTanks = new ArrayList<>();
        idiotEnemies = new ArrayList<>();
    }

    public void createMap(String fileAddress){
        try (BufferedReader f = new BufferedReader((new FileReader(fileAddress)))){
            int i = 24;
            while (f.ready()){
                String line = f.readLine();
                String[] lines = line.split(" ");
                for (int j = 0; j < 25; j++) {
                    map[24-i][j]=Integer.parseInt(lines[j]);
                    if (map[i][j] == 1){
                        hardWalls.add(new HardWall(i*150,1080 - (3750-(150*(24-j)))));
                    }
                    else if (map[i][j] == 2){
                        plants.add(new Plant(i*150,1080 - (3750-(150*(24-j)))));
                    }
                    else if (map[i][j] == 3){
                        softWalls.add(new SoftWall(i*150,1080 - (3750-(150*(24-j))),i,j));
                    } else if (map[i][j] == 4){
                        teazel.add(new Teazel(i*150,1080 - (3750-(150*(24-j)))));
                    } else if (map[i][j] == 5){
                        idiotEnemies.add(new IdiotEnemy(i*150,1080 - (3750-(150*(24-j)))));
                    } else if (map[i][j] == 6){
                        turrets.add(new Turret(i*150,1080 - (3750-(150*(24-j))),"UP"));
                    } else if (map[i][j] == 7){
                        enemyTanks.add(new EnemyTank(i*150,1080 - (3750-(150*(24-j)))));
                    }
                }
                i--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void drawPlants(Graphics2D g2d,GameState state,AffineTransform oldTrans){
        g2d.setTransform(oldTrans);
        AffineTransform atMap = g2d.getTransform();
        atMap.translate(0,1080);
        atMap.translate(-(Camera.originX%150),(Camera.originY%150));
        g2d.setTransform(atMap);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                g2d.setTransform(atMap);
                if (map[j+(int)(Camera.originX/150)][i+(int)(Camera.originY/150)] == 2){
                    g2d.drawImage(Plant.getImage(), 0, -150, null);
                }
                atMap.translate(150,0);
            }
            atMap.translate(-15*150,-150);
        }
    }

    public void drawWalls(Graphics2D g2d,GameState state,AffineTransform oldTrans){
        g2d.setTransform(oldTrans);
        AffineTransform atMap = g2d.getTransform();
        atMap.translate(0,1080);
        atMap.translate(-(Camera.originX%150),(Camera.originY%150));
        g2d.setTransform(atMap);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                g2d.setTransform(atMap);
                if (map[j+(int)(Camera.originX/150)][i+(int)(Camera.originY/150)] == 1) {
                    g2d.drawImage(HardWall.getImage(), 0, -150, null);
                } else if (map[j+(int)(Camera.originX/150)][i+(int)(Camera.originY/150)] ==4){
                    g2d.drawImage(Teazel.getImage(), 0, -150, null);
                } else if (map[j+(int)(Camera.originX/150)][i+(int)(Camera.originY/150)] == 3){
                    for (SoftWall s : softWalls) {
                        if (s.i == i+(int)(Camera.originY/150)&& s.j == j+(int)(Camera.originX/150)){
                            g2d.drawImage(s.getImage(), 0, -150, null);
                        }
                    }
                }
                atMap.translate(150,0);
            }
            atMap.translate(-15*150,-150);
        }
    }

    public void drawArea(Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        AffineTransform atMap = g2d.getTransform();
        atMap.translate(-150,1080+150);
        atMap.translate(-((Camera.originX%150) - 1),((Camera.originY%150)-1));
        g2d.setTransform(atMap);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                g2d.setTransform(atMap);
                g2d.drawImage(area, 0 ,  -150, null);
                atMap.translate(150,0);
            }
            atMap.translate(-15*150,-150);
        }
        g2d.setTransform(oldTrans);
    }
}
