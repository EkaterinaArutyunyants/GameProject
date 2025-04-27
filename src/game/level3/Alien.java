package game.level3;

import city.cs.engine.BodyImage;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import game.Asset;
import game.AssetFactory;
import org.jbox2d.common.Vec2;

import java.util.Random;

/**
 * Alien class - enemy for bird
 * appears at the screen at the random vertical position
 */
public class Alien extends Asset {
    private static final Shape shape = new PolygonShape(-1.18f, 1.32f, 0.96f, 1.32f, 1.59f, -0.77f, -0.09f, -1.09f, -1.77f, -0.74f);
    private static final BodyImage image = new BodyImage("data/level3/alien.gif", 4);
    private static final Random random = new Random();
    private static final float y_max = 15f;

    /**
     * Constructor for alien
     *
     * @param factory created alien
     */
    public Alien(AssetFactory factory) {
        super(factory, shape);
        addImage(image);
        //randomness by vertical
        setPosition(new Vec2(30, (random.nextFloat() - 0.5f) * y_max));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(-7, 0));
    }
}