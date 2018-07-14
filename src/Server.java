import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by mahdihs76 on 5/21/18.
 */
public class Server implements DataListener {
    public static int SERVER_PORT = 12345 ;
    private static Server instance = new Server(SERVER_PORT);
    private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public static final Sound SOUND = new Sound(".\\game.wav",105000);
    public static GameFrame frame;
    public static void main(String[] args) {
        // Initialize the global thread-pool
        ThreadPool.init();

        // Show the game menu ...

        // After the player clicks 'PLAY' ...
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SOUND.execute();
                frame = new GameFrame("Simple Ball !" , 0);
                frame.setLocationRelativeTo(null); // put frame at center of screen
//                Toolkit toolkit = Toolkit.getDefaultToolkit();
//                Image image = toolkit.getImage(".\\pointer.png");
//                int a = (frame.getX()) + 30;
//                int b = frame.getY() + 30;
//                Cursor c = toolkit.createCustomCursor(image, new Point(a, b), "img");
//                frame.setCursor(c);
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
                GameLoop game = new GameLoop(frame,1,0);
                game.initServer();
                ThreadPool.execute(game);
                // and the game starts ...
            }
        });
    }

    private Server(int port) {
        this.port = port;
    }

    public static Server getInstance() {
        return instance;
    }

    public void start(){
        try {
            setup();
            waitForClient();
            initIOStreams();
//            getData();
            //startThreads();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setup() throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private void waitForClient() throws IOException {
        clientSocket = serverSocket.accept();
    }

    public void reset(){
        try {
            outputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getData(){
        try {
            CoPlayerTank coPlayerTank = (CoPlayerTank) inputStream.readObject() ;
            System.out.println(coPlayerTank.up);
            GameState.coPlayer = coPlayerTank ;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initIOStreams() throws IOException {
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    private void startThreads() {
        new Thread(new GetDataRunnable(inputStream, this)).start();
    }

    public void sendData(Data data) {
        try {
            outputStream.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void receive(Data data) {

    }

    @Override
    public void receive(CoPlayerTank coPlayerTank) {
        //GameState.coPlayer = coPlayerTank ;
    }
}
