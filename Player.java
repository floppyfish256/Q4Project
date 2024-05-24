public class Player {
    private String name;
    private int money;

    public Player(String name) {
        this.name = name;
        money = 0;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        money += amount;
    }
}