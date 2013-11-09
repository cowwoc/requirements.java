package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

/**
 * Verifies preconditions of a Integer parameter.
 * <p/>
 * @author Gili Tzabari
 */
public final class IntegerPreconditions extends NumberPreconditions<Integer>
{
	/**
	 * Creates new IntgerPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @throws NullPointerException if name is null
	 */
	IntegerPreconditions(String name, Integer parameter)
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
	public IntegerPreconditions isIn(Range<Integer> range) throws IllegalArgumentException
	{
		return (IntegerPreconditions) isIn(range, DiscreteDomain.integers());
	}

	@Override
	public IntegerPreconditions isNotNegative()
	{
		if (parameter.compareTo(0) < 0)
			throw new IllegalArgumentException(name + " may not be negative");
		return this;
	}

	@Override
	public IntegerPreconditions isPositive()
	{
		if (parameter.compareTo(0) <= 0)
			throw new IllegalArgumentException(name + " may not be negative");
		return this;
	}
}
