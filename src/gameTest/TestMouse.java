package gameTest;

import city.cs.engine.BodyImage;
import city.cs.engine.DynamicBody;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import city.cs.engine.UserView;
import city.cs.engine.World;
import city.cs.engine.WorldView;
import org.jbox2d.common.Vec2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestMouse {
    private static final int width=1024;
    private static final int height = 800;
    private static final Shape shape = new PolygonShape(-2.02f, 0.43f, -2.13f, -0.23f, -0.83f, -1.94f, -0.21f, -1.95f, 2.11f, -0.83f, 2.05f, 0.91f, 0.96f, 1.94f, -0.78f, 1.57f);
    private static final BodyImage image = new BodyImage("data/level1/bird.png", 4);
    private static final BodyImage imageBirdFlyUp = new BodyImage("data/level1/birdFlyUp.png", 4);
    private static  DynamicBody bird;


    private static class MouseHandler extends MouseAdapter {
        private final UserView view;
        private boolean overBird=false;

        private MouseHandler(UserView view) {
            this.view = view;
        }
        public void mouseClicked(MouseEvent e) {
            if (bird.contains(view.viewToWorld(e.getPoint()))) {
                bird.destroy();
            }
        }
        public void mouseMoved(MouseEvent e){
            if (bird.contains(view.viewToWorld(e.getPoint()))) {
                if (overBird) return;
                overBird = true;
                bird.removeAllImages();
                bird.addImage(imageBirdFlyUp);
            } else{
                if (!overBird) return;
                overBird = false;
                bird.removeAllImages();
                bird.addImage(image);
            }
        }
    }


    private static WorldView createWorld(){
        World world = new World();
        bird = new DynamicBody(world, shape);
        bird.setPosition(new Vec2(7,-9));
        bird.addImage(image);
        bird.setAlwaysOutline(true);
        bird.setGravityScale(0f);
        UserView view = new UserView(world, width, height);
        var mouseHandler = new MouseHandler(view);
        view.addMouseListener(mouseHandler);
        view.addMouseMotionListener(mouseHandler);
        return view;
    }

    private static void createAndStartGame(){
        WorldView view = createWorld();
        JComponent viewWithBackground = addBackground2View(view); //вызываем background (swing)
        wrapWithSwingAndShow(viewWithBackground); //обертываем в swing
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


    private static void wrapWithSwingAndShow(JComponent view){ //(тип пар-р, тип пар-р)
        final JFrame frame = new JFrame("Test Mouse"); //создаем frame + название
        frame.setSize(width,height); //задаем размер
        frame.add(view); //в созданный view добавляем frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TestMouse::createAndStartGame);
    }
}

