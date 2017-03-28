package com.aor.arena.controller.entities;

import com.aor.arena.model.entities.AsteroidModel;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A concrete representation of an EntityBody representing a big sized asteroid.
 */
public class BigAsteroidBody extends EntityBody {
    /**
     * Constructs a medium sized asteroid body according to
     * a asteroid model.
     *
     * @param world the physical world this asteroid belongs to.
     * @param model the model representing this asteroid.
     */
    public BigAsteroidBody(World world, AsteroidModel model) {
        super(world, model);

        float density = 1f, friction = 0.4f, restitution = 0.5f;
        int width = 75, height = 75;

        // Fixture needs to be convex so we need two of them.
        createFixture(body, new float[]{
                0,33, 14,14, 46,7, 27,52, 7,52
        }, width, height, density, friction, restitution);

        createFixture(body, new float[]{
                46,7, 27,52, 45,67, 74,55, 71,29
        }, width, height, density, friction, restitution);
    }
}
