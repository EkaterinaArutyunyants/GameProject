package game;

import city.cs.engine.Body;
import city.cs.engine.BoxShape;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.DynamicBody;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import city.cs.engine.SoundClip;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import game.level2.Bomb;
import game.level2.Spider;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

//REQ: extensions: inheritance + encapsulation (superclass, subclass)
public class BasicLevel extends World implements CollisionListener, SensorListener {
    protected Image background = new ImageIcon("data/level1/sky.jpg").getImage();
    private final String name;
    private final BirdGame game;
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

    public BasicLevel(BirdGame game, String name, int targetScore) {
        super(); //parent
        this.game = game;
        this.name = name;
        this.targetScore = targetScore;

        factories.add(new AssetFactory(this, 7000, 3) {
            @Override
            protected void createAsset() {
                super.createAsset();
                new Heart(this);
            }
        });
        factories.add(new AssetFactory(this, 8000, 3) {
            @Override
            protected void createAsset() {
                super.createAsset();
                new Coin(this, 2);
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
            //level2
        } else if (collisionEvent.getOtherBody() instanceof Spider spider) {
            health--;
            spider.destroy();
        } else if (collisionEvent.getOtherBody() instanceof Bomb bomb) {
            health--;
            bomb.destroy();
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

    protected void complete() {
        stop();
        factories.forEach(AssetFactory::stop);
        getDynamicBodies().forEach(Body::destroy);
        getStaticBodies().forEach(Body::destroy);
        complete = true;
        success = score >= targetScore;
        if (success) {
            if (winSound != null) winSound.play();
        } else {
            if (lostSound != null) lostSound.play();
        }
        game.completeLevel(this);
    }

    public Image getBackground() {
        return background;
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

