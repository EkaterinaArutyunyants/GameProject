package game.level2;

import city.cs.engine.*;
import game.Asset;
import game.AssetFactory;
import org.jbox2d.common.Vec2;

import java.util.Random;

public class Cloud extends Asset {
    private static final Shape shape = new PolygonShape(-8.68f,-0.68f, -3.89f,2.95f, 1.62f,3.85f, 6.73f,2.2f, 8.42f,-0.32f, 4.21f,-3.42f, -4.36f,-3.38f);
    private static final BodyImage image = new BodyImage("data/level2/cloud.png", 9);

    public Cloud(AssetFactory factory) {
        super(factory);
        new GhostlyFixture(this, shape);
        addImage(image);
        setPosition(new Vec2(30, 15));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(-7, 0));
        setAlwaysOutline(true);
    }
}