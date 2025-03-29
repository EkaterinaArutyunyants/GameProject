package game.level3;

import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.CircleShape;
import city.cs.engine.Sensor;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import game.Asset;
import game.AssetFactory;
import game.Bird;
import org.jbox2d.common.Vec2;

/**
 * Teleport class:
 * moving wall with 2 teleportation black holes
 * If the bird enters the left hole, it gets teleported to the right hole
 */

public class Teleport extends Asset implements SensorListener {
    //img for the teleport holes and wall
    private static final BodyImage holeImage = new BodyImage("data/level3/blackHole.png", 4);
    private static final BodyImage wallImage = new BodyImage("data/level3/wall.jpg", 4);

    //constants for wall and holes positioning
    private static final float ySky=22f, halfWallWidth = 3.5f / 2; //wall height, half of wall width
    //half height of hole; X position og the right hole
    private static final float holeHalfHeight=2f, rightHoleDistanceX=halfWallWidth +holeHalfHeight+2f;
    private static final float holeScale=1.5f,wallScale=5f; //scaling for images
    private static float vx = -7f; //teleporting hole velocity

    //position of the right hole relative to wall
    private final Vec2 rightHoleCenter= new Vec2(rightHoleDistanceX, 0);

    /**
     * Constructor for the teleport object
     * @param factory AssetFactory used for creating the teleport object
     * @param leftHoleDistanceX X coordinate of the left teleport hole
     * @param leftHoleDistanceY Y coordinate of the left teleport hole
     */
    public Teleport(AssetFactory factory,float leftHoleDistanceX,float leftHoleDistanceY) {
        super(factory, new BoxShape(halfWallWidth,ySky));

        //position of the left hole
        Vec2 leftHoleCenter = new Vec2(-leftHoleDistanceX, leftHoleDistanceY);

        //sensors for teleportation
        new Sensor(this, new CircleShape(holeHalfHeight, leftHoleCenter)).addSensorListener(this);
        new Sensor(this, new CircleShape(holeHalfHeight, rightHoleCenter));

        //Images
        AttachedImage wallAImage = addImage(wallImage);
        wallAImage.setScale(wallScale);

        AttachedImage leftimage = addImage(holeImage);
        leftimage.setOffset(leftHoleCenter);
        leftimage.setScale(holeScale);

        AttachedImage rightImage = addImage(holeImage);
        rightImage.setOffset(rightHoleCenter);
        rightImage.setScale(holeScale);

        //wall position and other props
        setPosition(new Vec2(35, 0));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
        setAlwaysOutline(true);
    }

    /**
     * when something touches the teleport sensor, this method checks if it's a Bird
     * If the Bird enters the left hole, it gets teleported to the right hole
     * @param sensorEvent The event triggered when something touches the sensor
     */
    @Override
    public void beginContact(SensorEvent sensorEvent) {
        if ((sensorEvent.getSensor().getBody() instanceof Teleport teleport) && (sensorEvent.getContactBody() instanceof Bird bird)){
            //teleport the bird to the right hole position
            bird.setPosition(teleport.getPosition().add(rightHoleCenter));
        } else {
            System.out.println("beginContact("+ sensorEvent+")");
        }
    }

    @Override
    public void endContact(SensorEvent sensorEvent) {}
}
