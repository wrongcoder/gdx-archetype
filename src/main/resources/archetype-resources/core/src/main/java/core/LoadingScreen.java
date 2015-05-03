#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class LoadingScreen extends Screen {

	public static final String LOADING_BORDER_ASSET = "loading-screen/border.png";
	public static final String LOADING_TEXT_ASSET = "loading-screen/text.png";

	private SpriteBatch batch;

	private Sprite loadingText;
	private Sprite loadingBorder;
	private ShapeRenderer loadingBarRenderer;

	private static final float FADE_TIME = 0.2f;
	private float fadeTimeLeft = FADE_TIME;

	private final Screen nextScreen;

	public LoadingScreen(final Screen nextScreen) {
		this.nextScreen = nextScreen;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		loadingBarRenderer = new ShapeRenderer();

		final boolean assetsAlreadyLoaded = Registry.getAssetManager().update(10);
		if (assetsAlreadyLoaded) {
			nextScreen();
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		loadingBarRenderer.dispose();
	}

	@Override
	public void render(final float delta) {
		final boolean finishedLoading = Registry.getAssetManager().update(10);
		final float fadeAlpha = fadeTimeLeft / FADE_TIME;
		final Color fadeColour = new Color(fadeAlpha, fadeAlpha, fadeAlpha, 1);

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tryLoadSprites();
		drawSprites(fadeColour);
		drawLoadingBar(fadeColour);

		if (fadeTimeLeft < 0) {
			nextScreen();
		}
		if (finishedLoading) {
			fadeTimeLeft -= delta;
		}
	}

	private void tryLoadSprites() {
		if (loadingText == null && Registry.getAssetManager().isLoaded(LOADING_TEXT_ASSET)) {
			loadingText = new Sprite(Registry.getAsset(LOADING_TEXT_ASSET, Texture.class));
			loadingText.setPosition(80, 224);
		}
		if (loadingBorder == null && Registry.getAssetManager().isLoaded(LOADING_BORDER_ASSET)) {
			loadingBorder = new Sprite(Registry.getAsset(LOADING_BORDER_ASSET, Texture.class));
			loadingBorder.setPosition(144, 80);
		}
	}

	private void drawSprites(final Color fadeColour) {
		batch.begin();
		if (loadingText != null) {
			loadingText.setColor(fadeColour);
			loadingText.draw(batch);
		}
		if (loadingBorder != null) {
			loadingBorder.setColor(fadeColour);
			loadingBorder.draw(batch);
		}
		batch.end();
	}

	private void drawLoadingBar(final Color fadeColour) {
		final float progress = Registry.getAssetManager().getProgress();
		final int width = (int) Math.ceil(480 * progress);
		loadingBarRenderer.begin(ShapeRenderer.ShapeType.Filled);
		loadingBarRenderer.setColor(fadeColour);
		loadingBarRenderer.rect(160, 96, width, 32);
		loadingBarRenderer.end();
	}

	private void nextScreen() {
		Registry.game().setScreen(nextScreen);
	}

	public static void queueAssets(final AssetManager assetManager) {
		assetManager.load(LOADING_TEXT_ASSET, Texture.class);
		assetManager.load(LOADING_BORDER_ASSET, Texture.class);
	}

}
