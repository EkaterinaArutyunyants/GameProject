package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.util.Random;

/**
 * Heart class - object heart is creating in the right side of the screen
 * with random vertical position and moving to the left by linear velocity.
 * player can collect hearts to increase health.
 */
public class Heart extends Asset {
    //heart shape
    private static final Shape shape = new PolygonShape(-0.369f, 0.867f, -0.934f, 0.233f, 0.0f, -0.86f, 0.926f, 0.224f, 0.369f, 0.859f);
    //heart img
    private static final BodyImage image = new BodyImage("data/heart.png", 2);
    //for generating random vertical pos
    private static final Random random = new Random();
    //max vertical range
    private static final float y_max = 15f;

    /**
     * Constructor for heart
     * @param factory (AssetFactoru) creating hearts
     */
    public Heart(AssetFactory factory) {
        super(factory, shape);
        addImage(image);
        //randomness by vertical
        setPosition(new Vec2(30, (random.nextFloat() - 0.5f) * y_max));
        //doesnt fall
        setGravityScale(0f);
        //move left
        setLinearVelocity(new Vec2(-7, 0));
    }
}