package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Bird class - bird is a main game character
 * Includes sounds for every bird related action (crash, collecting coin, collecting heart)
 */
public class Bird extends Walker {
    //bird shape
    private static final Shape shape = new PolygonShape(-2.02f, 0.43f, -2.13f, -0.23f, -0.83f, -1.94f, -0.21f, -1.95f, 2.11f, -0.83f, 2.05f, 0.91f, 0.96f, 1.94f, -0.78f, 1.57f);
    //sensor zone
    private static final Shape sensorShape = new PolygonShape(-7f, 10f, 2.1f, 10f, 2.1f, -10f, -7f, -10f);
    //bird img states
    private final BodyImage image; //default
    private final BodyImage imageBirdFlyUp; //when press space (bird wings up)
    //sound states
    private SoundClip crashSound = null;
    private SoundClip coinSound = null;
    private SoundClip heartSound = null;

    /**
     * Constructor for bird
     * appearance, sound, position
     *
     * @param level          where bird in
     * @param image          default one
     * @param imageBirdFlyUp used when press space and bird fly up
     */
    public Bird(BasicLevel level, BodyImage image, BodyImage imageBirdFlyUp) {
        super(level, shape);
        this.image = image;
        this.imageBirdFlyUp = imageBirdFlyUp;
        addImage(image); //default
        //physical props
        SolidFixture fixture = new SolidFixture(this, shape);
        fixture.setDensity(50);
        //sensor
        Sensor sensor = new Sensor(this, sensorShape);
        sensor.addSensorListener(level);
        //position + velocity
        setPosition(new Vec2(-13, -5));
        setLinearVelocity(new Vec2(7, 0));
        //sounds
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

    /**
     * when bird collide with enemy this method is called
     * REQ: changing state of body (decreasing health until destroy)
     */
    public void decHealth() {
        if (crashSound != null) crashSound.play();
    }

    /**
     * when bird collide with hearts this method called
     * increasing health
     */
    public void incHealth() {
        if (heartSound != null) heartSound.play();
    }

    /**
     * when bird collide with coins this method called
     * plays ding sound
     */
    public void sayDing() {
        if (coinSound != null) coinSound.play();
    }

    /**
     * resetting bird position after collision with pipes and etc
     */
    public void setStateAfterCollision() {
        setPosition((new Vec2(0, 0)));
    }

    /**
     * bird wings up
     * REQ: changing bird appearance
     */
    public void flyUp() {
        removeAllImages();
        addImage(imageBirdFlyUp);
    }

    /**
     * bird wings down (back to default img)
     */
    public void flyDown() {
        removeAllImages();
        addImage(image);
    }
}
