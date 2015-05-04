#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import ${package}.core.AssetManager;
import ${package}.core.Registry;
import ${package}.core.box2d.AbstractBodyUserData;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class Paddle {

	private final World world;
	private final Body body;

	private final Sprite sprite;

	private final float metresInPixels;
	private final float pixelsInMetres;

	public static final float pxPaddleHalfWidth = 100;
	public static final float pxPaddleHalfHeight = 5;

	public static final float pxMinimumX = -Registry.WIDTH / 2f + Walls.pxWallThickness / 2f + pxPaddleHalfWidth + 3;
	public static final float pxMaximumX = +Registry.WIDTH / 2f - Walls.pxWallThickness / 2f - pxPaddleHalfWidth - 3;

	public Paddle(final World world, final float metresInPixels, final float pxInitialMouseX) {
		this.metresInPixels = metresInPixels;
		this.pixelsInMetres = 1f / metresInPixels;

		final float mPaddleX = clamp(pxInitialMouseX, pxMinimumX, pxMaximumX) * pixelsInMetres;
		final float mPaddleY = (-Registry.HEIGHT / 2f + 50) * pixelsInMetres;

		final TextureAtlas textureAtlas = Registry.getAsset(AssetManager.TEXTURES);
		sprite = textureAtlas.createSprite("demo/paddle");
		sprite.setScale(this.pixelsInMetres);

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
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.85f;

		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.setUserData(new AbstractBodyUserData(body) {
			@Override
			protected void draw(final SpriteBatch worldBatch, final float alpha, final float mX, final float mY) {
				sprite.setCenter(mX, mY);
				sprite.draw(worldBatch);
			}
		});

		polygonShape.dispose();
	}

	public float getX() {
		return body.getPosition().x / pixelsInMetres;
	}

	public void moveTo(final float pxMouseX, final float timeStep) {
		final float pxWantX = pxMouseX - Registry.WIDTH / 2f;
		final float pxCurrentX = body.getPosition().x * metresInPixels;
		final float pxNextSpeed = computeMoveVelocity(pxCurrentX, pxWantX, timeStep);
		body.setLinearVelocity(pxNextSpeed * pixelsInMetres, 0);
	}

	public float computeMoveVelocity(final float pxCurrentX, final float pxWantX, final float timeStep) {
		final float pxWantDelta = pxWantX - pxCurrentX;
		final float pxDeltaToHitLeftWall = pxMinimumX - pxCurrentX;
		final float pxDeltaToHitRightWall = pxMaximumX - pxCurrentX;
		return clamp(pxWantDelta, pxDeltaToHitLeftWall, pxDeltaToHitRightWall) / timeStep;
	}

	public void dispose() {
		world.destroyBody(body);
	}

}
