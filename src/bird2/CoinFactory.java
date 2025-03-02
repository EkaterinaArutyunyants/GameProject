package bird2;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.World;

public class CoinFactory {
    private int moneyCounter = 0;
    private int maxMoneyCounter = 5;
    private long nextMoneyTime;
    private World world;
    private Bird bird;

    public CoinFactory(Bird bird, World world) {
        this.bird = bird;
        this.world = world;
    }

    public void createNewCoinIfNeeded() {
        if (moneyCounter < maxMoneyCounter && nextMoneyTime < System.currentTimeMillis()) {
            Coin coin = new Coin(world);
            coin.addCollisionListener(new CollisionListener() {
                @Override
                public void collide(CollisionEvent collisionEvent) {
                    if ("bird".equals(collisionEvent.getOtherBody().getName())) {
                        coin.destroy();
                        moneyCounter--;
                        bird.coins++;
                    }
                }
            });
            moneyCounter++;
            nextMoneyTime = System.currentTimeMillis() + 7000;
        }
    }
}
