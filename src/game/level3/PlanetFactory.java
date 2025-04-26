package game.level3;

import game.BasicLevel;
import game.RandomAssetFactory;

/**
 * Planet Factory class - for creating new planets
 */
public class PlanetFactory extends RandomAssetFactory {
    /**
     * Constructor for planet
     * @param level of this planet
     * @param creationDelay - time between creation planets
     * @param minValue - min planet half height
     * @param maxValue - max planet half height
     */
    public PlanetFactory(BasicLevel level, int creationDelay, float minValue, float maxValue) {
        super(level, creationDelay,Integer.MAX_VALUE,minValue,maxValue);
    }

    /**
     * timer calling periodically
     * creating planet with random height
     */
    @Override
    protected void createAsset() {
        new Planet(this, getNextValue());
    }
}
