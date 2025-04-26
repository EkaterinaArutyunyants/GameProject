package game.level2;

import game.AssetFactory;
import game.BasicLevel;
import game.RandomAssetFactory;
import game.level1.Pipe;

/**
 * CactusFactory class - factory for creating cactuses
 * at random height
 */
public class CactusFactory extends RandomAssetFactory {
    /**
     * Constructor for cactus factory
     * @param level of this factory
     * @param creationDelay - time between old cactus and creating new cactus
     * @param minValue - min cactus half height
     * @param maxValue - max cactus half height
     */
    public CactusFactory(BasicLevel level, int creationDelay, float minValue, float maxValue) {
        super(level, creationDelay,Integer.MAX_VALUE,minValue,maxValue);
    }

    /**
     * timer calling periodically
     * creating cactus with random height
     */
    @Override
    protected void createAsset() {
        new Cactus(this, getNextValue());
    }
}
