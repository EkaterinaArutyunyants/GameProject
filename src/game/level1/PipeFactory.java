package game.level1;

import city.cs.engine.World;
import game.AssetFactory;
import game.BasicLevel;

/**
 * PipeFactory class - factory for creating pipe objects with intervals
 * there are holes between pipes
 */
public class PipeFactory extends AssetFactory {
    //y positions for pipe holes
    private static final float[] holes = {4.5f, 6f, 7.5f};
    //index to track what hole size is next
    private int idx = 0;

    /**
     * Constructor for pipe factory
     * @param level for this factory
     * @param creationDelay time between creating each pipe
     */
    public PipeFactory(BasicLevel level, int creationDelay) {
        // Integer.MAX_VALUE maxCount for unlimited pipes
        super(level, creationDelay,Integer.MAX_VALUE);
    }

    /**
     * method is auto called by factory timer
     * creates new pipe with different holes
     */
    @Override
    protected void createAsset() {
        new Pipe(this, holes[idx++]);
        idx %= holes.length;
    }
}
