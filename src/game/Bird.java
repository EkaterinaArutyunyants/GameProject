package game;

import city.cs.engine.BodyImage;
import city.cs.engine.PolygonShape;
import city.cs.engine.Sensor;
import city.cs.engine.Shape;
import city.cs.engine.SolidFixture;
import city.cs.engine.SoundClip;
import city.cs.engine.Walker;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Bird extends Walker {
    private static final Shape shape = new PolygonShape(-2.02f, 0.43f, -2.13f, -0.23f, -0.83f, -1.94f, -0.21f, -1.95f, 2.11f, -0.83f, 2.05f, 0.91f, 0.96f, 1.94f, -0.78f, 1.57f);
    private static final Shape sensorShape = new  PolygonShape(-7f,10f, 2.1f,10f, 2.1f,-10f, -7f, -10f);
    private final BodyImage image;
    private final BodyImage imageBirdFlyUp;
    private SoundClip crashSound = null;
    private SoundClip coinSound = null;
    private SoundClip heartSound = null;

    //bird constructor
    public Bird(BasicLevel level, BodyImage image, BodyImage imageBirdFlyUp) {
        super(level, shape);
        this.image = image;
        this.imageBirdFlyUp = imageBirdFlyUp;
        addImage(image);
        SolidFixture fixture = new SolidFixture(this, shape);
        Sensor sensor = new Sensor(this,sensorShape);
        sensor.addSensorListener(level);
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

    public void setStateAfterCollision() {
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
