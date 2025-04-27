package game;

import city.cs.engine.DynamicBody;
import city.cs.engine.Shape;

/**
 * Asset class - asset factory create an object - asset in the game world
 * Extends DynamicBody class (means that can interact with other objects in the game world)
 */
public class Asset extends DynamicBody {
    // asset is creating and counts by factory
    private final AssetFactory factory;

    /**
     * Constructor for Asset
     * uses shape from factory for asset
     *
     * @param factory that created this asset
     */
    public Asset(AssetFactory factory) {
        super(factory.getLevel()); // level from the factory
        this.factory = factory;
    }

    /**
     * Constructor for Asset
     * allows specific shape
     *
     * @param factory that created this asset
     * @param shape   of asset
     */
    public Asset(AssetFactory factory, Shape shape) {
        super(factory.getLevel(), shape);
        this.factory = factory;
    }

    /**
     * Destroys the asset
     * method called when no longer need the asset
     * factory decrease its asset count
     */
    public void destroy() {
        factory.decCount();
        super.destroy();
    }
}
