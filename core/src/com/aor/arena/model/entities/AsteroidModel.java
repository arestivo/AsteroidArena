package com.aor.arena.model.entities;

/**
 * A model representing a asteroid with a certain size.
 */
public class AsteroidModel extends EntityModel{
    /**
     * Possible asteroid sizes.
     */
    public enum AsteroidSize {BIG, MEDIUM}

    /**
     * This asteroid size.
     */
    private AsteroidSize size;

    /**
     * Constructs a asteroid model belonging to a game model.
     *
     * @param x The x-coordinate of this asteroid.
     * @param y The y-coordinate of this asteroid.
     * @param rotation The rotation of this asteroid.
     * @param size The size of this asteroid.
     */
    public AsteroidModel(float x, float y, float rotation, AsteroidSize size) {
        super(x, y, rotation);
        this.size = size;
    }

    /**
     * Returns the size of this asteroid.
     *
     * @return The size of this asteroid.
     */
    public AsteroidSize getSize() {
        return size;
    }

    @Override
    public ModelType getType() {
        if (size == AsteroidSize.BIG)
            return ModelType.BIGASTEROID;
        if (size == AsteroidSize.MEDIUM)
            return ModelType.MEDIUMASTEROID;
        return null;
    }

}

