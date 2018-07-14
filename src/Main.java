/*** In The Name of Allah ***/

import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * Program start.
 */
public class Main {

    public static final Sound SOUND = new Sound(".\\game.wav", 105000);
    public static GameFrame frame;
    public static int level;
    public static MainFrame mainFrame;

    public static void main(String[] args) {
        // Initialize the global thread-pool
        ThreadPool.init();

        // Show the game menu ...

        // After the player clicks 'PLAY' ...
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFrame = new MainFrame();
                // and the game starts ...
            }
        });
    }

    public static void startGame(int level, String map) {
        SOUND.execute();
        frame = new GameFrame("Simple Ball", level);
        frame.setLocationRelativeTo(null); // put frame at center of screen
        Toolkit toolkit = Toolkit.getDefaultToolkit();
//                Image image = toolkit.getImage(".\\pointer.png");
//                int a = (frame.getX()) + 30;
//                int b = frame.getY() + 30;
//                Cursor c = toolkit.createCustomCursor(image, new Point(a, b), "img");
        Cursor blankCursor = null;
        try {
            blankCursor = Toolkit.getDefaultToolkit().createCustomCursor
                    (ImageIO.read(new File("blankCursor.png")), new Point(0, 0), "blank cursor");
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setCursor(blankCursor);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.initBufferStrategy();
        // Create and execute the game-loop
        GameLoop game = new GameLoop(frame, 0, level, map);
        game.init();
        ThreadPool.execute(game);

    }
}
