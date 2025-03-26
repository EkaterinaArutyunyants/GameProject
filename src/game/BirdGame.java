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
 *
 */
public class BirdGame {
    private static final int width = 1400;
    private static final int height = 800;
    private final GameView view;
    private final LevelSelector selector;
    private World activeWorld;

    public BirdGame() {
        selector = new LevelSelector(this);
        view = new GameView(selector, width, height);
        selector.setView(view);
        view.addMouseListener(selector.getMouseHandler());
        view.addMouseMotionListener(selector.getMouseHandler());
        activeWorld = selector;
        wrapWithSwingAndShow();
    }

    /**
     * This method .....
     * @param oldLevel
     */
    public void completeLevel(BasicLevel oldLevel){
        System.out.println("completeLevel("+oldLevel.getName()+")");
        for (var listener :  view.getKeyListeners())
            view.removeKeyListener(listener);
        /**
         *
         */
        view.setWorld(selector);
        selector.start();
    }

    /**
     * assdfsdfsdfsdfsdfsdfsdfds
     * @param index
     */
    public void startLevel(int index){
        System.out.println("startLevel("+index+")");
        for (var listener :  view.getKeyListeners())
            view.removeKeyListener(listener);
        var level = createLevel(index);
        view.setWorld(level);
        view.addKeyListener(level.getBirdController());

        activeWorld = level;
        activeWorld.start();
    }

    private BasicLevel createLevel(int index) {
        return switch (index) {
            case 0 -> new Level1(this, "LEVEL1", 4);
            case 1 -> new Level2(this, "LEVEL2", 4);
            case 2 -> new Level3(this, "LEVEL3", 4);
            default -> throw new IllegalStateException(Integer.toString(index));
        };
    }

    public void exitGame(){
        System.exit(0);
    };

    private void createAndStartGame() {
//        playBacksound();
        activeWorld.start();
    }

    private static void playBacksound() {
        try {
            SoundClip backSound = new SoundClip("data/backsound.wav");
            backSound.setVolume(.05);
            backSound.loop();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException skipped) {
            System.err.println(skipped.getMessage());
        }
    }

    private void wrapWithSwingAndShow() {
        final JFrame frame = new JFrame("Bird Game v02"); //create frame + frame title
        frame.setSize(width, height); //size
        frame.add(view); //add frame to created view
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        BirdGame game = new BirdGame();
        SwingUtilities.invokeLater(game::createAndStartGame);
    }
}

