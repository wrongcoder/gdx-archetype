#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.testutil;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Logger;
import ${package}.core.AssetManager;
import ${package}.core.PlatformSupport;
import ${package}.core.Registry;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.rule.PowerMockRule;

import java.lang.reflect.Field;

import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(GdxTestRunner.class)
public abstract class AbstractInGameTest {

	@Rule
	public PowerMockRule setupPowerMock = new PowerMockRule();

	public AssetManager assetManager;
	public Logger logger;
	public PlatformSupport platformSupport;

	@Before
	public void setupRegistry() throws NoSuchFieldException, IllegalAccessException {
		assetManager = PowerMockito.mock(AssetManager.class);
		logger = PowerMockito.mock(Logger.class);
		platformSupport = PowerMockito.mock(PlatformSupport.class);
		setStaticField(Registry.class, "assetManager", assetManager);
		setStaticField(Registry.class, "logger", logger);
		setStaticField(Registry.class, "platformSupport", platformSupport);
	}

	public void defaultMockAssetManagerBehaviour() {
		final TextureAtlas textures = mock(TextureAtlas.class);
		final Sprite sprite = mock(Sprite.class);
		when(textures.createSprite(anyString())).thenReturn(sprite);
		when(assetManager.get(AssetManager.TEXTURES)).thenReturn(textures);
	}

	private static void setStaticField(final Class clazz, final String fieldName, final Object newValue) throws NoSuchFieldException, IllegalAccessException {
		final Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(null, newValue);
	}

}
