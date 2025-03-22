package game.level2;

import city.cs.engine.Body;
import city.cs.engine.CollisionEvent;
import city.cs.engine.SensorEvent;
import game.BasicLevel;
import game.Bird;
import game.BirdGame;
import game.level1.SensorPipe;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

//REQ: extensions: inheritance + encapsulation (superclass, subclass)
public class Level2 extends BasicLevel {
    private final Bird2 bird;
    private final Set<Body> hittedPipes = new HashSet<>();

    public Level2(BirdGame game, String name, int targetScore) {
        super(game, name, targetScore); //parent
        bird = new Bird2(this);
        bird.addCollisionListener(this);
        background = new ImageIcon("data/level2/dessertBackground.jpeg").getImage();
        factories.add(new CactusFactory(this, 4000));
        birdController = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        bird.setLinearVelocity(new Vec2(0f, 7f));
                        //switch images when press space
                        bird.flyUp();
                        break;
                    case KeyEvent.VK_A:
                        bird.setLinearVelocity(new Vec2(-7f, 0f));
                        break;
                    case KeyEvent.VK_D:
                        bird.setLinearVelocity(new Vec2(7f, 0f));
                        break;
                    case KeyEvent.VK_S:
                        bird.setLinearVelocity(new Vec2(0f, -7f));
                        break;
                    case KeyEvent.VK_SHIFT:
                        bird.setLinearVelocity(new Vec2(15f, 0f));
                        break;
                    default:
                        System.out.println("Unhandled " + e);
                }
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
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
        if (collisionEvent.getOtherBody() instanceof Cactus cactus) {
            cactus.restoreStateAfterCollision();
            bird.setStateAfterCollisionWithPipe();
            if (!hittedPipes.contains(cactus)) {
                hittedPipes.add(cactus);
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
}

