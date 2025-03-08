package game;

import city.cs.engine.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HeartFactory implements ActionListener {
    private final int maxCount = 5;
    private static final int creationDelay = 7000;
    public int count = 0;
    private final World world;
    private final Timer timer;

    public HeartFactory(World world) {
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
            new Heart(world);
            count++;
        }
    }


    public void decCount(){
        count--;
    }

}
