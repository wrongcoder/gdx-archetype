#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ${package}.core.Registry;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

public class DesktopDriver {

	public static void main(final String[] args) {
		if (args.length > 0 && "logfile".equals(args[0])) {
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
			start();
		} catch (final Throwable t) {
			System.err.println("Uncaught exception at " + new Date());
			t.printStackTrace(System.err);
		}
	}

	private static void start() {
		final Registry r = new Registry("desktop");

		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Registry.WIDTH;
		config.height = Registry.HEIGHT;
		config.useGL20 = true;
		config.resizable = false;
		config.fullscreen = false;
		config.title = "${projectTitle}";

		new LwjglApplication(r.game, config);
	}

}
