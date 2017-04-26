package com.aor.arena.model.entities;

/**
 * A model representing a single bullet.
 */
public class BulletModel extends EntityModel{
    private float timeToLive;

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

    /**
     * Decreases this bullet's time to leave by delta seconds
     *
     * @param delta
     * @return
     */
    public boolean decreaseTimeToLive(float delta) {
        timeToLive -= delta;
        return  timeToLive < 0;
    }

    /**
     * Sets this bullet's time to live in seconds
     * @param timeToLive
     */
    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }
}
