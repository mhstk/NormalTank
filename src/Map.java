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
    public ArrayList<HardWall> hardWalls;
    public ArrayList<SoftWall> softWalls;
    public ArrayList<Plant> plants;
    public ArrayList<MashinGun> mashinGuns;
    public ArrayList<TankGun> tankGuns;

    public ArrayList<Teazel> teazel;
    public ArrayList<IdiotEnemy> idiotEnemies;
    public ArrayList<EnemyTank> enemyTanks;
    public ArrayList<Turret> turrets;
    public ArrayList<Mine> mines;
    public ArrayList<Repair> repaires;

    private int[][] map;
    private BufferedImage area;

    public Map() {
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
        mashinGuns = new ArrayList<>();
        tankGuns = new ArrayList<>();
        repaires  = new ArrayList<>();

        turrets = new ArrayList<>();
        enemyTanks = new ArrayList<>();
        idiotEnemies = new ArrayList<>();
        mines = new ArrayList<>();
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
                    } else if (map[i][j] == 8) {
                        mines.add(new Mine(i * 150, 1080 - (3750 - (150 * (24 - j)))));
                    } else if (map[i][j] == 9) {
                        mashinGuns.add(new MashinGun(i * 150, 1080 - (3750 - (150 * (24 - j))), i, j));
                    } else if (map[i][j] == 10) {
                        turrets.add(new Turret(i * 150, 1080 - (3750 - (150 * (24 - j))), "DOWN"));
                    } else if (map[i][j] == 11) {
                        turrets.add(new Turret(i * 150, 1080 - (3750 - (150 * (24 - j))), "LEFT"));
                    } else if (map[i][j] == 12) {
                        turrets.add(new Turret(i * 150, 1080 - (3750 - (150 * (24 - j))), "RIGHT"));
                    } else if (map[i][j] == 13) {
                        tankGuns.add(new TankGun(i * 150, 1080 - (3750 - (150 * (24 - j))),i,j));
                    }  else if (map[i][j] == 14) {
                        repaires.add(new Repair(i * 150, 1080 - (3750 - (150 * (24 - j))),i,j));
                    }
                }
                j--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void drawPlants(Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        AffineTransform atMap = g2d.getTransform();
        atMap.translate(0, 1080);
        atMap.translate(-(Camera.originX % 150), (Camera.originY % 150));
        g2d.setTransform(atMap);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                g2d.setTransform(atMap);
                if (map[j + (int) (Camera.originX / 150)][i + (int) (Camera.originY / 150)] == 2) {
                    g2d.drawImage(Plant.getImage(), 0, -150, null);
                }
                atMap.translate(150, 0);
            }
            atMap.translate(-15 * 150, -150);
        }
    }

    public void drawWalls(Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        g2d.setTransform(oldTrans);
        AffineTransform atMap = g2d.getTransform();
        atMap.translate(0, 1080);
        atMap.translate(-(Camera.originX % 150), (Camera.originY % 150));
        g2d.setTransform(atMap);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                g2d.setTransform(atMap);
                if (map[j + (int) (Camera.originX / 150)][i + (int) (Camera.originY / 150)] == 1) {
                    g2d.drawImage(HardWall.getImage(), 0, -150, null);
                } else if (map[j + (int) (Camera.originX / 150)][i + (int) (Camera.originY / 150)] == 4) {
                    g2d.drawImage(Teazel.getImage(), 0, -150, null);
                } else if (map[j + (int) (Camera.originX / 150)][i + (int) (Camera.originY / 150)] == 3) {
                    for (SoftWall s : softWalls) {
                        if (s.i == i + (int) (Camera.originY / 150) && s.j == j + (int) (Camera.originX / 150)) {
                            g2d.drawImage(s.getImage(), 0, -150, null);
                        }
                    }
                } else if (map[j + (int) (Camera.originX / 150)][i + (int) (Camera.originY / 150)] == 9) {
                    for (MashinGun mashinGun : mashinGuns) {
                        if (mashinGun.i == i + (int) (Camera.originY / 150) && mashinGun.j == j + (int) (Camera.originX / 150))
                            g2d.drawImage(mashinGun.getImage(), 0, -150, null);
                    }
                } else if (map[j + (int) (Camera.originX / 150)][i + (int) (Camera.originY / 150)] == 13) {
                    for (TankGun tankGun : tankGuns) {
                        if (tankGun.getI() == i + (int) (Camera.originY / 150) && tankGun.getJ() == j + (int) (Camera.originX / 150))
                            g2d.drawImage(tankGun.getImage(), 0, -150, null);
                    }
                }else if (map[j + (int) (Camera.originX / 150)][i + (int) (Camera.originY / 150)] == 14) {
                    for (Repair repair : repaires) {
                        if (repair.getI() == i + (int) (Camera.originY / 150) && repair.getJ() == j + (int) (Camera.originX / 150))
                            g2d.drawImage(repair.getImage(), 0, -150, null);
                    }
                }
                atMap.translate(150, 0);
            }
            atMap.translate(-15 * 150, -150);
        }
    }

    public void drawArea(Graphics2D g2d, GameState state, AffineTransform oldTrans) {
        AffineTransform atMap = g2d.getTransform();
        atMap.translate(-150, 1080 + 150);
        atMap.translate(-((Camera.originX % 150) - 1), ((Camera.originY % 150) - 1));
        g2d.setTransform(atMap);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                g2d.setTransform(atMap);
                g2d.drawImage(area, 0, -150, null);
                atMap.translate(150, 0);
            }
            atMap.translate(-15 * 150, -150);
        }
        g2d.setTransform(oldTrans);
    }
}
