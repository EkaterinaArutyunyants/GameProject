package game.level3;

import city.cs.engine.*;
import game.BasicLevel;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Bird extends Walker {
    private static final Shape shape = new PolygonShape(-2.02f, 0.43f, -2.13f, -0.23f, -0.83f, -1.94f, -0.21f, -1.95f, 2.11f, -0.83f, 2.05f, 0.91f, 0.96f, 1.94f, -0.78f, 1.57f);
    private static final BodyImage image = new BodyImage("data/level3/moonBird.png", 4);
    private static final BodyImage imageBirdFlyUp = new BodyImage("data/level3/moonBirdFlyUp.png", 4);
    private SoundClip crashSound = null;
    private SoundClip coinSound = null;
    private SoundClip heartSound = null;

    //bird constructor
    public Bird(BasicLevel level) {
        super(level, shape);
        addImage(image);
        SolidFixture fixture = new SolidFixture(this, shape);
        fixture.setDensity(50);
        setPosition(new Vec2(-13, -5));
        setLinearVelocity(new Vec2(7, 0));
        try {
            crashSound = new SoundClip("data/soundCrash.wav");
            crashSound.setVolume(.05);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println(e);
        }
        try {
            coinSound = new SoundClip("data/soundCoin.wav");
            coinSound.setVolume(.05);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println(e);
        }
        try {
            heartSound = new SoundClip("data/soundHeart.wav");
            heartSound.setVolume(.05);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println(e);
        }
    }

    //REQ: changing state of body (decreasing health until destroy)
    public void decHealth() {
        if (crashSound != null) crashSound.play();
    }

    //increasing health
    public void incHealth() {
        if (heartSound != null) heartSound.play();
    }

    //increasing coins and if win destroy
    public void incCoins(int coins) {
        if (coinSound != null) coinSound.play();
    }

    public void setStateAfterCollisionWithRocket() {
        setPosition((new Vec2(0, 0)));
    }

    //REQ: changing bird appearance
    public void flyUp() {
        removeAllImages();
        addImage(imageBirdFlyUp);
    }

    public void flyDown() {
        removeAllImages();
        addImage(image);
    }
}
