package game;

import city.cs.engine.UserView;
import city.cs.engine.World;

import javax.swing.*;
import java.awt.*;

public class GameView extends UserView {

    private static final Image heart = new ImageIcon("data/heart.png").getImage();
    private static final Image coin = new ImageIcon("data/coin.png").getImage();
    private static final Image imageYouWin = new ImageIcon("data/youWinBackground.png").getImage();
    private static final Image imageGameOver = new ImageIcon("data/gameOver.png").getImage();
    //font for coin
    private static final Font coinFont = new Font("Bold", Font.BOLD, 37);
    private static final int waitUntil =30;
    private final BirdGame game;
    private int waitCount;

    public GameView(BirdGame game, World world, int width, int height) {
        super(world, width, height);
        this.game = game;
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(((WorldWithBackground)getWorld()).getBackground(), 0, 0, this);
    }
    @Override
    protected void paintForeground(Graphics2D g){
        if (getWorld() instanceof BasicLevel level){
            paintForeground(level,g);
        }

    }

    protected void paintForeground(BasicLevel level, Graphics2D g) {
        if (level.isComplete()){
            if (level.isSuccess()) {
                paintSuccess(g);
            } else{
                paintLost(g);
            }
            waitSomeTimeToCompleteLevel(level);
        } else {
            waitCount=0;
            paintGameState(level, g);
        }
    }

    private void waitSomeTimeToCompleteLevel(BasicLevel level){
        waitCount++;
        if (waitCount>= waitUntil) {
            waitCount=0;
            game.completeLevel(level);
        }
    }

    private void paintGameState(BasicLevel level, Graphics2D g){
        //reduce size of img hearts
        int newWidth = heart.getWidth(this) / 35;
        int newHeight = heart.getHeight(this) / 35;

        //drawing 3 hearts
        for (int i = 0; i < level.getHealth(); i++)
            g.drawImage(heart, 25 + i * 35, 20, newWidth, newHeight, this);

        //drawing 1 coin
        g.setColor(Color.ORANGE);
        g.setFont(coinFont);
        g.drawImage(coin, 25, 70, newWidth, newHeight, this);
        //REQ: coin statistics
        g.drawString(Integer.toString(level.getScore()), (getWidth() / 2) - 580, 100);
    }

    //string when win game
    private void paintSuccess(Graphics2D g){
        g.drawImage(imageYouWin, 0, 0, this);
    }

    //string when lost game
    private void paintLost(Graphics2D g){
        g.drawImage(imageGameOver, 0, 0, this);
    }

}
