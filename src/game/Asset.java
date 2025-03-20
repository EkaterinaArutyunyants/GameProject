package game;

import city.cs.engine.DynamicBody;
import city.cs.engine.Shape;

public class Asset extends DynamicBody {
    private final AssetFactory factory;


    public Asset(AssetFactory factory) {
        super(factory.getWorld());
        this.factory = factory;
    }

    public Asset(AssetFactory factory,Shape shape) {
        super(factory.getWorld(),shape);
        this.factory = factory;
    }

    public AssetFactory getFactory() {
        return factory;
    }
}
