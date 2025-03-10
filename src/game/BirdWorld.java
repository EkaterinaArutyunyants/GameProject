package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

//REQ: extensions: inheritance + encapsulation (superclass, subclass)
public class BirdWorld extends World implements CollisionListener,DestructionListener{
    private final Bird bird;
    private boolean gameOver = false;
    private boolean success = false;
    private final StaticBody RIP;
    private SoundClip winSound = null;
    private SoundClip lostSound = null;
    private final PipeFactory pipeFactory = new PipeFactory(this);
    private final HeartFactory heartFactory = new HeartFactory(this);
    private final CoinFactory coinFactory = new CoinFactory(this);
    private final Set<Body> hittedPipes = new HashSet<>();

    private final KeyAdapter birdController = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE: //class KeyEvent
                    bird.setLinearVelocity(new Vec2(0f, 5f));
                    //switch images when press space
                    bird.flyUp();
                    break;
                case KeyEvent.VK_SHIFT:
                    bird.setLinearVelocity(new Vec2(15f, 0f));
                    break;
                default:
                    System.out.println("Unhandled " + e);
            }
        }
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                //switch images when release space
                bird.flyDown();
            } else {
                System.out.println("Unhandled key " + e);
            }
        }
    };

    public BirdWorld() {
        super(); //parent
        bird = new Bird(this,10);
        bird.addCollisionListener(this);
        bird.addDestructionListener(this);
        RIP = new StaticBody(this, new BoxShape(40f, 0.1f, new Vec2(0,-20f)));
        try {
            winSound = new SoundClip("data/soundWin.wav");
            winSound.setVolume(.05);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println(e);
        }
        try {
            lostSound = new SoundClip("data/soundLost.wav");
            lostSound.setVolume(.05);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println(e);
        }
    }

    //REQ: collision with coin,heart,pipe + bird dies
    @Override
    public void collide(CollisionEvent collisionEvent) {
        Body body = collisionEvent.getOtherBody();
        if (body instanceof Coin) {
            bird.incCoins(((Coin) body).getCoinAmount());
            coinFactory.decCount();
            body.destroy();
        } else if (body instanceof Heart) {
            bird.incHealth();
            heartFactory.decCount();
            body.destroy();
        } else if (body instanceof Pipe) {
            //restore linear and angle velocity of pipe
            ((Pipe) body).restoreStateAfterCollision();
            bird.setStateAfterCollisionWithPipe();
            if (!hittedPipes.contains(body)) {
                hittedPipes.add(body);
                bird.decHealth();
            }
        } else if (RIP.equals(body)){
            bird.destroy();
        } else {
            System.out.println("Unexpected " + collisionEvent);
        }
    }

    //destroy when win game
    @Override
    public void destroy(DestructionEvent destructionEvent) {
            if (destructionEvent.getSource() instanceof Bird){
                stop();
                gameOver = true;
                success = bird.isWin();
                if (success){
                    if (winSound != null) winSound.play();
                } else {
                    if (lostSound != null) lostSound.play();
                }
            } else{
                System.out.println("Unexpected " + destructionEvent);
            }
    }

    public Bird getBird() {
        return bird;
    }

    public KeyAdapter getBirdController() {
        return birdController;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isSuccess() {
        return success;
    }
}

