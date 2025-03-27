package game.level2;

import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.Asset;
import game.AssetFactory;
import org.jbox2d.common.Vec2;

public class Cactus extends Asset {
    private static final float yGround = -22f;
    private static final float halfWidth = 3.5f / 2;
    private static final float scale = 2.2f;
    private static float vx = -7f;

    public Cactus(AssetFactory factory, float halfHeight) {
        super(factory,new BoxShape(halfWidth, halfHeight));
        addImage(new BodyImage("data/level2/cactusDown.png", halfHeight * scale));
        setPosition(new Vec2(35, yGround+halfHeight));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
        setAlwaysOutline(true);
    }

    public void restoreStateAfterCollision() {
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
    }
}
