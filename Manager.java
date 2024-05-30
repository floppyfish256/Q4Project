public class Manager {
    private MyArrayList<ServerThread> serverThreads;
    private int playersReady;
    private int playersInGame;

    public Manager() {
        serverThreads = new MyArrayList<>();
        playersReady = 0;
        playersInGame = 0;
    }

    public void addServerThread(ServerThread serverThread) {
        serverThreads.add(serverThread);
    }

    public void broadcastMessage(String message) {
        for (ServerThread serverThread : serverThreads) {
            serverThread.send(message);
        }
    }

    public void incrementPlayersReady() {
        playersReady++;
    }

    public int getPlayersReady() {
        return playersReady;
    }

    public void incrementPlayersInGame() {
        playersInGame++;
    }

    public int getPlayersInGame() {
        return playersInGame;
    }
}