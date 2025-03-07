package game;

import city.cs.engine.BodyImage;
import city.cs.engine.CircleShape;
import city.cs.engine.DynamicBody;
import city.cs.engine.Shape;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.util.Random;

public class Coin extends DynamicBody {
    public int coinAmount;

    public Coin(int coinAmount, World world) {
        super(world,new CircleShape(1f));
        this.coinAmount = coinAmount; //initialisation
        Random random = new Random();
        int h = random.nextInt(20)-10;
        setPosition(new Vec2(30,h));
        addImage(new BodyImage("data/coin.png", 2));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(-7,0));
        setName("coin");
    }
}
