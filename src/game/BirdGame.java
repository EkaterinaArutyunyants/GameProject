package game;

import city.cs.engine.SoundClip;
import city.cs.engine.World;
import game.level1.Level1;
import game.level2.Level2;
import game.level3.Level3;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

/**
 * BirdGame class - main for the game
 * handles level transitions,
 * initializes game view and level selector
 */
public class BirdGame {
    private static final int width = 1400;
    private static final int height = 800;
    private final GameView view;
    private final LevelSelector selector;
    private World activeWorld;

    /**
     * Constructor for BirdGame
     * initialize level selector and game view
     * display main window
     */
    public BirdGame() {
        selector = new LevelSelector(this);
        view = new GameView(this, selector, width, height);
        selector.setView(view);
        //adding mouse
        view.addMouseListener(selector.getMouseHandler());
        view.addMouseMotionListener(selector.getMouseHandler());
        activeWorld = selector; //start game with level selector
        wrapWithSwingAndShow();
    }

    /**
     * Complete level
     * This method removes completed level keyListener
     * After - starting the selector to choose new level
     *
     * @param oldLevel - level that just been completed
     */
    public void completeLevel(BasicLevel oldLevel) {
        System.out.println("completeLevel(" + oldLevel.getName() + ")");
        for (var listener : view.getKeyListeners())
            view.removeKeyListener(listener);
        view.setWorld(selector);
        selector.start();
    }

    /**
     * start level (depends on index)
     * removes prev key listener
     *
     * @param index of level (0 - level1)
     */
    public void startLevel(int index) {
        System.out.println("startLevel(" + index + ")");
        for (var listener : view.getKeyListeners())
            view.removeKeyListener(listener);
        var level = createLevel(index); //creating level by index
        view.setWorld(level); //view displays level
        view.addKeyListener(level.getBirdController());

        activeWorld = level;
        activeWorld.start(); //run the level
    }

    /**
     * create level (depends on index)
     *
     * @param index of level
     * @return index of level
     */
    private BasicLevel createLevel(int index) {
        return switch (index) {
            case 0 -> new Level1(this, "LEVEL1", 4);
            case 1 -> new Level2(this, "LEVEL2", 4);
            case 2 -> new Level3(this, "LEVEL3", 4);
            default -> throw new IllegalStateException(Integer.toString(index));
        };
    }

    /**
     * game app terminates
     */
    public void exitGame() {
        System.exit(0);
    }

    ;

    /**
     * starts current level
     * playing sound
     */
    private void createAndStartGame() {
        playBacksound();
        activeWorld.start();
    }

    /**
     * method for background sound
     */
    private static void playBacksound() {
        try {
            SoundClip backSound = new SoundClip("data/backsound.wav");
            backSound.setVolume(.05);
            backSound.loop();
            //catching errors if something wrong w/ file
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException skipped) {
            System.err.println(skipped.getMessage());
        }
    }

    /**
     * create swing window
     * add game view
     * displaying
     */
    private void wrapWithSwingAndShow() {
        final JFrame frame = new JFrame("Bird Game v02"); //create frame + frame title
        frame.setSize(width, height); //size
        frame.add(view); //add frame to created view
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.pack(); //resizing frame
        frame.setVisible(true); //show window
    }

    /**
     * main method
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        BirdGame game = new BirdGame();
        SwingUtilities.invokeLater(game::createAndStartGame);
    }
}

