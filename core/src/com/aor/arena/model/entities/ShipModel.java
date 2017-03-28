package com.aor.arena.model.entities;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * A model representing a the user space ship.
 */
public class ShipModel extends EntityModel {
    private static final float ROTATE_SPEED = 5f;

    private static final float MOVE_SPEED = 10f;

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

    public void rotateLeft(float delta) {
        setRotation(getRotation() + ROTATE_SPEED * delta);
    }

    public void rotateRight(float delta) {
        setRotation(getRotation() - ROTATE_SPEED * delta);
    }

    public void accelerate(float delta) {
        setPosition((float) (getX() - delta * MOVE_SPEED * sin(getRotation())), (float)(getY() + delta * MOVE_SPEED * cos(getRotation())));
    }
}
