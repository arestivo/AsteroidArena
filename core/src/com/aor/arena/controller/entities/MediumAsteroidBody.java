package com.aor.arena.controller.entities;

import com.aor.arena.model.entities.AsteroidModel;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A concrete representation of an EntityBody representing a medium sized asteroid.
 */
public class MediumAsteroidBody extends EntityBody {
    /**
     * Constructs a medium sized asteroid body according to
     * a asteroid model.
     *
     * @param world the physical world this asteroid belongs to.
     * @param model the model representing this asteroid.
     */
    public MediumAsteroidBody(World world, AsteroidModel model) {
        super(world, model);

        float density = 1f, friction = 0.4f, restitution = 0.5f;
        int width = 35, height = 35;

        createFixture(body, new float[]{
                0,23, 3,2, 27,2, 34,18, 17,33
        }, width, height, density, friction, restitution);
    }
}
