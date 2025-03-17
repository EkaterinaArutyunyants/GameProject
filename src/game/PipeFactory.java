package game;

import city.cs.engine.World;

import javax.swing.*;

public class PipeFactory extends GenericFactory {
    private static final float[] holes = {4.5f, 6f, 7.5f};
    private int idx = 0;

    public PipeFactory(World world, int creationDelay) {
        super(world, creationDelay);
    }

    @Override
    protected void createAsset() {
        super.createAsset();
        new Pipe(world, holes[idx++]);
        idx %= holes.length;
    }
}
