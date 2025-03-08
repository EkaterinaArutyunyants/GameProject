package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class BirdWorld extends World {
    private final Bird bird;

    private final PipeFactory pipeFactory = new PipeFactory(this);
    private final HeartFactory heartFactory = new HeartFactory(this);
    private final CoinFactory coinFactory = new CoinFactory(this);
    private final Set<Body> hittedPipes = new HashSet<>();

    private final KeyAdapter birdController = new KeyAdapter() {
        //функция кей релисд
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE: //class KeyEvent
                    bird.setLinearVelocity(new Vec2(0f, 5f));
                    //switch images when press space
                    bird.removeAllImages();
                    bird.addImage(Bird.image2);
                    break;
                case KeyEvent.VK_SHIFT:
                    bird.setLinearVelocity(new Vec2(15f, 0f));
                default:
                    System.out.println("Unhandled key " + e);
            }
        }
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    //switch images when release space
                    bird.removeAllImages();
                    bird.addImage(Bird.image);
                default:
                    System.out.println("Unhandled key " + e);
            }
        }
    };

    public BirdWorld() {
        super();
        bird = new Bird(this);
        bird.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent collisionEvent) {
                Body body = collisionEvent.getOtherBody();
                if (body instanceof Coin) {
                    bird.incCoins(((Coin) body).getCoinAmount());
                    coinFactory.decCount();
                    body.destroy();
                } else if (body instanceof Heart) {
                    bird.incHealth();
                    heartFactory.decCount();
                    body.destroy();
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
    }

    public Bird getBird() {
        return bird;
    }

    public KeyAdapter getBirdController() {
        return birdController;
    }
}

