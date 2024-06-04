public class GameLogic {
    private CenterTower tower;
    private MyArrayList<Enemy> enemies;

    public GameLogic() {
        tower = new CenterTower(400, 300);
        enemies = new MyArrayList<>();
    }

    public void update() {
        for (Enemy enemy : enemies) {
            enemy.moveTowardsCenter();
            if (isAtCenterTower(enemy)) {
                tower.takeDamage(10); // Apply damage to the tower
                // Remove or reset the enemy, as it has reached the tower
                // enemies.remove(enemy); // Uncomment if you want to remove the enemy
            }
        }
        // Update the tower's health bar

    }

    public void handlePlayerAction(String action) {
        // Process player action (e.g. selected a building, placed a building, etc.)
    }

    public boolean isAtCenterTower(Enemy enemy) {
        int centerX = 400;
        int centerY = 300;
        int radius = 50; // Adjust this value based on the tower's size
        return Math.sqrt(Math.pow(enemy.getX() - centerX, 2) + Math.pow(enemy.getY() - centerY, 2)) <= radius;
    }
}