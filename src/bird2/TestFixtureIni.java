package bird2;

import city.cs.engine.AttachedImage;
import city.cs.engine.Body;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.DynamicBody;
import city.cs.engine.SolidFixture;
import city.cs.engine.UserView;
import city.cs.engine.World;
import city.cs.engine.WorldView;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class TestFixtureIni {
    private static int width = 1400;
    private static int height = 800;
    private static Bird bird;
//    private Bird bird;


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
        bird = new Bird(world);

        float yMax = 19f, halfWidth = 3.5f;
        float holeUp=17f;
        float halfHeightUp = (yMax-holeUp)/2f;
        float yCenterUp = (yMax+holeUp)/2f;
        Vec2 centerUp = new Vec2(0,yCenterUp);
        Vec2 centerDown = new Vec2(0,-yCenterUp);

        DynamicBody pipe;
        pipe = new DynamicBody(world);
        new SolidFixture(pipe,new BoxShape(halfWidth, halfHeightUp,centerUp));
        new SolidFixture(pipe,new BoxShape(halfWidth, halfHeightUp,centerDown));

        AttachedImage imageUp = pipe.addImage(new BodyImage("data/pipeUp.png",halfHeightUp));
        imageUp.setOffset(centerUp);
        imageUp.setScale(2f);
        AttachedImage imageDown = pipe.addImage(new BodyImage("data/pipeDown.png",halfHeightUp));
        imageDown.setOffset(centerDown);

        pipe.setPosition(new Vec2(35,0));
        pipe.setGravityScale(0f);
        pipe.setLinearVelocity(new Vec2(-7,0));
        pipe.setAngleDegrees(0f);
        pipe.setAngularVelocity(0f);
        pipe.setName("pipe");
        pipe.setAlwaysOutline(true);

        bird.addCollisionListener(new CollisionListener() {

            @Override
            public void collide(CollisionEvent collisionEvent) {
                if("pipe".equals(collisionEvent.getOtherBody().getName())) {
                    //restore linear and angle velocity of pipe
                    ((DynamicBody)collisionEvent.getOtherBody()).setLinearVelocity(new Vec2(-7,0));
                    ((DynamicBody)collisionEvent.getOtherBody()).setAngularVelocity(0);
//                    bird.setPosition((new Vec2(bird.getPosition().x - 2f, bird.getPosition().y)));
                    if (!hittedPipes.contains(collisionEvent.getOtherBody())){
                        hittedPipes.add(collisionEvent.getOtherBody());
                        bird.lostHealth();
                    }

                }
            }
        });


        Image background = new ImageIcon("data/sky.jpg").getImage();
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

                //drawing 1 coin
                g.setColor(Color.ORANGE);
                g.setFont(coinFont);
                g.drawString(bird.coins + "", (getWidth()/2) - 620, 100);

            }
        };
    }

    private static void createAndStartGame(){
        WorldView view = createWorld();
        //у view есть world -> берем world в котором есть character, берем character, index)
        KeyListener listener = new KeyboardHandler();
        wrapWithSwingAndShow(view, listener); //обертываем в swing
        view.getWorld().start(); //запускаем симуляцию (DinamicBody работает)
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
        SwingUtilities.invokeLater(TestFixtureIni::createAndStartGame);
    }
}

