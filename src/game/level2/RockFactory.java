package game.level2;

import game.BasicLevel;
import game.RandomAssetFactory;

/**
 * RockFactory class - factory for creating rocks with random height
 */
public class RockFactory extends RandomAssetFactory {
    /**
     * Constructor for RockFactory
     *
     * @param level         of this factory
     * @param creationDelay time between each rock created
     * @param minValue      - min rock half height
     * @param maxValue      - max rock half height
     */
    public RockFactory(BasicLevel level, int creationDelay, float minValue, float maxValue) {
        super(level, creationDelay, Integer.MAX_VALUE, minValue, maxValue);
    }

    /**
     * timer is calling for generating new rock
     */
    @Override
    protected void createAsset() {
        new Rock(this, getNextValue());
    }
}
