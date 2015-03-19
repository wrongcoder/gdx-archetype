#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Ball {

	private final World world;
	private final Body body;

	public Ball(final World world, final float metresInPixels) {
		final float pixelsInMetres = 1f / metresInPixels;

		this.world = world;

		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.fixedRotation = false;
		bodyDef.position.set(0, 0);
		bodyDef.linearVelocity.set(0, 0);

		final CircleShape circleShape = new CircleShape();
		circleShape.setRadius(10 * pixelsInMetres);

		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circleShape;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0.33f;

		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);

		circleShape.dispose();
	}

	public void dispose() {
		world.destroyBody(body);
	}

}
