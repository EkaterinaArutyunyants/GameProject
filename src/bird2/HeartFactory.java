package bird2;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.World;

public class HeartFactory {
    public int heartCounter = 0;
    private int maxHeartCounter = 5;
    private long nextHeartTime;
    private World world;

    public HeartFactory(World world) {
        this.world = world;
    }

    public void createNewHeartIfNeeded(){
        if (heartCounter < maxHeartCounter && nextHeartTime < System.currentTimeMillis()) {
            Heart heart = new Heart(world);
            heartCounter++;
            nextHeartTime = System.currentTimeMillis() + 8000;
        }
    }


}
