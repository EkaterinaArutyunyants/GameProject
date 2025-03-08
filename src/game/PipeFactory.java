package game;

import city.cs.engine.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PipeFactory implements ActionListener {
    private static final float[] holes = {4.5f,6f,7.5f};
    private static final int creationDelay = 4000;
    private final World world;
    private final Timer timer;
    private int idx = 0;

    public PipeFactory(World world) {
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
        new Pipe(world, holes[idx++]);
        idx%=holes.length;
    }
}
