package game.level2;

import game.AssetFactory;
import game.BasicLevel;
import game.level1.Pipe;

public class CactusFactory extends AssetFactory {
    private static final float[] holes = {4.5f, 6f, 7.5f};
    private int idx = 0;

    public CactusFactory(BasicLevel level, int creationDelay) {
        super(level, creationDelay,Integer.MAX_VALUE);
    }

    @Override
    protected void createAsset() {
        new Cactus(this, holes[idx++]);
        idx %= holes.length;
    }
}
