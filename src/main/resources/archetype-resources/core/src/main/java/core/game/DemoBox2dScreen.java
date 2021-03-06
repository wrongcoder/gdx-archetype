#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import ${package}.core.Fonts;
import ${package}.core.Registry;
import ${package}.core.Screen;
import ${package}.core.box2d.BodyUserData;
import ${package}.core.util.Counter;

public class DemoBox2dScreen extends Screen {

	public static final float metresInPixels = 32;
	public static final float pixelsInMetres = 1f / metresInPixels;

	public static final float timeStep = 1f / 20f;
	public static final int velocityIterations = 6;
	public static final int positionIterations = 2;

	private boolean debugMode;
	private SpriteBatch debugBatch;
	private Box2DDebugRenderer debugRenderer;
	private BitmapFont debugFont;

	private World world;
	private OrthographicCamera worldCamera;
	private SpriteBatch worldBatch;
	private ShapeRenderer worldRenderer;
	private Array<Body> worldBodyList;
	private float timeStepAccumulator = 0;
	private float timeStepAlpha = 1;
	private int timeStepSkipped = 0;

	private Walls walls;
	private Ball ball;
	private Paddle paddle;

	private boolean cancel;
	private Screen cancelScreen;

	public DemoBox2dScreen(final Screen cancelScreen) {
		this.cancelScreen = cancelScreen;
	}

	@Override
	public void show() {
		cancel = false;

		debugMode = true;
		debugBatch = new SpriteBatch();
		debugRenderer = new Box2DDebugRenderer();
		debugFont = Registry.getAsset(Fonts.MONO_SMALL);

		world = new World(new Vector2(0, -9.8f), true);
		worldCamera = new OrthographicCamera(Registry.WIDTH * pixelsInMetres, Registry.HEIGHT * pixelsInMetres);
		worldBatch = new SpriteBatch();
		worldBatch.setProjectionMatrix(worldCamera.combined);
		worldRenderer = new ShapeRenderer();
		worldRenderer.setProjectionMatrix(worldCamera.combined);
		worldBodyList = new Array<Body>();

		initWorld();

		Gdx.input.setInputProcessor(new DemoBox2dScreenInputProcessor());
	}

	private void initWorld() {
		walls = new Walls(world, metresInPixels);
		ball = new Ball(world, metresInPixels);
		paddle = new Paddle(world, metresInPixels, Gdx.input.getX());
	}

	@Override
	public void dispose() {
		walls.dispose();
		ball.dispose();
		paddle.dispose();

		world.dispose();
		worldBatch.dispose();
		worldRenderer.dispose();
		worldBodyList.clear();

		debugBatch.dispose();
		debugRenderer.dispose();
	}

	@Override
	public void render(final float delta) {
		timeStepAccumulator += delta;
		if (timeStepAccumulator > timeStep) {
			paddle.moveTo(Gdx.input.getX(), timeStep);
			world.step(timeStep, velocityIterations, positionIterations);
			world.getBodies(worldBodyList);
			for (final Body body : worldBodyList) {
				final BodyUserData userData = (BodyUserData) body.getUserData();
				userData.update();
			}
			timeStepAccumulator -= timeStep;
			timeStepAlpha = 0;

			while (timeStepAccumulator > timeStep) {
				timeStepSkipped += 1;
				timeStepAccumulator -= timeStep;
			}
		}
		else {
			timeStepAlpha += delta / timeStep;
		}

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		worldBatch.begin();
		for (final Body body : worldBodyList) {
			final BodyUserData userData = (BodyUserData) body.getUserData();
			userData.draw(worldBatch, timeStepAlpha);
		}
		worldBatch.end();

		if (debugMode) {
			renderDebug();
		}

		if (cancel) {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					Registry.game().setScreen(cancelScreen);
				}
			});
		}
	}

	private void renderDebug() {
		debugRenderer.render(world, worldCamera.combined);

		debugBatch.begin();
		final Counter y = new Counter(590, -16);
		debugFont.draw(debugBatch, "Debug mode (F3 to toggle)", 10, y.next());
		debugFont.draw(debugBatch, "MouseX=" + Gdx.input.getX(), 10, y.next());
		debugFont.draw(debugBatch, "PaddleX=" + paddle.getX(), 10, y.next());
		debugFont.draw(debugBatch, "FPS=" + Gdx.graphics.getFramesPerSecond() + "  Skipped=" + timeStepSkipped, 10, y.next());
		debugBatch.end();
	}

	private class DemoBox2dScreenInputProcessor extends InputAdapter {
		@Override
		public boolean keyUp(final int keycode) {
			if (keycode == Input.Keys.ESCAPE) {
				cancel = true;
				return true;
			}
			if (keycode == Input.Keys.F3) {
				debugMode = !debugMode;
				return true;
			}
			return false;
		}
	}
}
