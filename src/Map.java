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
    public ArrayList<MashinGun> mashinGuns;
    public ArrayList<TankGun> tankGuns;

    public ArrayList<Teazel> teazel;
    public ArrayList<IdiotEnemy> idiotEnemies;
    public ArrayList<EnemyTank> enemyTanks;
    public ArrayList<Turret> turrets;
    public ArrayList<Mine> mines;
    public ArrayList<Repair> repairs;
    public ArrayList<Star> stars;
    public ArrayList<Shield> shields;
    public Key key;

    private int level;
    private int[][] map;

    public Map(int level) {
        map = new int[30][30];
        this.level = level;


        plants = new ArrayList<>();
        hardWalls = new ArrayList<>();
        softWalls = new ArrayList<>();
        teazel = new ArrayList<>();
        mashinGuns = new ArrayList<>();
        tankGuns = new ArrayList<>();
        repairs = new ArrayList<>();
        stars = new ArrayList<>();
        shields = new ArrayList<>();

        turrets = new ArrayList<>();
        enemyTanks = new ArrayList<>();
        idiotEnemies = new ArrayList<>();
        mines = new ArrayList<>();
        key = new Key(0,0,0,0);

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
                        idiotEnemies.add(new IdiotEnemy(i * 150, 1080 - (3750 - (150 * (24 - j))), level));
                    } else if (map[i][j] == 6) {
                        turrets.add(new Turret(i * 150, 1080 - (3750 - (150 * (24 - j))), "UP", level));
                    } else if (map[i][j] == 7) {
                        enemyTanks.add(new EnemyTank(i * 150, 1080 - (3750 - (150 * (24 - j))), level));
                    } else if (map[i][j] == 8) {
                        mines.add(new Mine(i * 150, 1080 - (3750 - (150 * (24 - j))), level));
                    } else if (map[i][j] == 9) {
                        mashinGuns.add(new MashinGun(i * 150, 1080 - (3750 - (150 * (24 - j))), i, j));
                    } else if (map[i][j] == 10) {
                        turrets.add(new Turret(i * 150, 1080 - (3750 - (150 * (24 - j))), "DOWN", level));
                    } else if (map[i][j] == 11) {
                        turrets.add(new Turret(i * 150, 1080 - (3750 - (150 * (24 - j))), "LEFT", level));
                    } else if (map[i][j] == 12) {
                        turrets.add(new Turret(i * 150, 1080 - (3750 - (150 * (24 - j))), "RIGHT", level));
                    } else if (map[i][j] == 13) {
                        tankGuns.add(new TankGun(i * 150, 1080 - (3750 - (150 * (24 - j))), i, j));
                    } else if (map[i][j] == 14) {
                        repairs.add(new Repair(i * 150, 1080 - (3750 - (150 * (24 - j))), i, j));
                    } else if (map[i][j] == 15) {
                        stars.add(new Star(i * 150, 1080 - (3750 - (150 * (24 - j))), i, j));
                    } else if (map[i][j] == 16) {
                        shields.add(new Shield(i * 150, 1080 - (3750 - (150 * (24 - j))), i, j));
                    } else if (map[i][j] == 17) {
                        key = new Key(i * 150, 1080 - (3750 - (150 * (24 - j))), i, j);
                    }
                }
                j--;
            }
            System.out.println(softWalls.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void drawPlants(BufferedImage image, Graphics2D g2d, GameState state, AffineTransform oldTrans) {
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

    public void drawPlantsClient(BufferedImage image, Graphics2D g2d, GameState state, AffineTransform oldTrans) {
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

    public void drawWalls(BufferedImage hardwall, BufferedImage softWall0, BufferedImage softWall1, BufferedImage softWall2, BufferedImage softWall3, BufferedImage teazel, Graphics2D g2d, GameState state, AffineTransform oldTrans) {
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
                            switch (s.mood) {
                                case 0:
                                    g2d.drawImage(softWall0, 0, -150, null);
                                    break;
                                case 1:
                                    g2d.drawImage(softWall1, 0, -150, null);
                                    break;
                                case 2:
                                    g2d.drawImage(softWall2, 0, -150, null);
                                    break;
                                case 3:
                                    g2d.drawImage(softWall3, 0, -150, null);
                                    break;
                            }

                        }
                    }
                } else if (map[j + (int) (ServerCamera.originX / 150)][i + (int) (ServerCamera.originY / 150)] == 9) {
                    for (MashinGun mashinGun : mashinGuns) {
                        if (mashinGun.i == i + (int) (ServerCamera.originY / 150) && mashinGun.j == j + (int) (ServerCamera.originX / 150))
                            if (mashinGun.used())
                                g2d.drawImage(LoadImage.area, 0, -150, null);
                            else
                                g2d.drawImage(LoadImage.mashinGun, 0, -150, null);
                    }
                } else if (map[j + (int) (ServerCamera.originX / 150)][i + (int) (ServerCamera.originY / 150)] == 13) {
                    for (TankGun tankGun : tankGuns) {
                        if (tankGun.getI() == i + (int) (ServerCamera.originY / 150) && tankGun.getJ() == j + (int) (ServerCamera.originX / 150))
                            if (tankGun.used())
                                g2d.drawImage(LoadImage.area, 0, -150, null);
                            else
                                g2d.drawImage(LoadImage.tankGun, 0, -150, null);
                    }
                } else if (map[j + (int) (ServerCamera.originX / 150)][i + (int) (ServerCamera.originY / 150)] == 14) {
                    for (Repair repair : repairs) {
                        if (repair.getI() == i + (int) (ServerCamera.originY / 150) && repair.getJ() == j + (int) (ServerCamera.originX / 150)) {
                            if (repair.used())
                                g2d.drawImage(LoadImage.area, 0, -150, null);
                            else
                                g2d.drawImage(LoadImage.repair, 0, -150, null);
                        }
                    }
                } else if (map[j + (int) (ServerCamera.originX / 150)][i + (int) (ServerCamera.originY / 150)] == 15) {
                    for (Star star : stars) {
                        if (star.getI() == i + (int) (ServerCamera.originY / 150) && star.getJ() == j + (int) (ServerCamera.originX / 150))
                            if (star.used())
                                g2d.drawImage(LoadImage.area, 0, -150, null);
                            else
                                g2d.drawImage(LoadImage.star, 0, -150, null);
                    }
                } else if (map[j + (int) (ServerCamera.originX / 150)][i + (int) (ServerCamera.originY / 150)] == 16) {
                    for (Shield shield : shields) {
                        if (shield.getI() == i + (int) (ServerCamera.originY / 150) && shield.getJ() == j + (int) (ServerCamera.originX / 150))
                            if (shield.used())
                                g2d.drawImage(LoadImage.area, 0, -150, null);
                            else
                                g2d.drawImage(LoadImage.shield, 0, -150, null);
                    }
                }
                if (map[j + (int) (ServerCamera.originX / 150)][i + (int) (ServerCamera.originY / 150)] == 17) {
                    if (key.getI() == i + (int) (ServerCamera.originY / 150) && key.getJ() == j + (int) (ServerCamera.originX / 150))
                        if (key.used())
                            g2d.drawImage(LoadImage.area, 0, -150, null);
                        else
                            g2d.drawImage(LoadImage.key, 0, -150, null);

                }
                atMap.translate(150, 0);
            }
            atMap.translate(-15 * 150, -150);
        }

    }

    public void drawWallsClient(BufferedImage hardwall, BufferedImage softWall0, BufferedImage softWall1, BufferedImage softWall2, BufferedImage softWall3, BufferedImage teazel, Graphics2D g2d, ClientGameState state, AffineTransform oldTrans) {
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
                            switch (s.mood) {
                                case 0:
                                    g2d.drawImage(softWall0, 0, -150, null);
                                    break;
                                case 1:
                                    g2d.drawImage(softWall1, 0, -150, null);
                                    break;
                                case 2:
                                    g2d.drawImage(softWall2, 0, -150, null);
                                    break;
                                case 3:
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


    public void drawArea(BufferedImage image, Graphics2D g2d, GameState state, AffineTransform oldTrans) {
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

    public void drawAreaClient(BufferedImage image, Graphics2D g2d, GameState state, AffineTransform oldTrans) {
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
