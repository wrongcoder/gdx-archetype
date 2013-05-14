#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class LoadingScreen extends Screen {

	private SpriteBatch batch;

	private Sprite loadingText;
	private Sprite loadingBorder;
	private ShapeRenderer loadingBarRenderer;

	private static final float fadeTime = 0.2f;
	private float fadeTimeLeft = fadeTime;

	public LoadingScreen(final Game game) {
		super(game);
	}

	@Override
	public void show() {
		Asset.loadAssets(assetManager());
		batch = new SpriteBatch();
		loadingBarRenderer = new ShapeRenderer();
	}

	@Override
	public void render(final float delta) {
		final boolean finishedLoading = assetManager().update();
		final float fadeAlpha = fadeTimeLeft / fadeTime;
		final Color fadeColour = new Color(fadeAlpha, fadeAlpha, fadeAlpha, 1);

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		if (assetManager().isLoaded(Asset.loadingAtlas)) {
			final TextureAtlas atlas = assetManager().get(Asset.loadingAtlas);
			loadingText = atlas.createSprite(Asset.loadingText);
			loadingText.setPosition(192, 350);
			loadingBorder = atlas.createSprite(Asset.loadingBorder);
			loadingBorder.setPosition(144, 200);
		}

		if (loadingText != null && loadingBorder != null) {
			batch.begin();
			loadingText.setColor(fadeColour);
			loadingText.draw(batch);
			loadingBorder.setColor(fadeColour);
			loadingBorder.draw(batch);
			batch.end();
			drawLoadingBar(fadeAlpha);
		}

		if (fadeTimeLeft < 0) {
			game().setScreen(new DemoScreen(game()));
		}
		if (finishedLoading) {
			fadeTimeLeft -= delta;
		}
	}

	private void drawLoadingBar(final float fadeAlpha) {
		final float progress = assetManager().getProgress();
		final int width = (int) Math.ceil(480 * progress);
		loadingBarRenderer.begin(ShapeRenderer.ShapeType.Filled);
		loadingBarRenderer.setColor(fadeAlpha, fadeAlpha, fadeAlpha, 1);
		loadingBarRenderer.rect(160, 217, width, 32);
		loadingBarRenderer.end();
	}

}
