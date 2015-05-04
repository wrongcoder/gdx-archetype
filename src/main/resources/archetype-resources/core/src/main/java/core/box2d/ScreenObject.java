#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.box2d;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ScreenObject {

	/** Paint the object. Called by the main render loop on every frame. */
	void draw(final SpriteBatch spriteBatch, final float delta);

	/** Update the object state. Called by the main render loop after every time step. */
	void update();

}
