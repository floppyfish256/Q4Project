import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Manager manager;
    ServerSocket serverSocket;

    public Server() {
        manager = new Manager();
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