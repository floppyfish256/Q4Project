import java.awt.Color;
import java.awt.Graphics;

public class CenterTower {
    private int x;
    private int y;
    private int health;
    private Color green;
    private Color gold;
    private Color darkGreen;

    public CenterTower(int x, int y) {
        this.x = x;
        this.y = y;
        this.health = 100;
        green = new Color(13, 98, 91);
        gold = new Color(255,234,180);
        darkGreen = new Color(26, 61, 70);
    }

    public void drawMe(Graphics g) {
        g.setColor(green);
        g.fillRect(x - 50, y - 50, 100, 100);
        g.setColor(gold);
        g.fillRect(x - 40, y - 40, 80, 80);
        g.setColor(darkGreen);
        g.fillRect(x - 20, y - 20, 40, 40);
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public int getHealth() {
        return health;
    }
}