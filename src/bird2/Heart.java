package bird2;

import city.cs.engine.BodyImage;
import city.cs.engine.DynamicBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public class Heart extends DynamicBody {
    public Heart(World world) {
        super(world);
        setPosition(new Vec2(30,0));
        addImage(new BodyImage("data/heart.png", 2));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(-7,0));
        setName("heart");
    }
}
