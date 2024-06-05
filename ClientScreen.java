import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;

public class ClientScreen extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {
    
    private Socket socket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;
    private ClientGameManager gameManager;

    public ClientScreen() {
        gameManager = new ClientGameManager();
        gameManager.initGame(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameManager.drawGame(g);
        for(Building building : gameManager.getBuildings()) {
            if(building.time() > 8) {
                gameManager.removeBuilding(building);
                sendRemoveBuildingMessage(building);
            }
        }
    }

    public void connect() {
        try {
            socket = new Socket("localhost", 2048);
            //socket = new Socket("10.210.126.189", 2048);
            outputStream = new PrintWriter(socket.getOutputStream(), true);
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread serverListenerThread = new Thread(() -> {
                try {
                    while (true) {
                        String response = inputStream.readLine();
                        
                        if(response != null && response.equalsIgnoreCase("Start")) {
                            gameManager.setHeaderString("");
                            gameManager.startGame();
                            repaint();
                        }
                        if(response != null && response.startsWith("spawn:")) {
                            String[] tokens = response.split(":");
                            String enemyType = tokens[1];
                            int x = Integer.parseInt(tokens[2]);
                            int y = Integer.parseInt(tokens[3]);
                            gameManager.spawnEnemy(enemyType, x, y);
                            repaint();
                        }
                        if(response != null && response.startsWith("build:")) {
                            String[] tokens = response.split(":");
                            int x = Integer.parseInt(tokens[1]);
                            int y = Integer.parseInt(tokens[2]);
                            gameManager.build(x, y);
                        }
                        if(response != null && response.startsWith("removeBuilding:")) {
                            String[] tokens = response.split(":");
                            int x = Integer.parseInt(tokens[1]);
                            int y = Integer.parseInt(tokens[2]);
                            for(Building building : gameManager.getBuildings()) {
                                if(building.getX() == x && building.getY() == y) {
                                    gameManager.removeBuilding(building);
                                    break;
                                }
                            }
                        }
                        if(response != null && response.startsWith("restart")) {
                            gameManager.restartGame();
                            repaint();
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendBuildMessage(int x, int y) {
        try {
            outputStream.println("build:" + x + ":" + y);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRemoveBuildingMessage(Building building) {
        try {
            outputStream.println("removeBuilding:" + building.getX() + ":" + building.getY());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameManager.handleInput(e);
        if (e.getSource() == gameManager.getPlayButton()) {
            try {
                outputStream.println("play");
                outputStream.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            repaint();
        }
        if (e.getSource() == gameManager.getRestartButton()) {
            try {
                outputStream.println("restart");
                outputStream.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            repaint();
        }
    }

    public void mouseClicked(MouseEvent e) {
        if(gameManager.money() >= 50) {
            sendBuildMessage(e.getX(), e.getY());
        }
        gameManager.handleMouseInput(e.getX(), e.getY());
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}

    public void keyPressed(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}