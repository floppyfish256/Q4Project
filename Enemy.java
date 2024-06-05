import java.awt.*;
import java.io.Serializable;

public class Enemy implements Serializable {
    private String enemyType;
    private int x;
    private int y;
    private int health;
    private boolean alive;

    public Enemy(String enemyType, int x, int y) {
        this.enemyType = enemyType;
        this.x = x;
        this.y = y;
        this.health = 100;
        this.alive = true;
    }

    public void drawMe(Graphics g) {
        if(!alive) {
            return;
        }
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

    public int getHealth() {
        return health;
    }

    public void disappear() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}