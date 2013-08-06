#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.SerializationException;
import ${package}.core.demoscreen.DemoScreen;

public class Game extends com.badlogic.gdx.Game {
	private static final Logger logger = new Logger("${projectTitle}");

	private final Registry r;

	private Runnable postInit = new EmptyRunnable();
	private String version = "";

	public Game(final Registry r) {
		this.r = r;
	}

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		version = loadVersion();
		postInit.run();
		r.initialize();
		setScreen(new LoadingScreen(r).setNextScreen(new DemoScreen(r)));
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
			logger.error("Exception", e);
			return "(unknown version)";
		}
	}

}
