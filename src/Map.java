import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Map {
    private int[][] map;
    private BufferedImage area;
    private BufferedImage plant;
    private BufferedImage softWall;
    private BufferedImage hardWall;
    private BufferedImage sim;
    public ArrayList<HardWall> hardWalls;
    public ArrayList<SoftWall> softWalls;
    public ArrayList<Plant> plants;
    public ArrayList<Teazel> teazel;

    public Map(){
        map = new int[35][35];
        hardWalls = new ArrayList<>();

        try {
            area = ImageIO.read(new File("Area.jpg"));
            plant = ImageIO.read(new File("plant.png"));
            softWall = ImageIO.read(new File("softWall.png"));
            hardWall = ImageIO.read(new File("hardWall.png"));
            sim = ImageIO.read(new File("sim.png"));

        } catch (IOException e) {
            System.out.println(e);
        }

        plants = new ArrayList<>();
        hardWalls = new ArrayList<>();
        softWalls = new ArrayList<>();
        teazel = new ArrayList<>();

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
                        teazel.add(new Teazel(i*150,j*150));
                    }
                }
                i++;
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
                    g2d.drawImage(plant, 0, -150, null);
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
                if (map[j+(int)(Camera.originX/150)][i+(int)(Camera.originY/150)] == 3){
                    g2d.drawImage(softWall, 0, -150, null);
                } else if (map[j+(int)(Camera.originX/150)][i+(int)(Camera.originY/150)] == 1) {
                    g2d.drawImage(hardWall, 0, -150, null);
                } else if (map[j+(int)(Camera.originX/150)][i+(int)(Camera.originY/150)] ==4){
                    g2d.drawImage(sim, 0, -150, null);
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
        System.out.println("HELLLO");
        System.out.println(map[0][0]);
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
