package bird;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Calendar;

public class BirdGame {
    private static int width = 1400;
    private static int height = 800;
    public static SoundClip pickupJumpSound; //описание переменной
    private static DynamicBody bird;

    public static class KeyboardHandler extends KeyAdapter {
        //функция кей релисд
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode(); //method getKeyCode()
            switch (key) {
                case KeyEvent.VK_SPACE: //class KeyEvent
                    bird.setLinearVelocity(new Vec2(0f, 5f));
                    break;
                default:
                    System.out.println("Unsupported key keyReleased " + e);
            }
        }
    }


    private static WorldView createWorld(){
        World world = new World(); //создаем контейнер world

        // platform _
        StaticBody ground = new StaticBody(world, new BoxShape(50, 0.5f)); //(пар-р, new прямоуг(полШир,полДлин float))
        ground.setPosition(new Vec2(0f, -7.5f)); //.object(new class(по x,y позиция))
        BoxShape boxShape = new BoxShape(50, 0.5f);
        ground.setFillColor(new Color(0,0,0,0));
        ground.setLineColor(new Color(0,0,0,0));

        //serfer
        bird = new DynamicBody(world, new BoxShape(1,2));
        bird.setPosition(new Vec2(-13,-5)); //по х, у позиция
        bird.addImage(new BodyImage("data/bird.png", 4)); //("ссылка", высота)
        bird.setLinearVelocity(new Vec2(7,0)); //скорость по х, у !непостоянная скорость
        bird.setName("bird");
        createJumpSound();

        Image background = new ImageIcon("data/sky.jpg").getImage();

        return new UserView(world, width, height){
            @Override
            protected void paintBackground(Graphics2D g) {
                g.drawImage(background, 0, 0 ,background.getWidth(this),background.getHeight(this), this);
            }
            @Override
            protected void paintForeground(Graphics2D g) {
                g.setColor(Color.red);
                g.drawString("Press space to start", (getWidth()/2) - 200, 100);
            }
        };
    }

    private static void createAndStartGame(){
        WorldView view = createWorld();
        playBacksound(); //вызываем sound
        //у view есть world -> берем world в котором есть character, берем character, index)
        KeyListener listener = new KeyboardHandler();
        wrapWithSwingAndShow(view, listener); //обертываем в swing
        view.getWorld().start(); //запускаем симуляцию (DinamicBody работает)
    }



    private static void playBacksound(){
        try {
            SoundClip pickupSound = new SoundClip("data/backsound.wav"); //класс SoundClip - загружаем туда файл звука
            pickupSound.setVolume(.05); //задаем громкость
            pickupSound.loop(); //повтор (.play() -> до завершения аудиодорожки)

        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createJumpSound(){
        try {
            pickupJumpSound = new SoundClip("data/jumpSound.wav"); //класс SoundClip - загружаем туда файл звука
            pickupJumpSound.setVolume(.05); //задаем громкость

        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    private static void wrapWithSwingAndShow(JComponent view, KeyListener listener){ //(тип пар-р, тип пар-р)
        final JFrame frame = new JFrame("KL_01"); //создаем frame + название
        frame.setSize(width,height); //задаем размер
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

