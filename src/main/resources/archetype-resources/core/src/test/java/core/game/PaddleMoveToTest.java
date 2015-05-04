#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import ${package}.core.testutil.AbstractInGameTest;
import org.junit.Before;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static ${package}.core.testutil.Matchers.isCloseTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest({World.class, Body.class})
public class PaddleMoveToTest extends AbstractInGameTest {

	public Paddle paddle;
	public Body paddleBody;

	@Before
	public void setup() {
		defaultMockAssetManagerBehaviour();

		final World world = mock(World.class);
		paddleBody = mock(Body.class);
		when(world.createBody(any(BodyDef.class))).thenReturn(paddleBody);
		when(paddleBody.getPosition()).thenReturn(new Vector2());

		paddle = new Paddle(world, 100, 0);
	}

	@Test
	public void basicMoves() {
		assertThat("No movement from zero", paddle.computeMoveVelocity(0, 0, 1), isCloseTo(0));
		assertThat("No movement from negative", paddle.computeMoveVelocity(-100, -100, 1), isCloseTo(0));
		assertThat("No movement from positive", paddle.computeMoveVelocity(100, 100, 1), isCloseTo(0));
		assertThat("Slightly to the left from zero", paddle.computeMoveVelocity(0, -5, 0.5f), isCloseTo(-10f));
		assertThat("Slightly to the right from zero", paddle.computeMoveVelocity(0, 5, 0.5f), isCloseTo(10f));
		assertThat("Slightly to the left from negative", paddle.computeMoveVelocity(-100, -105, 0.5f), isCloseTo(-10f));
		assertThat("Slightly to the right from negative", paddle.computeMoveVelocity(-100, -95, 0.5f), isCloseTo(10f));
		assertThat("Slightly to the left from positive", paddle.computeMoveVelocity(100, 95, 0.5f), isCloseTo(-10f));
		assertThat("Slightly to the right from positive", paddle.computeMoveVelocity(100, 105, 0.5f), isCloseTo(10f));
	}

	@Test
	public void collisionWithWalls() {
		assertThat("Going through left wall", paddle.computeMoveVelocity(Paddle.pxMinimumX + 10, Paddle.pxMinimumX - 20, 0.1f), isCloseTo(-100));
		assertThat("Going through right wall", paddle.computeMoveVelocity(Paddle.pxMaximumX - 10, Paddle.pxMaximumX + 20, 0.1f), isCloseTo(100));
	}

}
