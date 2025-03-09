package game;

import city.cs.engine.SoundClip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class BirdGame {
    private static final int width = 1400;
    private static final int height = 800;

    private static BirdWorldView createWorld() {
        BirdWorld world = new BirdWorld();
        return new BirdWorldView(world, width, height);
    }

    private static void createAndStartGame() {
        BirdWorldView view = createWorld();
        playBacksound();
        wrapWithSwingAndShow(view);
        view.getWorld().start();
    }

    private static void playBacksound() {
        try {
            SoundClip backSound = new SoundClip("data/backsound.wav");
            backSound.setVolume(.05);
            backSound.loop();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ignored) {
        }
    }

    private static void wrapWithSwingAndShow(BirdWorldView view) {
        final JFrame frame = new JFrame("Bird Game v01"); //create frame + frame title
        frame.setSize(width, height); //size
        frame.add(view); //add frame to created view
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.addKeyListener(view.getWorld().getBirdController()); //keys
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BirdGame::createAndStartGame);
    }
}

