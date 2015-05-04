#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import ${package}.core.Registry;
import ${package}.core.box2d.BodyUserData;

public class Walls {

	private final World world;
	private final Body leftWall;
	private final Body rightWall;
	private final Body topWall;
	private final Body bottomWall;

	public static final float pxWallThickness = 3f;

	public Walls(final World world, final float metresInPixels) {
		final float pixelsInMetres = 1f / metresInPixels;
		final float mWallThickness = pxWallThickness * pixelsInMetres;

		this.world = world;

		final BodyDef bodyDef = new BodyDef();
		final FixtureDef fixtureDef = new FixtureDef();
		final PolygonShape polygonShape = new PolygonShape();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.fixedRotation = true;
		fixtureDef.shape = polygonShape;
		fixtureDef.friction = 1;
		fixtureDef.restitution = 1;

		polygonShape.setAsBox(mWallThickness, Registry.HEIGHT);
		bodyDef.position.set(-Registry.WIDTH * pixelsInMetres / 2, 0);
		leftWall = world.createBody(bodyDef);
		leftWall.createFixture(fixtureDef);
		leftWall.setUserData(new WallUserData());
		bodyDef.position.set(+Registry.WIDTH * pixelsInMetres / 2, 0);
		rightWall = world.createBody(bodyDef);
		rightWall.createFixture(fixtureDef);
		rightWall.setUserData(new WallUserData());

		polygonShape.setAsBox(Registry.WIDTH, mWallThickness);
		bodyDef.position.set(0, -Registry.HEIGHT * pixelsInMetres / 2);
		bottomWall = world.createBody(bodyDef);
		bottomWall.createFixture(fixtureDef);
		bottomWall.setUserData(new WallUserData());
		bodyDef.position.set(0, +Registry.HEIGHT * pixelsInMetres / 2);
		topWall = world.createBody(bodyDef);
		topWall.createFixture(fixtureDef);
		topWall.setUserData(new WallUserData());

		polygonShape.dispose();
	}

	public void dispose() {
		world.destroyBody(leftWall);
		world.destroyBody(rightWall);
		world.destroyBody(topWall);
		world.destroyBody(bottomWall);
	}

	private class WallUserData implements BodyUserData {
		@Override
		public void draw(final SpriteBatch spriteBatch, final float delta) {
		}

		@Override
		public void update() {
		}
	}

}
