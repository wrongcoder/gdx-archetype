#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vdxp.gdx.lib.ScreenAdapter;

public abstract class Screen extends ScreenAdapter {

	public static void drawCentred(final BitmapFont font, final SpriteBatch batch, final String text, final float x, final float y) {
		final BitmapFont.TextBounds bounds = font.getBounds(text);
		final float centreX = x - bounds.width / 2;
		final float centreY = y + bounds.height / 2;
		font.draw(batch, text, centreX, centreY);
	}

	public static void drawCentred(final BitmapFont font, final SpriteBatch batch, final String text, final float y) {
		drawCentred(font, batch, text, Registry.WIDTH / 2, y);
	}

}
