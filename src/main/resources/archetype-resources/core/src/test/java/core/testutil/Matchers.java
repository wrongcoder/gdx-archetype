#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.testutil;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.mockito.ArgumentMatcher;

public class Matchers {

	public static Matcher isCloseTo(final float target) {
		return new FloatIsCloseTo(target);
	}

	public static Matcher isCloseTo(final float target, final float epsilon) {
		return new FloatIsCloseTo(target, epsilon);
	}

	public static Matcher isGreaterThan(final float target) {
		return new FloatIsGreaterThan(target);
	}

	public static class FloatIsCloseTo extends ArgumentMatcher<Float> {
		private final float epsilon;
		private final float target;

		public FloatIsCloseTo(final float target) {
			this.target = target;
			this.epsilon = 0.001f;
		}

		public FloatIsCloseTo(final float target, final float epsilon) {
			this.target = target;
			this.epsilon = epsilon;
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
