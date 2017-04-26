package com.aor.arena.view.entities;

import com.aor.arena.AsteroidArena;

import java.util.HashMap;
import java.util.Map;

import static com.aor.arena.view.entities.ViewFactory.ViewType.BIGASTEROID;
import static com.aor.arena.view.entities.ViewFactory.ViewType.BULLET;
import static com.aor.arena.view.entities.ViewFactory.ViewType.MEDIUMASTEROID;
import static com.aor.arena.view.entities.ViewFactory.ViewType.SHIP;

/**
 * A factory for EntityView objects with cache
 */

public class ViewFactory {
    public enum ViewType {BIGASTEROID, MEDIUMASTEROID, SHIP, BULLET};

    private static Map<ViewType, EntityView> cache = new HashMap<ViewType, EntityView>();

    public static EntityView makeView(AsteroidArena game, ViewType type) {
        if (!cache.containsKey(type)) {
            if (type == BIGASTEROID) cache.put(BIGASTEROID, new BigAsteroidView(game));
            if (type == MEDIUMASTEROID) cache.put(MEDIUMASTEROID, new MediumAsteroidView(game));
            if (type == SHIP) cache.put(SHIP, new ShipView(game));
            if (type == BULLET) cache.put(BULLET, new BulletView(game));
        }
        return cache.get(type);
    }
}
