import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class ClientGameManager {
    private int screenWidth;
    private int screenHeight;
    private String headerString;
    private JButton playButton;
    private JButton helpButton;
    private JButton restartButton;
    private Font exoRegular;
    private TextFieldWithPrompt nameInputTextField;
    private CenterTower tower;
    private boolean gameStarted;
    private MyArrayList<Enemy> enemies;
    private MyArrayList<Building> buildings;
    private int money;
    private MyMap<String, String> sounds;

    public ClientGameManager() {
        screenWidth = 800;
        screenHeight = 600;
        tower = new CenterTower(screenWidth / 2, screenHeight / 2);
        gameStarted = false;
        enemies = new MyArrayList<>();
        buildings = new MyArrayList<>();
        money = 100;
        sounds = new MyMap<String, String>();
        sounds.put("capture", "capture.wav");
        sounds.put("game_over", "game_over.wav");
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

        headerString = "THE HORDE";

        playButton = new JButton("PLAY");
        playButton.setBounds(screenWidth / 2 - 50, screenHeight / 2 - 25, 100, 50);
        playButton.setFont(exoRegular);
        playButton.addActionListener(screen);

        helpButton = new JButton("HELP");
        helpButton.setBounds(screenWidth / 2 - 50, screenHeight / 2 + 50, 100, 50);
        helpButton.setFont(exoRegular);
        helpButton.addActionListener(screen);

        restartButton = new JButton("RESTART");
        restartButton.setBounds(screenWidth / 2 - 75, screenHeight / 2 - 25, 150, 50);
        restartButton.setFont(exoRegular);
        restartButton.addActionListener(screen);

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
        screen.add(restartButton);
        restartButton.setVisible(false);

        screen.addMouseListener(screen);
        screen.addMouseMotionListener(screen);
        screen.addKeyListener(screen);
        screen.setFocusable(true);
    }

    public void drawGame(Graphics g) {
        
        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);
        // gray lines every 50 pixels
        g.setColor(Color.GRAY);
        for(int i = 0; i < screenWidth; i += 50) {
            g.drawLine(i, 0, i, screenHeight);
        }
        for(int i = 0; i < screenHeight; i += 50) {
            g.drawLine(0, i, screenWidth, i);
        }

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

        if(gameStarted) {
            tower.drawMe(g);
            for(Enemy enemy : enemies) {
                enemy.drawMe(g);
            }
            for(Building building : buildings) {
                building.drawMe(g);
                for(Enemy enemy : enemies) {
                    if(building.shoot(enemy)) {
                        playSound(sounds.get("capture"));
                        money += 50;
                        break;
                    }
                }
            }
            for(Enemy enemy : enemies) {
                if(enemy.getX() > 400 - 50 && enemy.getX() < 400 + 50 && enemy.getY() > 300 - 50 && enemy.getY() < 300 + 50) {
                    playSound(sounds.get("game_over"));
                    setHeaderString("GAME OVER");
                    gameStarted = false;
                    restartButton.setVisible(true);
                }
            }
            g.setColor(Color.WHITE);
            g.drawString("Money: " + money, 10, 30);
        }
    }

    public void startGame() {
        gameStarted = true;
    }

    public void handleInput(ActionEvent e) {
        if(e.getSource() == playButton) {
            nameInputTextField.setVisible(false);
            playButton.setVisible(false);
            helpButton.setVisible(false);
            setHeaderString("WAITING FOR OTHER PLAYER");
        }
        if(e.getSource() == helpButton) {
            setHeaderString("Click to place towers, defend the center");
        }
        if(e.getSource() == restartButton) {
            restartButton.setVisible(false);
            setHeaderString("");
            gameStarted = true;
            enemies.clear();
            buildings.clear();
            money = 100;
        }
    }

    public void handleMouseInput(int x, int y) {
        if(gameStarted && money >= 50) {
            buildings.add(new Building(x, y));
            money -= 50;
        }
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

    public JButton getRestartButton() {
        return restartButton;
    }

    public void spawnEnemy(String enemyType, int x, int y) {
        if(gameStarted) {
            enemies.add(new Enemy(enemyType, x, y));
        }
    }

    public int money() {
        return money;
    }

    public void build(int x, int y) {
        if(gameStarted) {
            buildings.add(new Building(x, y));
        }
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public MyArrayList<Building> getBuildings() {
        return buildings;
    }

    public void removeBuilding(Building building) {
        buildings.remove(building);
    }

    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            public void run() {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                ClientGameManager.class.getResourceAsStream("sounds/" + url));
                clip.open(inputStream);
                clip.start(); 
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            }
        }).start();
    }

    public void restartGame() {
        gameStarted = true;
        enemies.clear();
        buildings.clear();
        money = 100;
        restartButton.setVisible(false);
        headerString = "";
    }

    public MyArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }
}