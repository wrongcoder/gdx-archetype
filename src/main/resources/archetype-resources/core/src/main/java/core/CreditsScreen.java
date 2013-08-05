#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CreditsScreen extends Screen {

	private SpriteBatch batch;
	private BitmapFont mainTitleFont;
	private BitmapFont sectionTitleFont;
	private BitmapFont normalFont;
	private BitmapFont boldFont;
	private BitmapFont smallFont;

	private Screen nextScreen;
	private boolean skip = false;

	private double top = 0;
	private static final float speed = 120; // pixels per second

	private final CreditsText[] creditsText = {
			new MainTitleText("${projectTitle}"),

			new VerticalSpaceCreditsText(),
			new MessageCreditsText("Built on libGDX"),
			new MessageCreditsText("http://libgdx.badlogicgames.com/")
	};

	public CreditsScreen(final Registry r) {
		super(r);
	}

	public Screen setNextScreen(final Screen nextScreen) {
		this.nextScreen = nextScreen;
		return this;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		mainTitleFont = r.assetManager.get(Fonts.sansHugeBoldFont);
		sectionTitleFont = r.assetManager.get(Fonts.sansLargeBoldFont);
		normalFont = r.assetManager.get(Fonts.sansMediumFont);
		boldFont = r.assetManager.get(Fonts.sansMediumBoldFont);
		smallFont = r.assetManager.get(Fonts.sansSmallFont);
		Gdx.input.setInputProcessor(new CreditsScreenInputHandler());

		for (final CreditsText text : creditsText) {
			text.init();
		}
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		top += delta * speed;
		float current = (float) top;

		batch.begin();
		for (final CreditsText text : creditsText) {
			current -= text.draw(current);
		}
		batch.end();

		if (skip || current > Registry.HEIGHT) {
			r.game.setScreen(nextScreen);
		}
	}

	private class CreditsScreenInputHandler extends InputAdapter {
		@Override
		public boolean keyDown(final int keycode) {
			skip = true;
			return true;
		}

		@Override
		public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
			skip = true;
			return true;
		}
	}

	private abstract class CreditsText {
		public abstract void init();

		/** @return height consumed by this CreditsText */
		public abstract float draw(final float y);
	}

	private class MainTitleText extends CreditsText {
		private final String titleText;
		private float titleX;
		private float height = 0;

		public MainTitleText(final String titleText) {
			this.titleText = titleText;
		}

		@Override
		public void init() {
			final BitmapFont.TextBounds textBounds = mainTitleFont.getBounds(titleText);
			titleX = (Registry.WIDTH - textBounds.width) / 2;
			height = textBounds.height + 30;
		}

		@Override
		public float draw(final float y) {
			mainTitleFont.draw(batch, titleText, titleX, y);
			return height;
		}
	}

	private class SectionTitleText extends CreditsText {
		private final String titleText;
		private float titleX;
		private float height = 0;

		private static final float padding = 50;

		private SectionTitleText(final String titleText) {
			this.titleText = titleText;
		}

		@Override
		public void init() {
			final BitmapFont.TextBounds textBounds = sectionTitleFont.getBounds(titleText);
			titleX = (Registry.WIDTH - textBounds.width) / 2;
			height = textBounds.height + padding;
		}

		@Override
		public float draw(final float y) {
			sectionTitleFont.draw(batch, titleText, titleX, y - padding);
			return height;
		}
	}

	private class VerticalSpaceCreditsText extends SectionTitleText {
		private VerticalSpaceCreditsText() {
			super("");
		}
	}

	private class MessageCreditsText extends CreditsText {
		private final String messageText;
		private float messageX;
		private float height = 0;

		private static final float padding = 8;

		public MessageCreditsText(final String messageText) {
			this.messageText = messageText;
		}

		@Override
		public void init() {
			final BitmapFont.TextBounds textBounds = normalFont.getBounds(messageText);
			messageX = (Registry.WIDTH - textBounds.width) / 2;
			height = textBounds.height + padding;
		}

		@Override
		public float draw(final float y) {
			normalFont.draw(batch, messageText, messageX, y - padding);
			return height;
		}
	}

	private class MusicCreditsText extends CreditsText {
		private final String identityText;
		private final String usageText;
		private final String noteText;

		private float identityX, identityY;
		private float usageX, usageY;
		private float noteX, noteY;
		private float height = 0;

		private MusicCreditsText(final String artist, final String title, final String usageText, final String noteText) {
			this.identityText = artist + " - " + "\"" + title + "\"";
			this.usageText = usageText;
			this.noteText = noteText;
		}

		private MusicCreditsText(final String artist, final String title, final String noteText) {
			this(artist, title, null, noteText);
		}

		@Override
		public void init() {
			final BitmapFont.TextBounds identityTextBounds = boldFont.getBounds(identityText);
			identityX = (Registry.WIDTH - identityTextBounds.width) / 2;
			identityY = 26;
			height = identityY + identityTextBounds.height;

			if (usageText != null) {
				final BitmapFont.TextBounds usageTextBounds = normalFont.getBounds(usageText);
				usageX = (Registry.WIDTH - usageTextBounds.width) / 2;
				usageY = height + 8;
				height = usageY + usageTextBounds.height;
			}

			if (noteText != null) {
				final BitmapFont.TextBounds noteTextBounds = smallFont.getBounds(noteText);
				noteX = (Registry.WIDTH - noteTextBounds.width) / 2;
				noteY = height + 8;
				height = noteY + noteTextBounds.height;
			}
		}

		@Override
		public float draw(final float y) {
			boldFont.draw(batch, identityText, identityX, y - identityY);
			if (usageText != null) {
				normalFont.draw(batch, usageText, usageX, y - usageY);
			}
			if (noteText != null) {
				smallFont.draw(batch, noteText, noteX, y - noteY);
			}
			return height;
		}
	}

}
