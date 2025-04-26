package game;

import city.cs.engine.UserView;
import city.cs.engine.World;

import javax.swing.*;
import java.awt.*;

/**
 * GameView class - game world visualization
 * Display: background, health, score, win/lost
 */

public class GameView extends UserView {
    //img for displaying objects
    private static final Image heart = new ImageIcon("data/heart.png").getImage();
    private static final Image coin = new ImageIcon("data/coin.png").getImage();
    private static final Image imageYouWin = new ImageIcon("data/youWinBackground.png").getImage();
    private static final Image imageGameOver = new ImageIcon("data/gameOver.png").getImage();
    //font for coin
    private static final Font coinFont = new Font("Bold", Font.BOLD, 37);
    //time between completed level and next one
    private static final int waitUntil =30;

    private final BirdGame game;
    private int waitCount;

    /**
     * Constructor for game view
     * @param game - reference to main BirdGame instance
     * @param world -display game world
     * @param width of the view
     * @param height of the view
     */
    public GameView(BirdGame game, World world, int width, int height) {
        super(world, width, height);
        this.game = game;
    }

    /**
     * draws background image
     * @param g graphic context
     */
    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(((WorldWithBackground)getWorld()).getBackground(), 0, 0, this);
    }

    /**
     * draws in the foreground
     * @param g graphic context
     */
    @Override
    protected void paintForeground(Graphics2D g){
        if (getWorld() instanceof BasicLevel level){
            paintForeground(level,g);
        }

    }

    /**
     * draws foreground
     * @param level - current level which is displaying
     * @param g graphic context
     */
    protected void paintForeground(BasicLevel level, Graphics2D g) {
        if (level.isComplete()){
            if (level.isSuccess()) {
                paintSuccess(g); //you win message
            } else{
                paintLost(g); //game over message
            }
            waitSomeTimeToCompleteLevel(level); //allow to see the result
        } else {
            waitCount=0; // level is active -> resetting delay counter
            paintGameState(level, g); //coins and hearts
        }
    }

    /**
     * delay for player to see result of his game play
     * @param level that have been just completed
     */
    private void waitSomeTimeToCompleteLevel(BasicLevel level){
        waitCount++;
        if (waitCount>= waitUntil) {
            waitCount=0;
            game.completeLevel(level); // level is done
        }
    }

    /**
     * paint health, coin score, etc
     * @param level current level
     * @param g graphics context
     */
    private void paintGameState(BasicLevel level, Graphics2D g){
        //reduce size of img hearts
        int newWidth = heart.getWidth(this) / 35;
        int newHeight = heart.getHeight(this) / 35;

        //drawing hearts
        for (int i = 0; i < level.getHealth(); i++)
            g.drawImage(heart, 25 + i * 35, 20, newWidth, newHeight, this);

        //drawing 1 coin
        g.setColor(Color.ORANGE);
        g.setFont(coinFont);
        g.drawImage(coin, 25, 70, newWidth, newHeight, this);
        //coin statistics
        g.drawString(Integer.toString(level.getScore()), (getWidth() / 2) - 580, 100);
    }

    /**
     * paints img when win game
     * @param g graphics context
     */
    private void paintSuccess(Graphics2D g){
        g.drawImage(imageYouWin, 0, 0, this);
    }

    /**
     * paints img when lost game
     * @param g graphics context
     */
    private void paintLost(Graphics2D g){
        g.drawImage(imageGameOver, 0, 0, this);
    }

}
