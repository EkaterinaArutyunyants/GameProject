package surferGame;

import city.cs.engine.*;
import game.KeyboardHandlerTest;
import org.jbox2d.common.Vec2;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class surferMain {
    private static int width=610;
    private static int height = 430;
    public static SoundClip pickupJumpSound; //описание переменной

    private static WorldView createWorld(){
        World world = new World(); //создаем контейнер world

        // platform _
        StaticBody ground = new StaticBody(world, new BoxShape(50, 0.5f)); //(пар-р, new прямоуг(полШир,полДлин float))
        ground.setPosition(new Vec2(0f, -7.5f)); //.object(new class(по x,y позиция))
        BoxShape boxShape = new BoxShape(50, 0.5f);
        ground.setFillColor(new Color(0,0,0,0));
        ground.setLineColor(new Color(0,0,0,0));

        //serfer
        DynamicBody character = new DynamicBody(world, new BoxShape(1,2));
        character.setPosition(new Vec2(-13,-5)); //по х, у позиция
        character.addImage(new BodyImage("data/serfer.png", 4)); //("ссылка", высота)
        character.setLinearVelocity(new Vec2(7,0)); //скорость по х, у !непостоянная скорость
        character.setName("serfer");
        createJumpSound();

        return new UserView(world, width, height);
    }

    private static void createAndStartGame(){
        WorldView view = createWorld();
        JComponent viewWithBackground = addBackground2View(view); //вызываем background (swing)
        playBacksound(); //вызываем sound
        //у view есть world -> берем world в котором есть character, берем character, index)
        KeyListener listener = new KeyboardHandlerTest(view.getWorld().getDynamicBodies().get(0));
        wrapWithSwingAndShow(viewWithBackground,listener); //обертываем в swing
        view.getWorld().start(); //запускаем симуляцию (DinamicBody работает)
    }

    private static JComponent addBackground2View(WorldView view){ //берет пар-р view
        try {
            //layered pane
            JLayeredPane layeredPane = new JLayeredPane(); //как сэндвич
            layeredPane.setOpaque(false); //прозрачность
            layeredPane.setPreferredSize(new Dimension(width,height)); //размер

            //view объекты (чел)
            layeredPane.add(view,0); //индекс от 0 чем больше тем дальше
            view.setOpaque(false);
            view.setBounds(0, 0, width, height); //прямоуг resize от коорд. до width height в px

            //backImage считываем
            BufferedImage backImage = ImageIO.read(new File("data/ocean.jpg"));
            JLabel background = new JLabel(new ImageIcon(backImage)); //берем компонент JLabel и заполняем туда img
            layeredPane.add(background,1); //background дальше чем view
            background.setBounds(0, 0, width, height);

            return layeredPane;

        } catch (IOException e) {
            System.err.println(e);
        };
        return view;
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
        SwingUtilities.invokeLater(surferMain::createAndStartGame);
    }
}

