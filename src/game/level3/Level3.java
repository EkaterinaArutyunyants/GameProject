package game.level3;

import city.cs.engine.BodyImage;
import city.cs.engine.CollisionEvent;
import game.AssetFactory;
import game.BasicLevel;
import game.Bird;
import game.BirdGame;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Level3 class - level 3 of the game
 * new bird, new enemies , new appearance and teleport
 * REQ: extensions: inheritance + encapsulation (superclass, subclass)
 */
public class Level3 extends BasicLevel {
    private final Bird bird;
    //bird img
    private static final BodyImage image = new BodyImage("data/level3/moonBird.png", 4);
    private static final BodyImage imageBirdFlyUp = new BodyImage("data/level3/moonBirdFlyUp.png", 4);

    /**
     * Constructor for level3
     *
     * @param game        controller
     * @param name        for level
     * @param targetScore score to win
     */
    public Level3(BirdGame game, String name, int targetScore) {
        super(game, name, targetScore); //parent
        bird = new Bird(this, image, imageBirdFlyUp);
        bird.addCollisionListener(this);
        background = new ImageIcon("data/level3/moonBackground.jpg").getImage();

        //FACTORIES:
        factories.add(new RocketFactory(this, 4000, 6f, 12f));
        factories.add(new PlanetFactory(this, 4000, 6f, 12f));
        factories.add(new TeleportFactory(this, 10000, 10f, 15f));
        factories.add(new AssetFactory(this, 17000, 3) {
            @Override
            protected void createAsset() {
                new Alien(this);
            }
        });

        //KEYS:

        birdController = new KeyAdapter() {
            /**
             * key pressed
             * @param e the event to be processed
             */
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

            /**
             * key released
             * @param e the event to be processed
             */
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

    /**
     * COLLISION for specified objects of level3
     * common are in {@link game.BasicLevel#collide(CollisionEvent collisionEvent) BasicLevel.collide} method.
     *
     * @param collisionEvent
     */
    @Override
    public void collide(CollisionEvent collisionEvent) {
        if (collisionEvent.getOtherBody() instanceof Rocket rocket) {
            rocket.restoreStateAfterCollision();
            bird.setStateAfterCollision();
            if (!hittedBodies.contains(rocket)) {
                hittedBodies.add(rocket);
                bird.decHealth();
                health--;
                if (health <= 0) complete();
            }
        } else if (collisionEvent.getOtherBody() instanceof Planet planet) {
            planet.restoreStateAfterCollision();
            bird.setStateAfterCollision();
            if (!hittedBodies.contains(planet)) {
                hittedBodies.add(planet);
                bird.decHealth();
                health--;
                if (health <= 0) complete();
            }
        } else if (collisionEvent.getOtherBody() instanceof Alien alien) {
            health--;
            alien.destroy();
        } else {
            super.collide(collisionEvent);
        }
    }
}

