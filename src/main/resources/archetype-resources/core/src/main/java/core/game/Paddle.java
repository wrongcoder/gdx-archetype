#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import ${package}.core.Registry;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class Paddle {

	private final World world;
	private final Body body;

	private final float metresInPixels;
	private final float pixelsInMetres;

	public static final float pxPaddleHalfWidth = Registry.WIDTH / 8f;
	public static final float pxPaddleHalfHeight = 5;

	public static final float pxMinimumX = -Registry.WIDTH / 2f + Walls.pxWallThickness / 2f + pxPaddleHalfWidth + 5;
	public static final float pxMaximumX = +Registry.WIDTH / 2f - Walls.pxWallThickness / 2f - pxPaddleHalfWidth - 5;
	public static final float pxMaximumSpeed = Registry.WIDTH; /* pixels per second */

	public Paddle(final World world, final float metresInPixels, final float pxInitialMouseX) {
		this.metresInPixels = metresInPixels;
		this.pixelsInMetres = 1f / metresInPixels;

		final float mPaddleX = clamp(pxInitialMouseX, pxMinimumX, pxMaximumX) * pixelsInMetres;
		final float mPaddleY = (-Registry.HEIGHT / 2f + 50) * pixelsInMetres;

		this.world = world;

		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.KinematicBody;
		bodyDef.fixedRotation = true;
		bodyDef.position.set(mPaddleX, mPaddleY);

		final PolygonShape polygonShape = new PolygonShape();
		final float mPaddleHalfWidth = pxPaddleHalfWidth * pixelsInMetres;
		final float mPaddleHalfHeight = pxPaddleHalfHeight * pixelsInMetres;
		polygonShape.setAsBox(mPaddleHalfWidth, mPaddleHalfHeight);

		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0.85f;

		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);

		polygonShape.dispose();
	}

	public float getX() {
		return body.getPosition().x / pixelsInMetres;
	}

	public void moveTo(final float pxMouseX, final float delta) {
		final float pxWantX = pxMouseX - Registry.WIDTH / 2f;
		final float pxCurrentX = body.getPosition().x * metresInPixels;
		final float pxVelocityToHitLeftWall = (pxMinimumX - pxCurrentX) / delta;
		final float pxVelocityToHitRightWall = (pxMaximumX - pxCurrentX) / delta;

		final float pxSpeed = clamp((pxWantX - pxCurrentX) / delta,
				Math.max(-pxMaximumSpeed, pxVelocityToHitLeftWall),
				Math.min(pxMaximumSpeed, pxVelocityToHitRightWall));

		body.setLinearVelocity(pxSpeed * pixelsInMetres, 0);
	}

	public void dispose() {
		world.destroyBody(body);
	}

}
