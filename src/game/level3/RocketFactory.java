package game.level3;

import game.BasicLevel;
import game.RandomAssetFactory;
import game.level2.Cactus;

public class RocketFactory extends RandomAssetFactory {

    public RocketFactory(BasicLevel level, int creationDelay, float minValue, float maxValue) {
        super(level, creationDelay,Integer.MAX_VALUE,minValue,maxValue);
    }

    @Override
    protected void createAsset() {
        new Rocket(this, getNextValue());
    }
}
