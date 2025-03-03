package bird2;

import city.cs.engine.Body;
import city.cs.engine.BodyImage;
import city.cs.engine.CircleShape;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.DynamicBody;
import city.cs.engine.Shape;
import city.cs.engine.SolidFixture;
import city.cs.engine.SoundClip;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import city.cs.engine.UserView;
import city.cs.engine.World;
import city.cs.engine.WorldView;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BirdGame2 {
    private static int width = 1400;
    private static int height = 800;
    public static SoundClip pickupJumpSound; //описание переменной
    private static Bird bird;
//    private Bird bird;

//    private static int moneyCounter = 0;
//    private static int maxMoneyCounter = 5;
//    private static long nextMoneyTime;

    private static Set<Body> hittedPipes = new HashSet<>();

    public static class KeyboardHandler extends KeyAdapter {
        //функция кей релисд
        @Override
        public void keyPressed(KeyEvent e) {
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
        PipeFactory factory = new PipeFactory(world);
        bird = new Bird(world);

        HeartFactory heartFactory = new HeartFactory(bird,world);
        CoinFactory coinFactory = new CoinFactory(bird,world);

        bird.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent collisionEvent) {
                Body body = collisionEvent.getOtherBody();
                if(body instanceof Coin){
                    body.destroy();
                    coinFactory.moneyCounter--;
                    bird.coins++;
                } else if(body instanceof Heart){
                    body.destroy();
                    heartFactory.heartCounter--;
                    bird.addHealth();
                } else if(body instanceof PipeNew){
                    //restore linear and angle velocity of pipe
                    ((PipeNew) body).restorePosition();
                    bird.setPosition((new Vec2(bird.getPosition().x - 2f, bird.getPosition().y)));
                    if (!hittedPipes.contains(collisionEvent.getOtherBody())){
                        hittedPipes.add(collisionEvent.getOtherBody());
                        bird.lostHealth();
                    }
                } else {
                    System.out.println("Unsupported collisionEvent " + collisionEvent);
                }
            }
        });

        //pipes, coins, hearts
        StepListener stepListener = new StepListener() {

            @Override
            public void preStep(StepEvent stepEvent) {
                factory.createNewPipeIfNeeded();
                heartFactory.createNewHeartIfNeeded();
                coinFactory.createNewCoinIfNeeded();
            }

            @Override
            public void postStep(StepEvent stepEvent) {

            }
        };
        world.addStepListener(stepListener);

        Image background = new ImageIcon("data/sky.jpg").getImage();
        Image heart = new ImageIcon("data/heart.png").getImage();
        Image coin = new ImageIcon("data/coin.png").getImage();
        //font for Press space
        final Font foregroundFont = new Font("Bold", Font.BOLD,60);
        //font for coin
        final Font coinFont = new Font("Bold", Font.BOLD,37);

        return new UserView(world, width, height){
            //background
            @Override
            protected void paintBackground(Graphics2D g) {
                g.drawImage(background, 0, 0 ,background.getWidth(this),background.getHeight(this), this);
            }

            //foreground
            @Override
            protected void paintForeground(Graphics2D g) {
                //string before start
                g.setColor(Color.darkGray);
                g.setFont(foregroundFont);
                //g.drawString("Press space to start " + health, (getWidth()/2) - 500, 400);

                //making img of hearts smaller
                int newWidth = heart.getWidth(this) / 35;  // Reduce size by half
                int newHeight = heart.getHeight(this) / 35;
                //drawing 3 hearts

                for (int i=0; i <bird.health;i++ )
                    g.drawImage(heart, 25+i*35, 20, newWidth, newHeight, this);


                //drawing 1 coin
                g.setColor(Color.ORANGE);
                g.setFont(coinFont);
                g.drawImage(coin, 25, 70, newWidth, newHeight, this);
                g.drawString(bird.coins + "", (getWidth()/2) - 620, 100);

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
            SoundClip pickupSound = new SoundClip("data/birdBacksound.wav"); //класс SoundClip - загружаем туда файл звука
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
        SwingUtilities.invokeLater(BirdGame2::createAndStartGame);
    }
}

