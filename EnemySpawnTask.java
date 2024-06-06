public class EnemySpawnTask implements Runnable {
    private Manager manager;

    public EnemySpawnTask(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        int centerX = 400;
        int centerY = 300;
        int radius = 400;
        
        double angle = Math.random() * 2 * Math.PI;
        int x = (int) (centerX + radius * Math.cos(angle));
        int y = (int) (centerY + radius * Math.sin(angle));
        String spawnMessage = "spawn:enemy_type:" + x + ":" + y;
        manager.broadcastMessage(spawnMessage);
    }
}
