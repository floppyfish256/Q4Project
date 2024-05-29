import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
    private Socket clientSocket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;
    private Manager manager;

    public ServerThread(Socket clientSocket, Manager manager) {
        this.clientSocket = clientSocket;
        this.manager = manager;
    }

    public void send(String message) {
        outputStream.println(message);
        outputStream.flush();
    }

    @Override
    public void run() {
        try {
            outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            // Wait for client input
            while(true) {
                String message = inputStream.readLine();
                if(message != null && message.equalsIgnoreCase("play")) {
                    manager.incrementPlayersReady();
                    manager.broadcastMessage(manager.getPlayersReady() + " players ready");
                    if(manager.getPlayersReady() == 2) {
                        manager.broadcastMessage("Start");
                    }
                }
                if(message != null && message.equalsIgnoreCase("centerTower")) {
                    manager.broadcastMessage("Health: " + manager.getCenterTower().getHealth());
                }
            }
        } catch (EOFException e) {
            System.out.println("Client disconnected");
        } catch (SocketException e) {
            System.out.println("Client connection reset");
        } catch (IOException e) {
            System.out.println("Error reading from client");
        }
    }
}