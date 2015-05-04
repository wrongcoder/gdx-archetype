#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.box2d;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class AbstractBodyUserData implements BodyUserData {

	protected final Body body;

	protected float mPrevPositionX, mPrevPositionY;
	protected float mNextPositionX, mNextPositionY;

	protected AbstractBodyUserData(final Body body) {
		this.body = body;
		update();
	}

	@Override
	public void update() {
		final Vector2 nextPosition = body.getPosition();
		mPrevPositionX = mNextPositionX;
		mPrevPositionY = mNextPositionY;
		mNextPositionX = nextPosition.x;
		mNextPositionY = nextPosition.y;
	}

	@Override
	public void draw(final SpriteBatch worldBatch, final float alpha) {
		final float mX = ((1 - alpha) * mPrevPositionX) + (alpha * mNextPositionX);
		final float mY = ((1 - alpha) * mPrevPositionY) + (alpha * mNextPositionY);
		draw(worldBatch, alpha, mX, mY);
	}

	protected abstract void draw(final SpriteBatch worldBatch, final float alpha, final float mX, final float mY);

}
