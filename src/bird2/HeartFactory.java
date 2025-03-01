package bird2;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.World;

public class HeartFactory {
    private int heartCounter = 0;
    private int maxHeartCounter = 5;
    private long nextHeartTime;
    private World world;
    private Bird bird;

    public HeartFactory(Bird bird, World world) {
        this.bird = bird;
        this.world = world;
    }

    public void createNewHeartIfNeeded(){
        if (heartCounter < maxHeartCounter && nextHeartTime < System.currentTimeMillis()) {
            Heart heart = new Heart(world);
            heart.addCollisionListener(new CollisionListener() {
                @Override
                public void collide(CollisionEvent collisionEvent) {
                    if ("bird".equals(collisionEvent.getOtherBody().getName())) {
                        heart.destroy();
                        heartCounter--;
                        bird.addHealth();
                    }
                }
            });
            heartCounter++;
            nextHeartTime = System.currentTimeMillis() + 8000;
        }
    }


}
