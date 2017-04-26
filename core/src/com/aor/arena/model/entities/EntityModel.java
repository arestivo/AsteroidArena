package com.aor.arena.model.entities;

/**
 * An abstract model representing an entity belonging to a game model.
 */
public abstract class EntityModel {
    /**
     * The x-coordinate of this model in meters.
     */
    private float x;

    /**
     * The y-coordinate of this model in meters.
     */
    private float y;

    /**
     * The current rotation of this model in radians.
     */
    private float rotation;

    /**
     * Has this model been flagged for removal?
     */
    private boolean flaggedForRemoval = false;

    /**
     * Constructs a model with a position and a rotation.
     *
     * @param x The x-coordinate of this entity in meters.
     * @param y The y-coordinate of this entity in meters.
     * @param rotation The current rotation of this entity in radians.
     */
    EntityModel(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    /**
     * Returns the x-coordinate of this entity.
     *
     * @return The x-coordinate of this entity in meters.
     */
    public float getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this entity.
     *
     * @return The y-coordinate of this entity in meters.
     */
    public float getY() {
        return y;
    }

    /**
     * Returns the rotation of this entity.
     *
     * @return The rotation of this entity in radians.
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Sets the position of this entity.
     *
     * @param x The x-coordinate of this entity in meters.
     * @param y The y-coordinate of this entity in meters.
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the rotation of this entity.
     *
     * @param rotation The current rotation of this entity in radians.
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    /**
     * Makes this model flagged for removal on next step
     */
    public void flagForRemoval() {
        this.flaggedForRemoval = true;
    };

    public boolean isFlaggedToBeRemoved() {
        return flaggedForRemoval;
    }
}
