#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

import com.badlogic.gdx.audio.Music;
import org.hamcrest.Description;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MusicSystemTest {

	public static final float arbitraryLongTime = 10;

	@Spy
	public MusicSystem system;

	@Mock
	public Music music1;
	public boolean music1play = false;
	public ArgumentCaptor<Float> volume1 = ArgumentCaptor.forClass(Float.class);

	@Mock
	public Music music2;
	public ArgumentCaptor<Float> volume2 = ArgumentCaptor.forClass(Float.class);

	@Mock
	public Music music3;
	public ArgumentCaptor<Float> volume3 = ArgumentCaptor.forClass(Float.class);

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void playPlaysMusic() {
		system.play(music1);
		system.update(0);
		verify(music1).play();
	}

	@Test
	public void queuePlaysMusicWhenNothingPlaying() {
		system.queue(music1);
		system.update(0);
		verify(music1).play();
	}

	@Test
	public void playThenQueuePlaysFirstMusic() {
		system.play(music1);
		system.update(0);
		verify(music1, times(1)).play();

		when(music1.isPlaying()).thenReturn(true);
		system.queue(music2);
		system.update(0);
		verify(music1, times(1)).play();
		verify(music2, times(0)).play();
	}

	@Test
	public void queuedMusicPlaysWhenCurrentMusicStops() {
		system.play(music1);
		system.update(0);
		verify(music1, times(1)).play();

		when(music1.isPlaying()).thenReturn(false);
		system.queue(music2);
		system.update(0);
		verify(music1, times(1)).play();
		verify(music2, times(1)).play();
	}

	@Test
	public void playClearsQueue() {
		system.queue(music1);
		system.update(0);
		verify(music1, times(1)).play();

		when(music1.isPlaying()).thenReturn(true);
		system.queue(music2);
		system.update(0);
		verify(music1, times(1)).play();
		verify(music2, times(0)).play();

		when(music1.isPlaying()).thenReturn(true);
		when(music2.isPlaying()).thenReturn(false);
		system.play(music3);
		system.update(0);

		verify(music1, times(1)).play();
		verify(music2, times(0)).play();
		verify(music3, times(1)).play();
		assertThat(system.getQueueSize(), is(0));
	}

	@Test
	public void playSetsVolumeHighWhenNothingPlaying() {
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(final InvocationOnMock invocation) throws Throwable {
				music1play = true;
				return null;
			}
		}).when(music1).play();
		doAnswer(new Answer<Boolean>() {
			@Override
			public Boolean answer(final InvocationOnMock invocation) throws Throwable {
				return music1play;
			}
		}).when(music1).isPlaying();

		system.play(music1);
		system.update(0);
		verify(music1, atLeastOnce()).setVolume(volume1.capture());
		assertThat(volume1.getValue(), new IsCloseTo(1));
	}

	@Test
	public void queueSetsVolumeHighWhenNothingPlaying() {
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(final InvocationOnMock invocation) throws Throwable {
				music1play = true;
				return null;
			}
		}).when(music1).play();
		doAnswer(new Answer<Boolean>() {
			@Override
			public Boolean answer(final InvocationOnMock invocation) throws Throwable {
				return music1play;
			}
		}).when(music1).isPlaying();

		system.queue(music1);
		system.update(0);
		verify(music1, atLeastOnce()).setVolume(volume1.capture());
		assertThat(volume1.getValue(), new IsCloseTo(1));
	}

	@Test
	public void playCrossFadesNewMusic() {
		system.play(music1);
		system.update(arbitraryLongTime);

		when(music1.isPlaying()).thenReturn(true);
		system.play(music2);
		system.update(0);
		verify(music1, atLeastOnce()).setVolume(volume1.capture());
		verify(music2, atLeastOnce()).setVolume(volume2.capture());
		assertThat(volume1.getValue(), new IsCloseTo(1));
		assertThat(volume2.getValue(), new IsCloseTo(0));

		when(music1.isPlaying()).thenReturn(true);
		when(music2.isPlaying()).thenReturn(true);
		system.update(MusicSystem.CROSS_FADE_SECONDS / 2);
		verify(music1, atLeastOnce()).setVolume(volume1.capture());
		verify(music2, atLeastOnce()).setVolume(volume2.capture());
		assertThat(volume1.getValue(), new IsGreaterThan(0.1f));
		assertThat(volume2.getValue(), new IsGreaterThan(0.1f));

		when(music1.isPlaying()).thenReturn(true);
		when(music2.isPlaying()).thenReturn(true);
		system.update(MusicSystem.CROSS_FADE_SECONDS / 2);
		verify(music1, atLeastOnce()).setVolume(volume1.capture());
		verify(music2, atLeastOnce()).setVolume(volume2.capture());
		assertThat(volume1.getValue(), new IsCloseTo(0));
		assertThat(volume2.getValue(), new IsCloseTo(1));
	}

	@Test
	public void playWhenJustStartedFadingPseudoCrossFades() {
		system.play(music1);
		when(music1.isPlaying()).thenReturn(true);
		system.update(arbitraryLongTime);

		system.play(music2);
		when(music2.isPlaying()).thenReturn(true);
		system.update(MusicSystem.CROSS_FADE_SECONDS * 1f / 8f);

		system.play(music3);
		when(music3.isPlaying()).thenReturn(true);
		verify(music1, times(1)).play();
		verify(music2, times(1)).stop();
		verify(music3, times(1)).play();
		verify(music1, atLeastOnce()).setVolume(volume1.capture());
		verify(music3, atLeastOnce()).setVolume(volume3.capture());
		assertThat(volume1.getValue(), new IsGreaterThan(0.5f));
		assertThat(volume3.getValue(), new IsCloseTo(0));
	}

	@Test
	public void playWhenAlmostFinishedFadingPseudoCrossFades() {
		system.play(music1);
		when(music1.isPlaying()).thenReturn(true);
		system.update(arbitraryLongTime);

		system.play(music2);
		when(music2.isPlaying()).thenReturn(true);
		system.update(MusicSystem.CROSS_FADE_SECONDS * 7f / 8f);

		system.play(music3);
		when(music3.isPlaying()).thenReturn(true);
		verify(music1, times(1)).stop();
		verify(music2, times(1)).play();
		verify(music3, times(1)).play();
		verify(music2, atLeastOnce()).setVolume(volume2.capture());
		verify(music3, atLeastOnce()).setVolume(volume3.capture());
		assertThat(volume2.getValue(), new IsGreaterThan(0.5f));
		assertThat(volume3.getValue(), new IsCloseTo(0));
	}

	private static class IsCloseTo extends ArgumentMatcher<Float> {
		private static final float epsilon = 0.001f;
		private final float target;

		public IsCloseTo(final float target) {
			this.target = target;
		}

		@Override
		public boolean matches(final Object argument) {
			return Math.abs(target - (Float) argument) < epsilon;
		}

		@Override
		public void describeTo(final Description description) {
			super.describeTo(description);
			description.appendText(" " + target);
		}
	}

	private static class IsGreaterThan extends ArgumentMatcher<Float> {
		private final float target;

		public IsGreaterThan(final float target) {
			this.target = target;
		}

		@Override
		public boolean matches(final Object argument) {
			return (Float) argument > target;
		}

		@Override
		public void describeTo(final Description description) {
			super.describeTo(description);
			description.appendText(" " + target);
		}
	}

}
