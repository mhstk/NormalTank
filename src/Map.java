import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Map implements Serializable {
    public ArrayList<HardWall> hardWalls;
    public ArrayList<SoftWall> softWalls;
    public ArrayList<Plant> plants;
    public ArrayList<Teazel> teazel;
    public ArrayList<IdiotEnemy> idiotEnemies;
    public ArrayList<EnemyTank> enemyTanks;
    //    public ArrayList<Mine> mines;
    public ArrayList<Turret> turrets;
    private int[][] map;
    private int wall = 0;


    public Map() {
        map = new int[30][30];


        plants = new ArrayList<>();
        hardWalls = new ArrayList<>();
        softWalls = new ArrayList<>();
        teazel = new ArrayList<>();

        turrets = new ArrayList<>();
        enemyTanks = new ArrayList<>();
        idiotEnemies = new ArrayList<>();
    }

    public void createMap(String fileAddress) {
        try (BufferedReader f = new BufferedReader((new FileReader(fileAddress)))) {
            int j = 24;
            while (f.ready()) {
                String line = f.readLine();
                String[] lines = line.split(" ");
                for (int i = 0; i < 25; i++) {
                    map[i][j] = Integer.parseInt(lines[i]);
                    if (map[i][j] == 1) {
                        hardWalls.add(new HardWall((i) * 150, 1080 - (3750 - (150 * (24 - j)))));
                    } else if (map[i][j] == 2) {
                        plants.add(new Plant((i) * 150, 1080 - (3750 - (150 * (24 - j)))));
                    } else if (map[i][j] == 3) {
                        softWalls.add(new SoftWall(i * 150, 1080 - (3750 - (150 * (24 - j))), i, j));
                    } else if (map[i][j] == 4) {
                        teazel.add(new Teazel(i * 150, 1080 - (3750 - (150 * (24 - j)))));
                    } else if (map[i][j] == 5) {
                        idiotEnemies.add(new IdiotEnemy(i * 150, 1080 - (3750 - (150 * (24 - j)))));
                    } else if (map[i][j] == 6) {
                        turrets.add(new Turret(i * 150, 1080 - (3750 - (150 * (24 - j))), "UP"));
                    } else if (map[i][j] == 7) {
                        enemyTanks.add(new EnemyTank(i * 150, 1080 - (3750 - (150 * (24 - j)))));
                    }
                }
                j--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void drawPlants(BufferedImage image , Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        AffineTransform atMap = g2d.getTransform();
        atMap.translate(0, 1080);
        atMap.translate(-(ServerCamera.originX % 150), (ServerCamera.originY % 150));
        g2d.setTransform(atMap);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                g2d.setTransform(atMap);
                if (map[j + (int) (ServerCamera.originX / 150)][i + (int) (ServerCamera.originY / 150)] == 2) {
                    g2d.drawImage(image, 0, -150, null);
                }
                atMap.translate(150, 0);
            }
            atMap.translate(-15 * 150, -150);
        }
    }

    public void drawPlantsClient(BufferedImage image , Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        AffineTransform atMap = g2d.getTransform();
        atMap.translate(0, 1080);
        atMap.translate(-(ClientCamera.originX % 150), (ClientCamera.originY % 150));
        g2d.setTransform(atMap);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                g2d.setTransform(atMap);
                if (map[j + (int) (ClientCamera.originX / 150)][i + (int) (ClientCamera.originY / 150)] == 2) {
                    g2d.drawImage(image, 0, -150, null);
                }
                atMap.translate(150, 0);
            }
            atMap.translate(-15 * 150, -150);
        }
    }

    public void drawWalls(BufferedImage hardwall ,BufferedImage softWall0,BufferedImage softWall1,BufferedImage softWall2,BufferedImage softWall3,BufferedImage teazel, Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        AffineTransform atMap = g2d.getTransform();
        atMap.translate(0, 1080);
        atMap.translate(-(ServerCamera.originX % 150), (ServerCamera.originY % 150));
        g2d.setTransform(atMap);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                g2d.setTransform(atMap);
                if (map[j + (int) (ServerCamera.originX / 150)][i + (int) (ServerCamera.originY / 150)] == 1) {
                    g2d.drawImage(hardwall, 0, -150, null);
                } else if (map[j + (int) (ServerCamera.originX / 150)][i + (int) (ServerCamera.originY / 150)] == 4) {
                    g2d.drawImage(teazel, 0, -150, null);
                } else if (map[j + (int) (ServerCamera.originX / 150)][i + (int) (ServerCamera.originY / 150)] == 3) {
                    for (SoftWall s : softWalls) {
                        if (s.i == i + (int) (ServerCamera.originY / 150) && s.j == j + (int) (ServerCamera.originX / 150)) {
                            switch (s.mood){
                                case 0 :
                                    g2d.drawImage(softWall0, 0, -150, null);
                                    break;
                                case 1:
                                    g2d.drawImage(softWall1, 0, -150, null);
                                    break;
                                case 2 :
                                    g2d.drawImage(softWall2, 0, -150, null);
                                    break;
                                case 3 :
                                    g2d.drawImage(softWall3, 0, -150, null);
                                    break;
                            }

                        }
                    }
                }
                atMap.translate(150, 0);
            }
            atMap.translate(-15 * 150, -150);
        }
    }

    public void drawWallsClient(BufferedImage hardwall ,BufferedImage softWall0,BufferedImage softWall1,BufferedImage softWall2,BufferedImage softWall3,BufferedImage teazel, Graphics2D g2d, ClientGameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        AffineTransform atMap = g2d.getTransform();
        atMap.translate(0, 1080);
        atMap.translate(-(ClientCamera.originX % 150), (ClientCamera.originY % 150));
        g2d.setTransform(atMap);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                g2d.setTransform(atMap);
                if (map[j + (int) (ClientCamera.originX / 150)][i + (int) (ClientCamera.originY / 150)] == 1) {
                    g2d.drawImage(hardwall, 0, -150, null);
                } else if (map[j + (int) (ClientCamera.originX / 150)][i + (int) (ClientCamera.originY / 150)] == 4) {
                    g2d.drawImage(teazel, 0, -150, null);
                } else if (map[j + (int) (ClientCamera.originX / 150)][i + (int) (ClientCamera.originY / 150)] == 3) {
                    for (SoftWall s : state.data.map.softWalls) {
                        if (s.i == i + (int) (ClientCamera.originY / 150) && s.j == j + (int) (ClientCamera.originX / 150)) {
                            System.out.println(s.mood);
                            switch (s.mood){
                                case 0 :
                                    g2d.drawImage(softWall0, 0, -150, null);
                                    break;
                                case 1:
                                    g2d.drawImage(softWall1, 0, -150, null);
                                    break;
                                case 2 :
                                    g2d.drawImage(softWall2, 0, -150, null);
                                    break;
                                case 3 :
                                    g2d.drawImage(softWall3, 0, -150, null);
                                    break;
                            }

                        }
                    }
                }
                atMap.translate(150, 0);
            }
            atMap.translate(-15 * 150, -150);
        }
    }


    public void drawArea(BufferedImage image , Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        AffineTransform atMap = g2d.getTransform();
        atMap.translate(-150, 1080 + 150);
        atMap.translate(-((ServerCamera.originX % 150) - 1), ((ServerCamera.originY % 150) - 1));
        g2d.setTransform(atMap);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                g2d.setTransform(atMap);
                g2d.drawImage(image, 0, -150, null);
                atMap.translate(150, 0);
            }
            atMap.translate(-15 * 150, -150);
        }
        g2d.setTransform(oldTrans);
    }
    public void drawAreaClient(BufferedImage image , Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        AffineTransform atMap = g2d.getTransform();
        atMap.translate(-150, 1080 + 150);
        atMap.translate(-((ClientCamera.originX % 150) - 1), ((ClientCamera.originY % 150) - 1));
        g2d.setTransform(atMap);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                g2d.setTransform(atMap);
                g2d.drawImage(image, 0, -150, null);
                atMap.translate(150, 0);
            }
            atMap.translate(-15 * 150, -150);
        }
        g2d.setTransform(oldTrans);
    }
}
