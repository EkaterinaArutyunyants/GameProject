package game;

import java.util.Random;

public abstract class RandomAssetFactory extends AssetFactory{
//    private Random
    protected static final Random random = new Random();
    protected final float minValue;
    protected final float maxValue;
    /**
     * @param level         - this level manages this factory.
     * @param creationDelay -
     * @param maxCount
     */
    public RandomAssetFactory(BasicLevel level, int creationDelay, int maxCount, float minValue, float maxValue) {
        super(level, random.nextInt(creationDelay),creationDelay, maxCount);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
    protected float getNextValue(){
        return minValue+(maxValue-minValue)*random.nextFloat();
    }
}
