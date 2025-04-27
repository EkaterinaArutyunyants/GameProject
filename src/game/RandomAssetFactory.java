package game;

import java.util.Random;

/**
 * RandomAssetFactory class - abstract class
 * create assets with random values and time between assets.
 */

public abstract class RandomAssetFactory extends AssetFactory {
    protected static final Random random = new Random();
    protected final float minValue;
    protected final float maxValue;

    /**
     * Constructor for generating assets
     *
     * @param level         - for managing factory
     * @param creationDelay - time between creating assets
     * @param maxCount      - max num of assets at the same time
     * @param minValue      - min random value
     * @param maxValue      - max random value
     */
    public RandomAssetFactory(BasicLevel level, int creationDelay, int maxCount, float minValue, float maxValue) {
        super(level, random.nextInt(creationDelay), creationDelay, maxCount);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     * method for generating next random value
     *
     * @return value between min and max value
     */
    protected float getNextValue() {
        return minValue + (maxValue - minValue) * random.nextFloat();
    }
}
