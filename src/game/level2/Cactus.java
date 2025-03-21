package game.level2;

import city.cs.engine.*;
import game.Asset;
import game.AssetFactory;
import org.jbox2d.common.Vec2;

public class Cactus extends Asset {
    private static float vx = -7f;
    private static final Shape shape = new BoxShape(3.5f,5);
    private static final BodyImage image = new BodyImage("data/level2/cactus.png", 4);

    public Cactus(AssetFactory factory) {
        super(factory, shape); //parent
        addImage(image);
        //REQ: enemies that move on their own
        setPosition(new Vec2(35, 0));
        //REQ: dif physical property
        setGravityScale(0f);
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
    }

    //after collision restoring velocity
    public void restoreStateAfterCollision() {
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
    }
}
