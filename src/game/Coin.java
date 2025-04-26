package game;

import city.cs.engine.BodyImage;
import city.cs.engine.CircleShape;
import city.cs.engine.DynamicBody;
import city.cs.engine.Shape;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.util.Random;

/**
 * Coin class - object coin is creating in the right side of the screen
 * with random vertical position and moving to the left by linear velocity.
 * player can collect coins to increase score.
 */
public class Coin extends Asset{
    //circle shape
    private static final Shape shape = new CircleShape(1f);
    //REQ: interesting use of image - GIF
    private static final BodyImage image = new BodyImage("data/coinGif.gif", 2);
    //max vertical range
    private static final float y_max = 15f;
    //random num for vertical location
    private static final Random random = new Random();
    //point num coin awards
    private final int coinAmount;

    /**
     * Constructor for coin
     * @param factory - creating this coin
     * @param coinAmount - value of the collected coin
     */
    public Coin(AssetFactory factory, int coinAmount) {
        super(factory, shape);
        addImage(image);
        this.coinAmount = coinAmount;
        //randomness by vertical
        setPosition(new Vec2(30, (random.nextFloat() - 0.5f) * y_max));
        setGravityScale(0f); //doesnt fall
        setLinearVelocity(new Vec2(-7, 0)); //move left
    }

    /**
     * getter method for score value
     * @return amount of points which coin gives
     */
    public int getCoinAmount() {
        return coinAmount;
    }

}
