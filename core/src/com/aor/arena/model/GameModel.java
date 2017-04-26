package com.aor.arena.model;

import com.aor.arena.controller.GameController;
import com.aor.arena.model.entities.AsteroidModel;
import com.aor.arena.model.entities.BulletModel;
import com.aor.arena.model.entities.EntityModel;
import com.aor.arena.model.entities.ShipModel;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * A model representing a game.
 */

public class GameModel {
    /**
     * The singleton instance of the game model
     */
    private static GameModel instance;

    /**
     * Number of asteroids in the beggining of the game
     */

    private static final int ASTEROID_COUNT = 100;
    /**
     * The space ship controlled by the user in this game.
     */
    private ShipModel ship;

    /**
     * The asteroids roaming around in this game.
     */
    private List<AsteroidModel> asteroids;

    /**
     * The bullets currently flying through space.
     */
    private List<BulletModel> bullets;

    /**
     * A pool of bullets
     */
    Pool<BulletModel> bulletPool = new Pool<BulletModel>() {
        @Override
        protected BulletModel newObject() {
            return new BulletModel(0, 0, 0);
        }
    };

    /**
     * Returns a singleton instance of the game model
     *
     * @return the singleton instance
     */
    public static GameModel getInstance() {
        if (instance == null)
            instance = new GameModel();
        return instance;
    }


    /**
     * Constructs a game with a.space ship in the middle of the
     * arena and a certain number of asteroids in different sizes.
     */
    private GameModel() {
        asteroids = new ArrayList<AsteroidModel>();
        bullets = new ArrayList<BulletModel>();
        ship = new ShipModel(GameController.ARENA_WIDTH / 2, GameController.ARENA_HEIGHT / 2, 0);

        for (int i = 0; i < ASTEROID_COUNT; i++)
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
        BulletModel bullet = bulletPool.obtain();

        bullet.setFlaggedForRemoval(false);
        bullet.setPosition(ship.getX() - (float)(Math.sin(ship.getRotation()) * 1.4), ship.getY() + (float)(Math.cos(ship.getRotation()) * 1.4));
        bullet.setRotation(ship.getRotation());
        bullet.setTimeToLive(.5f);

        bullets.add(bullet);

        return bullet;
    }

    /**
     * Removes a model from this game.
     *
     * @param model the model to be removed
     */
    public void remove(EntityModel model) {
        if (model instanceof BulletModel) {
            bullets.remove(model);
            bulletPool.free((BulletModel) model);
        }
        if (model instanceof AsteroidModel) {
            asteroids.remove(model);
        }
    }

    /**
     * Adds a new asteroid to the model
     *
     * @param asteroidModel the asteroid model to be added
     */
    public void addAsteroid(AsteroidModel asteroidModel) {
        asteroids.add(asteroidModel);
    }

    public void update(float delta) {
        for (BulletModel bullet : bullets)
            if (bullet.decreaseTimeToLive(delta))
                bullet.setFlaggedForRemoval(true);
    }
}
