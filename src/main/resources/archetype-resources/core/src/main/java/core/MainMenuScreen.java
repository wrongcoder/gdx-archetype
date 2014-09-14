#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ${package}.core.game.DemoScreen;

public class MainMenuScreen extends Screen {

	private static final float FADE_IN_SECONDS = 0.1f;
	private static final float FADE_OUT_SECONDS = 0.085f;

	private static final String BUTTON_CLICK = "ui/button.wav";
	private static final float BUTTON_CLICK_VOLUME = 0.2f;

	private Stage stage;

	public MainMenuScreen(final Registry r) {
		super(r);
	}

	@Override
	public void show() {
		stage = makeMainMenuStage();
		Gdx.input.setInputProcessor(stage);
	}

	private Stage makeMainMenuStage() {
		final Stage stage = new Stage();

		final Skin uiSkin = r.assetManager.get(AssetManager.UI, Skin.class);
		final Sound buttonClick = r.assetManager.get(BUTTON_CLICK, Sound.class);

		final Table table = new Table();

		final Label titleLabel = new Label("${projectTitle}", uiSkin, "title");

		final TextButton startButton = new TextButton("Start", uiSkin);
		startButton.addListener(new ChangeListener() {
			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				buttonClick.play(BUTTON_CLICK_VOLUME);
				stage.addAction(Actions.sequence(
						Actions.fadeOut(FADE_OUT_SECONDS),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								Gdx.app.postRunnable(new Runnable() {
									@Override
									public void run() {
										r.game.setScreen(new DemoScreen(r));
									}
								});
							}
						})
				));
			}
		});

		final TextButton creditsButton = new TextButton("Credits", uiSkin);
		creditsButton.addListener(new ChangeListener() {
			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				buttonClick.play(BUTTON_CLICK_VOLUME);
				stage.addAction(Actions.sequence(
						Actions.fadeOut(FADE_OUT_SECONDS),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								Gdx.app.postRunnable(new Runnable() {
									@Override
									public void run() {
										final CreditsScreen creditsScreen = new CreditsScreen(r);
										r.game.setScreen(creditsScreen);
										creditsScreen.setNextScreen(MainMenuScreen.this);
									}
								});
							}
						})
				));
			}
		});

		table.defaults().padTop(50);

		table.add(titleLabel).padBottom(150);
		table.row();
		table.add(startButton).size(300, 48);
		table.row();
		table.add(creditsButton).size(300, 48);

		stage.addActor(table);
		table.setFillParent(true);

		stage.addAction(Actions.alpha(0));
		stage.addAction(Actions.fadeIn(FADE_IN_SECONDS));

		return stage;
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void render(final float delta) {
		Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	public static void queueAssets(final AssetManager assetManager) {
		assetManager.load(BUTTON_CLICK, Sound.class);
	}

}
