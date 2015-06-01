/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.util.Objects;
import java.util.Optional;

/**
 * Default implementation of StringLengthPreconditions.
 * <p>
 * @author Gili Tzabari
 */
final class StringLengthPreconditionsImpl
	extends PrimitiveIntegerPreconditionsImpl<StringLengthPreconditions>
	implements StringLengthPreconditions, PrimitiveIntegerPreconditions<StringLengthPreconditions>
{
	private final String string;

	/**
	 * Creates new StringLengthPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	StringLengthPreconditionsImpl(String parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter.length(), name, exceptionOverride);
		this.string = parameter;
	}

	@Override
	public StringLengthPreconditions isGreaterThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter >= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least %d characters. It contained %d characters: \"%s\"",
				name, value, parameter, string));
	}

	@Override
	public StringLengthPreconditions isGreaterThanOrEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull();
		if (parameter >= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least %d (%s) characters. It contained " +
				"%d characters: \"%s\"", this.name, value, name, parameter, string));
	}

	@Override
	public StringLengthPreconditions isGreaterThan(Integer value) throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter > value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain more than %d characters. It contained %d characters: \"%s\"",
				name, value, parameter, string));
	}

	@Override
	public StringLengthPreconditions isGreaterThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull();
		if (parameter > value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain more than %d (%s) characters. It contained " +
				"%d characters: \"%s\"", name, value, this.name, parameter, string));
	}

	@Override
	public StringLengthPreconditions isLessThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter <= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s may contain at most %d characters. It contained %d characters: \"%s\"",
				name, value, parameter, string));
	}

	@Override
	public StringLengthPreconditions isLessThanOrEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull();
		if (parameter <= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s may contain at most %d (%s) characters. It contained " +
				"%d characters: \"%s\"", this.name, value, name, parameter, string));
	}

	@Override
	public StringLengthPreconditions isLessThan(Integer value) throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter < value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain less than %d characters. It contained %d characters: \"%s\"",
				this.name, value, parameter, string));
	}

	@Override
	public StringLengthPreconditions isLessThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull();
		if (parameter < value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain less than %d (%s) characters. It contained " +
				"%d characters: \"%s\"", this.name, value, name, parameter, string));
	}

	@Override
	public StringLengthPreconditions isNotPositive() throws IllegalArgumentException
	{
		return isZero();
	}

	@Override
	public StringLengthPreconditions isPositive() throws IllegalArgumentException
	{
		if (parameter > 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least one character. It contained %d characters: \"%s\"",
				name, parameter, string));
	}

	@Override
	public StringLengthPreconditions isNotZero() throws IllegalArgumentException
	{
		return isPositive();
	}

	@Override
	public StringLengthPreconditions isZero() throws IllegalArgumentException
	{
		if (parameter == 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.
			format("%s must be empty. It contained %d characters: \"%s\"", name, parameter, string));
	}

	@Override
	public StringLengthPreconditions isNotNegative() throws IllegalArgumentException
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public StringLengthPreconditions isNegative() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s cannot have a negative length", name));
	}

	@Override
	public StringLengthPreconditions isIn(Range<Integer> range) throws NullPointerException,
		IllegalArgumentException
	{
		Preconditions.requireThat(range, "range").isNotNull();
		if (range.contains(parameter))
			return self;
		StringBuilder message = new StringBuilder(name + " must contain ");
		Ranges.appendRange(range, message);
		message.append(String.format(" characters. It contained %d characters: \"%s\"", parameter,
			string));
		return throwException(IllegalArgumentException.class, message.toString());
	}

	@Override
	public StringLengthPreconditions isEqualTo(Integer value) throws IllegalArgumentException
	{
		if (Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %d characters. It contained %d characters: \"%s\"", name,
				value, parameter, string));
	}

	@Override
	public StringLengthPreconditions isEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		if (Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %d characters (%s). It contained %d characters: \"%s\"",
				this.name, value, name, parameter, string));
	}

	@Override
	public StringLengthPreconditions isNotEqualTo(Integer value) throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %d characters, but it did. It contained \"%s\"", name,
				value, string));
	}

	@Override
	public StringLengthPreconditions isNotEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not contain %d characters (%s), but it did. It contained \"%s\"",
				this.name, value, name, string));
	}
}
