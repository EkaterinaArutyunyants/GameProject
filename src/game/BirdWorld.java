package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class BirdWorld extends World {
    private Bird bird;
    private static final BodyImage birdImage2 = new BodyImage("data/bird2.png", 4);
    private final PipeFactory pipeFactory = new PipeFactory(this);
    private final HeartFactory heartFactory = new HeartFactory(this);
    private final CoinFactory coinFactory = new CoinFactory(this);
    private final Set<Body> hittedPipes = new HashSet<>();
    private final KeyAdapter keyHandler = new KeyAdapter() {
        //функция кей релисд
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode(); //method getKeyCode()
            switch (key) {
                case KeyEvent.VK_SPACE: //class KeyEvent
                    bird.setLinearVelocity(new Vec2(0f, 5f));
                    bird.addImage(birdImage2);
                    break;
                case KeyEvent.VK_SHIFT:
                    bird.setLinearVelocity(new Vec2(15f, 0f));
                default:
                    System.out.println("Unsupported key keyReleased " + e);
            }
        }
    };

    public BirdWorld() {
        bird = new Bird(this);
        bird.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent collisionEvent) {
                Body body = collisionEvent.getOtherBody();
                if (body instanceof Coin) {
                    body.destroy();
                    coinFactory.moneyCounter--;
                    bird.incCoins(((Coin) body).getCoinAmount());
                } else if (body instanceof Heart) {
                    body.destroy();
                    heartFactory.heartCounter--;
                    bird.incHealth();
                } else if (body instanceof Pipe) {
                    //restore linear and angle velocity of pipe
                    ((Pipe) body).restoreStateAfterCollision();
                    bird.restoreStateAfterCollision();
                    if (!hittedPipes.contains(body)) {
                        hittedPipes.add(body);
                        bird.decHealth();
                    }
                } else {
                    System.out.println("Unexpected collisionEvent " + collisionEvent);
                }
            }
        });

        //pipes, coins, hearts
        addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent stepEvent) {
                pipeFactory.createNewPipeIfNeeded();
                heartFactory.createNewHeartIfNeeded();
                coinFactory.createNewCoinIfNeeded();
            }

            @Override
            public void postStep(StepEvent stepEvent) {

            }
        });
    }

    public Bird getBird() {
        return bird;
    }

    public KeyAdapter getKeyHandler() {
        return keyHandler;
    }
}

