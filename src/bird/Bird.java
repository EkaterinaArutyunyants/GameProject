package bird;

import city.cs.engine.*;

public class Bird extends DynamicBody {
    private static final Shape birdShape = new PolygonShape(-2.02f, 0.43f, -2.13f, -0.23f, -0.83f, -1.94f, -0.21f, -1.95f, 2.11f, -0.83f, 2.05f, 0.91f, 0.96f, 1.94f, -0.78f, 1.57f);
    private static final BodyImage birdImage = new BodyImage("data/bird.png", 4);

    //constructor
    public Bird(World w) {
        super(w, birdShape);
        addImage(birdImage);
        SolidFixture fixture = new SolidFixture(this, birdShape);
        fixture.setDensity(58);
    }
//    bird = new DynamicBody(world, new CircleShape(2));
//    bird.addImage(new BodyImage("data/bird.png", 4)); //("ссылка", высота)
}
