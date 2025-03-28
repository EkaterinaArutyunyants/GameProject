package game;

import city.cs.engine.Body;
import city.cs.engine.BoxShape;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import city.cs.engine.SoundClip;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import game.level2.Bomb;
import game.level3.Alien;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.util.*;

//REQ: extensions: inheritance + encapsulation (superclass, subclass)
public class BasicLevel extends WorldWithBackground implements CollisionListener, SensorListener {
    private final String name;
    private boolean complete = false;
    private boolean success = false;
    protected final StaticBody RIP;
    private SoundClip winSound = null;
    private SoundClip lostSound = null;
    protected int health = 3;
    protected final int targetScore;
    protected int score = 0;
    protected final Collection<AssetFactory> factories = new ArrayList<>();
    protected KeyAdapter birdController;
    protected final Random random = new Random();
    protected final Set<Body> hittedBodies = new HashSet<>();

    public BasicLevel(BirdGame game, String name, int targetScore) {
        super(); //parent
        this.name = name;
        this.targetScore = targetScore;

        factories.add(new AssetFactory(this,  random.nextInt(9000),9000, 3) {
            @Override
            protected void createAsset() {
                new Heart(this);
            }
        });
        factories.add(new AssetFactory(this, random.nextInt(11000),11000, 3) {
            @Override
            protected void createAsset() {
                new Coin(this, 1);
            }
        });

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


    @Override
    public void collide(CollisionEvent collisionEvent) {
        if (collisionEvent.getOtherBody() instanceof Coin coin) {
            score += coin.getCoinAmount();
            if (score >= targetScore) complete();
            coin.destroy();
        } else if (collisionEvent.getOtherBody() instanceof Heart heart) {
            health++;
            heart.destroy();
        } else if (RIP.equals(collisionEvent.getOtherBody())) {
            complete();
        } else {
            System.out.println("Unexpected " + collisionEvent);
        }
    }



    @Override
    public void beginContact(SensorEvent sensorEvent) {
        System.out.println("beginContact("+sensorEvent+")");
    }

    @Override
    public void endContact(SensorEvent sensorEvent) {
        System.out.println("endContact("+sensorEvent+")");
    }

    /**
     * call this method when we are going to complete the level
     */
    protected void complete() {
        complete = true;
        success = score >= targetScore;
        if (success) {
            if (winSound != null) winSound.play();
        } else {
            if (lostSound != null) lostSound.play();
        }
        stop(); //stopping the world
        factories.forEach(AssetFactory::stop); //stopping all factories
        //destroying all objects from the world
        getDynamicBodies().forEach(Body::destroy);
        getStaticBodies().forEach(Body::destroy);
    }

    @Override
    public void start(){
        super.start();
        factories.forEach(AssetFactory::start);
    }

    @Override
    public void stop(){
        factories.forEach(AssetFactory::stop);
        super.stop();
    }


    public boolean isComplete() {
        return complete;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getHealth() {
        return health;
    }

    public KeyAdapter getBirdController() {
        return birdController;
    }
}

