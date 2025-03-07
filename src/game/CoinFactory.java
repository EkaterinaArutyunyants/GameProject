package game;

import city.cs.engine.World;

public class CoinFactory {
    public int moneyCounter = 0;
    private int maxMoneyCounter = 5;
    private long nextMoneyTime;
    private World world;

    public CoinFactory(World world) {
        this.world = world;
    }

    public void createNewCoinIfNeeded() {
        if (moneyCounter < maxMoneyCounter && nextMoneyTime < System.currentTimeMillis()) {
            Coin coin = new Coin(2, world);
            moneyCounter++;
            nextMoneyTime = System.currentTimeMillis() + 7000;
        }
    }
}
