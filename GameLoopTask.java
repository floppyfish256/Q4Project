import java.util.TimerTask;

public class GameLoopTask implements Runnable {
    private GameLogic gameLogic;

    public GameLoopTask(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public void run() {
        gameLogic.update();
    }
}
