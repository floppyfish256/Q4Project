import java.awt.Color;
import java.awt.Graphics;

public class CenterTower {
    private int x;
    private int y;
    private Color green;
    private Color gold;
    private Color darkGreen;

    public CenterTower(int x, int y) {
        this.x = x;
        this.y = y;
        green = new Color(13, 98, 91);
        gold = new Color(255,234,180);
        darkGreen = new Color(26, 61, 70);
    }

    public void drawMe(Graphics g) {

    }
}