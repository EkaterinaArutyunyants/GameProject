package game.level3;

import game.BasicLevel;
import game.RandomAssetFactory;
import game.level2.Cactus;

public class TeleportFactory extends RandomAssetFactory {
    private float median;
    public TeleportFactory(BasicLevel level, int creationDelay, float minValue, float maxValue) {
        super(level, creationDelay,Integer.MAX_VALUE,minValue,maxValue);
        median=(minValue+maxValue)/2;
    }

    @Override
    protected void createAsset() {

        new Teleport(this, getNextValue(), getNextValue()-median);
    }
}
