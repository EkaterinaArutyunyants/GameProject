package game;

import city.cs.engine.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoinFactory  implements ActionListener {
    private final int maxCount = 5;
    private static final int creationDelay = 8000;
    private int count = 0;
    private final World world;
    private final Timer timer;

    public CoinFactory(World world) {
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
            new Coin(world, 2);
            count++;
        }
    }

    public void decCount(){
        count--;
    }

}
