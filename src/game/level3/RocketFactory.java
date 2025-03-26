package game.level3;

import game.AssetFactory;
import game.BasicLevel;
import game.level1.Pipe;

public class RocketFactory extends AssetFactory {
    private static final float[] holes = {4.5f, 6f, 7.5f};
    private int idx = 0;

    public RocketFactory(BasicLevel level, int creationDelay) {
        super(level, creationDelay,Integer.MAX_VALUE);
    }

    @Override
    protected void createAsset() {
        new Rocket(this, holes[idx++]);
        idx %= holes.length;
    }
}
