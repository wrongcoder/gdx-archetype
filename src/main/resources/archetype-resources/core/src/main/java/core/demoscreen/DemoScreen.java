#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.demoscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ${package}.core.Asset;
import ${package}.core.Game;
import ${package}.core.Screen;

public class DemoScreen extends Screen {

	private Texture texture;
	private SpriteBatch batch;

	public DemoScreen(final Game game) {
		super(game);
	}

	@Override
	public void show() {
		texture = Asset.manager.get(Asset.libgdxLogo);
		batch = new SpriteBatch();
	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texture, 100, 100);
		batch.end();
	}

}
