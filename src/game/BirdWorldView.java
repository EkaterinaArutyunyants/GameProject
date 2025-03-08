package game;

import city.cs.engine.UserView;

import javax.swing.*;
import java.awt.*;

public class BirdWorldView extends UserView {

    private static final Image background = new ImageIcon("data/sky.jpg").getImage();
    private static final Image heart = new ImageIcon("data/heart.png").getImage();
    private static final Image coin = new ImageIcon("data/coin.png").getImage();
    //font for Press space
    private static final Font foregroundFont = new Font("Bold", Font.BOLD, 20);
    //font for coin
    private static final Font coinFont = new Font("Bold", Font.BOLD, 37);

    public BirdWorldView(BirdWorld world, int width, int height) {
        super(world, width, height);
    }

    @Override
    public BirdWorld getWorld(){
        return (BirdWorld)super.getWorld();
    }
    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, this);
    }

    //foreground
    @Override
    protected void paintForeground(Graphics2D g) {
        if (getWorld().isGameOver()){
            g.setColor(Color.darkGray);
            g.setFont(foregroundFont);
            g.drawString("Game over ", (getWidth() / 2) - 670, 140);

        } else {
            //string before start
            g.setColor(Color.darkGray);
            g.setFont(foregroundFont);
            g.drawString("Press shift for speed up ", (getWidth() / 2) - 670, 140);

            //making img of hearts smaller
            int newWidth = heart.getWidth(this) / 35;  // Reduce size by half
            int newHeight = heart.getHeight(this) / 35;
            //drawing 3 hearts

            for (int i = 0; i < getWorld().getBird().getHealth(); i++)
                g.drawImage(heart, 25 + i * 35, 20, newWidth, newHeight, this);


            //drawing 1 coin
            g.setColor(Color.ORANGE);
            g.setFont(coinFont);
            g.drawImage(coin, 25, 70, newWidth, newHeight, this);
            g.drawString(Integer.toString(getWorld().getBird().getCoins()), (getWidth() / 2) - 580, 100);
        }
    }

}
