public class GameLoopTask implements Runnable {
    private GameLogic gameLogic;

    public GameLoopTask(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    @Override
    public void run() {
        gameLogic.update();
    }
}
