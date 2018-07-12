//
//
//import guis.ServerGUI;
//import models.Message;
//import runnables.GetDataRunnable;
//import runnables.MessageListener;
//import utils.ViewHandler;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//import static utils.MyConstants.SERVER_PORT;
//
///**
// * Created by mahdihs76 on 5/21/18.
// */
//public class Server implements MessageListener {
//    private static Server instance = new Server(SERVER_PORT);
//    private int port;
//    private ServerSocket serverSocket;
//    private Socket clientSocket;
//    private ObjectOutputStream outputStream;
//    private ObjectInputStream inputStream;
//
//    private Server(int port) {
//        this.port = port;
//    }
//
//    public static Server getInstance() {
//        return instance;
//    }
//
//    public void start(){
//        try {
//            setup();
//            waitForClient();
//            initIOStreams();
//            startThreads();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private void setup() throws IOException {
//        serverSocket = new ServerSocket(port);
//        ViewHandler.serverStarted();
//    }
//
//    private void waitForClient() throws IOException {
//        ViewHandler.waitForClient();
//        clientSocket = serverSocket.accept();
//        ViewHandler.clientConnected();
//    }
//
//    private void initIOStreams() throws IOException {
//        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
//        inputStream = new ObjectInputStream(clientSocket.getInputStream());
//    }
//
//    private void startThreads() {
//        new Thread(new GetDataRunnable(inputStream, this)).start();
//    }
//
//    public void sendData(String text) {
//        GameState message = ;
//        try {
//            outputStream.writeObject(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void receive(Message message) {
//        ServerGUI.getData(message);
//    }
//}
