package game;

import city.cs.engine.SoundClip;
import game.level1.Level1;
import game.level2.Level2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BirdGame {
    private static final int width = 1400;
    private static final int height = 800;
    private final GameView view;
    private BasicLevel level;
    private int levelNum;
    private final List<BasicLevel> levels = new ArrayList<>();

    public BirdGame() {
//        for (int levelNum=0; levelNum<3;levelNum++)
        levels.add(new Level1(this,"LEVEL"+levelNum,4));
        levels.add(new Level2(this,"LEVEL2",4));
        levelNum=0;
        level= levels.get(levelNum);
        view = new GameView(level, width, height);
        view.addKeyListener(level.getBirdController());

        view.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                view.requestFocus();
            }
        });
        wrapWithSwingAndShow();
    }

    public void completeLevel(BasicLevel oldLevel){
        for (var listener :  view.getKeyListeners())
            view.removeKeyListener(listener);
        levelNum++;
        if (levelNum<levels.size()){
            level= levels.get(levelNum);
            view.setWorld(level);
            view.addKeyListener(level.getBirdController());
            level.start();
        } else {
            System.out.println("GameOver");
        }
    }


    private void createAndStartGame() {
        playBacksound();
        level.start();
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
//        frame.addKeyListener(world.getBirdController()); //keys
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        BirdGame game = new BirdGame();
        SwingUtilities.invokeLater(game::createAndStartGame);
    }
}

