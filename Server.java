import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Server {
    private Manager manager;
    ServerSocket serverSocket;
    private GameLogic gameLogic;
    private ScheduledExecutorService scheduler;

    public Server() {
        manager = new Manager();
        gameLogic = new GameLogic();
        scheduler = Executors.newScheduledThreadPool(1);
        
        // Start the game loop
        scheduler.scheduleAtFixedRate(new GameLoopTask(gameLogic), 0, 16, TimeUnit.MILLISECONDS); // ~60 FPS
        scheduler.scheduleAtFixedRate(new EnemySpawnTask(manager), 0, 5, TimeUnit.SECONDS); // Spawns every 5 seconds
    }

    public void start() {
        try {
            try {
                serverSocket = new ServerSocket(2048);
                System.out.println("Server started on port 2048");

                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("New connection established");

                    ServerThread serverThread = new ServerThread(socket, manager);
                    manager.addServerThread(serverThread);
                    serverThread.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}