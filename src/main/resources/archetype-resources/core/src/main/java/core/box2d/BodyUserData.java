#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.box2d;

import com.badlogic.gdx.physics.box2d.Body;

/** Every {@link Body} should have an instance of {@link BodyUserData} as its {@link Body#userData}. */
public interface BodyUserData extends ScreenObject {
}
