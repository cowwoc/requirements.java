/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.math.BigDecimal;

/**
 * Verifies preconditions of a {@link Number} parameter.
 * <p>
 * @param <S> the type of preconditions that was instantiated
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public class NumberPreconditions<S extends NumberPreconditions<S, T>, T extends Number & Comparable<? super T>>
	extends Preconditions<S, T>
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
	 * <p>
	 * @param range the range
	 * @return this
	 * @throws NullPointerException     if range is null
	 * @throws IllegalArgumentException if the parameter is not in range
	 */
	public S isIn(Range<T> range)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(range, "range").isNotNull();
		boolean inRange = range.contains(parameter);
		if (inRange)
			return self;
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
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not negative
	 */
	public S isNegative() throws IllegalArgumentException
	{
		if (parameter.longValue() >= 0L)
			throw new IllegalArgumentException(name + " must be negative");
		return self;
	}

	/**
	 * Ensures that the parameter is not negative.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is negative
	 */
	public S isNotNegative() throws IllegalArgumentException
	{
		if (parameter.longValue() < 0L)
			throw new IllegalArgumentException(name + " may not be negative");
		return self;
	}

	/**
	 * Ensures that the parameter is zero.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not zero
	 */
	public S isZero() throws IllegalArgumentException
	{
		if (parameter instanceof BigDecimal)
		{
			// Number.longValue() truncates the fractional portion, which we need to take into account
			BigDecimal decimal = (BigDecimal) parameter;
			if (decimal.signum() != 0)
				throw new IllegalArgumentException(name + " must be zero");
			return self;
		}
		if (parameter.longValue() != 0L)
			throw new IllegalArgumentException(name + " must be zero");
		return self;
	}

	/**
	 * Ensures that the parameter is not zero.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is zero
	 */
	public S isNotZero() throws IllegalArgumentException
	{
		if (parameter instanceof BigDecimal)
		{
			// Number.longValue() truncates the fractional portion, which we need to take into account
			BigDecimal decimal = (BigDecimal) parameter;
			if (decimal.signum() == 0)
				throw new IllegalArgumentException(name + " may not be zero");
			return self;
		}
		if (parameter.longValue() == 0L)
			throw new IllegalArgumentException(name + " may not be zero");
		return self;
	}

	/**
	 * Ensures that the parameter is positive.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not positive
	 */
	public S isPositive() throws IllegalArgumentException
	{
		if (parameter.longValue() <= 0L)
			throw new IllegalArgumentException(name + " must be positive");
		return self;
	}

	/**
	 * Ensures that the parameter is not positive.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is positive
	 */
	public S isNotPositive() throws IllegalArgumentException
	{
		if (parameter.longValue() > 0L)
			throw new IllegalArgumentException(name + " may not be positive");
		return self;
	}

	/**
	 * Ensures that the parameter is less than the value of a variable.
	 * <p>
	 * @param value the value the parameter must be less than
	 * @param name  the name of the variable
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is greater than or equal to
	 *                                  {@code value}
	 */
	public S isLessThan(T value, String name) throws IllegalArgumentException
	{
		if (parameter.compareTo(value) >= 0)
		{
			throw new IllegalArgumentException(this.name + " (" + parameter + ") must be less than " +
				name + " (" + value + ")");
		}
		return self;
	}

	/**
	 * Ensures that the parameter is less than a constant.
	 * <p>
	 * @param value the value the parameter must be less than
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is greater than or equal to
	 *                                  {@code value}
	 */
	public S isLessThan(T value) throws IllegalArgumentException
	{
		if (parameter.compareTo(value) >= 0)
		{
			throw new IllegalArgumentException(this.name + " (" + parameter + ") must be less than " +
				value);
		}
		return self;
	}

	/**
	 * Ensures that the parameter is less than or equal to a variable.
	 * <p>
	 * @param value the value the parameter must be less than or equal to
	 * @param name  the name of the variable
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is greater than {@code value}
	 */
	public S isLessThanOrEqualTo(T value, String name)
		throws IllegalArgumentException
	{
		if (parameter.compareTo(value) > 0)
		{
			throw new IllegalArgumentException(this.name + " (" + parameter + ") must be less than " +
				"or equal to " + name + " (" + value + ")");
		}
		return self;
	}

	/**
	 * Ensures that the parameter is less than or equal to a constant.
	 * <p>
	 * @param value the value the parameter must be less than or equal to
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is greater than {@code value}
	 */
	public S isLessThanOrEqualTo(T value)
		throws IllegalArgumentException
	{
		if (parameter.compareTo(value) > 0)
		{
			throw new IllegalArgumentException(this.name + " (" + parameter + ") must be less than " +
				"or equal to " + value);
		}
		return self;
	}

	/**
	 * Ensures that the parameter is greater than a variable.
	 * <p>
	 * @param value the value the parameter must be greater than
	 * @param name  the name of the variable
	 * @return this
	 * @throws IllegalArgumentException if the parameter is less than or equal to {@code value}
	 */
	public S isGreaterThan(T value, String name)
		throws IllegalArgumentException
	{
		if (parameter.compareTo(value) <= 0)
		{
			throw new IllegalArgumentException(this.name + " (" + parameter + ") must be greater than " +
				name + " (" + value + ")");
		}
		return self;
	}

	/**
	 * Ensures that the parameter is greater than a constant.
	 * <p>
	 * @param value the value the parameter must be greater than
	 * @return this
	 * @throws IllegalArgumentException if the parameter is less than or equal to {@code value}
	 */
	public S isGreaterThan(T value)
		throws IllegalArgumentException
	{
		if (parameter.compareTo(value) <= 0)
		{
			throw new IllegalArgumentException(this.name + " (" + parameter + ") must be greater than " +
				value);
		}
		return self;
	}

	/**
	 * Ensures that the parameter is greater than or bigger than a variable.
	 * <p>
	 * @param value the value the parameter must be greater than or equal to
	 * @param name  the name of the variable
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is less than to {@code value}
	 */
	public S isGreaterThanOrEqualTo(T value, String name)
		throws IllegalArgumentException
	{
		if (parameter.compareTo(value) < 0)
		{
			throw new IllegalArgumentException(this.name + " (" + parameter + ") must be greater than " +
				"or equal to " + name + " (" + value + ")");
		}
		return self;
	}

	/**
	 * Ensures that the parameter is greater than or bigger than a constant.
	 * <p>
	 * @param value the value the parameter must be greater than or equal to
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is less than to {@code value}
	 */
	public S isGreaterThanOrEqualTo(T value)
		throws IllegalArgumentException
	{
		if (parameter.compareTo(value) < 0)
		{
			throw new IllegalArgumentException(this.name + " (" + parameter + ") must be greater than " +
				"or equal to " + value);
		}
		return self;
	}
}
