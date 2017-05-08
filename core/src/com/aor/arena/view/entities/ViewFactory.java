package com.aor.arena.view.entities;

import com.aor.arena.AsteroidArena;
import com.aor.arena.model.entities.EntityModel;

import java.util.HashMap;
import java.util.Map;

import static com.aor.arena.model.entities.EntityModel.ModelType.BIGASTEROID;
import static com.aor.arena.model.entities.EntityModel.ModelType.BULLET;
import static com.aor.arena.model.entities.EntityModel.ModelType.MEDIUMASTEROID;
import static com.aor.arena.model.entities.EntityModel.ModelType.SHIP;

/**
 * A factory for EntityView objects with cache
 */

public class ViewFactory {
    private static Map<EntityModel.ModelType, EntityView> cache =
            new HashMap<EntityModel.ModelType, EntityView>();

    public static EntityView makeView(AsteroidArena game, EntityModel model) {
        if (!cache.containsKey(model.getType())) {
            if (model.getType() == BIGASTEROID)
                cache.put(model.getType(), new BigAsteroidView(game));
            if (model.getType() == MEDIUMASTEROID)
                cache.put(model.getType(), new MediumAsteroidView(game));
            if (model.getType() == SHIP)
                cache.put(model.getType(), new ShipView(game));
            if (model.getType() == BULLET)
                cache.put(model.getType(), new BulletView(game));
        }
        return cache.get(model.getType());
    }
}
