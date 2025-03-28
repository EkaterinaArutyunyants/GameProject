package game.level1;

import city.cs.engine.Body;
import city.cs.engine.BodyImage;
import city.cs.engine.CollisionEvent;
import city.cs.engine.SensorEvent;
import game.BasicLevel;
import game.Bird;
import game.BirdGame;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

//REQ: extensions: inheritance + encapsulation (superclass, subclass)
public class Level1 extends BasicLevel {
    private final Bird bird;
    private final Set<Body> hittedPipes = new HashSet<>();
    private static final BodyImage image = new BodyImage("data/level1/bird.png", 4);
    private static final BodyImage imageBirdFlyUp = new BodyImage("data/level1/birdFlyUp.png", 4);

    public Level1(BirdGame game, String name, int targetScore) {
        super(game, name, targetScore); //parent
        bird = new Bird(this, image, imageBirdFlyUp);
        bird.addCollisionListener(this);
        background = new ImageIcon("data/level1/sky.jpg").getImage();

        factories.add(new PipeFactory(this, 4000));

        //KEYS:
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

    //COLLISION:
    @Override
    public void collide(CollisionEvent collisionEvent) {
        if (collisionEvent.getOtherBody() instanceof Pipe pipe) {
            pipe.restoreStateAfterCollision();
            bird.setStateAfterCollision();
            if (!hittedPipes.contains(pipe)) {
                hittedPipes.add(pipe);
                bird.decHealth();
                health--;
                if (health <= 0) complete();
            }
        } else {
            super.collide(collisionEvent);
        }
    }

    @Override
    public void beginContact(SensorEvent sensorEvent) {
        if ((sensorEvent.getSensor().getBody() instanceof SensorPipe pipe) && (sensorEvent.getContactBody() instanceof Bird)) {
            if (!hittedPipes.contains(pipe)) {
                hittedPipes.add(pipe);
                bird.decHealth();
                health--;
                if (health <= 0) complete();
            }
        } else {
            super.beginContact(sensorEvent);
        }
    }

    @Override
    public void endContact(SensorEvent sensorEvent) {
        System.out.println("endContact("+sensorEvent+")");
        if (sensorEvent.getSensor().getBody() instanceof Bird && !RIP.equals(sensorEvent.getContactBody())) {
            sensorEvent.getContactBody().destroy();
        } else {
            super.endContact(sensorEvent);
        }
    }

}

