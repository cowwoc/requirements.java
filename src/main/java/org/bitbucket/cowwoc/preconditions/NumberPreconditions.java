/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.math.BigDecimal;

/**
 * Verifies preconditions of a Number parameter.
 * <p/>
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public final class NumberPreconditions<T extends Number & Comparable<? super T>>
	extends Preconditions<NumberPreconditions<T>, T>
{
	/**
	 * Creates new NumberPreconditions.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	NumberPreconditions(T parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name);
	}

	/**
	 * Ensures that the parameter is within range.
	 * <p/>
	 * @param range the range
	 * @return this
	 * @throws NullPointerException     if range is null
	 * @throws IllegalArgumentException if the parameter is not in range
	 */
	public NumberPreconditions<T> isIn(Range<T> range)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(range, "range").isNotNull();
		boolean inRange = range.contains(parameter);
		if (inRange)
			return this;
		StringBuilder message = new StringBuilder(name + " (" + parameter + ") must be in the range ");
		switch (range.lowerBoundType())
		{
			case OPEN:
				message.append("(");
				break;
			case CLOSED:
				message.append("[");
				break;
			default:
				throw new AssertionError(range.lowerBoundType().name());
		}
		message.append(range.lowerEndpoint()).append(", ").append(range.upperEndpoint());
		switch (range.lowerBoundType())
		{
			case OPEN:
				message.append(")");
				break;
			case CLOSED:
				message.append("]");
				break;
			default:
				throw new AssertionError(range.lowerBoundType().name());
		}
		throw new IllegalArgumentException(message.toString());
	}

	/**
	 * Ensures that the parameter is negative.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not negative
	 */
	public NumberPreconditions<T> isNegative() throws IllegalArgumentException
	{
		if (parameter.longValue() >= 0L)
			throw new IllegalArgumentException(name + " must be negative");
		return this;
	}

	/**
	 * Ensures that the parameter is not negative.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is negative
	 */
	public NumberPreconditions<T> isNotNegative() throws IllegalArgumentException
	{
		if (parameter.longValue() < 0L)
			throw new IllegalArgumentException(name + " may not be negative");
		return this;
	}

	/**
	 * Ensures that the parameter is zero.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not zero
	 */
	public NumberPreconditions<T> isZero() throws IllegalArgumentException
	{
		if (parameter instanceof BigDecimal)
		{
			// Number.longValue() truncates the fractional portion, which we need to take into account
			BigDecimal decimal = (BigDecimal) parameter;
			if (decimal.signum() != 0)
				throw new IllegalArgumentException(name + " must be zero");
			return this;
		}
		if (parameter.longValue() != 0L)
			throw new IllegalArgumentException(name + " must be zero");
		return this;
	}

	/**
	 * Ensures that the parameter is not zero.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is zero
	 */
	public NumberPreconditions<T> isNotZero() throws IllegalArgumentException
	{
		if (parameter instanceof BigDecimal)
		{
			// Number.longValue() truncates the fractional portion, which we need to take into account
			BigDecimal decimal = (BigDecimal) parameter;
			if (decimal.signum() == 0)
				throw new IllegalArgumentException(name + " may not be zero");
			return this;
		}
		if (parameter.longValue() == 0L)
			throw new IllegalArgumentException(name + " may not be zero");
		return this;
	}

	/**
	 * Ensures that the parameter is positive.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not positive
	 */
	public NumberPreconditions<T> isPositive() throws IllegalArgumentException
	{
		if (parameter.longValue() <= 0L)
			throw new IllegalArgumentException(name + " must be positive");
		return this;
	}

	/**
	 * Ensures that the parameter is not positive.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is positive
	 */
	public NumberPreconditions<T> isNotPositive() throws IllegalArgumentException
	{
		if (parameter.longValue() > 0L)
			throw new IllegalArgumentException(name + " may not be positive");
		return this;
	}

	/**
	 * Ensures that the parameter is less than a value.
	 * <p>
	 * @param value the value the parameter must be less than
	 * @param name  the name of the value
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is greater than or equal to
	 *                                  {@code value}
	 */
	public NumberPreconditions<T> isLessThan(long value, String name) throws IllegalArgumentException
	{
		if (parameter.longValue() >= value)
		{
			throw new IllegalArgumentException(this.name + " (" + parameter + ") must be less than " +
				name + " (" + value + ")");
		}
		return this;
	}

	/**
	 * Ensures that the parameter is less than or equal to value.
	 * <p>
	 * @param value the value the parameter must be less than or equal to
	 * @param name  the name of the value
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is greater than {@code value}
	 */
	public NumberPreconditions<T> isLessThanOrEqualTo(long value, String name) throws
		IllegalArgumentException
	{
		if (parameter.longValue() < value)
		{
			throw new IllegalArgumentException(this.name + " (" + parameter + ") must be less than " +
				"or equal to " + name + " (" + value + ")");
		}
		return this;
	}

	/**
	 * Ensures that the parameter is greater than a value.
	 * <p>
	 * @param value the value the parameter must be greater than
	 * @param name  the name of the value
	 * @return this
	 * @throws IllegalArgumentException if the parameter is less than or equal to value
	 */
	public NumberPreconditions<T> isGreaterThan(long value, String name)
		throws IllegalArgumentException
	{
		if (parameter.longValue() <= value)
		{
			throw new IllegalArgumentException(this.name + " (" + parameter + ") must be greater than " +
				name + " (" + value + ")");
		}
		return this;
	}

	/**
	 * Ensures that the parameter is greater than or bigger than a value.
	 * <p>
	 * @param value the value the parameter must be greater than or equal to
	 * @param name  the name of the value
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is less than to {@code value}
	 */
	public NumberPreconditions<T> isGreaterThanOrEqualTo(long value, String name) throws
		IllegalArgumentException
	{
		if (parameter.longValue() < value)
		{
			throw new IllegalArgumentException(this.name + " (" + parameter + ") must be greater than " +
				"or equal to " + name + " (" + value + ")");
		}
		return this;
	}
}
