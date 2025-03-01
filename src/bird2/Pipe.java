package bird2;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.DynamicBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public class Pipe {
    DynamicBody pipeUp;
    DynamicBody pipeDown;
    public Pipe(float heightUp, float heightDown, World world){
        pipeUp = new DynamicBody(world, new BoxShape(3.5f, heightUp));
        pipeUp.setPosition(new Vec2(35,15));
        pipeUp.addImage(new BodyImage("data/pipeUp.png", heightUp * 2)); //("ссылка", высота)
        pipeUp.setGravityScale(0f);
        pipeUp.setLinearVelocity(new Vec2(-7,0));
        pipeUp.setName("pipeUp");
        pipeDown = new DynamicBody(world, new BoxShape(3.5f, heightDown));
        pipeDown.setPosition(new Vec2(35,-22f));
        pipeDown.addImage(new BodyImage("data/pipeDown.png", heightDown * 2)); //("ссылка", высота)
        pipeDown.setGravityScale(0f);
        pipeDown.setLinearVelocity(new Vec2(-7,0));
        pipeDown.setName("pipeDown");
    }

    public void restoreState(){
        pipeUp.setLinearVelocity(new Vec2(-7,0));
        pipeUp.setAngularVelocity(0);
        pipeUp.setAngleDegrees(0);
        pipeDown.setLinearVelocity(new Vec2(-7,0));
        pipeDown.setAngularVelocity(0);
        pipeDown.setAngleDegrees(0);
    }

}
