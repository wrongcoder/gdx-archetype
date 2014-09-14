#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
		texture = r.assetManager.get(AssetManager.LIBGDX_LOGO);
		font = r.assetManager.get(Fonts.SANS_MEDIUM_BOLD);
		batch = new SpriteBatch();
	}

	@Override
	public void hide() {
		batch.dispose();
	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texture, 100, 100);
		drawCentred(font, batch, "${projectTitle}", Registry.WIDTH / 2, Registry.HEIGHT - 150);
		batch.end();
	}

}
