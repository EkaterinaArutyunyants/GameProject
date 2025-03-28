package game.level3;

import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.CircleShape;
import city.cs.engine.Sensor;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import city.cs.engine.SolidFixture;
import game.Asset;
import game.AssetFactory;
import game.Bird;
import org.jbox2d.common.Vec2;

public class Teleport extends Asset implements SensorListener {
    private static final BodyImage holeImage = new BodyImage("data/level3/blackHole.png", 4);
    private static final BodyImage wallImage = new BodyImage("data/level3/wall.jpg", 4);
    private static final float ySky=22f, halfWallWidth = 3.5f / 2;
    private static final float holeHalfHeight=2f, rightHoleDistanceX=halfWallWidth +holeHalfHeight+2f;
    private static final float holeScale=1.5f,wallScale=5f;
    private static float vx = -7f;
    private final Vec2 rightHoleCenter= new Vec2(rightHoleDistanceX, 0);
    public Teleport(AssetFactory factory,float leftHoleDistanceX,float leftHoleDistanceY) {
        super(factory, new BoxShape(halfWallWidth,ySky));
        Vec2 leftHoleCenter = new Vec2(-leftHoleDistanceX, leftHoleDistanceY);
        new Sensor(this, new CircleShape(holeHalfHeight, leftHoleCenter)).addSensorListener(this);
        new Sensor(this, new CircleShape(holeHalfHeight, rightHoleCenter));
        AttachedImage wallAImage = addImage(wallImage);
        wallAImage.setScale(wallScale);
        AttachedImage leftimage = addImage(holeImage);
        leftimage.setOffset(leftHoleCenter);
        leftimage.setScale(holeScale);
        AttachedImage rightImage = addImage(holeImage);
        rightImage.setOffset(rightHoleCenter);
        rightImage.setScale(holeScale);
        setPosition(new Vec2(35, 0));
        setGravityScale(0f);
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
        setAlwaysOutline(true);
    }

    @Override
    public void beginContact(SensorEvent sensorEvent) {
        if ((sensorEvent.getSensor().getBody() instanceof Teleport teleport) && (sensorEvent.getContactBody() instanceof Bird bird)){
            bird.setPosition(teleport.getPosition().add(rightHoleCenter));
        } else {
            System.out.println("beginContact("+ sensorEvent+")");
        }
    }

    @Override
    public void endContact(SensorEvent sensorEvent) {}
}
