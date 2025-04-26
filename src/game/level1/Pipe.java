package game.level1;

import city.cs.engine.*;
import game.Asset;
import game.AssetFactory;
import org.jbox2d.common.Vec2;

/**
 * pipe class - vertical pipes for level 1
 * there are holes between pipes for bird to fly through
 */
public class Pipe extends Asset {
    //max height range for holes
    private static final float yMax = 19f;
    //half width for pipe
    private static final float halfWidth = 3.5f / 2;
    private static final float scale = 2.5f;
    //speed, moves left
    private static float vx = -7f;

    /**
     * Constructor for pipe
     * @param factory creating pipe
     * @param holeUp vertical pos of holes between pipes
     */
    public Pipe(AssetFactory factory, float holeUp) {
        super(factory); //parent

        //dif size of holes between pipes
        float halfHeightUp = (yMax - holeUp) / 2f;
        float yCenterUp = (yMax + holeUp) / 2f;
        Vec2 centerUp = new Vec2(0, yCenterUp);
        Vec2 centerDown = new Vec2(0, -yCenterUp);

        //REQ: multiple fixtures
        new SolidFixture(this, new BoxShape(halfWidth, halfHeightUp * 1.2f, centerUp));
        new SolidFixture(this, new BoxShape(halfWidth, halfHeightUp * 1.2f, centerDown));

        AttachedImage imageUp = addImage(new BodyImage("data/level1/pipeUp.png", halfHeightUp));
        imageUp.setOffset(centerUp);
        imageUp.setScale(scale);
        AttachedImage imageDown = addImage(new BodyImage("data/level1/pipeDown.png", halfHeightUp));
        imageDown.setOffset(centerDown);
        imageDown.setScale(scale);

        //REQ: enemies that move on their own
        setPosition(new Vec2(35, 0));
        //REQ: dif physical property
        setGravityScale(0f);
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
    }

    /**
     *  after collision restoring velocity
     */
    public void restoreStateAfterCollision() {
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
    }
}
