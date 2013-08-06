#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ${package}.core.Registry;

public class DesktopDriver {

	public static void main(final String[] args) throws Exception {
		final Registry r = new Registry();

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
