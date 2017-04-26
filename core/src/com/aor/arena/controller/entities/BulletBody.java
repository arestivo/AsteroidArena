package com.aor.arena.controller.entities;

import com.aor.arena.model.entities.AsteroidModel;
import com.aor.arena.model.entities.BulletModel;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A concrete representation of an EntityBody representing a bullet.
 */
public class BulletBody extends EntityBody {
    /**
     * Constructs a bullet body according to
     * a bullet model.
     *
     * @param world the physical world this asteroid belongs to.
     * @param model the model representing this bullet.
     */
    public BulletBody(World world, BulletModel model) {
        super(world, model);

        float density = 1f, friction = 0.4f, restitution = 0.5f;
        int width = 12, height = 12;

        createFixture(body, new float[]{
                5,5, 5,10, 10,10, 10,5,
        }, width, height, density, friction, restitution);
    }
}
