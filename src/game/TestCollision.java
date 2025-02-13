package game;

import city.cs.engine.*;
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

import static game.KL_01MainInit.pickupJumpSound;

public class TestCollision {
    private static int width=1024;
    private static int height = 800;
    public static SoundClip pickupJumpSound; //описание переменной

    private static WorldView createWorld(){
        World world = new World(); //создаем контейнер world

        //platform _
        StaticBody ground = new StaticBody(world, new BoxShape(11, 0.5f)); //(пар-р, new прямоуг(полШир,полДлин float))
        ground.setPosition(new Vec2(0f, -11.5f)); //.object(new class(по x,y позиция))
        ground.setFillColor(Color.RED); //цвет

        //left platform
        StaticBody platformLeft = new StaticBody(world, new BoxShape(0.5f, 5f));
        platformLeft.setPosition(new Vec2(-10.5f, -6f));
        platformLeft.setFillColor(Color.BLUE);

        //right platform
        StaticBody platformRight = new StaticBody(world, new BoxShape(0.5f, 5f));
        platformRight.setPosition(new Vec2(10.5f, -6f));
        platformRight.setFillColor(Color.GREEN);

        //first character
        DynamicBody character = new DynamicBody(world, new BoxShape(1,2));
        character.setPosition(new Vec2(7,-9)); //по х, у позиция
        character.addImage(new BodyImage("data/student.png", 4)); //("ссылка", высота)
        character.setLinearVelocity(new Vec2(-6,0)); //скорость по х, у !непостоянная скорость
        character.setName("boy");
        createJumpSound();
        character.setAlwaysOutline(true);
        character.addCollisionListener(new CollisionListener() {

            @Override
            public void collide(CollisionEvent collisionEvent) {
                //System.out.println(collisionEvent);
                if (collisionEvent.getOtherBody().getName().equals("books")) {
                    pickupJumpSound.play();
                }
            }
        });

        //second character
        DynamicBody secCharacter = new DynamicBody(world, new BoxShape(1,2));
        secCharacter.setPosition(new Vec2(-7,-9));
        secCharacter.addImage(new BodyImage("data/books.png", 4));
        secCharacter.setLinearVelocity(new Vec2(6,0));
        secCharacter.setName("books");
        secCharacter.setAlwaysOutline(true);

        return new UserView(world, width, height);
    }

    private static void createAndStartGame(){
        WorldView view = createWorld();
        JComponent viewWithBackground = addBackground2View(view); //вызываем background (swing)
        //playBacksound(); //вызываем sound
        //у view есть world -> берем world в котором есть character, берем character, index)
        KeyListener listener = new KeyboardHandlerTest(view.getWorld().getDynamicBodies().get(1));
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
            BufferedImage backImage = ImageIO.read(new File("data/background.jpg"));
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
        SwingUtilities.invokeLater(TestCollision::createAndStartGame);
    }
}

