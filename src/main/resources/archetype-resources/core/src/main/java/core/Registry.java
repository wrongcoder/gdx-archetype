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

	private static Logger logger;
	private static AssetManager assetManager;
	private static PlatformSupport platformSupport;

	public static Game game() {
		return (Game) Gdx.app.getApplicationListener();
	}

	public static Logger getLogger() {
		return logger;
	}

	public static AssetManager getAssetManager() {
		return assetManager;
	}

	public static PlatformSupport getPlatformSupport() {
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
	public static void initialize(final PlatformSupport platformSupport) {
		assert Registry.logger == null;
		assert Registry.assetManager == null;
		assert Registry.platformSupport == null;
		Registry.logger = new Logger("${artifactId}", Logger.DEBUG);
		Registry.assetManager = new AssetManager();
		Registry.platformSupport = platformSupport;
	}

	// --- Delegates for convenience ---

	public static void logDebug(final String message) {
		logger.debug(message);
	}

	public static void logDebug(final String message, final Exception exception) {
		logger.debug(message, exception);
	}

	public static void logInfo(final String message) {
		logger.info(message);
	}

	public static void logInfo(final String message, final Exception exception) {
		logger.info(message, exception);
	}

	public static void logError(final String message) {
		logger.error(message);
	}

	public static void logError(final String message, final Throwable exception) {
		logger.error(message, exception);
	}

	public static <T> T getAsset(final String fileName) {
		return assetManager.get(fileName);
	}

	public static <T> T getAsset(final String fileName, final Class<T> type) {
		return assetManager.get(fileName, type);
	}

}
