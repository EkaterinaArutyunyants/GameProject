package game;

import city.cs.engine.DynamicBody;
import city.cs.engine.Shape;
/**
 * The Asset class represents an object in the game world that is created by AssetFactory
 * It extends the DynamicBody class (can interact with other objects in the game world)
 */
public class Asset extends DynamicBody {
    // factory that created this asset
    // factory is responsible for managing asset creation and counting
    private final AssetFactory factory;
    /**
     * Constructor to create an Asset object
     *  uses the default shape for the asset (provided by factory)
     * @param factory AssetFactory that created this asset
     */
    public Asset(AssetFactory factory) {
        super(factory.getLevel()); // Calls the parent constructor (DynamicBody) and sets the level from the factory
        this.factory = factory;
    }
    /**
     * Constructor to create an Asset object with a custom shape
     * allows specifying the shape of the asset
     * @param factory AssetFactory that created this asset
     * @param shape The shape of asset. Defines the appearance,collision of asset
     */
    public Asset(AssetFactory factory,Shape shape) {
        super(factory.getLevel(),shape);
        this.factory = factory;
    }
    /**
     * Destroys the asset
     * method called when no longer need the asset
     * factory decrease its asset count
     */
    public void destroy(){
        factory.decCount();
        super.destroy();
    }
}
