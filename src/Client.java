import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "192.168.43.95";
    private static final int SERVER_PORT = 12345;
    private static Client instance = new Client();
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private Client(){}

    public static Client getInstance() {
        return instance;
    }

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
                frame = new ClientGameFrame("Simple Ball !");
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
                GameLoop game = new GameLoop(frame,2,0);
                game.initClient();
                ThreadPool.execute(game);
                // and the game starts ...
            }
        });
    }

    public void reset(){
        try {
            outputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start(){
        try {
            connect2Server();
            initIOStreams();
            //startTheards();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect2Server() throws IOException {
        clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);

    }

    public void initIOStreams() throws IOException {
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    public void getData(){
        try {
            Data data = (Data) inputStream.readObject() ;
            ClientGameState.data = data ;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendData(CoPlayerTank coPlayerTank) {
        try {
            outputStream.writeObject(coPlayerTank);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
