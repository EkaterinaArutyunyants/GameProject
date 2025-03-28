package game.level3;

import city.cs.engine.Body;
import city.cs.engine.CollisionEvent;
import city.cs.engine.SensorEvent;
import game.AssetFactory;
import game.BasicLevel;
import game.BirdGame;
import game.level2.Bomb;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

//REQ: extensions: inheritance + encapsulation (superclass, subclass)
public class Level3 extends BasicLevel {
    private final Bird bird;
    private final Set<Body> hittedRockets = new HashSet<>();

    public Level3(BirdGame game, String name, int targetScore) {
        super(game, name, targetScore); //parent
        bird = new Bird(this);
        bird.addCollisionListener(this);
        background = new ImageIcon("data/level3/moonBackground.jpg").getImage();

        //FACTORIES:

        factories.add(new RocketFactory(this, 4000));
        factories.add(new TeleportFactory(this, 10000, 10f,15f));
        factories.add(new AssetFactory(this, 17000, 3) {
            @Override
            protected void createAsset() {
                new Alien(this);
            }
        });

        //KEYS:

        birdController = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
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
        if (collisionEvent.getOtherBody() instanceof Rocket rocket) {
            rocket.restoreStateAfterCollision();
            bird.setStateAfterCollisionWithRocket();
            if (!hittedRockets.contains(rocket)) {
                hittedRockets.add(rocket);
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
        if ((sensorEvent.getSensor().getBody() instanceof SensorRocket rocket) && (sensorEvent.getContactBody() instanceof game.level1.Bird)) {
            if (!hittedRockets.contains(rocket)) {
                hittedRockets.add(rocket);
                bird.decHealth();
                health--;
                if (health <= 0) complete();
            }
        } else {
            super.beginContact(sensorEvent);
        }
    }
}

