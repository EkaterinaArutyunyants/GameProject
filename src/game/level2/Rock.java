package game.level2;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import game.Asset;
import game.AssetFactory;
import org.jbox2d.common.Vec2;

/**
 * Rock class - for rocks at the top
 * moves left
 */
public class Rock extends Asset {
    private static final float yTop = 7f; //vertical for top position
    private static final float halfWidth = 3.5f / 2;
    private static final float scale = 2.2f;
    private static float vx = -7f; //speed

    /**
     * Constructor for rock
     *
     * @param factory    which is creating rock
     * @param halfHeight of rock
     */
    public Rock(AssetFactory factory, float halfHeight) {
        super(factory, new BoxShape(halfWidth, halfHeight));
        addImage(new BodyImage("data/level2/rock.png", halfHeight * scale));
        setPosition(new Vec2(35, yTop + halfHeight));
        setGravityScale(0f); //doesnt fall
        setLinearVelocity(new Vec2(vx, 0)); //moves horizontally
        setAngleDegrees(0f);
        setAngularVelocity(0f);
    }

    /**
     * restoring position of the rock after collision
     */
    public void restoreStateAfterCollision() {
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
    }
}
