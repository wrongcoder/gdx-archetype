#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;

public class MusicSystem {

	public static final float CROSS_FADE_SECONDS = 2.5f;

	private Music previousMusic = null;
	private float previousFadeOutSeconds = 0;
	private Music currentMusic = null;
	private float currentFadeInSeconds = 0;

	private final Array<Music> queuedMusic = new Array<Music>(true, 1);

	/** Start playing this Music immediately */
	public void play(final Music music) {
		queuedMusic.clear();
		switchMusic(music);
	}

	/** Queue this Music to be played after currently playing/queued Music ends */
	public void queue(final Music nextMusic) {
		queuedMusic.add(nextMusic);
	}

	/** Stop playing all Music */
	public void stop() {
		if (isPlaying(currentMusic)) {
			currentMusic.stop();
		}
		if (isPlaying(previousMusic)) {
			previousMusic.stop();
		}
		if (queuedMusic.size > 0) {
			queuedMusic.clear();
		}
	}

	/** Inform MusicSystem that time has passed. Call this during world update. */
	public void update(final float delta) {
		if (!isPlaying(currentMusic) && queuedMusic.size > 0) {
			final Music nextMusic = queuedMusic.pop();
			switchMusic(nextMusic);
		}

		if (previousFadeOutSeconds > 0 && isPlaying(previousMusic)) {
			previousFadeOutSeconds -= delta;
			if (previousFadeOutSeconds < 0) {
				previousMusic.stop();
			}
			else {
				previousMusic.setVolume(previousFadeOutSeconds / CROSS_FADE_SECONDS);
			}
		}
		if (currentFadeInSeconds < CROSS_FADE_SECONDS && isPlaying(currentMusic)) {
			currentFadeInSeconds += delta;
			if (currentFadeInSeconds > CROSS_FADE_SECONDS) {
				currentMusic.setVolume(1);
			}
			else {
				currentMusic.setVolume(currentFadeInSeconds / CROSS_FADE_SECONDS);
			}
		}
	}

	public int getQueueSize() {
		return queuedMusic.size;
	}

	public boolean isPlaying() {
		return isPlaying(currentMusic);
	}

	private void switchMusic(final Music nextMusic) {
		if (isPlaying(previousMusic) && isPlaying(currentMusic)) { /* both playing */
			// Two previous tracks playing: fade out whichever is louder, drop the other
			if (previousFadeOutSeconds > currentFadeInSeconds) {
				currentMusic.stop();
				currentMusic = previousMusic;
			}
			else {
				previousMusic.stop();
				previousFadeOutSeconds = currentFadeInSeconds;
			}
			nextMusic.setVolume(0);
			currentFadeInSeconds = 0;
		}
		else if (isPlaying(currentMusic)) { /* previous is not playing */
			previousFadeOutSeconds = CROSS_FADE_SECONDS;
			nextMusic.setVolume(0);
			currentFadeInSeconds = 0;
		}
		else { /* neither are playing */
			nextMusic.setVolume(1);
			currentFadeInSeconds = CROSS_FADE_SECONDS;
		}

		previousMusic = currentMusic;
		currentMusic = nextMusic;
		currentMusic.play();
	}

	private static boolean isPlaying(final Music music) {
		return music != null && music.isPlaying();
	}

}
