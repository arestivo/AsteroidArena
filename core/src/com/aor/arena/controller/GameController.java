package com.aor.arena.controller;

import com.aor.arena.controller.entities.BigAsteroidBody;
import com.aor.arena.controller.entities.BulletBody;
import com.aor.arena.controller.entities.MediumAsteroidBody;
import com.aor.arena.controller.entities.ShipBody;
import com.aor.arena.model.GameModel;
import com.aor.arena.model.entities.AsteroidModel;
import com.aor.arena.model.entities.BulletModel;
import com.aor.arena.model.entities.EntityModel;
import com.aor.arena.model.entities.ShipModel;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Controls the physics aspect of the game.
 */

public class GameController implements ContactListener {
    /**
     * The arena width in meters.
     */
    public static final int ARENA_WIDTH = 100;

    /**
     * The arena height in meters.
     */
    public static final int ARENA_HEIGHT = 50;

    /**
     * The rotation speed in radians per second.
     */
    private static final float ROTATION_SPEED = 5f;

    /**
     * The acceleration impulse in newtons.
     */
    private static final float ACCELERATION_FORCE = 1000f;

    /**
     * The physics world controlled by this controller.
     */
    private final World world;

    /**
     * The spaceship body.
     */
    private final ShipBody shipBody;

    /**
     * The game model.
     */
    private final GameModel model;

    /**
     * Accumulator used to calculate the simulation step.
     */
    private float accumulator;

    private List<AsteroidModel> asteroidsToAdd = new ArrayList<AsteroidModel>();

    /**
     * Creates a new GameController that controls the physics of a certain GameModel.
     *
     * @param model The model controlled by this controller.
     */
    public GameController(GameModel model) {
        world = new World(new Vector2(0, 0), true);
        this.model = model;

        shipBody = new ShipBody(world, model.getShip());

        List<AsteroidModel> asteroids = model.getAsteroids();
        for (AsteroidModel asteroid : asteroids)
        if (asteroid.getSize() == AsteroidModel.AsteroidSize.BIG)
            new BigAsteroidBody(world, asteroid);
        else if (asteroid.getSize() == AsteroidModel.AsteroidSize.MEDIUM)
            new MediumAsteroidBody(world, asteroid);

        world.setContactListener(this);
    }

    /**
     * Calculates the next physics step of duration delta (in seconds).
     *
     * @param delta The size of this physics step in seconds.
     */
    public void update(float delta) {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            verifyBounds(body);
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
            ((EntityModel) body.getUserData()).setRotation(body.getAngle());
        }
    }

    /**
     * Verifies if the body is inside the arena bounds and if not
     * wraps it around to the other side.
     *
     * @param body The body to be verified.
     */
    private void verifyBounds(Body body) {
        if (body.getPosition().x < 0)
            body.setTransform(ARENA_WIDTH, body.getPosition().y, body.getAngle());

        if (body.getPosition().y < 0)
            body.setTransform(body.getPosition().x, ARENA_HEIGHT, body.getAngle());

        if (body.getPosition().x > ARENA_WIDTH)
            body.setTransform(0, body.getPosition().y, body.getAngle());

        if (body.getPosition().y > ARENA_HEIGHT)
            body.setTransform(body.getPosition().x, 0, body.getAngle());
    }

    /**
     * Returns the world controlled by this controller. Needed for debugging purposes only.
     *
     * @return The world controlled by this controller.
     */
    public World getWorld() {
        return world;
    }

    /**
     * Rotates the spaceship left. The rotation takes into consideration the
     * constant rotation speed and the delta for this simulation step.
     *
     * @param delta Duration of the rotation in seconds.
     */
    public void rotateLeft(float delta) {
        shipBody.setTransform(shipBody.getX(), shipBody.getY(), shipBody.getAngle() + ROTATION_SPEED * delta);
        shipBody.setAngularVelocity(0);
    }

    /**
     * Rotates the spaceship right. The rotation takes into consideration the
     * constant rotation speed and the delta for this simulation step.
     *
     * @param delta Duration of the rotation in seconds.
     */
    public void rotateRight(float delta) {
        shipBody.getX();

        shipBody.setTransform(shipBody.getX(), shipBody.getY(), shipBody.getAngle() - ROTATION_SPEED * delta);
        shipBody.setAngularVelocity(0);
    }

    /**
     * Acceleratesins the spaceship. The acceleration takes into consideration the
     * constant acceleration force and the delta for this simulation step.
     *
     * @param delta Duration of the rotation in seconds.
     */

    public void accelerate(float delta) {
        shipBody.applyForceToCenter(-(float) sin(shipBody.getAngle()) * ACCELERATION_FORCE * delta, (float) cos(shipBody.getAngle()) * ACCELERATION_FORCE * delta, true);
        ((ShipModel)shipBody.getUserData()).setAccelerating(true);
    }

    public void shoot() {
        BulletModel bullet = model.createBullet(model.getShip());
        BulletBody body = new BulletBody(world, bullet);
        body.setLinearVelocity(10);
    }

    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof BulletModel)
            bulletCollision(bodyA);
        if (bodyB.getUserData() instanceof BulletModel)
            bulletCollision(bodyB);

        if (bodyA.getUserData() instanceof BulletModel && bodyB.getUserData() instanceof AsteroidModel)
            bulletAsteroidCollision(bodyA, bodyB);
        if (bodyA.getUserData() instanceof AsteroidModel && bodyB.getUserData() instanceof BulletModel)
            bulletAsteroidCollision(bodyB, bodyA);

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void bulletCollision(Body bulletBody) {
        ((BulletModel)bulletBody.getUserData()).setFlaggedForRemoval(true);
    }

    private void bulletAsteroidCollision(Body bulletBody, Body asteroidBody) {
        AsteroidModel asteroidModel = (AsteroidModel) asteroidBody.getUserData();
        asteroidModel.setFlaggedForRemoval(true);

        if (asteroidModel.getSize() == AsteroidModel.AsteroidSize.BIG) {
            AsteroidModel a1 = new AsteroidModel(asteroidModel.getX(), asteroidModel.getY(), asteroidModel.getRotation(), AsteroidModel.AsteroidSize.MEDIUM);
            AsteroidModel a2 = new AsteroidModel(asteroidModel.getX(), asteroidModel.getY(), asteroidModel.getRotation(), AsteroidModel.AsteroidSize.MEDIUM);
            AsteroidModel a3 = new AsteroidModel(asteroidModel.getX(), asteroidModel.getY(), asteroidModel.getRotation(), AsteroidModel.AsteroidSize.MEDIUM);

            asteroidsToAdd.add(a1);
            asteroidsToAdd.add(a2);
            asteroidsToAdd.add(a3);
        }
    }

    public void createNewAsteroids() {
        for (AsteroidModel asteroidModel : asteroidsToAdd) {
            model.addAsteroid(asteroidModel);
            if (asteroidModel.getSize() == AsteroidModel.AsteroidSize.BIG)
                new MediumAsteroidBody(world, asteroidModel);
            if (asteroidModel.getSize() == AsteroidModel.AsteroidSize.MEDIUM)
                new MediumAsteroidBody(world, asteroidModel);
        }
        asteroidsToAdd.clear();
    }

    /**
     * Removes objects that have been flagged for removal on the
     * previous step.
     */
    public void removeFlagged() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (((EntityModel)body.getUserData()).isFlaggedToBeRemoved()) {
                model.remove((EntityModel) body.getUserData());
                world.destroyBody(body);
            }
        }
    }


}

