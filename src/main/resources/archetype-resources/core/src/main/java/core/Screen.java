#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.assets.AssetManager;

public abstract class Screen implements com.badlogic.gdx.Screen {

	private final Game game;

	public Screen(final Game game) {
		this.game = game;
	}

	public Game game() {
		return game;
	}

	public AssetManager assetManager() {
		return game().assetManager();
	}

	public void log(final String message) {
		game().log(message);
	}

	public void log(final String message, final Exception exception) {
		game().log(message, exception);
	}

	public void debug(final String message) {
		game().debug(message);
	}

	public void debug(final String message, final Exception exception) {
		game().debug(message, exception);
	}

	public void error(final String message) {
		game().error(message);
	}

	public void error(final String message, final Exception exception) {
		game().error(message, exception);
	}

	@Override
	public void resize(final int width, final int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
