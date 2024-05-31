public class GameLogic {
    private CenterTower tower;
    private MyList<Enemy> enemies;
    // private MyList<Player> players; // May need this later if we want to show which player placed a tower, etc.

    public GameLogic() {
        tower = new CenterTower(400, 300);
        enemies = new MyList<>();
        // players = new MyList<>();
    }

    public void update() {
        // Update tower and enemies
        System.out.println("Game loop running");
    }

    public void handlePlayerAction(String action) {
        // Process player action (e.g. selected a building, placed a building, etc.)
    }
}