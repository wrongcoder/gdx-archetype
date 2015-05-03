#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ${package}.core.Game;
import ${package}.core.PlatformSupport;
import ${package}.core.Registry;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

public class DesktopDriver {

	public static void main(final String[] args) {
		final boolean production = (args.length > 0 && "production".equals(args[0]));

		if (production) {
			try {
				final FileOutputStream logOutputStream = new FileOutputStream("${artifactId}.log", true);
				final PrintStream logStream = new PrintStream(logOutputStream);
				System.setOut(logStream);
				System.setErr(logStream);
			} catch (final FileNotFoundException e) {
				System.err.println("Unable to open log file");
				e.printStackTrace(System.err);
			}

			System.out.println();
			System.out.println("**********");
			System.out.println("Launched at " + new Date());
		}

		try {
			start(production);
		} catch (final Throwable t) {
			System.err.println("Uncaught exception at " + new Date());
			t.printStackTrace(System.err);
		}
	}

	private static void start(final boolean production) {
		Registry.initialize(new DesktopPlatformSupport(production));

		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Registry.WIDTH;
		config.height = Registry.HEIGHT;
		config.resizable = false;
		config.fullscreen = false;
		config.addIcon("icons/gamepad-64x.png", Files.FileType.Classpath);
		config.addIcon("icons/gamepad-48x.png", Files.FileType.Classpath);
		config.addIcon("icons/gamepad-32x.png", Files.FileType.Classpath);
		config.addIcon("icons/gamepad-16x.png", Files.FileType.Classpath);
		config.title = "${projectTitle}";

		new LwjglApplication(new Game(), config);
	}

	private static class DesktopPlatformSupport implements PlatformSupport {

		private final boolean production;

		private DesktopPlatformSupport(final boolean production) {
			this.production = production;
		}

		@Override
		public void initializePlatform() {
			if (production) {
				Gdx.app.setLogLevel(Application.LOG_ERROR);
			}
		}

	}

}
