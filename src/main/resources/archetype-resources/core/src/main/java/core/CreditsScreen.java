#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CreditsScreen extends Screen {

	private final Screen nextScreen;

	private SpriteBatch batch;
	private TweenManager tweenManager;
	private BitmapFont mainTitleFont;
	private BitmapFont sectionTitleFont;
	private BitmapFont normalFont;
	private BitmapFont boldFont;
	private BitmapFont smallFont;

	private boolean done = false;

	private float top = 0;
	private static final float SPEED = 90; // pixels per second

	static {
		Tween.registerAccessor(CreditsScreen.class, new CreditsScreenTweenAccessor());
	}

	private final CreditsText[] creditsText = {
			new MainTitleText("${projectTitle}"),

			new VerticalSpaceCreditsText(),
			new MessageCreditsText("Built on libGDX"),
			new MessageCreditsText("http://libgdx.badlogicgames.com/")
	};

	public CreditsScreen(final Screen nextScreen) {
		this.nextScreen = nextScreen;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		mainTitleFont = Registry.getAsset(Fonts.SANS_HUGE_BOLD);
		sectionTitleFont = Registry.getAsset(Fonts.SANS_LARGE_BOLD);
		normalFont = Registry.getAsset(Fonts.SANS_MEDIUM);
		boldFont = Registry.getAsset(Fonts.SANS_MEDIUM_BOLD);
		smallFont = Registry.getAsset(Fonts.SANS_SMALL);
		Gdx.input.setInputProcessor(new CreditsScreenInputHandler());

		float nextY = 0;
		for (final CreditsText text : creditsText) {
			nextY = text.init(nextY);
		}

		final float duration = (-nextY + Registry.HEIGHT) / SPEED;
		Tween.to(this, 0, duration)
				.target(-nextY + Registry.HEIGHT)
				.ease(TweenEquations.easeNone)
				.setCallback(new CreditsScreenTweenCallback())
				.start(tweenManager);
	}

	@Override
	public void dispose() {
		batch.dispose();
		tweenManager = null;
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tweenManager.update(delta);

		batch.begin();
		for (final CreditsText text : creditsText) {
			text.draw(top);
		}
		batch.end();

		if (done) {
			Registry.game().setScreen(nextScreen);
		}
	}

	private class CreditsScreenInputHandler extends InputAdapter {
		@Override
		public boolean keyDown(final int keycode) {
			done = true;
			return true;
		}

		@Override
		public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
			done = true;
			return true;
		}
	}

	private abstract class CreditsText {
		/**
		 * @param y where to draw this block of text, relative to the credits
		 * @return where to draw the next block of text
		 */
		public abstract float init(final float y);

		/**
		 * @param top where to draw the credits, relative to the screen
		 */
		public abstract void draw(final float top);
	}

	private class MainTitleText extends CreditsText {
		private final String titleText;
		private float x;
		private float y;

		public MainTitleText(final String titleText) {
			this.titleText = titleText;
		}

		@Override
		public float init(final float y) {
			final BitmapFont.TextBounds textBounds = mainTitleFont.getBounds(titleText);
			this.x = (Registry.WIDTH - textBounds.width) / 2;
			this.y = y;
			return y - (textBounds.height + 30);
		}

		@Override
		public void draw(final float top) {
			mainTitleFont.draw(batch, titleText, x, top + y);
		}
	}

	private class SectionTitleText extends CreditsText {
		private final String titleText;
		private float x;
		private float y;

		private SectionTitleText(final String titleText) {
			this.titleText = titleText;
		}

		@Override
		public float init(final float y) {
			final BitmapFont.TextBounds textBounds = sectionTitleFont.getBounds(titleText);
			this.x = (Registry.WIDTH - textBounds.width) / 2;
			this.y = y;
			return y - (textBounds.height + 50);
		}

		@Override
		public void draw(final float top) {
			sectionTitleFont.draw(batch, titleText, x, top + y);
		}
	}

	private class VerticalSpaceCreditsText extends SectionTitleText {
		private VerticalSpaceCreditsText() {
			super("");
		}
	}

	private class MessageCreditsText extends CreditsText {
		private final String messageText;
		private float x;
		private float y;

		public MessageCreditsText(final String messageText) {
			this.messageText = messageText;
		}

		@Override
		public float init(final float y) {
			final BitmapFont.TextBounds textBounds = normalFont.getBounds(messageText);
			this.x = (Registry.WIDTH - textBounds.width) / 2;
			this.y = y;
			return y - (textBounds.height + 8);
		}

		@Override
		public void draw(final float top) {
			normalFont.draw(batch, messageText, x, top + y);
		}
	}

	private class MusicCreditsText extends CreditsText {
		private final String identityText;
		private final String usageText;
		private final String noteText;

		private float identityX, identityY;
		private float usageX, usageY;
		private float noteX, noteY;

		private MusicCreditsText(final String artist, final String title, final String usageText, final String noteText) {
			this.identityText = artist + " - " + "${symbol_escape}"" + title + "${symbol_escape}"";
			this.usageText = usageText;
			this.noteText = noteText;
		}

		private MusicCreditsText(final String artist, final String title, final String noteText) {
			this(artist, title, null, noteText);
		}

		@Override
		public float init(final float y) {
			float nextY = y;

			final BitmapFont.TextBounds identityTextBounds = boldFont.getBounds(identityText);
			identityX = (Registry.WIDTH - identityTextBounds.width) / 2;
			identityY = nextY;
			nextY -= identityTextBounds.height + 8;

			if (usageText != null) {
				final BitmapFont.TextBounds usageTextBounds = normalFont.getBounds(usageText);
				usageX = (Registry.WIDTH - usageTextBounds.width) / 2;
				usageY = nextY;
				nextY -= usageTextBounds.height + 8;
			}

			if (noteText != null) {
				final BitmapFont.TextBounds noteTextBounds = smallFont.getBounds(noteText);
				noteX = (Registry.WIDTH - noteTextBounds.width) / 2;
				noteY = nextY;
				nextY -= noteTextBounds.height + 8;
			}

			return nextY;
		}

		@Override
		public void draw(final float top) {
			boldFont.draw(batch, identityText, identityX, top + identityY);
			if (usageText != null) {
				normalFont.draw(batch, usageText, usageX, top + usageY);
			}
			if (noteText != null) {
				smallFont.draw(batch, noteText, noteX, top + noteY);
			}
		}
	}

	private static class CreditsScreenTweenAccessor implements TweenAccessor<CreditsScreen> {
		@Override
		public int getValues(final CreditsScreen creditsScreen, final int tweenType, final float[] returnValues) {
			returnValues[0] = creditsScreen.top;
			return 1;
		}

		@Override
		public void setValues(final CreditsScreen creditsScreen, final int tweenType, final float[] newValues) {
			creditsScreen.top = newValues[0];
		}
	}

	private class CreditsScreenTweenCallback implements TweenCallback {
		@Override
		public void onEvent(final int type, final BaseTween<?> source) {
			if (type == TweenCallback.COMPLETE) {
				done = true;
			}
		}
	}

}
