/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.time.Year;

/**
 * Verifies preconditions of a {@link Year} parameter.
 * <p>
 * @author Gili Tzabari
 */
public class YearPreconditions extends Preconditions<YearPreconditions, Year>
{
	/**
	 * Creates new YearPreconditions.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	YearPreconditions(Year parameter, String name)
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
	public YearPreconditions isIn(Range<Year> range)
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
	 * Ensures that the parameter is less than the value of a variable.
	 * <p>
	 * @param value the value the parameter must be less than
	 * @param name  the name of the variable
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is greater than or equal to
	 *                                  {@code value}
	 */
	public YearPreconditions isLessThan(Year value, String name) throws IllegalArgumentException
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
	public YearPreconditions isLessThan(Year value) throws IllegalArgumentException
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
	public YearPreconditions isLessThanOrEqualTo(Year value, String name)
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
	public YearPreconditions isLessThanOrEqualTo(Year value)
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
	public YearPreconditions isGreaterThan(Year value, String name)
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
	public YearPreconditions isGreaterThan(Year value)
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
	public YearPreconditions isGreaterThanOrEqualTo(Year value, String name)
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
	public YearPreconditions isGreaterThanOrEqualTo(Year value)
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
