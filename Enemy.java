import java.awt.*;

public class Enemy {
    private String enemyType;
    private int x;
    private int y;
    private int health;

    public Enemy(String enemyType, int x, int y) {
        this.enemyType = enemyType;
        this.x = x;
        this.y = y;
        this.health = 100;
    }

    public void drawMe(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x-25, y-25, 50, 50);
        if(health > 0) {
            moveTowardsCenter();
        }
    }

    public void moveTowardsCenter() {
        if(x < 400) {
            x += 5;
        } else if(x > 400) {
            x -= 5;
        }
        if(y < 300) {
            y += 5;
        } else if(y > 300) {
            y -= 5;
        }
    }

    public String getEnemyType() {
        return enemyType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if(health < 0) health = 0;
    }
}