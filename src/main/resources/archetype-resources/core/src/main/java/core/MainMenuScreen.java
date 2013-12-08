#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ${package}.core.game.DemoScreen;

public class MainMenuScreen extends Screen {

	private static final String BUTTON_CLICK = "ui/button.wav";
	private static final float BUTTON_CLICK_VOLUME = 0.2f;

	private Stage stage;

	public MainMenuScreen(final Registry r) {
		super(r);
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		final Skin uiSkin = r.assetManager.get(AssetManager.UI, Skin.class);
		final Sound buttonClick = r.assetManager.get(BUTTON_CLICK, Sound.class);

		final TextButton startButton = new TextButton("Start", uiSkin);
		startButton.setSize(300, 48);
		startButton.setPosition(250, 175);
		startButton.addListener(new ChangeListener() {
			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				buttonClick.play(BUTTON_CLICK_VOLUME);
				r.game.setScreen(new DemoScreen(r));
			}
		});

		final TextButton creditsButton = new TextButton("Credits", uiSkin);
		creditsButton.setSize(300, 48);
		creditsButton.setPosition(250, 75);
		creditsButton.addListener(new ChangeListener() {
			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				buttonClick.play(BUTTON_CLICK_VOLUME);
				final CreditsScreen creditsScreen = new CreditsScreen(r);
				r.game.setScreen(creditsScreen);
				creditsScreen.setNextScreen(MainMenuScreen.this);
			}
		});

		stage.addActor(startButton);
		stage.addActor(creditsButton);
	}

	@Override
	public void hide() {
		stage.dispose();
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void render(final float delta) {
		Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	public static void queueAssets(final AssetManager assetManager) {
		assetManager.load(BUTTON_CLICK, Sound.class);
	}

}
