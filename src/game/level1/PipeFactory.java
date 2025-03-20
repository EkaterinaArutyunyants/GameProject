package game.level1;

import city.cs.engine.World;
import game.AssetFactory;

public class PipeFactory extends AssetFactory {
    private static final float[] holes = {4.5f, 6f, 7.5f};
    private int idx = 0;

    public PipeFactory(World world, int creationDelay) {
        super(world, creationDelay,Integer.MAX_VALUE);
    }

    @Override
    protected void createAsset() {
        super.createAsset();
        new Pipe(this, holes[idx++]);
        idx %= holes.length;
    }
}
