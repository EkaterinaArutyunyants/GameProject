package game.level1;

import city.cs.engine.Body;
import city.cs.engine.BoxShape;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.DestructionEvent;
import city.cs.engine.DestructionListener;
import city.cs.engine.SoundClip;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import game.Bird;
import game.BirdGame;
import game.Coin;
import game.GenericFactory;
import game.Heart;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//REQ: extensions: inheritance + encapsulation (superclass, subclass)
public class Level1 extends World implements CollisionListener, DestructionListener {
    private final String name;
    private final BirdGame game;
    private final Bird bird;
    private boolean gameOver = false;
    private boolean success = false;
    private final StaticBody RIP;
    private SoundClip winSound = null;
    private SoundClip lostSound = null;
    private final Map<String, GenericFactory> factories = Map.of(
            "pipe",new PipeFactory(this, 4000),
            "coin",new GenericFactory(this, 7000) {
                @Override
                protected void createAsset() {
                    super.createAsset();
                    new Heart(world);
                }
            },
            "heart",            new GenericFactory(this, 8000) {
                @Override
                protected void createAsset() {
                    super.createAsset();
                    new Coin(this.world, 2);
                }
            }
    );

    private final Set<Body> hittedPipes = new HashSet<>();

    private final KeyAdapter birdController
    = new KeyAdapter() {
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
    public Level1(BirdGame game,String name) {
        super(); //parent
        this.game = game;
        this.name = name;
        bird = new Bird(this, 10);
        bird.addCollisionListener(this);
        bird.addDestructionListener(this);

        RIP = new StaticBody(this, new BoxShape(40f, 0.1f, new Vec2(0, -20f)));
        try {
            winSound = new SoundClip("data/soundWin.wav");
            winSound.setVolume(.05);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println(e.getMessage());
        }
        try {
            lostSound = new SoundClip("data/soundLost.wav");
            lostSound.setVolume(.05);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println(e.getMessage());
        }
    }

    //REQ: collision with coin,heart,pipe + bird dies
    @Override
    public void collide(CollisionEvent collisionEvent) {
        Body body = collisionEvent.getOtherBody();
        if (body instanceof Coin) {
            bird.incCoins(((Coin) body).getCoinAmount());
            factories.get("coin").decCount();
            body.destroy();
        } else if (body instanceof Heart) {
            bird.incHealth();
            factories.get("heart").decCount();
            body.destroy();
        } else if (body instanceof Pipe) {
            //restore linear and angle velocity of pipe
            ((Pipe) body).restoreStateAfterCollision();
            bird.setStateAfterCollisionWithPipe();
            if (!hittedPipes.contains(body)) {
                hittedPipes.add(body);
                bird.decHealth();
            }
        } else if (RIP.equals(body)) {
            bird.destroy();
        } else {
            System.out.println("Unexpected " + collisionEvent);
        }
    }

    //destroy when win game
    @Override
    public void destroy(DestructionEvent destructionEvent) {
        if (destructionEvent.getSource() instanceof Bird) {
            stop();
            factories.values().forEach(GenericFactory::stop);
            gameOver = true;
            success = bird.isWin();
            if (success) {
                if (winSound != null) winSound.play();
            } else {
                if (lostSound != null) lostSound.play();
            }
            game.completeLevel(this);
        } else {
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

    public String getName() {
        return name;
    }
    public int getScore(){
        return bird.getCoins();
    }

    public int getHealth(){
        return bird.getHealth();
    }
}

