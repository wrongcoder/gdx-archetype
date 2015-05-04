#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;

public class Game extends com.badlogic.gdx.Game {

	private String fullVersion;
	private String shortVersion;
	private String versionId;

	static {
		Tween.setWaypointsLimit(2);
	}

	@Override
	public void create() {
		assert Registry.getLogger() != null;
		assert Registry.getAssetManager() != null;
		assert Registry.getPlatformSupport() != null;

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		loadVersion();
		Registry.getAssetManager().queueAssets();
		Registry.getPlatformSupport().initializePlatform();
		setScreen(new LoadingScreen(new MainMenuScreen()));
	}

	public String getFullVersion() {
		return fullVersion;
	}

	public String getShortVersion() {
		return shortVersion;
	}

	public String getVersionId() {
		return versionId;
	}

	private void loadVersion() {
		try {
			final FileHandle versionFile = Gdx.files.internal("version.json");
			final JsonValue version = new JsonReader().parse(versionFile);
			final VersionStrings versionStrings = new VersionStrings(version);
			fullVersion = versionStrings.fullVersion;
			shortVersion = versionStrings.shortVersion;
			versionId = versionStrings.versionId;
		}
		catch (final SerializationException e) {
			Registry.logError("Error reading version number", e);
			fullVersion = "(unknown)";
			shortVersion = "(unknown)";
			versionId = "unknown";
		}
	}

}
