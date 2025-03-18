package bird;

import city.cs.engine.*;
import city.cs.engine.Shape;
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

public class BirdGame {
    private static int width = 1400;
    private static int height = 800;
    public static SoundClip pickupJumpSound; //описание переменной
    private static DynamicBody bird;
//    private Bird bird;

    private static int pipeUpCounter = 0;
    private static int maxPipeUpCounter = 5;
    private static long nextPipeUpTime;

    private static int pipeDownCounter = 0;
    private static int maxPipeDownCounter = 5;
    private static long nextPipeDownTime;

    private static int moneyCounter = 0;
    private static int maxMoneyCounter = 5;
    private static long nextMoneyTime;

    private static int heartCounter = 0;
    private static int maxHeartCounter = 5;
    private static long nextHeartTime;

    private static float[] heightsUp = {15f,12f,17f};
    private static float[] heightsDown = {15f,17f,12f};
    private static int idxH = 0;

    //userView foreground
    private static int health = 3;
    private static int cash = 0;

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

        //bird
//        Bird bird = new Bird(world);
//        bird = new DynamicBody(world, new CircleShape(2));
        bird = new Walker(world);
        Shape birdShape = new CircleShape(2);
        SolidFixture fixture = new SolidFixture(bird, birdShape);
        fixture.setDensity(100);
        bird.addImage(new BodyImage("data/bird.png", 4)); //("ссылка", высота)
        bird.setPosition(new Vec2(-13,-5)); //по х, у позиция
        bird.setLinearVelocity(new Vec2(7,0)); //скорость по х, у !непостоянная скорость
        bird.setName("bird");

        //pipes, coins, hearts
        StepListener stepListener = new StepListener() {

            @Override
            public void preStep(StepEvent stepEvent) {
                //upper pipes
                if (pipeUpCounter < maxPipeUpCounter && nextPipeUpTime < System.currentTimeMillis()) {
                    DynamicBody pipeUp = new DynamicBody(world, new BoxShape(3.5f, heightsUp[idxH]));
                    pipeUp.setPosition(new Vec2(35,15));
                    pipeUp.addImage(new BodyImage("data/pipeUp.png", heightsUp[idxH] * 2)); //("ссылка", высота)
                    pipeUp.setGravityScale(0f);
                    pipeUp.setLinearVelocity(new Vec2(-7,0));
                    pipeUp.setName("pipeUp");
                    bird.addCollisionListener(new CollisionListener() {

                        @Override
                        public void collide(CollisionEvent collisionEvent) {
                            if("pipeUp".equals(collisionEvent.getOtherBody().getName())) {
                                //restore linear and angle velocity of pipe
                                ((DynamicBody)collisionEvent.getOtherBody()).setLinearVelocity(new Vec2(-7,0));
                                ((DynamicBody)collisionEvent.getOtherBody()).setAngularVelocity(0);
                                bird.setPosition((new Vec2(bird.getPosition().x - 2f, bird.getPosition().y)));
                                if (!hittedPipes.contains(collisionEvent.getOtherBody())){
                                    hittedPipes.add(collisionEvent.getOtherBody());
                                    health--;
                                    if(health <= 0)  //1 operator
                                        bird.destroy();
                                }

                            }
                        }
                    });
                    pipeUpCounter--; //infinity pipes up
                    pipeUpCounter++;
                    nextPipeUpTime = System.currentTimeMillis() + 4000;
                }

                //down pipes
                if (pipeDownCounter < maxPipeDownCounter && nextPipeDownTime < System.currentTimeMillis()) {
                    DynamicBody pipeDown = new DynamicBody(world, new BoxShape(3.5f, heightsDown[idxH]));
                    pipeDown.setPosition(new Vec2(35,-22f));
                    pipeDown.addImage(new BodyImage("data/pipeDown.png", heightsDown[idxH] * 2)); //("ссылка", высота)
                    pipeDown.setGravityScale(0f);
                    pipeDown.setLinearVelocity(new Vec2(-7,0));
                    pipeDown.setName("pipeDown");
                    bird.addCollisionListener(new CollisionListener() {
                        @Override
                        public void collide(CollisionEvent collisionEvent) {
                            if("pipeDown".equals(collisionEvent.getOtherBody().getName())) {
                                ((DynamicBody)collisionEvent.getOtherBody()).setLinearVelocity(new Vec2(-7,0));
                                ((DynamicBody)collisionEvent.getOtherBody()).setAngularVelocity(0);
                                bird.setPosition((new Vec2(bird.getPosition().x - 2f, bird.getPosition().y)));
                                if (!hittedPipes.contains(collisionEvent.getOtherBody())){
                                    hittedPipes.add(collisionEvent.getOtherBody());
                                    health--;
                                    if(health <= 0)  //1 operator
                                        bird.destroy();
                                }

                            }
                        }
                    });
                    idxH++;
                    if (idxH == heightsUp.length) {
                        idxH = 0;
                    }
                    pipeDownCounter--; //infinity pipes down
                    pipeDownCounter++;
                    nextPipeDownTime = System.currentTimeMillis() + 4000;
                }

                //coins
                if (moneyCounter < maxMoneyCounter && nextMoneyTime < System.currentTimeMillis()) {
                    DynamicBody money = new DynamicBody(world, new CircleShape(1f));
                    money.setPosition(new Vec2(30,0));
                    money.addImage(new BodyImage("data/coin.png", 2));
                    money.setGravityScale(0f);
                    money.setLinearVelocity(new Vec2(-7,0));
                    money.setName("money");
                    money.addCollisionListener(new CollisionListener() {

                        @Override
                        public void collide(CollisionEvent collisionEvent) {
                            if ("bird".equals(collisionEvent.getOtherBody().getName())) {
                                money.destroy();
                                moneyCounter--;
                                cash++;
                            }
                        }
                    });
                    moneyCounter++;
                    nextMoneyTime = System.currentTimeMillis() + 7000;
                }

                //hearts
                if (heartCounter < maxHeartCounter && nextHeartTime < System.currentTimeMillis()) {
                    DynamicBody heart = new DynamicBody(world, new CircleShape(1f));
                    heart.setPosition(new Vec2(30,0));
                    heart.addImage(new BodyImage("data/heart.png", 2));
                    heart.setGravityScale(0f);
                    heart.setLinearVelocity(new Vec2(-7,0));
                    heart.setName("heart");
                    heart.addCollisionListener(new CollisionListener() {

                        @Override
                        public void collide(CollisionEvent collisionEvent) {
                            if ("bird".equals(collisionEvent.getOtherBody().getName())) {
                                heart.destroy();
                                heartCounter--;
                                health++;
                            }
                        }
                    });
                    heartCounter++;
                    nextHeartTime = System.currentTimeMillis() + 8000;
                }
            }

            @Override
            public void postStep(StepEvent stepEvent) {

            }
        };
        world.addStepListener(stepListener);

        Image background = new ImageIcon("data/level1/sky.jpg").getImage();
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

                for (int i=0; i <health;i++ )
                    g.drawImage(heart, 25+i*35, 20, newWidth, newHeight, this);


                //drawing 1 coin
                g.setColor(Color.ORANGE);
                g.setFont(coinFont);
                g.drawImage(coin, 25, 70, newWidth, newHeight, this);
                g.drawString(cash + "", (getWidth()/2) - 620, 100);

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
        SwingUtilities.invokeLater(BirdGame::createAndStartGame);
    }
}

