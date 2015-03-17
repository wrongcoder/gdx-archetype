#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.testutils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.mockito.ArgumentMatcher;

public class Matchers {

	public static Matcher isCloseTo(final float target) {
		return new FloatIsCloseTo(target);
	}

	public static Matcher isGreaterThan(final float target) {
		return new FloatIsGreaterThan(target);
	}

	public static class FloatIsCloseTo extends ArgumentMatcher<Float> {
		private static final float epsilon = 0.001f;
		private final float target;

		public FloatIsCloseTo(final float target) {
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

	public static class FloatIsGreaterThan extends ArgumentMatcher<Float> {
		private final float target;

		public FloatIsGreaterThan(final float target) {
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
