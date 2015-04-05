#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Counter implements Iterator<Integer> {

	private final int start;
	private final int skip;
	private final Integer count;

	private int index = 0;

	public Counter(final int start) {
		this.start = start;
		this.skip = 1;
		this.count = null;
	}

	public Counter(final int start, final int skip) {
		this.start = start;
		this.skip = skip;
		this.count = null;
	}

	public Counter(final int start, final int skip, final int count) {
		this.start = start;
		this.skip = skip;
		this.count = count;
	}

	@Override
	public boolean hasNext() {
		return count == null || index < count;
	}

	@Override
	public Integer next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		return start + (index++ * skip);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
