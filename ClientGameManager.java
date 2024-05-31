import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class ClientGameManager {
    private int screenWidth;
    private int screenHeight;
    private String headerString;
    private String username;
    private JButton playButton;
    private JButton helpButton;
    private Font exoRegular;
    private boolean inGame;
    private CenterTower centerTower;
    private TextFieldWithPrompt nameInputTextField;
    private MyMap<String, Enemy> enemies;
    // private MyMap<String, Tower> towers;
    // private MyMap<String, Bullet> bullets;

    public ClientGameManager() {
        inGame = false;
        screenWidth = 800;
        screenHeight = 600;
        centerTower = new CenterTower(400, 300);
    }

    public void initGame(ClientScreen screen) {
        screen.setLayout(null);
        screen.setDoubleBuffered(true);
        screen.setPreferredSize(new Dimension(screenWidth, screenHeight));

        try {
            exoRegular = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Exo/static/Exo-Regular.ttf")).deriveFont(24f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        headerString = "TOWER DEFENSE GAME";
        username = "";

        playButton = new JButton("PLAY");
        playButton.setBounds(screenWidth / 2 - 50, screenHeight / 2 - 25, 100, 50);
        playButton.setFont(exoRegular);
        playButton.addActionListener(screen);

        helpButton = new JButton("HELP");
        helpButton.setBounds(screenWidth / 2 - 50, screenHeight / 2 + 50, 100, 50);
        helpButton.setFont(exoRegular);
        helpButton.addActionListener(screen);

        nameInputTextField = new TextFieldWithPrompt();
        nameInputTextField.setBounds(screenWidth / 2 - 50, screenHeight / 2 - 95, 100, 50);
        nameInputTextField.setFont(exoRegular);
        nameInputTextField.setForeground(Color.LIGHT_GRAY);
        nameInputTextField.setOpaque(false);
        nameInputTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        nameInputTextField.setCaretColor(Color.WHITE);
        nameInputTextField.addActionListener(screen);
        
        screen.add(nameInputTextField);
        screen.add(playButton);
        screen.add(helpButton);

        screen.addMouseListener(screen);
        screen.addMouseMotionListener(screen);
        screen.addKeyListener(screen);
        screen.setFocusable(true);
    }

    public void startGame() {
        inGame = true;
    }

    public void startGameLoop() {
        while(inGame) {
            updateGame();
        }
    }

    public void updateGame() {
        System.out.println("Game loop running");
    }

    public void drawGame(Graphics g) {
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

        if(inGame) {
            centerTower.drawMe(g);
        }
    }

    public void handleInput(ActionEvent e) {
        if(e.getSource() == playButton) {
            username = nameInputTextField.getText();
            nameInputTextField.setVisible(false);
            playButton.setVisible(false);
            helpButton.setVisible(false);
            setHeaderString("WAITING FOR OTHER PLAYER");
        }
        if(e.getSource() == helpButton) {
            System.out.println("Help button clicked");
        }
    }

    public void handleMouseInput(int x, int y) {
    }

    public void setHeaderString(String headerString) {
        this.headerString = headerString;
    }

    public JButton getPlayButton() {
        return playButton;
    }

    public JButton getHelpButton() {
        return helpButton;
    }
}