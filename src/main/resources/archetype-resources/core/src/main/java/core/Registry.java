#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.utils.Logger;

/** Registry and initializer of global resources */
public class Registry {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public final Game game;
	public final Logger log;
	public final AssetManager assetManager;

	private boolean initialized = false;

	public Registry() {
		// This happens before Gdx initialization
		game = new Game(this);
		log = new Logger("${projectTitle}", Logger.DEBUG);
		assetManager = new AssetManager();
	}

	/** Call exactly once in {@link Game#create()} */
	public void initialize() {
		// This happens after Gdx initialization

		if (initialized) {
			throw new IllegalStateException();
		}
		initialized = true;

		assetManager.queueAssets();
	}

}
