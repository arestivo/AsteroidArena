package com.aor.arena.controller.entities;

import com.aor.arena.model.entities.ShipModel;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A concrete representation of an EntityBody
 * representing the player space ship.
 */
public class ShipBody extends EntityBody {
    /**
     * Constructs a space ship body according to
     * a space ship model.
     *
     * @param world the physical world this space ship belongs to.
     * @param model the model representing this space ship.
     */
    public ShipBody(World world, ShipModel model) {
        super(world, model);

        float density = 0.5f, friction = 0.4f, restitution = 0.5f;
        int width = 75, height = 75;

        // Left winglet
        createFixture(body, new float[]{
                12,28, 15,28, 19,32, 19,42, 13,43
        }, width, height, density, friction, restitution, SHIP_BODY, (short) (ASTEROID_BODY | SHIP_BODY | BULLET_BODY));

        // Right winglet
        createFixture(body, new float[]{
                61,28, 58,28, 55,32, 55,42, 60,43
        }, width, height, density, friction, restitution, SHIP_BODY, (short) (ASTEROID_BODY | SHIP_BODY | BULLET_BODY));

        // Left wing
        createFixture(body, new float[]{
                19,32, 19,42, 31,46, 31,25
        }, width, height, density, friction, restitution, SHIP_BODY, (short) (ASTEROID_BODY | SHIP_BODY | BULLET_BODY));

        // Right wing
        createFixture(body, new float[]{
                55,32, 55,42, 43,46, 43,25
        }, width, height, density, friction, restitution, SHIP_BODY, (short) (ASTEROID_BODY | SHIP_BODY | BULLET_BODY));

        // Body
        createFixture(body, new float[]{
                32,12, 31,46, 34,51, 40,51, 43,46, 41,12
        }, width, height, density, friction, restitution, SHIP_BODY, (short) (ASTEROID_BODY | SHIP_BODY | BULLET_BODY));
    }
}

