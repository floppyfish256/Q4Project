import java.util.TimerTask;

public class EnemySpawnTask implements Runnable {
    private Manager manager;

    public EnemySpawnTask(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        // Logic for enemy spawning
        int x = (int) (Math.random() * 800 - 400);
        int y = (int) (Math.sqrt(400 * 400 - x * x) * (Math.random() < 0.5 ? -1 : 1));
        System.out.println("Spawning enemy at: " + x + ", " + y);
        String spawnMessage = "spawn:enemy_type:" + x + ":" + y;
        manager.broadcastMessage(spawnMessage);
    }
}
