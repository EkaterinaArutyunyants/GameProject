package game.level3;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.CircleShape;
import game.Asset;
import game.AssetFactory;
import org.jbox2d.common.Vec2;

/**
 * Planet class - planet objects at the top of the screen
 * moves left
 */
public class Planet extends Asset {
    private static final float yTop = 7f;
    private static final float halfWidth = 10f / 2;
    private static final float scale = 2.2f;
    private static float vx = -7f;

    /**
     * Constructor for planet
     * @param factory created this planet
     * @param halfHeight of planet
     */
    public Planet(AssetFactory factory, float halfHeight) {
        super(factory,new CircleShape(halfWidth));
        addImage(new BodyImage("data/level3/planet.png", halfHeight * scale));
        setPosition(new Vec2(35, yTop +halfHeight));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
    }

    /**
     * restoring position after collision
     */
    public void restoreStateAfterCollision() {
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
    }
}
