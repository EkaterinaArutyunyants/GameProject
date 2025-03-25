package game;

import city.cs.engine.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//REQ: inheritance to create extensible groups of game assets
public class AssetFactory implements ActionListener {
    private final int maxCount;
    private int count = 0;
    protected final BasicLevel level;
    private final Timer timer;

    public AssetFactory(BasicLevel level, int creationDelay, int maxCount) {
        this.maxCount = maxCount;
        this.level = level;
        timer = new Timer(creationDelay,this);
        timer.setInitialDelay(0);
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

    public void start(){
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public void decCount(){
        count--;
    }



    public void stop(){
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    public BasicLevel getLevel() {
        return level;
    }
}
