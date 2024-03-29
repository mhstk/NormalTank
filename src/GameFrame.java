/*** In The Name of Allah ***/


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering,
 * actually it performs triple-buffering!
 * For more information on BufferStrategy check out:
 * http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 * http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 */
public class GameFrame extends JFrame {

    public static final int GAME_HEIGHT = 1080;  // 720p game resolution
    public static final int GAME_WIDTH = 1920;  // wide aspect ratio

    protected long lastRender;
    protected ArrayList<Float> fpsHistory;

    private BufferStrategy bufferStrategy;

    public GameFrame(String title, int level) {
        super(title);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        try {
            LoadImage.LoadImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        lastRender = -1;
        fpsHistory = new ArrayList<>(100);
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
    protected void doRendering(Graphics2D g2d, GameState state) {
        if (state.pause){
            String str = "Game Paused!";
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
            return;
        }

        AffineTransform oldTrans = g2d.getTransform();

        // Draw background
        state.map.drawArea(LoadImage.area, g2d, state, oldTrans);

        // Draw Objects in map

        state.map.drawWalls(LoadImage.hardWall,
                LoadImage.softWall0,
                LoadImage.softWall1,
                LoadImage.softWall2,
                LoadImage.softWall3,
                LoadImage.teazel,
                g2d, state, oldTrans);

        // Draw Body
        if (GameState.mode == 1 && GameState.coPlayer != null) {
            GameState.coPlayer.drawBodyServer(LoadImage.tankUnder3, g2d, state, oldTrans);
        }
        for (Turret turret : state.map.turrets)
            turret.drawBody(LoadImage.blank , g2d, state, oldTrans);
        for (EnemyTank enemyTank : state.map.enemyTanks)
            enemyTank.drawBody(LoadImage.tankUnder5, LoadImage.tankUnder6,LoadImage.destroyed, g2d, state, oldTrans);
        for (IdiotEnemy idiotEnemy : state.map.idiotEnemies) {
            if (idiotEnemy.isAlive() && idiotEnemy.isVisible())
                idiotEnemy.drawBody(LoadImage.idiotEnemy1, LoadImage.idiotEnemy2,LoadImage.destroyed, g2d, state, oldTrans);
        }
        for (Mine mine : state.map.mines) {
            if (mine.isAlive() && mine.isVisible()) {
                mine.drawBody(LoadImage.onMine, LoadImage.offMine, g2d, state, oldTrans);
            }
        }
        state.getPlayerTank().drawBody(LoadImage.tankUnder1, LoadImage.tankUnder2,LoadImage.destroyed, g2d, state, oldTrans);


        //Draw Bullet's Gun
        state.getPlayerTank().drawBullets(LoadImage.bullet1, LoadImage.bullet2, g2d, state, oldTrans);
        state.getPlayerTank().updateBullet();
        for (Turret turret : state.map.turrets) {
            turret.drawBullets(LoadImage.bullet1, LoadImage.bullet2, g2d, state, oldTrans);
            turret.updateBullet();
        }
        for (EnemyTank enemyTank : state.map.enemyTanks) {
            enemyTank.drawBullets(LoadImage.bullet1, LoadImage.bullet2, g2d, state, oldTrans);
            enemyTank.updateBullet();
        }
        // Draw Gun
        state.getPlayerTank().drawGun(LoadImage.tankTop1,LoadImage.tankTop2, g2d, state, oldTrans);
        if (state.getPlayerTank().getShield()){
            g2d.drawImage(LoadImage.shield , 0,0,null);
        }
        if (GameState.mode == 1) {
            GameState.coPlayer.drawGunClient(LoadImage.tankTop3, g2d, state, oldTrans);
        }
        for (EnemyTank enemyTank : state.map.enemyTanks)
            enemyTank.drawGun(LoadImage.tankTop5,LoadImage.tankTop2 , g2d, state, oldTrans);
        for (Turret turret : state.map.turrets)
            turret.drawGun(LoadImage.turretGun,g2d, state, oldTrans);
            // Draw trees
            state.map.drawPlants(LoadImage.plant, g2d, state, oldTrans);


        // Back to normal affine
        g2d.setTransform(oldTrans);
        g2d.drawImage(LoadImage.cursor, state.mouseX - 40, state.mouseY - 40, null);

        // Print FPS info
//        long currentRender = System.currentTimeMillis();
//        if (lastRender > 0) {
//            fpsHistory.add(1000.0f / (currentRender - lastRender));
//            if (fpsHistory.size() > 100) {
//                fpsHistory.remove(0); // remove oldest
//            }
//            float avg = 0.0f;
//            for (float fps : fpsHistory) {
//                avg += fps;
//            }
//            avg /= fpsHistory.size();
//            String str = String.format("Average FPS = %.1f , Last Interval = %d ms",
//                    avg, (currentRender - lastRender));
//            g2d.setColor(Color.CYAN);
//            g2d.setFont(g2d.getFont().deriveFont(18.0f));
//            int strWidth = g2d.getFontMetrics().stringWidth(str);
//            int strHeight = g2d.getFontMetrics().getHeight();
//            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, strHeight + 50);
//        }
//        lastRender = currentRender;
//        // Print user guide
//        String userGuide
//                = "Use ARROW KEYS to move the PlayerTank. "
//                + "Press ESCAPE to end the game.";
//
//        g2d.setFont(g2d.getFont().deriveFont(18.0f));
//        g2d.drawString(userGuide, 10, GAME_HEIGHT - 10);
        AffineTransform heartat = g2d.getTransform();
        heartat.translate(1920,0);
        g2d.setTransform(heartat);
        for (int i=0 ; i<state.getPlayerTank().getHealth() ; i+=4){
            g2d.drawImage(LoadImage.heart , -50 , 15 , null);
            heartat.translate(-60 , 0);
            g2d.setTransform(heartat);
        }
        g2d.setTransform(oldTrans);



        g2d.drawImage(LoadImage.NumberOfHeavyBullet,40,40,null);
        g2d.drawImage(LoadImage.NumberOfMachinGun,40,100,null);
        g2d.setColor(Color.orange);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(32.0f));
        g2d.drawString(String.valueOf(state.getPlayerTank().getTankGun()),65,85);
        g2d.drawString(String.valueOf(state.getPlayerTank().getMashinGun()),65,145);
        g2d.setColor(Color.black);

        // Draw GAME OVER
        if (state.gameOver) {
            String str = "GAME OVER";
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
        }

        if (state.map.key.used()) {
            String str = "YOU WON";
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
        }
    }

}
