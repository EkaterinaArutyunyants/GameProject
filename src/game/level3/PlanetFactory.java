package game.level3;

import game.BasicLevel;
import game.RandomAssetFactory;

public class PlanetFactory extends RandomAssetFactory {

    public PlanetFactory(BasicLevel level, int creationDelay, float minValue, float maxValue) {
        super(level, creationDelay,Integer.MAX_VALUE,minValue,maxValue);
    }

    @Override
    protected void createAsset() {
        new Planet(this, getNextValue());
    }
}
