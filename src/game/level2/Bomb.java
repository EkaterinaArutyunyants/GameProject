package game.level2;

import city.cs.engine.BodyImage;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import game.Asset;
import game.AssetFactory;
import org.jbox2d.common.Vec2;

import java.util.Random;

/**
 * Bomb class - bomb is enemy for the bird
 * appearance in the screen at random vertical position
 */
public class Bomb extends Asset {
    private static final Shape shape = new PolygonShape(-1.13f, -0.14f, 0.18f, 1.5f, 1.1f, -0.13f, 1.13f, -0.86f, 0.23f, -1.61f, -0.22f, -1.62f, -1.14f, -0.83f);
    private static final BodyImage image = new BodyImage("data/level2/bomb.gif", 4);
    private static final Random random = new Random();
    private static final float y_max = 15f;

    /**
     * Constructor for bomb
     *
     * @param factory created this bomb
     */
    public Bomb(AssetFactory factory) {
        super(factory, shape);
        addImage(image);
        //randomness by vertical
        setPosition(new Vec2(30, (random.nextFloat() - 0.5f) * y_max));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(-7, 0));
    }
}