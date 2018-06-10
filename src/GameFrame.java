/*** In The Name of Allah ***/


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering,
 * actually it performs triple-buffering!
 * For more information on BufferStrategy check out:
 * http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 * http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 *
 */
public class GameFrame extends JFrame {

    public static final int GAME_HEIGHT = 1080;                  // 720p game resolution
    public static final int GAME_WIDTH = 1920;  // wide aspect ratio


    //uncomment all /*...*/ in the class for using PlayerTank icon instead of a simple circle
    private BufferedImage tankBody;
    private BufferedImage tankGun;

    private long lastRender;
    private ArrayList<Float> fpsHistory;

    private BufferStrategy bufferStrategy;

    public GameFrame(String title) {
        super(title);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        lastRender = -1;
        fpsHistory = new ArrayList<>(100);

        try {
            tankBody = ImageIO.read(new File("PlayerTank-under.png"));
            tankGun = ImageIO.read(new File("PlayerTank-top.png"));

        } catch (IOException e) {
            System.out.println(e);
        }
    }


    /**
     * This must be called once after the JFrame is shown:
     * frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }


    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state);
                } finally {
                    // Dispose the graphics
                    graphics.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());

            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }

    /**
     * Rendering all game elements based on the game state.
     */
    private void doRendering(Graphics2D g2d, GameState state) {
        // Draw background
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        // Draw ball

        AffineTransform oldTrans = g2d.getTransform();
        g2d.setColor(Color.BLACK);
        g2d.fillOval(state.getPlayerTank().getX(), state.getPlayerTank().getY(), 10, 10);


        AffineTransform atBody = g2d.getTransform();
        atBody.rotate(Math.toRadians(state.getPlayerTank().getAngelBody()), state.getPlayerTank().getX() + state.getPlayerTank().getBodyImage().getWidth() / 2, state.getPlayerTank().getY() + state.getPlayerTank().getBodyImage().getHeight() / 2);
        g2d.setTransform(atBody);
        g2d.drawImage(state.getPlayerTank().getBodyImage(), state.getPlayerTank().getX(), state.getPlayerTank().getY(), null);

        g2d.setTransform(oldTrans);
        AffineTransform atGun = g2d.getTransform();
        atGun.translate(state.getPlayerTank().getX() , state.getPlayerTank().getY() );
        System.out.println("AAAAAAAAAAAAAA : " + Math.toDegrees(state.getPlayerTank().getAngelGun()));
        atGun.rotate(state.getPlayerTank().getAngelGun(), 87,67);


        g2d.setTransform(atGun);


        g2d.drawImage(state.getPlayerTank().getGunImage(),0,0,null);



        g2d.setColor(Color.BLACK);

        g2d.setTransform(oldTrans);
        g2d.fillOval(state.getPlayerTank().getX() + 87, state.getPlayerTank().getY() + 67, 2, 2);
        g2d.drawLine(state.getPlayerTank().getX()+87,state.getPlayerTank().getY()+67,state.mouseX,state.mouseY);






        // Print FPS info
        long currentRender = System.currentTimeMillis();
        if (lastRender > 0) {
            fpsHistory.add(1000.0f / (currentRender - lastRender));
            if (fpsHistory.size() > 100) {
                fpsHistory.remove(0); // remove oldest
            }
            float avg = 0.0f;
            for (float fps : fpsHistory) {
                avg += fps;
            }
            avg /= fpsHistory.size();
            String str = String.format("Average FPS = %.1f , Last Interval = %d ms",
                    avg, (currentRender - lastRender));
            g2d.setColor(Color.CYAN);
            g2d.setFont(g2d.getFont().deriveFont(18.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            int strHeight = g2d.getFontMetrics().getHeight();
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, strHeight + 50);
        }
        lastRender = currentRender;
        // Print user guide
        String userGuide
                = "Use ARROW KEYS to move the PlayerTank. "
                + "Press ESCAPE to end the game.";
        g2d.setFont(g2d.getFont().deriveFont(18.0f));
        g2d.drawString(userGuide, 10, GAME_HEIGHT - 10);
        // Draw GAME OVER
        if (state.gameOver) {
            String str = "GAME OVER";
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
        }
    }

}
