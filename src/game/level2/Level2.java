package game.level2;

import city.cs.engine.Body;
import city.cs.engine.BodyImage;
import city.cs.engine.CollisionEvent;
import city.cs.engine.SensorEvent;
import game.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

//REQ: extensions: inheritance + encapsulation (superclass, subclass)
public class Level2 extends BasicLevel {
    private final Bird bird;
    private static final BodyImage image = new BodyImage("data/level2/dessertBird.png", 4);
    private static final BodyImage imageBirdFlyUp = new BodyImage("data/level2/dessertBirdFlyUp.png", 4);

    public Level2(BirdGame game, String name, int targetScore) {
        super(game, name, targetScore); //parent
        bird = new Bird(this, image, imageBirdFlyUp);
        bird.addCollisionListener(this);
        background = new ImageIcon("data/level2/dessertBackground.jpeg").getImage();

        //FACTORIES:
        factories.add(new CactusFactory(this, 4000,6f,12f));
        factories.add(new RockFactory(this, 4000,6f,12f));
        factories.add(new AssetFactory(this, 17000, 3) {
            @Override
            protected void createAsset() {
                new Bomb(this);
            }
        });
        factories.add(new AssetFactory(this, 15000, 3) {
            @Override
            protected void createAsset() {
                new Cloud(this);
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

    //COLLISION
    @Override
    public void collide(CollisionEvent collisionEvent) {
        if (collisionEvent.getOtherBody() instanceof Cactus cactus) {
            cactus.restoreStateAfterCollision();
            bird.setStateAfterCollision();
            if (!hittedBodies.contains(cactus)) {
                hittedBodies.add(cactus);
                bird.decHealth();
                health--;
                if (health <= 0) complete();
            }
        } else if (collisionEvent.getOtherBody() instanceof Rock rock) {
            rock.restoreStateAfterCollision();
            bird.setStateAfterCollision();
            if (!hittedBodies.contains(rock)) {
                hittedBodies.add(rock);
                bird.decHealth();
                health--;
                if (health <= 0) complete();
            }
        } else if (collisionEvent.getOtherBody() instanceof Bomb bomb) {
            health--;
            bomb.destroy();
        } else {
            super.collide(collisionEvent);
        }
    }

    @Override
    public void beginContact(SensorEvent sensorEvent) {
        if ((sensorEvent.getSensor().getBody() instanceof SensorCactus cactus) && (sensorEvent.getContactBody() instanceof game.Bird)) {
            if (!hittedBodies.contains(cactus)) {
                hittedBodies.add(cactus);
                bird.decHealth();
                health--;
                if (health <= 0) complete();
            }
        } else {
            super.beginContact(sensorEvent);
        }
    }
}

