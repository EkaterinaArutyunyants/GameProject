package game.level3;

import city.cs.engine.BodyImage;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import game.Asset;
import game.AssetFactory;
import org.jbox2d.common.Vec2;

import java.util.Random;

public class BlackHole extends Asset {
    private static final Shape shape = new PolygonShape(-0.369f, 0.867f, -0.934f, 0.233f, 0.0f, -0.86f, 0.926f, 0.224f, 0.369f, 0.859f);
    private static final BodyImage image = new BodyImage("data/level3/blackHole.png", 4);
//    private static final Random random = new Random();
//    private static final float y_max = 15f;

    public BlackHole(AssetFactory factory) {
        super(factory, shape);
        addImage(image);
        //randomness by vertical
//        setPosition(new Vec2(30, (random.nextFloat() - 0.5f) * y_max));
        setPosition(new Vec2(20, 10));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(-7, 0));
    }
}