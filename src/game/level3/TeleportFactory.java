package game.level3;

import game.BasicLevel;
import game.RandomAssetFactory;

/**
 * TeleportFactory class - factory creates teleport at random positions
 * Teleports appear at diff heights based on calculated median
 */
public class TeleportFactory extends RandomAssetFactory {
    private float median;

    /**
     * Constructor for teleport factory
     *
     * @param level         where teleports will appear
     * @param creationDelay time delay between teleport appears
     * @param minValue      min position for teleport
     * @param maxValue      max position for teleport
     */
    public TeleportFactory(BasicLevel level, int creationDelay, float minValue, float maxValue) {
        super(level, creationDelay, Integer.MAX_VALUE, minValue, maxValue);
        median = (minValue + maxValue) / 2; // calculates the middle value for positioning
    }

    /**
     * creates a new teleport at random pos
     * Y position is adjusted relative to the median value.
     */
    @Override
    protected void createAsset() {
        new Teleport(this, getNextValue(), getNextValue() - median);
    }
}
