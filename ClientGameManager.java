/* The relativeX and relativeY are the character's positions relative to the 
 * nexus, not the center of the screen. The character will always be in the
 * center of the screen, and the relativeX and relativeY will be used to move
 * the character around the screen. The characterAngle is the angle the character
 * is facing, and the angleOffset is the offset to make the character face the
 * correct direction. The selectedCharacter is the character the player has
 * selected, and the highlightCharacter is the character the player is hovering
 * over. The imageScale is the scale of the character images, and the darkGray
 * is the color of the background. The mageX, mageW, mageH, and mageY are the
 * x, width, height, and y of the mage character. The knightX, knightW, knightH,
 * and knightY are the x, width, height, and y of the knight character. The
 * agentX, agentW, agentH, and agentY are the x, width, height, and y of the
 * agent character. The handleCharacterSelection method checks if the player has
 * clicked on a character, and if so, sets the selectedCharacter to that
 * character. The handleCharacterHover method checks if the player is hovering
 * over a character, and if so, sets the highlightCharacter to that character.
 * The displayCharacterSelection method displays the character selection screen,
 * and the displayGame method displays the game screen. The handleCharacterAngle
 * method calculates the angle the character is facing based on the mouse
 * position. The handleCharacterMovement method moves the character based on the
 * key pressed. The spawnCharacterAtRandomLocation method spawns the character at
 * a random location. The drawCharacterImage method draws the character image at
 * the specified position and angle. The rotateImage method rotates the image by
 * the specified degrees. The ClientGameManager class manages the game state on
 * the client side.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Image;

public class ClientGameManager {

    private Color darkGray;
    private int imageScale;
    private int mageX;
    private int mageW;
    private int mageH;
    private int mageY;
    private int knightX;
    private int knightW;
    private int knightH;
    private int knightY;
    private int agentX;
    private int agentW;
    private int agentH;
    private int agentY;
    private int characterAngle;
    private int angleOffset;
    private int relativeX;
    private int relativeY;
    private String highlightCharacter;
    private String selectedCharacter;

    public ClientGameManager() {
        imageScale = 4;
        darkGray = new Color(50, 50, 50);
        selectedCharacter = "";
        highlightCharacter = "";
        characterAngle = 0;
        angleOffset = 70;
    }

    public void displayCharacterSelection(Graphics g, Font exoRegular, int screenWidth) {
        g.setColor(Color.WHITE);
        g.setFont(exoRegular);

        Graphics2D g2d = (Graphics2D) g;

        try {
            drawDisplayImage(g2d, "sprites/firewallmage.png", screenWidth, 1, 50);
            drawDisplayImage(g2d, "sprites/byteknight.png", screenWidth, 4, 50);
            drawDisplayImage(g2d, "sprites/hackeragent.png", screenWidth, 7, 50);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawDisplayImage(Graphics2D g2d, String imagePath, int screenWidth, int positionX, int positionY) throws IOException {
        BufferedImage image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        image = rotateImage(image, 90);
        int imageX = (screenWidth - image.getWidth() / imageScale) * positionX / 8;
        int imageY = positionY + (image.getHeight() / imageScale) / 2;
        if (positionX == 1) {
            mageX = imageX + (image.getWidth() / imageScale / 6);
            mageW = image.getWidth() / imageScale * 2/3;
            mageH = image.getHeight() / imageScale;
            mageY = imageY;
        } else if (positionX == 4) {
            knightX = imageX + (image.getWidth() / imageScale / 6);
            knightW = image.getWidth() / imageScale * 2/3;
            knightH = image.getHeight() / imageScale;
            knightY = imageY;
        } else if (positionX == 7) {
            agentX = imageX + (image.getWidth() / imageScale / 6);
            agentW = image.getWidth() / imageScale * 2/3;
            agentH = image.getHeight() / imageScale;
            agentY = imageY;
        }
        g2d.setColor(darkGray);
        g2d.fillRect(imageX + (image.getWidth() / imageScale / 6), imageY, image.getWidth() / imageScale * 2/3, image.getHeight() / imageScale);
        if (highlightCharacter.equals("mage") && positionX == 1) {
            g2d.setColor(Color.WHITE);
        } else if (highlightCharacter.equals("knight") && positionX == 4) {
            g2d.setColor(Color.WHITE);
        } else if (highlightCharacter.equals("agent") && positionX == 7) {
            g2d.setColor(Color.WHITE);
        } else {
            g2d.setColor(Color.BLACK);
        }
        g2d.drawRect(imageX + (image.getWidth() / imageScale / 6), imageY, image.getWidth() / imageScale * 2/3, image.getHeight() / imageScale);
        g2d.drawImage(image.getScaledInstance(image.getWidth() / imageScale, image.getHeight() / imageScale, Image.SCALE_SMOOTH), imageX, imageY, null);
    }

    private BufferedImage rotateImage(BufferedImage image, int degrees) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = newImage.createGraphics();
        g2d.rotate(Math.toRadians(degrees), width / 2, height / 2);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return newImage;
    }

    public boolean handleCharacterSelection(int x, int y, int screenWidth, int screenHeight) {
        if (x >= mageX && x <= mageX + mageW && y >= mageY && y <= mageY + mageH) {
            selectedCharacter = "mage";
            spawnCharacterAtRandomLocation(screenWidth, screenHeight);
            return true;
        } else if (x >= knightX && x <= knightX + knightW && y >= knightY && y <= knightY + knightH) {
            selectedCharacter = "knight";
            spawnCharacterAtRandomLocation(screenWidth, screenHeight);
            return true;
        } else if (x >= agentX && x <= agentX + agentW && y >= agentY && y <= agentY + agentH) {
            selectedCharacter = "agent";
            spawnCharacterAtRandomLocation(screenWidth, screenHeight);
            return true;
        }
        return false;
    }

    public void handleCharacterHover(int x, int y, int screenWidth) {
        if (x >= mageX && x <= mageX + mageW && y >= mageY && y <= mageY + mageH) {
            highlightCharacter = "mage";
        } else if (x >= knightX && x <= knightX + knightW && y >= knightY && y <= knightY + knightH) {
            highlightCharacter = "knight";
        } else if (x >= agentX && x <= agentX + agentW && y >= agentY && y <= agentY + agentH) {
            highlightCharacter = "agent";
        } else {
            highlightCharacter = "";
        }
    }

    public void displayGame(Graphics g, Font exoRegular, int screenWidth, int screenHeight) {
        if(selectedCharacter.equals("mage")) {
            drawCharacterImage((Graphics2D) g, "sprites/firewallmage.png", (screenWidth - mageW) / 2, (screenHeight - mageH) / 2, characterAngle);
        } else if(selectedCharacter.equals("knight")) {
            drawCharacterImage((Graphics2D) g, "sprites/byteknight.png", (screenWidth - knightW) / 2, (screenHeight - knightH) / 2, characterAngle);
        } else if(selectedCharacter.equals("agent")) {
            drawCharacterImage((Graphics2D) g, "sprites/hackeragent.png", (screenWidth - agentW) / 2, (screenHeight - agentH) / 2, characterAngle);
        }
    }

    private void drawCharacterImage(Graphics2D g2d, String imagePath, int positionX, int positionY, int degrees) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            image = rotateImage(image, degrees);
            g2d.drawImage(image.getScaledInstance(image.getWidth() / imageScale, image.getHeight() / imageScale, Image.SCALE_SMOOTH), positionX, positionY, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCharacterAngle(int x, int y, int screenWidth, int screenHeight) {
        characterAngle = (int)Math.toDegrees(Math.atan2(y - screenHeight / 2, x - screenWidth / 2)) + angleOffset;
    }

    public void handleCharacterMovement(int keyCode, int screenWidth, int screenHeight) {
        if(keyCode == 37) {
            relativeX -= 10;
        } else if(keyCode == 38) {
            relativeY -= 10;
        } else if(keyCode == 39) {
            relativeX += 10;
        } else if(keyCode == 40) {
            relativeY += 10;
        }
    }

    public void spawnCharacterAtRandomLocation(int screenWidth, int screenHeight) {
        relativeX = (int)(Math.random() * screenWidth) - screenWidth / 2;
        relativeY = (int)(Math.random() * screenHeight) - screenHeight / 2;
    }

    public int relativeX() {
        return relativeX;
    }

    public int relativeY() {
        return relativeY;
    }

    public String selectedCharacter() {
        return selectedCharacter;
    }

    public int characterAngle() {
        return characterAngle;
    }

    public void displayOtherPlayers(Graphics g, String username, int x, int y, String character, int angle) {
        if(character.equals(selectedCharacter)) {
            return;
        }
        if(character.equals("mage")) {
            drawCharacterImage((Graphics2D) g, "sprites/firewallmage.png", x, y, angle);
        } else if(character.equals("knight")) {
            drawCharacterImage((Graphics2D) g, "sprites/byteknight.png", x, y, angle);
        } else if(character.equals("agent")) {
            drawCharacterImage((Graphics2D) g, "sprites/hackeragent.png", x, y, angle);
        }
    }
}
