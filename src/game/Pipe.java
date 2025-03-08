package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Pipe extends DynamicBody {
    private static final float yMax = 19f;
    private static final float halfWidth = 3.5f / 2;
    private static final float scale = 2.5f;
    private static float vy = -7f;

    public Pipe(float holeUp, World world) {
        super(world);

        float halfHeightUp = (yMax - holeUp) / 2f;
        float yCenterUp = (yMax + holeUp) / 2f;
        Vec2 centerUp = new Vec2(0, yCenterUp);
        Vec2 centerDown = new Vec2(0, -yCenterUp);

        new SolidFixture(this, new BoxShape(halfWidth, halfHeightUp * 1.2f, centerUp));
        new SolidFixture(this, new BoxShape(halfWidth, halfHeightUp * 1.2f, centerDown));

        AttachedImage imageUp = addImage(new BodyImage("data/pipeUp.png", halfHeightUp));
        imageUp.setOffset(centerUp);
        imageUp.setScale(scale);
        AttachedImage imageDown = addImage(new BodyImage("data/pipeDown.png", halfHeightUp));
        imageDown.setOffset(centerDown);
        imageDown.setScale(scale);

        setPosition(new Vec2(35, 0));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(vy, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
    }

    public void restoreStateAfterCollision() {
        setLinearVelocity(new Vec2(vy, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
    }
}
