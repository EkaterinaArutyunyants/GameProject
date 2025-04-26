package game;

import city.cs.engine.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Asset Factory class - for creating objects (coins, hearts, etc) in the game
 * REQ: inheritance to create extensible groups of game assets
 */
public abstract class AssetFactory implements ActionListener {
    private final int maxCount; //max asset num
    private int count = 0; // active assets current num
    protected final BasicLevel level; //level for this factory
    private final Timer timer; //swing timer for scheduling assets

    /**
     * Constructor for asset factory
     * @param level for this factory
     * @param creationDelay - time between creating each asset
     * @param maxCount - max asset num
     */
    public AssetFactory(BasicLevel level, int creationDelay, int maxCount){
        this(level,0, creationDelay,maxCount);
    }

    /**
     * Constructor for asset factory
     * @param level for this factory
     * @param initialDelay waiting time before creating first asset
     * @param creationDelay time between creating each asset
     * @param maxCount
     */
    public AssetFactory(BasicLevel level, int initialDelay, int creationDelay, int maxCount) {
        this.maxCount = maxCount;
        this.level = level;
        timer = new Timer(creationDelay,this);
        timer.setInitialDelay(initialDelay);
    }

    /**
     * method to create new asset, when asset num is less than maximum
     *
     * @param e event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (count < maxCount) {
            createAsset();
            count++;
        }
    }

    /**
     * for implementing this method for defining how to create asset
     * for different objects
     */
    abstract protected void createAsset();

    /**
     * starts the timer for creating asset
     */
    public void start(){
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    /**
     * stops the timer
     */
    public void stop(){
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    /**
     * calling this method to decrease asset count when asset is destroyed
     */
    public void decCount(){
        count--;
    }

    /**
     * method to return level
     * @return level of this factory
     */
    public BasicLevel getLevel() {
        return level;
    }
}
