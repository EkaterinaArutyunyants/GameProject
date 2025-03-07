package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.util.Random;

public class Heart extends DynamicBody {
    private static final Shape heartShape = new PolygonShape(-0.369f,0.867f, -0.934f,0.233f, 0.0f,-0.86f, 0.926f,0.224f, 0.369f,0.859f);
    public Heart(World world) {
        super(world,heartShape);

        Random random = new Random();
        int h = random.nextInt(20)-2;
        setPosition(new Vec2(30,h));

        setPosition(new Vec2(30,0));
        addImage(new BodyImage("data/heart.png", 2));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(-7,0));
        setName("heart");
    }
}