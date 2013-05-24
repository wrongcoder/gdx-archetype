#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;

public class Game extends com.badlogic.gdx.Game {
	private static final Game instance = new Game();

	private Runnable postInit = new EmptyRunnable();
	private String version = "";

	public static final String TAG = "${projectTitle}";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

    private AssetManager assetManager;

	private Game() {
	}

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		assetManager = new AssetManager();
		version = loadVersion();
		setScreen(new LoadingScreen(this).setNextScreen(new DemoScreen(this)));
		postInit.run();
	}

	public static Game instance() {
		return instance;
	}

	public AssetManager assetManager() {
		return assetManager;
	}

	public void log(final String message) {
		Gdx.app.log(TAG, message);
	}

	public void log(final String message, final Exception exception) {
		Gdx.app.log(TAG, message, exception);
	}

	public void debug(final String message) {
		Gdx.app.debug(TAG, message);
	}

	public void debug(final String message, final Exception exception) {
		Gdx.app.debug(TAG, message, exception);
	}

	public void error(final String message) {
		Gdx.app.error(TAG, message);
	}

	public void error(final String message, final Exception exception) {
		Gdx.app.error(TAG, message, exception);
	}

	public String getVersion() {
		return version;
	}

	public void setPostInit(final Runnable postInit) {
		this.postInit = postInit;
	}

	private static class EmptyRunnable implements Runnable {
		@Override
		public void run() {
		}
	}

	private static String loadVersion() {
		try {
			final FileHandle versionFile = Gdx.files.internal("version.json");
			final JsonValue versionRoot = new JsonReader().parse(versionFile);
			final String versionNumber = versionRoot.get("version").asString();
			final String buildNumber = versionRoot.get("buildNumber").asString();
			final String buildDate = versionRoot.get("buildDate").asString();
			final String commit = versionRoot.get("commit").asString();

			String version;

			if (buildNumber != null && !buildNumber.isEmpty() && !buildNumber.contains("${symbol_dollar}{")) {
				version = "Build " + buildNumber;
			} else if (commit != null && !commit.isEmpty() && !commit.contains("${symbol_dollar}{")) {
				version = "Commit " + commit;
			} else {
				version = "Version " + versionNumber;
			}

			if (buildDate != null && !buildDate.isEmpty() && !buildDate.contains("${symbol_dollar}{")) {
				version += " (" + buildDate + ")";
			}

			return version;

		} catch (SerializationException e) {
			Gdx.app.error(TAG, "Exception", e);
			return "(unknown version)";
		}
	}

}
