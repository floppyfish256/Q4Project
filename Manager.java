public class Manager {
    private MyArrayList<ServerThread> serverThreads;
    private int playersReady;

    public Manager() {
        serverThreads = new MyArrayList<>();
        playersReady = 0;
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
}