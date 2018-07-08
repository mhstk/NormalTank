/*** In The Name of Allah ***/

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * Program start.
 *
 */
public class Main {

    public static void main(String[] args) {
        // Initialize the global thread-pool
        ThreadPool.init();

        // Show the game menu ...

        // After the player clicks 'PLAY' ...
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameFrame frame = new GameFrame("Simple Ball !");
                frame.setLocationRelativeTo(null); // put frame at center of screen
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Image image = Toolkit.getDefaultToolkit().getImage(".\\pointer.png");
                int a = (frame.getX())+30;
                int b = frame.getY()+30;
                Cursor c = toolkit.createCustomCursor(image, new Point(a,b),"img");
                frame.setCursor(c);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.initBufferStrategy();
                // Create and execute the game-loop
                GameLoop game = new GameLoop(frame);
                game.init();
                ThreadPool.execute(game);
                // and the game starts ...
            }
        });
    }
}
