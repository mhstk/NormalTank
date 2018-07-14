import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MapEditor extends JFrame {
    private JButton[][] map;
    private int[][] mapInt;
    private int selected = 0;

    public MapEditor() {
        JButton ok = new JButton("Ok");
        ok.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                saveMap();
            }
        });
        JPanel mapPanel = new JPanel(new GridLayout(25, 25));
        map = new JButton[25][25];
        mapInt = new int[25][25];
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                map[i][j] = new JButton(new ImageIcon("area.png"));
                mapPanel.add(map[i][j]);
                mapInt[i][j] = 0;
            }
        }

        JButton[][] mapPart = new JButton[2][8];

        mapPart[0][0] = new JButton(new ImageIcon("area.png"));
        mapPart[0][1] = new JButton(new ImageIcon("hardWall0.png"));
        mapPart[0][2] = new JButton(new ImageIcon("plant.png"));
        mapPart[0][3] = new JButton(new ImageIcon("softWall.png"));
        mapPart[0][4] = new JButton(new ImageIcon("sim.png"));
        mapPart[0][5] = new JButton(new ImageIcon("idiotEnemy1.png"));
        mapPart[0][6] = new JButton(new ImageIcon("turret3.png"));
        mapPart[0][7] = new JButton(new ImageIcon("Tank-under3.png"));
        mapPart[1][0] = new JButton(new ImageIcon("onMine.png"));
        mapPart[1][1] = new JButton(new ImageIcon("MashinGunFood.png"));
        mapPart[1][2] = new JButton(new ImageIcon("turret3.png"));
        mapPart[1][3] = new JButton(new ImageIcon("turret3.png"));
        mapPart[1][4] = new JButton(new ImageIcon("turret3.png"));
        mapPart[1][5] = new JButton(new ImageIcon("CannonFood.png"));
        mapPart[1][6] = new JButton(new ImageIcon("TankGunFood.png"));
        mapPart[1][7] = new JButton(new ImageIcon("star.png"));
        JPanel mapPartPanel = new JPanel(new GridLayout(8, 2));

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                mapPartPanel.add(mapPart[i][j]);
                addAction(i, j, mapPart[i][j]);
            }
        }
        createMap();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(ok,BorderLayout.SOUTH);
        this.add(mapPartPanel, BorderLayout.WEST);
        this.add(mapPanel, BorderLayout.CENTER);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        this.setVisible(true);
    }

    private void saveMap() {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("mapUser.txt"))){
            for (int i = 0; i < 25; i++) {
                for (int j = 0; j < 25; j++) {
                    bufferedWriter.write(mapInt[i][j]+" ");
                }
                bufferedWriter.newLine();
            }
            this.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Main.startGame(0,"mapUser.txt");
    }

    private void createMap() {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                addActionToMapPart(map[i][j], i, j);
            }
        }
    }

    private void addActionToMapPart(JButton mapPart, int i, int j) {
        mapPart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switch (selected) {
                    case 0:
                        mapPart.setIcon(new ImageIcon("area.png"));
                        break;
                    case 1:
                        mapPart.setIcon(new ImageIcon("hardWall.png"));
                        break;
                    case 2:
                        mapPart.setIcon(new ImageIcon("plant.png"));
                        break;
                    case 3:
                        mapPart.setIcon(new ImageIcon("softWall0.png"));
                        break;
                    case 4:
                        mapPart.setIcon(new ImageIcon("sim.png"));
                        break;
                    case 5:
                        mapPart.setIcon(new ImageIcon("idiotEnemy1.png"));
                        break;
                    case 6:
                        mapPart.setIcon(new ImageIcon("turret3.png"));
                        break;
                    case 7:
                        mapPart.setIcon(new ImageIcon("Tank-under3.png"));
                        break;
                    case 8:
                        mapPart.setIcon(new ImageIcon("onMine.png"));
                        break;
                    case 9:
                        mapPart.setIcon(new ImageIcon("MashinGunFood.png"));
                        break;
                    case 10:
                        mapPart.setIcon(new ImageIcon("turret3.png"));
                        break;
                    case 11:
                        mapPart.setIcon(new ImageIcon("turret3.png"));
                        break;
                    case 12:
                        mapPart.setIcon(new ImageIcon("turret3.png"));
                        break;
                    case 13:
                        mapPart.setIcon(new ImageIcon("CannonFood.png"));
                        break;
                    case 14:
                        mapPart.setIcon(new ImageIcon("TankGunFood.png"));
                        break;
                    case 15:
                        mapPart.setIcon(new ImageIcon("star.png"));
                        break;

                }
                mapInt[i][j] = selected;
            }
        });
    }

    private void addAction(int i, int j, JButton jButton) {
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                selected = 8 * i + j;
            }
        });
    }
}
