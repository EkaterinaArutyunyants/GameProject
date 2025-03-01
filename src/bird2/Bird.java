package bird2;

import city.cs.engine.BodyImage;
import city.cs.engine.CircleShape;
import city.cs.engine.DynamicBody;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import city.cs.engine.SolidFixture;
import city.cs.engine.Walker;
import city.cs.engine.World;
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
        fixture.setDensity(58);
        fixture.setDensity(100);
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
//    bird = new DynamicBody(world, new CircleShape(2));
//    bird.addImage(new BodyImage("data/bird.png", 4)); //("ссылка", высота
}
