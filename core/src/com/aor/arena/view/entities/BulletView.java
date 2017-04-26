package com.aor.arena.view.entities;

import com.aor.arena.AsteroidArena;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A view representing a bullet
 */
public class BulletView extends EntityView{
    /**
     * Constructs a bullet view.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     */
    public BulletView(AsteroidArena game) {
        super(game);
    }

    /**
     * Creates a sprite representing this bullet.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     * @return the sprite representing this bullet
     */
    public Sprite createSprite(AsteroidArena game) {
        Texture texture = game.getAssetManager().get("bullet.png");

        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
