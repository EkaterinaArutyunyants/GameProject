package game.level1;

import city.cs.engine.World;
import game.AssetFactory;
import game.BasicLevel;

public class PipeFactory extends AssetFactory {
    private static final float[] holes = {4.5f, 6f, 7.5f};
    private int idx = 0;

    public PipeFactory(BasicLevel level, int creationDelay) {
        super(level, creationDelay,Integer.MAX_VALUE);
    }

    @Override
    protected void createAsset() {
        super.createAsset();
        new SensorPipe(this, holes[idx++]);
        idx %= holes.length;
    }
}
