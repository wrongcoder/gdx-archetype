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

	public final Runnable platformInitializer;
	private boolean initialized = false;

	public Registry() {
		this(null);
	}

	public Registry(final Runnable platformInitializer) {
		// This happens before Gdx initialization
		this.game = new Game(this);
		this.log = new Logger("${projectTitle}", Logger.DEBUG);
		this.assetManager = new AssetManager();
		this.platformInitializer = platformInitializer;
	}

	/** Call exactly once in {@link Game#create()} */
	public void initialize() {
		if (initialized) {
			throw new IllegalStateException();
		}
		initialized = true;

		// This happens after Gdx initialization

		assetManager.queueAssets();

		if (platformInitializer != null) {
			platformInitializer.run();
		}
	}

}
