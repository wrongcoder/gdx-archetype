#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetManager extends com.badlogic.gdx.assets.AssetManager {

	public static final String textures = "textures.atlas";
	public static final String ui = "ui.json";

	public static final String libgdxLogo = "libgdx-logo.png";

	public void queueAssets() {
		// Always load the loading screen assets first
		LoadingScreen.queueAssets(this);

		// Shared asset loaders and systems
		Fonts.queueAssets(this);

		// Other assets that have no home
		load(textures, TextureAtlas.class);
		load(ui, Skin.class, new SkinLoader.SkinParameter(textures));

		load(libgdxLogo, Texture.class);
	}

}
