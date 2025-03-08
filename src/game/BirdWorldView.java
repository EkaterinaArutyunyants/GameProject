package game;

import city.cs.engine.UserView;

import javax.swing.*;
import java.awt.*;

public class BirdWorldView extends UserView {
    private final BirdWorld world;
    private final Image background = new ImageIcon("data/sky.jpg").getImage();
    private final Image heart = new ImageIcon("data/heart.png").getImage();
    private final Image coin = new ImageIcon("data/coin.png").getImage();
    //font for Press space
    final Font foregroundFont = new Font("Bold", Font.BOLD, 20);
    //font for coin
    final Font coinFont = new Font("Bold", Font.BOLD, 37);

    public BirdWorldView(BirdWorld world, int width, int height) {
        super(world, width, height);
        this.world = world;
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, background.getWidth(this), background.getHeight(this), this);
    }

    //foreground
    @Override
    protected void paintForeground(Graphics2D g) {
        //string before start
        g.setColor(Color.darkGray);
        g.setFont(foregroundFont);
        g.drawString("Press shift for speed up ", (getWidth() / 2) - 670, 140);
        //g.drawString("Press space to start ", (getWidth()/2) - 500, 400);

        //making img of hearts smaller
        int newWidth = heart.getWidth(this) / 35;  // Reduce size by half
        int newHeight = heart.getHeight(this) / 35;
        //drawing 3 hearts

        for (int i = 0; i < world.getBird().getHealth(); i++)
            g.drawImage(heart, 25 + i * 35, 20, newWidth, newHeight, this);


        //drawing 1 coin
        g.setColor(Color.ORANGE);
        g.setFont(coinFont);
        g.drawImage(coin, 25, 70, newWidth, newHeight, this);
        g.drawString(world.getBird().getCoins() + "", (getWidth() / 2) - 620, 100);

    }


}
