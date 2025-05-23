package gameTest;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestStep {
    private static int width = 1024;
    private static int height = 800;
    public static SoundClip pickupJumpSound; //описание переменной
    private static DynamicBody character;
    private static int redBallCounter = 0;
    private static int maxRedBallCounter = 3;
    private static long nextRedBallTime;

    public static class KeyboardHandlerFlip extends KeyAdapter {
        @Override
        //функция кей прессед
        public void keyPressed(KeyEvent e) {
            System.out.println("keyPressed(" + e + ")");
        }

        //функция кей релисд
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode(); //method getKeyCode()
            AttachedImage img = character.getImages().get(0);

            switch (key) {
                case KeyEvent.VK_W: //class KeyEvent
                    character.setLinearVelocity(new Vec2(0f, 5f));
                    pickupJumpSound.play();
//                System.out.println("jump");
                    break;
                case KeyEvent.VK_A:
                    character.setLinearVelocity(new Vec2(-5f, 0f));
//                System.out.println("left");
                    if (img.isFlippedHorizontal()) { //flip если уже перевернут
                        img.flipHorizontal();
                    }
                    break;
                case KeyEvent.VK_D:
                    character.setLinearVelocity(new Vec2(5f, 0f));
//                System.out.println("right");
                    if (!img.isFlippedHorizontal()) { // ! - flip когда еще не перевернут
                        img.flipHorizontal();
                    }
                    break;

                default:
                    System.out.println("Unsupported key keyReleased " + e);
            }
        }
    }

    private static WorldView createWorld() {
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
        character = new DynamicBody(world, new BoxShape(1, 2));
        character.setPosition(new Vec2(7, -9)); //по х, у позиция
        character.addImage(new BodyImage("data/student.png", 4)); //("ссылка", высота)
        character.setLinearVelocity(new Vec2(-6, 0)); //скорость по х, у !непостоянная скорость
        character.setName("boy");
        createJumpSound();
        character.setAlwaysOutline(true);

        StepListener stepListener = new StepListener() {
            @Override
            public void preStep(StepEvent stepEvent) {
                if (redBallCounter < maxRedBallCounter && nextRedBallTime < System.currentTimeMillis()) {
                    DynamicBody redBall = new DynamicBody(world, new CircleShape(0.2f));
                    redBall.setPosition(new Vec2(-10, -9));
                    redBall.setFillColor(Color.RED);
                    redBall.setLinearVelocity(new Vec2(5, 0f));
                    redBallCounter++;
                    nextRedBallTime = System.currentTimeMillis() + 2000;
                }
            }

            @Override
            public void postStep(StepEvent stepEvent) {

            }
        };
        world.addStepListener(stepListener);

//        character.addCollisionListener(new CollisionListener() {
//
//            @Override
//            public void collide(CollisionEvent collisionEvent) {
//                //System.out.println(collisionEvent);
//                if ("books".equals(collisionEvent.getOtherBody().getName())) {
//                    pickupJumpSound.play();
//                }
//            }
//        });

        //second character
//        DynamicBody secCharacter = new DynamicBody(world, new BoxShape(1,2));
//        secCharacter.setPosition(new Vec2(-7,-9));
//        secCharacter.addImage(new BodyImage("data/books.png", 4));
//        secCharacter.setLinearVelocity(new Vec2(6,0));
//        secCharacter.setName("books");
//        secCharacter.setAlwaysOutline(true);

        return new UserView(world, width, height);
    }

    private static void createAndStartGame() {
        WorldView view = createWorld();
        JComponent viewWithBackground = addBackground2View(view); //вызываем background (swing)
        //playBacksound(); //вызываем sound
        //у view есть world -> берем world в котором есть character, берем character, index)
        KeyListener listener = new KeyboardHandlerFlip();
        wrapWithSwingAndShow(viewWithBackground, listener); //обертываем в swing
        view.getWorld().start(); //запускаем симуляцию (DinamicBody работает)
    }

    private static JComponent addBackground2View(WorldView view) { //берет пар-р view
        try {
            //layered pane
            JLayeredPane layeredPane = new JLayeredPane(); //как сэндвич
            layeredPane.setOpaque(false); //прозрачность
            layeredPane.setPreferredSize(new Dimension(width, height)); //размер

            //view объекты (чел)
            layeredPane.add(view, 0); //индекс от 0 чем больше тем дальше
            view.setOpaque(false);
            view.setBounds(0, 0, width, height); //прямоуг resize от коорд. до width height в px

            //backImage считываем
            BufferedImage backImage = ImageIO.read(new File("data/background.jpg"));
            JLabel background = new JLabel(new ImageIcon(backImage)); //берем компонент JLabel и заполняем туда img
            layeredPane.add(background, 1); //background дальше чем view
            background.setBounds(0, 0, width, height);
            return layeredPane;

        } catch (IOException e) {
            System.err.println(e);
        }
        ;
        return view;
    }

    private static void playBacksound() {
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

    public static void createJumpSound() {
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

    private static void wrapWithSwingAndShow(JComponent view, KeyListener listener) { //(тип пар-р, тип пар-р)
        final JFrame frame = new JFrame("KL_01"); //создаем frame + название
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
        SwingUtilities.invokeLater(TestStep::createAndStartGame);
    }
}

