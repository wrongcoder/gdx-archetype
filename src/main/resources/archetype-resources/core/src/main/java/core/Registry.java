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

	public static final Logger log = new Logger("${artifactId}", Logger.DEBUG);
	public static final AssetManager assetManager = new AssetManager();

	private static PlatformSupport platformSupport;

	public static Game game() {
		return (Game) Gdx.app.getApplicationListener();
	}

	public static PlatformSupport platformSupport() {
		return platformSupport;
	}

	public static Preferences getPreferences() {
		return Gdx.app.getPreferences("${groupId}.${artifactId}");
	}

	public static String getClientId() {
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

	/** This must be called exactly once from the platform driver */
	public static void registerPlatformSupport(final PlatformSupport platformSupport) {
		assert Registry.platformSupport == null;
		Registry.platformSupport = platformSupport;
	}

}
