package com.aor.arena.view;

import com.aor.arena.AsteroidArena;
import com.aor.arena.controller.GameController;
import com.aor.arena.model.entities.AsteroidModel;
import com.aor.arena.model.GameModel;
import com.aor.arena.model.entities.BulletModel;
import com.aor.arena.view.entities.BigAsteroidView;
import com.aor.arena.view.entities.BulletView;
import com.aor.arena.view.entities.MediumAsteroidView;
import com.aor.arena.view.entities.ShipView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import java.util.List;

import static com.aor.arena.controller.GameController.ARENA_HEIGHT;
import static com.aor.arena.controller.GameController.ARENA_WIDTH;

/**
 * A view representing the game screen. Draws all the other views and
 * controls the camera.
 */
public class GameView extends ScreenAdapter {
    /**
     * Used to debug the position of the physics fixtures
     */
    private static final boolean DEBUG_PHYSICS = false;

    /**
     * How much meters does a pixel represent.
     */
    public final static float PIXEL_TO_METER = 0.04f;

    /**
     * The width of the viewport in meters. The height is
     * automatically calculated using the screen ratio.
     */
    private static final float VIEWPORT_WIDTH = 20;

    /**
     * The game this screen belongs to.
     */
    private final AsteroidArena game;

    /**
     * The model drawn by this screen.
     */
    private final GameModel model;

    /**
     * The physics controller for this game.
     */
    private final GameController controller;

    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;

    /**
     * A ship view used to draw ships.
     */
    private final ShipView shipView;

    /**
     * A big asteroid view used to draw big asteroids.
     */
    private final BigAsteroidView bigAsteroidView;

    /**
     * A medium asteroid view used to draw medium asteroids.
     */
    private final MediumAsteroidView mediumAsteroidView;

    /**
     * A bullet view used to draw bullets.
     */
    private final BulletView bulletView;

    /**
     * A renderer used to debug the physical fixtures.
     */
    private Box2DDebugRenderer debugRenderer;

    /**
     * The transformation matrix used to transform meters into
     * pixels in order to show fixtures in their correct places.
     */
    private Matrix4 debugCamera;


    /**
     * Creates this screen.
     *
     * @param game The game this screen belongs to
     * @param model The model to be drawn
     * @param controller The physics controller
     */
    public GameView(AsteroidArena game, GameModel model, GameController controller) {
        this.game = game;
        this.model = model;
        this.controller = controller;

        loadAssets();

        shipView = new ShipView(game);
        bigAsteroidView = new BigAsteroidView(game);
        mediumAsteroidView = new MediumAsteroidView(game);
        bulletView = new BulletView(game);

        camera = createCamera();
    }

    /**
     * Creates the camera used to show the viewport.
     *
     * @return the camera
     */
    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }

    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {
        this.game.getAssetManager().load( "spaceship-no-thrust.png" , Texture.class);
        this.game.getAssetManager().load( "spaceship-thrust.png" , Texture.class);

        this.game.getAssetManager().load( "asteroid-big.png" , Texture.class);
        this.game.getAssetManager().load( "asteroid-medium.png" , Texture.class);

        this.game.getAssetManager().load( "bullet.png" , Texture.class);

        this.game.getAssetManager().load( "background.png" , Texture.class);

        this.game.getAssetManager().load( "health-bar.png" , Texture.class);
        this.game.getAssetManager().load( "controller-back.png" , Texture.class);
        this.game.getAssetManager().load( "controller-knob.png" , Texture.class);

        this.game.getAssetManager().finishLoading();
    }

    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    @Override
    public void render(float delta) {
        controller.removeFlagged();

        handleInputs(delta);

        controller.update(delta);

        camera.position.set(model.getShip().getX() / PIXEL_TO_METER, model.getShip().getY() / PIXEL_TO_METER, 0);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        drawBackground();
        drawEntities();
        game.getBatch().end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(controller.getWorld(), debugCamera);
        }
    }

    /**
     * Handles any inputs and passes them to the controller.
     *
     * @param delta time since last time inputs where handled in seconds
     */
    private void handleInputs(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            controller.rotateLeft(delta);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            controller.rotateRight(delta);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            controller.accelerate(delta);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            controller.shoot();
        }
    }

    /**
     * Draws the entities to the screen.
     */
    private void drawEntities() {
        List<AsteroidModel> asteroids = model.getAsteroids();
        for (AsteroidModel asteroid : asteroids) {
            if (asteroid.getSize() == AsteroidModel.AsteroidSize.BIG) {
                bigAsteroidView.update(asteroid);
                bigAsteroidView.draw(game.getBatch());
            } else if (asteroid.getSize() == AsteroidModel.AsteroidSize.MEDIUM) {
                mediumAsteroidView.update(asteroid);
                mediumAsteroidView.draw(game.getBatch());
            }
        }

        List<BulletModel> bullets = model.getBullets();
        for (BulletModel bullet : bullets) {
            bulletView.update(bullet);
            bulletView.draw(game.getBatch());
        }

        shipView.update(model.getShip());
        shipView.draw(game.getBatch());
    }

    /**
     * Draws the background
     */
    private void drawBackground() {
        Texture background = game.getAssetManager().get("background.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int)(ARENA_WIDTH / PIXEL_TO_METER), (int) (ARENA_HEIGHT / PIXEL_TO_METER));
    }
}