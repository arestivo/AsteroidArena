package com.aor.arena.model.entities;

/**
 * A model representing a single bullet.
 */
public class BulletModel extends EntityModel{

    /**
     * Constructs a asteroid model belonging to a game model.
     *
     * @param x The x-coordinate of this bullet.
     * @param y The y-coordinate of this bullet.
     * @param rotation The rotation of this bullet.
     */
    public BulletModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }
}
