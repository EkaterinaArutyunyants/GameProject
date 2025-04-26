package game.level3;

import game.BasicLevel;
import game.RandomAssetFactory;
import game.level2.Cactus;
/**
 * Rocket Factory class - for creating new rockets
 */
public class RocketFactory extends RandomAssetFactory {
    /**
     * Constructor for rocket
     * @param level of this rocket
     * @param creationDelay - time between creation rockets
     * @param minValue - min rocket half height
     * @param maxValue - max rocket half height
     */
    public RocketFactory(BasicLevel level, int creationDelay, float minValue, float maxValue) {
        super(level, creationDelay,Integer.MAX_VALUE,minValue,maxValue);
    }
    /**
     * timer calling periodically
     * creating rocket with random height
     */
    @Override
    protected void createAsset() {
        new Rocket(this, getNextValue());
    }
}
