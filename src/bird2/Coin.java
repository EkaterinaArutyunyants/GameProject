package bird2;

import city.cs.engine.BodyImage;
import city.cs.engine.DynamicBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public class Coin extends DynamicBody {
    public Coin(World world) {
        super(world);
        setPosition(new Vec2(30,0));
        addImage(new BodyImage("data/coin.png", 2));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(-7,0));
        setName("coin");
    }
}
