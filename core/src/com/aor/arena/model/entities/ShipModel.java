package com.aor.arena.model.entities;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * A model representing a the user space ship.
 */
public class ShipModel extends EntityModel {
    /**
     * The radian degrees the ship rotates every second.
     */
    private static final float ROTATE_SPEED = 5f;

    /**
     * The distance the ship moves every second.
     */
    private static final float MOVE_SPEED = 10f;

    /**
     * Is the ship accelerating.
     */
    private boolean accelerating = true;

    /**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     * @param rotation the rotation in radians
     */
    public ShipModel(float x, float y, int rotation) {
        super(x, y, rotation);
    }

    /**
     * Set the accelerating flag for this ship
     *
     * @param accelerating the accelerating tag
     */
    public void setAccelerating(boolean accelerating) {
        this.accelerating = accelerating;
    }

    /**
     * Is the ship accelerating in this update
     *
     * @return the accelerating flag
     */
    public boolean isAccelerating() {
        return accelerating;
    }

    /**
     * Rotates the ship left
     */
    public void rotateLeft(float delta) {
        setRotation(getRotation() + ROTATE_SPEED * delta);
    }

    /**
     * Rotates the ship right
     */
    public void rotateRight(float delta) {
        setRotation(getRotation() - ROTATE_SPEED * delta);
    }

    /**
     * Moves the ship forward
     */
    public void accelerate(float delta) {
        setPosition((float) (getX() - delta * MOVE_SPEED * sin(getRotation())), (float)(getY() + delta * MOVE_SPEED * cos(getRotation())));
        accelerating = true;
    }
}