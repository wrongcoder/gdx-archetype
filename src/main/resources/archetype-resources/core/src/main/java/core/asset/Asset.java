#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ${package}.core.Registry;

public class Asset {

	public static final String loadingAtlas = "textures/loading.atlas";
	public static final String loadingText = "text";
	public static final String loadingBorder = "border";

	public static final String libgdxLogo = "libgdx-logo.png";

	public static void queueAssets(final Registry r) {
		// Assets for LoadingScreen should be loaded first
		r.assetManager.load(loadingAtlas, TextureAtlas.class);

		// Remaining assets
		r.assetManager.load(libgdxLogo, Texture.class);
	}

}
