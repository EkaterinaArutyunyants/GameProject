package game.level2;

import game.BasicLevel;
import game.RandomAssetFactory;

public class RockFactory extends RandomAssetFactory {

    public RockFactory(BasicLevel level, int creationDelay, float minValue, float maxValue) {
        super(level, creationDelay,Integer.MAX_VALUE,minValue,maxValue);
    }

    @Override
    protected void createAsset() {
        new Rock(this, getNextValue());
    }
}
