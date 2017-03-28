package com.aor.arena.view.entities;

import com.aor.arena.AsteroidArena;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A view representing a medium asteroid
 */

public class MediumAsteroidView extends EntityView{
    /**
     * Constructs a medium asteroid view.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     */
    public MediumAsteroidView(AsteroidArena game) {
        super(game);
    }

    /**
     * Creates a sprite representing this asteroid.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     * @return the sprite representing this asteroid
     */
    public Sprite createSprite(AsteroidArena game) {
        Texture texture = game.getAssetManager().get("asteroid-medium.png");

        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
