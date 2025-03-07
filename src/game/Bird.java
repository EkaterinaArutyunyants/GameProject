package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Bird extends Walker {
    private static final Shape birdShape = new PolygonShape(-2.02f,0.43f, -2.13f,-0.23f, -0.83f,-1.94f, -0.21f,-1.95f, 2.11f,-0.83f, 2.05f,0.91f, 0.96f,1.94f, -0.78f,1.57f);
    private static final BodyImage birdImage = new BodyImage("data/bird.png", 4);
    int health = 3;
    public int coins = 0;

//constructor
    public Bird(World w) {
        super(w, birdShape);
        addImage(birdImage);
        SolidFixture fixture = new SolidFixture(this, birdShape);
        fixture.setDensity(50);
        setPosition(new Vec2(-13,-5)); //по х, у позиция
        setLinearVelocity(new Vec2(7,0)); //скорость по х, у !непостоянная скорость
        setName("bird");
    }

    public void lostHealth(){
        health--;

        if(health <= 0)  //1 operator
            destroy();
    }
    public void addHealth(){
        health++;
    }
}
