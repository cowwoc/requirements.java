package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

/**
 * Verifies preconditions of a Number parameter.
 * <p/>
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public abstract class NumberPreconditions<T extends Number & Comparable<T>> extends Preconditions<T>
{
	/**
	 * Creates new NumberPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @throws NullPointerException if name is null
	 */
	NumberPreconditions(String name, T parameter)
	{
		super(name, parameter);
	}

	/**
	 * Ensures that the parameter is within range.
	 * <p/>
	 * @param range  the range
	 * @param domain the number domain
	 * @return this
	 * @throws NullPointerException     if domain is null
	 * @throws IllegalArgumentException if the parameter is not in range
	 */
	protected NumberPreconditions<T> isIn(Range<T> range, DiscreteDomain<T> domain)
		throws IllegalArgumentException
	{
		boolean inRange = range.contains(parameter);
		if (inRange)
			return this;
		Range<T> canonical = range.canonical(domain);
		throw new IllegalArgumentException(name + " must be in the range[" + canonical.lowerEndpoint() +
			", " + canonical.upperEndpoint() + "): " + parameter);
	}

	/**
	 * Ensures that the parameter is not negative.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is negative
	 */
	public abstract NumberPreconditions<T> isNotNegative();

	/**
	 * Ensures that the parameter is positive.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not positive
	 */
	public abstract NumberPreconditions<T> isPositive();
}
