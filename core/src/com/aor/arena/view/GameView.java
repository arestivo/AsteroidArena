package com.aor.arena.view;

import com.aor.arena.AsteroidArena;
import com.aor.arena.model.entities.AsteroidModel;
import com.aor.arena.model.GameModel;
import com.aor.arena.view.entities.BigAsteroidView;
import com.aor.arena.view.entities.MediumAsteroidView;
import com.aor.arena.view.entities.ShipView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;


/**
 * A view representing the game screen. Draws all the other views and
 * controls the camera.
 */
public class GameView extends ScreenAdapter {
    /**
     * The arena width in meters
     */
    public static final int ARENA_WIDTH = 200;

    /**
     * The arena height in meters
     */
    public static final int ARENA_HEIGHT = 100;

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
     * Creates this screen.
     *
     * @param game The game this screen belongs to
     * @param model The model to be drawn
     */
    public GameView(AsteroidArena game, GameModel model) {
        this.game = game;
        this.model = model;

        loadAssets();

        shipView = new ShipView(game);
        bigAsteroidView = new BigAsteroidView(game);
        mediumAsteroidView = new MediumAsteroidView(game);

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
        handleInputs(delta);

        camera.position.set(model.getShip().getX() / PIXEL_TO_METER, model.getShip().getY() / PIXEL_TO_METER, 0);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        drawBackground();
        drawEntities();
        game.getBatch().end();
    }

    /**
     * Handles any inputs and passes them to the controller.
     *
     * @param delta time since last time inputs where handled in seconds
     */
    private void handleInputs(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            model.getShip().rotateLeft(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            model.getShip().rotateRight(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            model.getShip().accelerate(delta);
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