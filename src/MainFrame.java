import javafx.scene.control.RadioButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    private Image image;
    private JPanel startPanel;
    private JPanel singlePlayer;
    private JPanel twoPlayer;
    private SpringLayout layout;
    private JPanel exit;

    public MainFrame() {
        layout = new SpringLayout();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        try {
            image = ImageIO.read(new File("MainPanel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setUndecorated(true);
        this.setVisible(true);
        setStartPanel();

    }

    private void setSinglePlayer() {
        singlePlayer = new JPanel(new BorderLayout());
        JLabel jLabel = new JLabel("SinglePlayer");
        singlePlayer.setBackground(Color.BLACK);
        jLabel.setBackground(Color.BLACK);
        jLabel.setForeground(Color.WHITE);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        singlePlayer.add(jLabel, BorderLayout.CENTER);

        singlePlayer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                startSinglePlayer();

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                jLabel.setForeground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                jLabel.setForeground(Color.WHITE);
            }
        });

        startPanel.add(singlePlayer);
        layout.putConstraint(SpringLayout.SOUTH, singlePlayer, 500, SpringLayout.NORTH, startPanel);
        layout.putConstraint(SpringLayout.WEST, singlePlayer, 500, SpringLayout.WEST, startPanel);
    }

    private void startTwoPlayer() {
        Object[] objects = new Object[2];
        JRadioButton server = new JRadioButton("Server");
        JRadioButton client = new JRadioButton("Client");
        ButtonGroup buttonGroup = new ButtonGroup();
        server.setSelected(true);
        buttonGroup.add(server);
        buttonGroup.add(client);

        objects[0] = server;
        objects[1] = client;
        int res = JOptionPane.showOptionDialog(new JFrame(), objects, "GameMood", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (res == JOptionPane.OK_OPTION) {
            this.dispose();
            if (server.isSelected())
                Main.startGame(0);
            else if (client.isSelected())
                Main.startGame(1);
        }

    }

    private void startSinglePlayer() {
        Object[] objects = new Object[3];
        JRadioButton easy = new JRadioButton("Easy");
        JRadioButton normal = new JRadioButton("Normal");
        JRadioButton hard = new JRadioButton("Hard");
        ButtonGroup buttonGroup = new ButtonGroup();
        easy.setSelected(true);
        buttonGroup.add(easy);
        buttonGroup.add(normal);
        buttonGroup.add(hard);

        objects[0] = easy;
        objects[1] = normal;
        objects[2] = hard;
        int res = JOptionPane.showOptionDialog(new JFrame(), objects, "GameMood", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (res == JOptionPane.OK_OPTION) {
            this.dispose();
            if (easy.isSelected())
                Main.startGame(0);
            else if (normal.isSelected())
                Main.startGame(1);
            else if (hard.isSelected())
                Main.startGame(2);
        }
    }

    private void setTwoPlayer() {
        twoPlayer = new JPanel(new BorderLayout());
        JLabel jLabel = new JLabel("TwoPlayer");
        twoPlayer.setBackground(Color.BLACK);
        jLabel.setBackground(Color.BLACK);
        jLabel.setForeground(Color.WHITE);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        twoPlayer.add(jLabel, BorderLayout.CENTER);

        twoPlayer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                startTwoPlayer();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                jLabel.setForeground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                jLabel.setForeground(Color.WHITE);
            }
        });

        startPanel.add(twoPlayer);
        layout.putConstraint(SpringLayout.SOUTH, twoPlayer, 600, SpringLayout.NORTH, startPanel);
        layout.putConstraint(SpringLayout.WEST, twoPlayer, 500, SpringLayout.WEST, startPanel);
    }

    private void setStartPanel() {
        startPanel = new JPanel(layout) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };
        JLabel jLabel = new JLabel("Select mode to play");
        jLabel.setForeground(Color.WHITE);
        jLabel.setBackground(Color.BLACK);
        jLabel.setFont(new Font(jLabel.getFont().getName(), Font.PLAIN, 25));

        startPanel.add(jLabel);
        layout.putConstraint(SpringLayout.SOUTH, jLabel, 1000, SpringLayout.NORTH, startPanel);
        layout.putConstraint(SpringLayout.WEST, jLabel, 800, SpringLayout.WEST, startPanel);

        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    jLabel.setForeground(Color.WHITE);
                    Thread.sleep(1200);
                    jLabel.setForeground(Color.BLACK);
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        this.add(startPanel);
        setSinglePlayer();
        setTwoPlayer();
        setExit();
    }

    private void setExit() {
        exit = new JPanel(new BorderLayout());
        JLabel jLabel = new JLabel("Exit");
        exit.setBackground(Color.BLACK);
        jLabel.setBackground(Color.BLACK);
        jLabel.setForeground(Color.WHITE);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        exit.add(jLabel, BorderLayout.CENTER);

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                jLabel.setForeground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                jLabel.setForeground(Color.WHITE);
            }
        });

        startPanel.add(exit);
        layout.putConstraint(SpringLayout.SOUTH, exit, 700, SpringLayout.NORTH, startPanel);
        layout.putConstraint(SpringLayout.WEST, exit, 500, SpringLayout.WEST, startPanel);
    }
}


