package com.aor.arena.model;

import com.aor.arena.controller.GameController;
import com.aor.arena.model.entities.AsteroidModel;
import com.aor.arena.model.entities.BulletModel;
import com.aor.arena.model.entities.ShipModel;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * A model representing a game.
 */

public class GameModel {
    /**
     * The space ship controlled by the user in this game.
     */
    private ShipModel ship;

    /**
     * The asteroids roaming around in this game.
     */
    private List<AsteroidModel> asteroids;
    private List<BulletModel> bullets;

    /**
     * Constructs a game with a.space ship in a certain position and
     * a certain number of asteroids in different sizes.
     *
     * @param x the x-coordinate of the space ship in meters
     * @param y the y-coordinate of the space ship in meters
     * @param asteroidCount The number of asteroids to create
     */
    public GameModel(float x, float y, int asteroidCount) {
        asteroids = new ArrayList<AsteroidModel>();
        bullets = new ArrayList<BulletModel>();
        ship = new ShipModel(x, y, 0);

        for (int i = 0; i < asteroidCount; i++)
            asteroids.add(new AsteroidModel(
                    random.nextFloat() * GameController.ARENA_WIDTH,
                    random.nextFloat() * GameController.ARENA_HEIGHT,
                    (float) Math.toRadians(random.nextFloat() * 360),
                    random.nextBoolean()?AsteroidModel.AsteroidSize.BIG:AsteroidModel.AsteroidSize.MEDIUM));
    }

    /**
     * Returns the player space ship.
     *
     * @return the space ship.
     */
    public ShipModel getShip() {
        return ship;
    }

    /**
     * Returns the asteroids.
     *
     * @return the asteroid list
     */
    public List<AsteroidModel> getAsteroids() {
        return asteroids;
    }

    /**
     * Returns the bullets.
     *
     * @return the bullet list
     */
    public List<BulletModel> getBullets() {
        return bullets;
    }

    public BulletModel createBullet(ShipModel ship) {
        BulletModel bullet = new BulletModel(ship.getX() - (float)(Math.sin(ship.getRotation())), ship.getY() + (float)(Math.cos(ship.getRotation())), ship.getRotation());
        bullets.add(bullet);
        return bullet;
    }
}
