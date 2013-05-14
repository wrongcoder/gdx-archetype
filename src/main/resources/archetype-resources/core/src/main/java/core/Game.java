#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

public class Game extends com.badlogic.gdx.Game {
	private static final Game instance = new Game();
	private Runnable postInit = new EmptyRunnable();

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

    private AssetManager assetManager;

	private Game() {
	}

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		assetManager = new AssetManager();
		setScreen(new LoadingScreen(this));
		postInit.run();
	}

	public static Game instance() {
		return instance;
	}

	public AssetManager assetManager() {
		return assetManager;
	}

	public void setPostInit(final Runnable postInit) {
		this.postInit = postInit;
	}

	private static class EmptyRunnable implements Runnable {
		@Override
		public void run() {
		}
	}

}
