package com.aor.arena.model.entities;

/**
 * A model representing a the user space ship.
 */
public class ShipModel extends EntityModel {
    private boolean accelerating = true;

    public ShipModel(float x, float y, int rotation) {
        super(x, y, rotation);
    }

    public void setAccelerating(boolean accelerating) {
        this.accelerating = accelerating;
    }

    public boolean isAccelerating() {
        return accelerating;
    }
}
