package game.level2;

import game.AssetFactory;
import game.BasicLevel;
import game.RandomAssetFactory;
import game.level1.Pipe;

public class CactusFactory extends RandomAssetFactory {

    public CactusFactory(BasicLevel level, int creationDelay, float minValue, float maxValue) {
        super(level, creationDelay,Integer.MAX_VALUE,minValue,maxValue);
    }

    @Override
    protected void createAsset() {
        new Cactus(this, getNextValue());
    }
}
