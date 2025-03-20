package game;

import city.cs.engine.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//REQ: inheritance to create extensible groups of game assets
public class AssetFactory implements ActionListener {
    private final int maxCount;
    private int count = 0;
    protected final World world;
    private final Timer timer;

    public AssetFactory(World world, int creationDelay, int maxCount) {
        this.maxCount = maxCount;
        this.world = world;
        timer = new Timer(creationDelay,this);
        timer.setInitialDelay(0);
        timer.start();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (count < maxCount) {
            createAsset();
            count++;
        }
    }

    protected void createAsset(){
    }

    public void decCount(){
        count--;
    }

    public void stop(){
        timer.stop();
    }

    public World getWorld() {
        return world;
    }
}
