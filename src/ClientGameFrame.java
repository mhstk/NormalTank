import java.awt.*;
import java.awt.geom.AffineTransform;

public class ClientGameFrame extends GameFrame {
    public ClientGameFrame(String title) {
        super(title,0);
    }

    @Override
    protected void doRendering(Graphics2D g2d, GameState state) {
        ClientGameState clientState = (ClientGameState) state;
        AffineTransform oldTrans = g2d.getTransform();

        // Draw background
        g2d.fill(new Rectangle(0, 0, 1920, 1080));
        ClientGameState.data.map.drawAreaClient(LoadImage.area, g2d, state, oldTrans);

        // Draw Objects in map

        ClientGameState.data.map.drawWallsClient(LoadImage.hardWall, LoadImage.softWall0, LoadImage.softWall1, LoadImage.softWall2, LoadImage.softWall3, LoadImage.teazel, g2d, clientState, oldTrans);

        // Draw Body
        System.out.println("321321");

        ClientGameState.data.playerTank.drawBodyClient(LoadImage.tankUnder3, g2d, state, oldTrans);
        ClientGameState.data.coPlayer.drawBody(LoadImage.tankUnder1, g2d, state, oldTrans);
//        for (Turret turret : state.map.turrets)
        //turret.drawBody(g2d,state,oldTrans);
//            for (EnemyTank enemyTank : state.map.enemyTanks)
//                enemyTank.drawBody(LoadImage.tankUnder1,g2d, state, oldTrans);
//        for (IdiotEnemy idiotEnemy : state.map.idiotEnemies) {
//            if (idiotEnemy.isAlive()&&idiotEnemy.isVisible())
//                idiotEnemy.drawBody(LoadImage.idiotEnemy1,g2d, state, oldTrans);
//        }

        // Draw Gun
        ClientGameState.data.playerTank.drawGunClient(LoadImage.tankTop3, g2d, state, oldTrans);
        ClientGameState.data.coPlayer.drawGun(LoadImage.tankTop1,LoadImage.tankTop2, g2d, state, oldTrans);


        // Draw trees
          ClientGameState.data.map.drawPlants(LoadImage.plant,g2d, state, oldTrans);


        // Back to normal affine
        g2d.setTransform(oldTrans);
        g2d.drawImage(LoadImage.cursor , clientState.mouseX-40,clientState.mouseY-40,null);


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
    }
}
