package game.level3;

import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.Asset;
import game.AssetFactory;
import org.jbox2d.common.Vec2;

public class Rocket extends Asset {
    private static final float yMax = 19f;
    private static final float halfWidth = 3.5f / 2;
    private static final float scale = 2.5f;
    private static float vx = -7f;

    public Rocket(AssetFactory factory, float holeUp) {
        super(factory); //parent

        //dif size of holes between pipes
        float halfHeightUp = (yMax - holeUp) / 2f;
        float yCenterUp = (yMax + holeUp) / 2f;
        Vec2 centerUp = new Vec2(0, yCenterUp);
        Vec2 centerDown = new Vec2(0, -yCenterUp);

        //REQ: multiple fixtures
        new SolidFixture(this, new BoxShape(halfWidth, halfHeightUp * 1.2f, centerUp));
        new SolidFixture(this, new BoxShape(halfWidth, halfHeightUp * 1.2f, centerDown));

        AttachedImage imageUp = addImage(new BodyImage("data/level3/rocketUp.png", halfHeightUp * 1.1f));
        imageUp.setOffset(centerUp);
        imageUp.setScale(scale);
        AttachedImage imageDown = addImage(new BodyImage("data/level3/rocketDown.png", halfHeightUp * 1.1f));
        imageDown.setOffset(centerDown);
        imageDown.setScale(scale);

        //REQ: enemies that move on their own
        setPosition(new Vec2(35, 0));
        //REQ: dif physical property
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
