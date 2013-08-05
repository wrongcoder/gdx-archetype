#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

/** Shared asset loader */
public class Fonts {

	public static final String monoSmallFont = "fonts/liberation-mono-16.fnt";
	public static final String sansSmallFont = "fonts/liberation-sans-14.fnt";
	public static final String sansSmallBoldFont = "fonts/liberation-sans-14.fnt";
	public static final String sansMediumFont = "fonts/liberation-sans-18.fnt";
	public static final String sansMediumBoldFont = "fonts/liberation-sans-18-bold.fnt";
	public static final String sansLargeFont = "fonts/liberation-sans-24.fnt";
	public static final String sansLargeBoldFont = "fonts/liberation-sans-24-bold.fnt";
	public static final String sansHugeFont = "fonts/liberation-sans-48.fnt";
	public static final String sansHugeBoldFont = "fonts/liberation-sans-48-bold.fnt";

	public static void queueAssets(final AssetManager assetManager) {
		assetManager.load(monoSmallFont, BitmapFont.class);
		assetManager.load(sansSmallFont, BitmapFont.class);
		assetManager.load(sansSmallBoldFont, BitmapFont.class);
		assetManager.load(sansMediumFont, BitmapFont.class);
		assetManager.load(sansMediumBoldFont, BitmapFont.class);
		assetManager.load(sansLargeFont, BitmapFont.class);
		assetManager.load(sansLargeBoldFont, BitmapFont.class);
		assetManager.load(sansHugeFont, BitmapFont.class);
		assetManager.load(sansHugeBoldFont, BitmapFont.class);
	}

}
