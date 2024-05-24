import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;

public class ClientScreen extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {
    private Socket socket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;
    private JButton playButton;
    private JButton helpButton;
    private Font exoRegular;
    private int screenWidth;
    private int screenHeight;
    private String headerString;
    private String username;
    private String[] currentMessage;
    private boolean displayCharacterSelection;
    private boolean inGame;
    private boolean positionUpdated;
    private ClientGameManager gameManager;
    private TextFieldWithPrompt nameInputTextField;

    public ClientScreen() {
        this.setLayout(null);
        this.setDoubleBuffered(true);

        screenWidth = 800;
        screenHeight = 600;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        gameManager = new ClientGameManager();

        try {
            exoRegular = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Exo/static/Exo-Regular.ttf")).deriveFont(24f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        headerString = "CYBER FORTRESS TD";
        username = "";

        displayCharacterSelection = false;
        inGame = false;
        positionUpdated = false;

        playButton = new JButton("PLAY");
        playButton.setBounds(screenWidth / 2 - 50, screenHeight / 2 - 25, 100, 50);
        playButton.setFont(exoRegular);
        playButton.addActionListener(this);

        helpButton = new JButton("HELP");
        helpButton.setBounds(screenWidth / 2 - 50, screenHeight / 2 + 50, 100, 50);
        helpButton.setFont(exoRegular);
        helpButton.addActionListener(this);

        nameInputTextField = new TextFieldWithPrompt();
        nameInputTextField.setBounds(screenWidth / 2 - 50, screenHeight / 2 - 95, 100, 50);
        nameInputTextField.setFont(exoRegular);
        nameInputTextField.setForeground(Color.LIGHT_GRAY);
        nameInputTextField.setOpaque(false);
        nameInputTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        nameInputTextField.setCaretColor(Color.WHITE);
        nameInputTextField.addActionListener(this);
        add(nameInputTextField);
        add(playButton);
        add(helpButton);

        addMouseListener(this);
        addMouseMotionListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);

        g.setColor(Color.WHITE);
        g.setFont(exoRegular);

        Graphics2D g2d = (Graphics2D) g;
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(headerString);

        g.drawString(headerString, screenWidth/2 - stringWidth/2, 100);

        if(nameInputTextField.isVisible()) {
            g2d.setStroke(new BasicStroke(3));
            g.setColor(Color.WHITE);
            g.drawLine(screenWidth / 2 - 50, screenHeight / 2 - 50, screenWidth / 2 + 50, screenHeight / 2 - 50);
        }

        if(displayCharacterSelection && !inGame) {
            gameManager.displayCharacterSelection(g, exoRegular, screenWidth);
        }

        if(inGame) {
            gameManager.displayGame(g, exoRegular, screenWidth, screenHeight);
        }

        if(inGame && currentMessage != null) {
            gameManager.displayOtherPlayers(g, currentMessage[1], Integer.parseInt(currentMessage[2]), Integer.parseInt(currentMessage[3]), currentMessage[4], Integer.parseInt(currentMessage[5]));
        }

        if(!positionUpdated && inGame) {
            outputStream.println("/SetOtherPlayersPosition" + " " + username + " " + gameManager.relativeX() + " " + gameManager.relativeY() + " " + gameManager.selectedCharacter() + " " + gameManager.characterAngle());
            outputStream.flush();
        }
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
                            headerString = "SELECT YOUR CHARACTER";
                            displayCharacterSelection = true;
                            repaint();
                        }
                        if(response != null && response.contains("/SetOtherPlayersPosition")) {
                            currentMessage = response.split(" ");
                            positionUpdated = false;
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

    public String username() {
        return username;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            username = nameInputTextField.getText();
            nameInputTextField.setVisible(false);
            playButton.setVisible(false);
            helpButton.setVisible(false);
            headerString = "WAITING FOR OTHER PLAYER";
            try {
                outputStream.println("play");
                outputStream.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            repaint();
        }
        if (e.getSource() == helpButton) {
            System.out.println("Help");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(displayCharacterSelection) {
            int x = e.getX();
            int y = e.getY();
            inGame = gameManager.handleCharacterSelection(x, y, screenWidth, screenHeight);
            if(inGame) {
                displayCharacterSelection = false;
                headerString = "";
            }
            repaint();
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {
        if(inGame) {
            int x = e.getX();
            int y = e.getY();
            gameManager.handleCharacterAngle(x, y, screenWidth, screenHeight);
            repaint();
        }
        else if(displayCharacterSelection) {
            int x = e.getX();
            int y = e.getY();
            gameManager.handleCharacterHover(x, y, screenWidth);
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(inGame) {
            gameManager.handleCharacterMovement(e.getKeyCode(), screenWidth, screenHeight);
            repaint();
        }
    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}