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
