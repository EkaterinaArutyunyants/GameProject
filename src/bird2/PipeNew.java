package bird2;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class PipeNew extends DynamicBody {
    private static final float yMax = 19f, halfWidth = 3.5f;

    public PipeNew(float holeUp, World world){
        super(world);

        float halfHeightUp = (yMax-holeUp)/2f;
        float yCenterUp = (yMax+holeUp)/2f;
        Vec2 centerUp = new Vec2(0,yCenterUp);
        Vec2 centerDown = new Vec2(0,-yCenterUp);

        new SolidFixture(this,new BoxShape(halfWidth, halfHeightUp, centerUp));
        new SolidFixture(this,new BoxShape(halfWidth, halfHeightUp,centerDown));

        AttachedImage imageUp = addImage(new BodyImage("data/pipeUp.png",halfHeightUp));
        imageUp.setOffset(centerUp);
        imageUp.setScale(2f);
        AttachedImage imageDown = addImage(new BodyImage("data/pipeDown.png",halfHeightUp));
        imageDown.setOffset(centerDown);

        setPosition(new Vec2(35,0));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(-7,0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
        setName("pipe");
        setAlwaysOutline(true);
    }
}
