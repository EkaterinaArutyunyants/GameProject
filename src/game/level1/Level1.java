package game.level1;

import city.cs.engine.Body;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.BasicLevel;
import game.Bird;
import game.BirdGame;
import org.jbox2d.common.Vec2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

//REQ: extensions: inheritance + encapsulation (superclass, subclass)
public class Level1 extends BasicLevel implements CollisionListener {
    private final Bird bird;
    private final Set<Body> hittedPipes = new HashSet<>();

    public Level1(BirdGame game, String name, int targetScore) {
        super(game, name, targetScore); //parent
        bird = new Bird(this, 10);
        bird.addCollisionListener(this);
        factories.add(new PipeFactory(this, 4000));
        birdController = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE: //class KeyEvent
                        bird.setLinearVelocity(new Vec2(0f, 5f));
                        //switch images when press space
                        bird.flyUp();
                        break;
                    case KeyEvent.VK_SHIFT:
                        bird.setLinearVelocity(new Vec2(15f, 0f));
                        break;
                    default:
                        System.out.println("Unhandled " + e);
                }
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    //switch images when release space
                    bird.flyDown();
                } else {
                    System.out.println("Unhandled key " + e);
                }
            }
        };

    }

    @Override
    public void collide(CollisionEvent collisionEvent) {
        Body body = collisionEvent.getOtherBody();
        if (body instanceof Pipe pipe) {
            pipe.restoreStateAfterCollision();
            bird.setStateAfterCollisionWithPipe();
            if (!hittedPipes.contains(body)) {
                hittedPipes.add(body);
                bird.decHealth();
                health--;
                if (health <= 0) complete();
            }
        } else {
            super.collide(collisionEvent);
        }
    }


}

