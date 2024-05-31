import java.awt.*;

public class Enemy {
    private String enemyType;
    private int x;
    private int y;

    public Enemy(String enemyType, int x, int y) {
        this.enemyType = enemyType;
        this.x = x;
        this.y = y;
    }

    public void drawMe(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x-25, y-25, 50, 50);
    }

    public void moveTowardsCenter() {
        if(x < 400) {
            x++;
        } else if(x > 400) {
            x--;
        }
        if(y < 300) {
            y++;
        } else if(y > 300) {
            y--;
        }
    }

    public String getEnemyType() {
        return enemyType;
    }
}