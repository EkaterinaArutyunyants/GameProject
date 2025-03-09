package game;

import city.cs.engine.BodyImage;
import city.cs.engine.CircleShape;
import city.cs.engine.DynamicBody;
import city.cs.engine.Shape;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.util.Random;

public class Coin extends DynamicBody{
    private static final Shape shape = new CircleShape(1f);
    //REQ: interesting use of image - GIF
    private static final BodyImage image = new BodyImage("data/coinGif.gif", 2);
    private static final float y_max = 15f;
    private static final Random random = new Random();

    private final int coinAmount;

    public Coin(World world, int coinAmount) {
        super(world, shape);
        addImage(image);
        this.coinAmount = coinAmount;
        //randomness by vertical
        setPosition(new Vec2(30, (random.nextFloat() - 0.5f) * y_max));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(-7, 0));
    }

    public int getCoinAmount() {
        return coinAmount;
    }

}
