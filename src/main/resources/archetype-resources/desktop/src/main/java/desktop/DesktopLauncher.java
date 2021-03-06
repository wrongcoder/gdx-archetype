#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.desktop;

import javax.swing.JOptionPane;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;

/**
 * This is a JVM re-launcher that starts {@link DesktopDriver} with sufficient heap.
 */
public class DesktopLauncher {

	public static void main(final String[] args) {
		final ByteArrayOutputStream stream = new ByteArrayOutputStream(1024);
		final PrintWriter writer = new PrintWriter(stream);

		try {
			launch();
		}
		catch (final Throwable t) {
			t.printStackTrace();
			t.printStackTrace(writer);
			writer.flush();
			JOptionPane.showMessageDialog(null, stream.toString(), "Launcher Exception", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void launch() throws Exception {
		final String javaHome = System.getProperty("java.home");
		final File javaBinDir = new File(new File(javaHome), "bin");

		final String jarUrl = DesktopLauncher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		final String jarPath = URLDecoder.decode(jarUrl, "UTF-8");
		final File jarFile = new File(jarPath);

		final File home;
		if (jarFile.isFile()) {
			home = jarFile.getParentFile();
		}
		else {
			throw new IllegalStateException("Could not locate JAR");
		}

		final File javaExe = new File(javaBinDir, "java.exe");
		final File java = new File(javaBinDir, "java");
		final String javaExecutablePath;

		if (javaExe.exists()) {
			javaExecutablePath = javaExe.getPath();
		}
		else if (java.exists()) {
			javaExecutablePath = java.getPath();
		}
		else {
			throw new IllegalStateException("Could not locate Java executable");
		}

		final ProcessBuilder processBuilder = new ProcessBuilder(
				javaExecutablePath,
				"-Xmx512m",
				"-Xms512m",
				"-cp",
				fixWindowsPath(jarPath),
				"${package}.desktop.DesktopDriver",
				"production"
		);
		processBuilder.directory(home);

		try {
			processBuilder.getClass().getMethod("inheritIO").invoke(processBuilder);
		}
		catch (final InvocationTargetException e) {
			// Unexpected: ProcessBuilder#inheritIO() throws no checked exceptions
			e.printStackTrace();
		}
		catch (final NoSuchMethodException e) {
			// JDK 6
		}
		catch (final IllegalAccessException e) {
			// Security manager
		}

		final Process process = processBuilder.start();
		final long processStartTime = System.currentTimeMillis();

		while (System.currentTimeMillis() < processStartTime + 5000) {
			sleep(500);

			final Integer exitCode = getExitCode(process);
			if (exitCode != null && exitCode != 0) {
				throw new IllegalStateException("Exit code: " + exitCode);
			}
		}
	}

	private static String fixWindowsPath(final String path) {
		if (path.matches("/[A-Za-z]:/.*")) {
			return path.substring(1);
		}
		else {
			return path;
		}
	}

	private static Integer getExitCode(final Process process) {
		try {
			return process.exitValue();
		}
		catch (final IllegalThreadStateException e) {
			return null;
		}
	}

	private static void sleep(final long millis) {
		try {
			Thread.sleep(millis);
		}
		catch (final InterruptedException e) {
			// Ignore
		}
	}

}
