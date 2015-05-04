#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import ${package}.core.AssetManager;
import ${package}.core.Registry;
import ${package}.core.box2d.AbstractBodyUserData;

public class Ball {

	private final World world;
	private final Body body;

	private final Sprite sprite;

	public Ball(final World world, final float metresInPixels) {
		final float pixelsInMetres = 1f / metresInPixels;

		final TextureAtlas textureAtlas = Registry.getAsset(AssetManager.TEXTURES);
		sprite = textureAtlas.createSprite("demo/ball");
		sprite.setScale(pixelsInMetres);

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
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.33f;

		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.setUserData(new AbstractBodyUserData(body) {
			@Override
			protected void draw(final SpriteBatch worldBatch, final float alpha, final float mX, final float mY) {
				sprite.setCenter(mX, mY);
				sprite.draw(worldBatch);
			}
		});

		circleShape.dispose();
	}

	public void dispose() {
		world.destroyBody(body);
	}

}
