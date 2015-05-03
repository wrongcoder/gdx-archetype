#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ${package}.core.AssetManager;
import ${package}.core.Fonts;
import ${package}.core.Registry;
import ${package}.core.Screen;

public class DemoSplashScreen extends Screen {

	private static final float FADE_IN_SECONDS = 0.1f;
	private static final float FADE_HANG_SECONDS = 0.5f;
	private static final float FADE_OUT_SECONDS = 0.085f;

	private Stage stage;

	private final Screen nextScreen;

	public DemoSplashScreen(final Screen nextScreen) {
		this.nextScreen = nextScreen;
	}

	@Override
	public void show() {
		final Texture texture = Registry.getAsset(AssetManager.LIBGDX_LOGO);
		final BitmapFont font = Registry.getAsset(Fonts.SANS_MEDIUM_BOLD);
		stage = new Stage();

		final Image image = new Image(texture);
		image.setPosition(100, 100);
		stage.addActor(image);

		final Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
		final Label label = new Label("${projectTitle}", labelStyle);
		label.setPosition(Registry.WIDTH / 2, Registry.HEIGHT - 150);
		stage.addActor(label);

		stage.addAction(Actions.sequence(
				Actions.alpha(0),
				Actions.fadeIn(FADE_IN_SECONDS),
				Actions.delay(FADE_HANG_SECONDS),
				Actions.fadeOut(FADE_OUT_SECONDS),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						Gdx.app.postRunnable(new Runnable() {
							@Override
							public void run() {
								Registry.game().setScreen(nextScreen);
							}
						});
					}
				})
		));
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

}
