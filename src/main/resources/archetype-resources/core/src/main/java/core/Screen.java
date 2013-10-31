#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Screen implements com.badlogic.gdx.Screen {

	protected final Registry r;

	public Screen(final Registry r) {
		this.r = r;
	}

	@Override
	public void resize(final int width, final int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

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
