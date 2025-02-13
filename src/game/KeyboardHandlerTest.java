package game;

import city.cs.engine.DynamicBody;
import org.jbox2d.common.Vec2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static game.KL_01MainInit.pickupJumpSound;

public class KeyboardHandlerTest extends KeyAdapter {
    @Override
    //функция кей прессед
    public void keyPressed(KeyEvent e) {
        System.out.println("keyPressed("+e+")");
    }

    //неизмен, итоговый, тип, атрибут
    private final DynamicBody character;
    //constructor
    public KeyboardHandlerTest(DynamicBody character) {
        this.character = character;
    }

    //функция кей релисд
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode(); //method getKeyCode()
        switch (key) {
            case KeyEvent.VK_W: //class KeyEvent
                character.setLinearVelocity(new Vec2(0f, 5f));
                pickupJumpSound.play();
//                System.out.println("jump");
                break;
            case KeyEvent.VK_A:
                character.setLinearVelocity(new Vec2(-5f, 0f));
//                System.out.println("left");
                break;
            case KeyEvent.VK_D:
                character.setLinearVelocity(new Vec2(5f, 0f));
//                System.out.println("right");
                break;
            default:
                System.out.println("Unsupported key keyReleased " + e);
        }
    }
}
