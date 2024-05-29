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
    }

    public void connect() {
        try {
            socket = new Socket("localhost", 2048);
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
    }

    public void mouseClicked(MouseEvent e) {}
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