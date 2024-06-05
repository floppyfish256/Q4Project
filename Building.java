import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Building implements Serializable {
    // Buildings player can place on the map, shoots at enemies
    private int x;
    private int y;
    private int damage;
    private int range;
    private int time;

    public Building(int x, int y) {
        this.x = x;
        this.y = y;
        this.damage = 10;
        this.range = 50;
        this.time = 0;
    }

    public void drawMe(Graphics g) {
        if(time > 8) {
            return;
        }
        time++;
        g.setColor(Color.BLUE);
        g.fillRect(x-25, y-25, 50, 50);
    }

    public boolean shoot(Enemy enemy) {
        double distance = Math.sqrt(Math.pow(enemy.getX() - x, 2) + Math.pow(enemy.getY() - y, 2));
        if(distance < range) {
            if(enemy.getHealth() <= 0) {
                return false;
            }
            enemy.takeDamage(damage);

            if(enemy.getHealth() <= 0) {
                enemy.disappear();
                enemy = null;
                return true;
            }
        }
        return false;
    }

    public int time() {
        return time;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTime() {
        return time;
    }
}