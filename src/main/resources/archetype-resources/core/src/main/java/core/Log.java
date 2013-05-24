package ${package}.core;

import com.badlogic.gdx.Gdx;

public class Log {

	public static final String tag = "${projectTitle}";

	public static void log(final String message) {
		Gdx.app.log(tag, message);
	}

	public static void log(final String message, final Exception exception) {
		Gdx.app.log(tag, message, exception);
	}

	public static void debug(final String message) {
		Gdx.app.debug(tag, message);
	}

	public static void debug(final String message, final Exception exception) {
		Gdx.app.debug(tag, message, exception);
	}

	public static void error(final String message) {
		Gdx.app.error(tag, message);
	}

	public static void error(final String message, final Exception exception) {
		Gdx.app.error(tag, message, exception);
	}

}
