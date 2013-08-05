#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.graphics.Texture;

public class AssetManager extends com.badlogic.gdx.assets.AssetManager {

	public static final String libgdxLogo = "libgdx-logo.png";

	public void queueAssets() {
		// Always load the loading screen assets first
		LoadingScreen.queueAssets(this);

		// Shared asset loaders and systems
		Fonts.queueAssets(this);

		// Other assets that have no home
		load(libgdxLogo, Texture.class);
	}

}
