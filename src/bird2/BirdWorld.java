package bird2;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.util.HashSet;
import java.util.Set;

public class BirdWorld extends World {
    public Bird bird;
    public BirdWorld() {
        bird = new Bird(this);
        HeartFactory heartFactory = new HeartFactory(this);
        CoinFactory coinFactory = new CoinFactory(this);
        Set<Body> hittedPipes = new HashSet<>();

        PipeFactory factory = new PipeFactory(this);

        bird.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent collisionEvent) {
                Body body = collisionEvent.getOtherBody();
                if(body instanceof Coin){
                    body.destroy();
                    coinFactory.moneyCounter--;
                    bird.coins += ((Coin) body).coinAmount;
                } else if(body instanceof Heart){
                    body.destroy();
                    heartFactory.heartCounter--;
                    bird.addHealth();
                } else if(body instanceof PipeNew){
                    //restore linear and angle velocity of pipe
                    ((PipeNew) body).restorePosition();
                    bird.setPosition((new Vec2(bird.getPosition().x - 2f, bird.getPosition().y)));
                    if (!hittedPipes.contains(collisionEvent.getOtherBody())){
                        hittedPipes.add(collisionEvent.getOtherBody());
                        bird.lostHealth();
                    }
                } else {
                    System.out.println("Unsupported collisionEvent " + collisionEvent);
                }
            }
        });
        //pipes, coins, hearts
        StepListener stepListener = new StepListener() {

            @Override
            public void preStep(StepEvent stepEvent) {
                factory.createNewPipeIfNeeded();
                heartFactory.createNewHeartIfNeeded();
                coinFactory.createNewCoinIfNeeded();
            }

            @Override
            public void postStep(StepEvent stepEvent) {

            }
        };

        addStepListener(stepListener);
    }
}

