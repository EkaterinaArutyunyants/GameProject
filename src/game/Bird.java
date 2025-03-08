package game;

import city.cs.engine.BodyImage;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import city.cs.engine.SolidFixture;
import city.cs.engine.Walker;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public class Bird extends Walker {
    private static final Shape shape = new PolygonShape(-2.02f, 0.43f, -2.13f, -0.23f, -0.83f, -1.94f, -0.21f, -1.95f, 2.11f, -0.83f, 2.05f, 0.91f, 0.96f, 1.94f, -0.78f, 1.57f);
    private static final BodyImage image = new BodyImage("data/bird.png", 4);
    private int health = 3;
    private int coins = 0;

    //constructor
    public Bird(World world) {
        super(world, shape);
        addImage(image);
        SolidFixture fixture = new SolidFixture(this, shape);
        fixture.setDensity(50);
        setPosition(new Vec2(-13, -5)); //по х, у позиция
        setLinearVelocity(new Vec2(7, 0)); //скорость по х, у !непостоянная скорость
    }

    public void decHealth() {
        health--;
        if (health <= 0)  //1 operator
            destroy();
    }

    public void incHealth() {
        health++;
    }

    public int getHealth() {
        return health;
    }

    public int getCoins() {
        return coins;
    }

    public void incCoins(int coins) {
        this.coins += coins;
    }

    public void restoreStateAfterCollision() {
        setPosition((new Vec2(0, 0)));
    }
}
