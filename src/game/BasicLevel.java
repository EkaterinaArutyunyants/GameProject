package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.util.*;

/**
 * Basic level class - everything that is related to each level
 * objects creation through factory, collision, sensor for all levels
 * <p>
 * REQ: extensions: inheritance + encapsulation (superclass, subclass)
 */
public class BasicLevel extends WorldWithBackground implements CollisionListener, SensorListener {
    private final String name;
    private boolean complete = false; //level completed
    private boolean success = false; //level win
    protected final StaticBody RIP; //invisible platform at the bottom
    //sounds
    private SoundClip winSound = null;
    private SoundClip lostSound = null;
    //stats
    protected int health = 3;
    protected final int targetScore;
    protected int score = 0;
    //asset
    protected final Collection<AssetFactory> factories = new ArrayList<>();
    protected KeyAdapter birdController;
    protected final Random random = new Random();
    protected final Set<Body> hittedBodies = new HashSet<>();

    /**
     * Constructor for BasicLevel
     *
     * @param game        controller
     * @param name        of the level
     * @param targetScore required to complete level with success
     */
    public BasicLevel(BirdGame game, String name, int targetScore) {
        super(); //parent
        this.name = name;
        this.targetScore = targetScore;

        //setting random initial delay for objects (heart, coin)
        //Heart factory
        factories.add(new AssetFactory(this, random.nextInt(9000), 9000, 3) {
            @Override
            protected void createAsset() {
                new Heart(this);
            }
        });
        //Coin factory
        factories.add(new AssetFactory(this, random.nextInt(11000), 11000, 3) {
            @Override
            protected void createAsset() {
                new Coin(this, 1);
            }
        });
        //invisible bottom platform - bird collide = game over
        RIP = new StaticBody(this, new BoxShape(40f, 0.1f, new Vec2(0, -20f)));
        //win and lost sounds
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

    /**
     * COLLISION for common level objects (specific collisions are in level classes)
     *
     * @param collisionEvent
     */
    @Override
    public void collide(CollisionEvent collisionEvent) {
        if (collisionEvent.getOtherBody() instanceof Coin coin) {
            score += coin.getCoinAmount();
            if (score >= targetScore) complete();
            coin.destroy();
            if (collisionEvent.getReportingBody() instanceof Bird bird) {
                bird.sayDing();
            }
        } else if (collisionEvent.getOtherBody() instanceof Heart heart) {
            health++;
            heart.destroy();
            if (collisionEvent.getReportingBody() instanceof Bird bird) {
                bird.incHealth();
            }
        } else if (RIP.equals(collisionEvent.getOtherBody())) {
            complete();
        } else {
            System.out.println("Unexpected " + collisionEvent);
        }
    }

    @Override
    public void beginContact(SensorEvent sensorEvent) {
        System.out.println("beginContact(" + sensorEvent + ")");
    }

    /**
     * Processing sensor for bird in BasicLevel because in bird there are no info for our handle
     *
     * @param sensorEvent
     */
    @Override
    public void endContact(SensorEvent sensorEvent) {
        System.out.println("endContact(" + sensorEvent + ")");
        if (sensorEvent.getSensor().getBody() instanceof Bird && !RIP.equals(sensorEvent.getContactBody())) {
            sensorEvent.getContactBody().destroy();
        } else {
            System.out.println("endContact(" + sensorEvent + ")");
        }
    }

    /**
     * call this method when we are going to complete the level
     * also result of the completing the game (winn/lost)
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

    /**
     * start level and all asset factories
     */
    @Override
    public void start() {
        super.start();
        factories.forEach(AssetFactory::start);
    }

    /**
     * stop level and all asset factories
     */
    @Override
    public void stop() {
        factories.forEach(AssetFactory::stop);
        super.stop();
    }

//accessors

    /**
     * @return true/false level completed
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * @return true/false - won or lost
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @return level name
     */
    public String getName() {
        return name;
    }

    /**
     * @return current score
     */
    public int getScore() {
        return score;
    }

    /**
     * @return current health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return KeyAdapter for controlling bird
     */
    public KeyAdapter getBirdController() {
        return birdController;
    }
}

