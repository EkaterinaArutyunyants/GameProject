package game;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.DynamicBody;
import city.cs.engine.StaticBody;
import city.cs.engine.UserView;
import city.cs.engine.World;
import city.cs.engine.WorldView;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestFrontAndBack {
    private static int width=1024;
    private static int height = 800;
    private static Image background;
    private static long t;
    private static long dt = 50L;
    private static int dx = 0;
    private static int dy = -10;
    private static int x=0;
    private static int y=0;


    private static WorldView createGame(){
        World world = new World();

        StaticBody ground = new StaticBody(world, new BoxShape(11, 0.5f));
        ground.setPosition(new Vec2(0f, -11.5f));
        ground.setFillColor(Color.RED);

        DynamicBody character = new DynamicBody(world, new BoxShape(1,2));
        character.setPosition(new Vec2(7,-9));
        character.addImage(new BodyImage("data/student.png", 4));
        character.setLinearVelocity(new Vec2(-10,5));

        background = new ImageIcon("data/background.jpg").getImage();
        x=0;
        y=0;
        t=System.currentTimeMillis()+dt;

        final Font foregroundFont = new Font("Serif", Font.ITALIC ,60);
        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss:SSS");

        return new UserView(world, width, height){
            @Override
            protected void paintBackground(Graphics2D g) {
                if (t <=System.currentTimeMillis()){
                    x+=dx;
                    y+=dy;
                    t=System.currentTimeMillis()+dt;
                }
                g.drawImage(background, 0, 0 ,background.getWidth(this),background.getHeight(this),
                        x, y ,x+background.getWidth(this),y+background.getHeight(this),
                        this);
            }
            @Override
            protected void paintForeground(Graphics2D g) {
                g.setColor(Color.red);
                g.setFont(foregroundFont);
                g.drawString("Time: " + timeFormat.format(Calendar.getInstance().getTime()), (getWidth()/2) - 200, 100);
            }
        };
    }

    private static void createAndStartGame(){
        WorldView view = createGame();
        wrapWithSwingAndShow(view);
        view.getWorld().start();
    }

    private static void wrapWithSwingAndShow(JComponent view){
        final JFrame frame = new JFrame("KL_01");
        frame.setSize(width,height);
        frame.add(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TestFrontAndBack::createAndStartGame);
    }
}
