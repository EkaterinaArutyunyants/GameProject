package game.level1;

import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Sensor;
import game.Asset;
import game.AssetFactory;
import org.jbox2d.common.Vec2;

public class SensorPipe extends Asset {
    private static final float yMax = 19f;
    private static final float halfWidth = 3.5f / 2;
    private static final float scale = 2.5f;
    private static float vx = -7f;

    public SensorPipe(AssetFactory factory, float holeUp) {
        super(factory); //parent

        //dif size of holes between pipes
        float halfHeightUp = (yMax - holeUp) / 2f;
        float yCenterUp = (yMax + holeUp) / 2f;
        Vec2 centerUp = new Vec2(0, yCenterUp);
        Vec2 centerDown = new Vec2(0, -yCenterUp);

        //REQ: multiple fixtures
        Sensor sensor = new Sensor(this, new BoxShape(halfWidth, halfHeightUp * 1.2f, centerUp));
        sensor.addSensorListener(factory.getLevel());
        sensor = new Sensor(this, new BoxShape(halfWidth, halfHeightUp * 1.2f, centerDown));
        sensor.addSensorListener(factory.getLevel());

        AttachedImage imageUp = addImage(new BodyImage("data/level1/pipeUp.png", halfHeightUp));
        imageUp.setOffset(centerUp);
        imageUp.setScale(scale);
        AttachedImage imageDown = addImage(new BodyImage("data/level1/pipeDown.png", halfHeightUp));
        imageDown.setOffset(centerDown);
        imageDown.setScale(scale);

        //REQ: enemies that move on their own
        setPosition(new Vec2(35, 0));
        //REQ: dif physical property
        setGravityScale(0f);
        setLinearVelocity(new Vec2(vx, 0));
        setAngleDegrees(0f);
        setAngularVelocity(0f);
    }

}
