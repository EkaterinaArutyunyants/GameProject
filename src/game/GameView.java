package game;

import city.cs.engine.UserView;

import javax.swing.*;
import java.awt.*;

public class GameView extends UserView {

    private static final Image heart = new ImageIcon("data/heart.png").getImage();
    private static final Image coin = new ImageIcon("data/coin.png").getImage();
    private static final Image imageYouWin = new ImageIcon("data/youWinBackground.png").getImage();
    private static final Image imageGameOver = new ImageIcon("data/gameOver.png").getImage();
    //font for press shift
    private static final Font foregroundFont = new Font("Bold", Font.BOLD, 20);
    //font for coin
    private static final Font coinFont = new Font("Bold", Font.BOLD, 37);

    public GameView(BasicLevel world, int width, int height) {
        super(world, width, height);
    }

    public void startLevel(int index){

    }
    //REQ: background, foreground rendering + visual layering
    @Override
    public BasicLevel getWorld(){
        return (BasicLevel)super.getWorld();
    }
    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(getWorld().getBackground(), 0, 0, this);
    }

    //REQ: paintForeground method
    @Override
    protected void paintForeground(Graphics2D g) {
        if (getWorld().isComplete()){
            if (getWorld().isSuccess()) {
                paintSuccess(g);
            } else{
                paintLost(g);
            }
        } else {
            paintGameState(g);
        }
    }

    private void paintGameState(Graphics2D g){
        //string speed up
        g.setColor(Color.darkGray);
        g.setFont(foregroundFont);
        g.drawString(getWorld().getName(), (getWidth() / 2) - 670, 100);
        g.drawString("Press SHIFT for speed up ", (getWidth() / 2) - 670, 140);
        g.drawString("Press SPACE for jump ", (getWidth() / 2) - 670, 180);

        //reduce size of img hearts
        int newWidth = heart.getWidth(this) / 35;
        int newHeight = heart.getHeight(this) / 35;

        //drawing 3 hearts
        for (int i = 0; i < getWorld().getHealth(); i++)
            g.drawImage(heart, 25 + i * 35, 20, newWidth, newHeight, this);

        //drawing 1 coin
        g.setColor(Color.ORANGE);
        g.setFont(coinFont);
        g.drawImage(coin, 25, 70, newWidth, newHeight, this);
        //REQ: coin statistics
        g.drawString(Integer.toString(getWorld().getScore()), (getWidth() / 2) - 580, 100);
    }

    //string when win game
    private void paintSuccess(Graphics2D g){
        g.setColor(Color.RED);
        g.setFont(foregroundFont);
        g.drawImage(imageYouWin, 0, 0, this);
//        g.drawString("You win! ", (getWidth() / 2) - 670, 140);
    }

    //string when lost game
    private void paintLost(Graphics2D g){
        g.setColor(Color.RED);
        g.setFont(foregroundFont);
        g.drawImage(imageGameOver, 0, 0, this);
//        g.drawString("Game over! ", (getWidth() / 2) - 670, 140);
    }

}
