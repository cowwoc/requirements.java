package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

/**
 * Verifies preconditions of a Long parameter.
 * <p/>
 * @author Gili Tzabari
 */
public final class LongPreconditions extends NumberPreconditions<Long>
{
	/**
	 * Creates new LongPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @throws NullPointerException if name is null
	 */
	LongPreconditions(String name, Long parameter)
	{
		super(name, parameter);
	}

	/**
	 * Ensures that the parameter is within range.
	 * <p/>
	 * @param range the range
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not in range
	 */
	public LongPreconditions isIn(Range<Long> range) throws IllegalArgumentException
	{
		return (LongPreconditions) isIn(range, DiscreteDomain.longs());
	}

	@Override
	public LongPreconditions isNotNegative()
	{
		if (parameter.compareTo(0L) < 0)
			throw new IllegalArgumentException(name + " may not be negative");
		return this;
	}

	@Override
	public LongPreconditions isPositive()
	{
		if (parameter.compareTo(0L) <= 0)
			throw new IllegalArgumentException(name + " may not be negative");
		return this;
	}
}
