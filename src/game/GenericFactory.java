package game;

import city.cs.engine.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//REQ: inheritance to create extensible groups of game assets
public class GenericFactory implements ActionListener {
    private final int maxCount = 5;
    private int count = 0;
    protected final World world;
    private final Timer timer;

    public GenericFactory(World world, int creationDelay) {
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

}
