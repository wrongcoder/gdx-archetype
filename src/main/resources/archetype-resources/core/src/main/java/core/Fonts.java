#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

/** Shared asset loader */
public class Fonts {

	public static final String MONO_SMALL = "fonts/liberation-mono-16.fnt";
	public static final String SANS_SMALL = "fonts/liberation-sans-14.fnt";
	public static final String SANS_SMALL_BOLD = "fonts/liberation-sans-14.fnt";
	public static final String SANS_MEDIUM = "fonts/liberation-sans-18.fnt";
	public static final String SANS_MEDIUM_BOLD = "fonts/liberation-sans-18-bold.fnt";
	public static final String SANS_LARGE = "fonts/liberation-sans-24.fnt";
	public static final String SANS_LARGE_BOLD = "fonts/liberation-sans-24-bold.fnt";
	public static final String SANS_HUGE = "fonts/liberation-sans-48.fnt";
	public static final String SANS_HUGE_BOLD = "fonts/liberation-sans-48-bold.fnt";
	public static final String PIXEL = "fonts/pixel-font.fnt";

	public static void queueAssets(final AssetManager assetManager) {
		assetManager.load(MONO_SMALL, BitmapFont.class);
		assetManager.load(SANS_SMALL, BitmapFont.class);
		assetManager.load(SANS_SMALL_BOLD, BitmapFont.class);
		assetManager.load(SANS_MEDIUM, BitmapFont.class);
		assetManager.load(SANS_MEDIUM_BOLD, BitmapFont.class);
		assetManager.load(SANS_LARGE, BitmapFont.class);
		assetManager.load(SANS_LARGE_BOLD, BitmapFont.class);
		assetManager.load(SANS_HUGE, BitmapFont.class);
		assetManager.load(SANS_HUGE_BOLD, BitmapFont.class);
		assetManager.load(PIXEL, BitmapFont.class);
	}

}
