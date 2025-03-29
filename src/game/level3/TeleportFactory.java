package game.level3;

import game.BasicLevel;
import game.RandomAssetFactory;

/**
 * Factory that creates teleport objects at random positions
 * Teleports appear at diff heights based on calculated median
 */
public class TeleportFactory extends RandomAssetFactory {
    private float median;

    /**
     * Sets up the teleport factory with level details and params
     * @param level The game level where teleports will appear
     * @param creationDelay Time delay between teleport appears
     * @param minValue The lowest possible position where teleport can appear
     * @param maxValue The highest possible position where teleport can appear
     */
    public TeleportFactory(BasicLevel level, int creationDelay, float minValue, float maxValue) {
        super(level, creationDelay,Integer.MAX_VALUE,minValue,maxValue);
        median=(minValue+maxValue)/2; // calculates the middle value for positioning
    }
    /**
     * Creates a new Teleport object at random position
     * The Y position is adjusted relative to the median value.
     */
    @Override
    protected void createAsset() {
        new Teleport(this, getNextValue(), getNextValue()-median);
    }
}
