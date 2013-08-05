#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.demoscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ${package}.core.AssetManager;
import ${package}.core.Fonts;
import ${package}.core.Registry;
import ${package}.core.Screen;

public class DemoScreen extends Screen {

	private Texture texture;
	private BitmapFont font;
	private SpriteBatch batch;

	public DemoScreen(final Registry r) {
		super(r);
	}

	@Override
	public void show() {
		texture = r.assetManager.get(AssetManager.libgdxLogo);
		font = r.assetManager.get(Fonts.sansMediumBoldFont);
		batch = new SpriteBatch();
	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texture, 100, 100);
		drawCentred(font, batch, "${projectTitle}", Registry.WIDTH / 2, Registry.HEIGHT - 150);
		batch.end();
	}

	private static void drawCentred(final BitmapFont font, final SpriteBatch batch, final String text, final float x, final float y) {
		final BitmapFont.TextBounds bounds = font.getBounds(text);
		final float centreX = x - bounds.width / 2;
		final float centreY = y + bounds.height / 2;
		font.draw(batch, text, centreX, centreY);
	}

}
