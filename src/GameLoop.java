/*** In The Name of Allah ***/

/**
 * A very simple structure for the main game loop.
 * THIS IS NOT PERFECT, but works for most situations.
 * Note that to make this work, none of the 2 methods
 * in the while loop (update() and render()) should be
 * long running! Both must execute very quickly, without
 * any waiting and blocking!
 *
 * Detailed discussion on different game loop design
 * patterns is available in the following link:
 *    http://gameprogrammingpatterns.com/game-loop.html
 *
 */
public class GameLoop implements Runnable {

    /**
     * Frame Per Second.
     * Higher is better, but any value above 24 is fine.
     */
    public static final int FPS = 30;
    private int level;

    private GameFrame canvas;
    public static GameState state;
    private int mode ;
    private String map;

    public GameLoop(GameFrame frame, int mode , int level,String map) {
        canvas = frame;
        this.mode = mode;
        this.level = level ;
        this.map = map ;
    }

    /**
     * This must be called before the game loop starts.
     */
    public void init() {
        state = new GameState(mode , level,map);
        canvas.addKeyListener(state.getKeyListener());
        canvas.addMouseListener(state.getMouseListener());
        canvas.addMouseMotionListener(state.getMouseMotionListener());
    }

    public void initServer() {

        state = new GameState(mode , level,map);
        canvas.addKeyListener(state.getKeyListener());
        canvas.addMouseListener(state.getMouseListener());
        canvas.addMouseMotionListener(state.getMouseMotionListener());
    }

    public void initClient() {
        state = new ClientGameState(mode , level);
        canvas.addKeyListener(state.getKeyListener());
        canvas.addMouseListener(state.getMouseListener());
        canvas.addMouseMotionListener(state.getMouseMotionListener());
    }

    @Override
    public void run() {
        boolean gameOver = false;
        boolean key = false;
        boolean pause = false;
        while (!(gameOver || key) ) {
            try {
                if (pause){
                    canvas.render(state);
                    Thread.sleep(1000);
                    pause = state.pause;
                    continue;
                }
                long start = System.currentTimeMillis();
                //
                state.update();
                canvas.render(state);
                gameOver = state.gameOver;
                if (mode == 0) {
                    key = state.map.key.used();
                }
                pause = state.pause ;
                //
                long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
                if (delay > 0)
                    Thread.sleep(delay);
            } catch (InterruptedException ex) {
            }
        }
        canvas.render(state);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);


    }
}
