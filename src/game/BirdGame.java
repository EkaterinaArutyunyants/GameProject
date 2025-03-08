package game;

import city.cs.engine.SoundClip;
import city.cs.engine.WorldView;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.io.IOException;

public class BirdGame {
    private static final int width = 1400;
    private static final int height = 800;
    public static SoundClip pickupJumpSound; //описание переменно


    private static BirdWorldView createWorld() {
        BirdWorld world = new BirdWorld();
        return new BirdWorldView(world, width, height);
    }

    private static void createAndStartGame() {
        BirdWorldView view = createWorld();
        playBacksound(); //вызываем sound
        KeyAdapter listener = ((BirdWorld) view.getWorld()).getBirdController(); //new KeyboardHandler();
        wrapWithSwingAndShow(view, listener); //обертываем в swing
        view.getWorld().start(); //запускаем симуляцию (DinamicBody работает)
    }

    private static void playBacksound() {
        try {
            SoundClip pickupSound = new SoundClip("data/backsound.wav"); //класс SoundClip - загружаем туда файл звука
            pickupSound.setVolume(.05); //задаем громкость
            pickupSound.loop(); // (.play() -> до завершения аудиодорожки)

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    private static void wrapWithSwingAndShow(WorldView view, KeyListener listener) { //(тип пар-р, тип пар-р)
        final JFrame frame = new JFrame("Bird Game v01"); //создаем frame + название
        frame.setSize(width, height); //задаем размер
        frame.add(view); //в созданный view добавляем frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.addKeyListener(listener); //клавиши
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BirdGame::createAndStartGame);
    }
}

