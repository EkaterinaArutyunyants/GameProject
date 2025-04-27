package gameTest;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.Objects;

public class TestGravity {
    private static int width = 1024;
    private static int height = 800;


    private static WorldView createWorld() {
        World world = new World(); //создаем контейнер world

        //platform _
        StaticBody ground = new StaticBody(world, new BoxShape(11, 0.5f)); //(пар-р, new прямоуг(полШир,полДлин float))
        ground.setPosition(new Vec2(0f, -11.5f)); //.object(new class(по x,y позиция))
        ground.setFillColor(Color.RED); //цвет

        //left flying platform
        DynamicBody platformLeft = new DynamicBody(world, new BoxShape(0.5f, 5f));
        platformLeft.setPosition(new Vec2(-10.5f, -0f));
        platformLeft.setFillColor(Color.BLUE);
        platformLeft.setGravityScale(0f);
        platformLeft.setLinearVelocity(new Vec2(5f, 0));

        //right platform
        StaticBody platformRight = new StaticBody(world, new BoxShape(0.5f, 5f));
        platformRight.setFillColor(Color.GREEN);
        platformRight.putOn(10.5f, ground);

        final Image background = new ImageIcon("data/background.jpg").getImage();
        return new UserView(world, width, height) {
            @Override
            protected void paintBackground(Graphics2D g) {
                g.drawImage(background, 0, 0, this);
            }
        };
    }

    private static void createAndStartGame() {
        WorldView view = createWorld();
        //playBacksound(); //вызываем sound
        //у view есть world -> берем world в котором есть character, берем character, index)
        wrapWithSwingAndShow(view, null); //обертываем в swing
        view.getWorld().start(); //запускаем симуляцию (DinamicBody работает)
    }


    private static void wrapWithSwingAndShow(JComponent view, KeyListener listener) { //(тип пар-р, тип пар-р)
        final JFrame frame = new JFrame("KL_01"); //создаем frame + название
        frame.setSize(width, height); //задаем размер
        frame.add(view); //в созданный view добавляем frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        if (Objects.nonNull(listener)) frame.addKeyListener(listener); //клавиши
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TestGravity::createAndStartGame);
    }
}

