package bird2;

import city.cs.engine.BodyImage;
import city.cs.engine.CircleShape;
import city.cs.engine.DynamicBody;
import city.cs.engine.Shape;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public class Coin extends DynamicBody {
    public int coinAmount;

    public Coin(int coinAmount, World world) {
        super(world,new CircleShape(1f));
        this.coinAmount = coinAmount; //initialisation
        setPosition(new Vec2(30,0));
        addImage(new BodyImage("data/coin.png", 2));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(-7,0));
        setName("coin");
    }
}
