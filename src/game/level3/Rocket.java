package game.level3;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import game.Asset;
import game.AssetFactory;
import org.jbox2d.common.Vec2;

/**
 * Rocket class - rocket objects at the bottom of the screen
 * moving left
 */
public class Rocket extends Asset {
    private static final float yGround = -22f;
    private static final float halfWidth = 3.5f / 2;
    private static final float scale = 2.2f;
    private static float vx = -7f;

    /**
     * Constructor for rocket
     *
     * @param factory    created rocket
     * @param halfHeight of rocket
     */
    public Rocket(AssetFactory factory, float halfHeight) {
        super(factory, new BoxShape(halfWidth, halfHeight));
        addImage(new BodyImage("data/level3/rocketDown.png", halfHeight * scale));
        setPosition(new Vec2(35, yGround + halfHeight));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
    }

    /**
     * restoring position of rocket after collision
     */
    public void restoreStateAfterCollision() {
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
    }
}
