package game.level1;

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

/**
 * Level1 class - level 1 of bird game
 * bird is flying through pipes collecting coins and hearts
 * REQ: extensions: inheritance + encapsulation (superclass, subclass)
 */
public class Level1 extends BasicLevel {
    private final Bird bird;
    //level 1 bird img
    private static final BodyImage image = new BodyImage("data/level1/bird.png", 4);
    private static final BodyImage imageBirdFlyUp = new BodyImage("data/level1/birdFlyUp.png", 4);

    /**
     * Constructor for level1
     * @param game controller
     * @param name of this level
     * @param targetScore score to win level
     */
    public Level1(BirdGame game, String name, int targetScore) {
        super(game, name, targetScore); //parent
        //creating bird character
        bird = new Bird(this, image, imageBirdFlyUp);
        bird.addCollisionListener(this);
        //level 1 background
        background = new ImageIcon("data/level1/sky.jpg").getImage();
        //pipe factory
        factories.add(new PipeFactory(this, 4000));

        //KEYS:
        birdController = new KeyAdapter() {
            /**
             * key pressed
             * @param e the event to be processed
             */
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
     * COLLISION for specified objects of level1
     * common are in {@link game.BasicLevel#collide(CollisionEvent collisionEvent) BasicLevel.collide} method.
     * @param collisionEvent
     */
    @Override
    public void collide(CollisionEvent collisionEvent) {
        if (collisionEvent.getOtherBody() instanceof Pipe pipe) {
            pipe.restoreStateAfterCollision(); //restore pipe state
            bird.setStateAfterCollision(); //restore bird position
            if (!hittedBodies.contains(pipe)) {
                hittedBodies.add(pipe);
                bird.decHealth(); //play crash sound
                health--;
                if (health <= 0) complete();
            }
        } else {
            super.collide(collisionEvent);
        }
    }
}

