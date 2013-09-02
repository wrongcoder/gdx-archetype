#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Logger;

/** Registry and initializer of global resources */
public class Registry {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public final Game game;
	public final Logger log;
	public final AssetManager assetManager;

	public final PlatformSupport platformSupport;

	private boolean initialized = false;

	public Registry(final PlatformSupport platformSupport) {
		// This happens before Gdx initialization
		this.game = new Game(this);
		this.log = new Logger("${artifactId}", Logger.DEBUG);
		this.assetManager = new AssetManager();
		this.platformSupport = platformSupport;
	}

	/** Call exactly once in {@link Game#create()} */
	public void initialize() {
		if (initialized) {
			throw new IllegalStateException();
		}
		initialized = true;

		// This happens after Gdx initialization

		assetManager.queueAssets();

		platformSupport.initializePlatform();
	}

	public Preferences getPreferences() {
		return Gdx.app.getPreferences("${groupId}.${artifactId}");
	}

	public String getClientId() {
		final Preferences preferences = getPreferences();

		String clientId = preferences.getString("clientId", "");
		if (clientId.isEmpty()) {
			clientId = generateClientId();
			preferences.putString("clientId", clientId);
			preferences.flush();
		}
		return clientId;
	}

	private static String generateClientId() {
		//noinspection SpellCheckingInspection
		final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		final int numChars = chars.length();

		final StringBuilder sb = new StringBuilder(16);
		for (int i = 0; i < 16; i++) {
			final int c = (int) Math.floor(Math.random() * numChars);
			sb.append(chars.charAt(c));
		}

		return sb.toString();
	}

}
