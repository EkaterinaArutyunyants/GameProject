package game.level2;

import city.cs.engine.*;
import game.Asset;
import game.AssetFactory;
import org.jbox2d.common.Vec2;

import java.util.Random;

public class Cactus extends Asset {
    private static float vx = -7f;
    private static final BodyImage image = new BodyImage("data/level2/cactus.png", 19);
    private static final Random random = new Random();
    private static final float minHeight = 6f;
    private static final float maxHeight = 14f;
    private static final float width = 3f;
    private static final float height = minHeight + random.nextFloat() * (maxHeight - minHeight);
    private static final float yPos = -13f + height;

    public Cactus(AssetFactory factory) {
        super(factory, new BoxShape(width, height)); //parent
        addImage(image);

        //randomness height
        setPosition(new Vec2(30, yPos));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(-7, 0));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
        setAlwaysOutline(true);
    }

    //after collision restoring velocity
    public void restoreStateAfterCollision() {
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
    }
}
