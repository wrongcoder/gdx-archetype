#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CounterTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void countUp() {
		final Counter counter = new Counter(3);
		assertThat(counter.next(), is(3));
		assertThat(counter.next(), is(4));
	}

	@Test
	public void countDown() {
		final Counter counter = new Counter(3, -1);
		assertThat(counter.next(), is(3));
		assertThat(counter.next(), is(2));
	}

	@Test
	public void countBySeven() {
		final Counter counter = new Counter(2, 7);
		assertThat(counter.next(), is(2));
		assertThat(counter.next(), is(9));
	}

	@Test
	public void stopCountingAfterTwo() {
		final Counter counter = new Counter(1, 5, 2);
		assertThat(counter.hasNext(), is(true));
		counter.next();
		assertThat(counter.hasNext(), is(true));
		counter.next();
		assertThat(counter.hasNext(), is(false));
		thrown.expect(NoSuchElementException.class);
		counter.next();
	}

	@Test
	public void stopCountingEmpty() {
		final Counter counter = new Counter(1, 2, 0);
		assertThat(counter.hasNext(), is(false));
		thrown.expect(NoSuchElementException.class);
		counter.next();
	}

}
