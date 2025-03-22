package game.level2;

import city.cs.engine.*;
import game.Asset;
import game.AssetFactory;
import org.jbox2d.common.Vec2;

import java.util.Random;

public class Cactus extends Asset {
    private static float vx = -7f;
    private static final Random random = new Random();
    private static final float minHalfHeight = 6f;
    private static final float maxHalfHeight = 11f;
    private static final float halfWidth = 3f;

    public Cactus(AssetFactory factory) {
        super(factory);
        final float halfHeight = minHalfHeight + random.nextFloat() * (maxHalfHeight - minHalfHeight);
        final BodyImage image = new BodyImage("data/level2/cactus.png", halfHeight * 2);
        //TODO: replace boxShape with polygon shape
        new SolidFixture(this,new BoxShape(halfWidth, halfHeight));
        addImage(image);

        setPosition(new Vec2(30, -13));
        setGravityScale(0f);
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
