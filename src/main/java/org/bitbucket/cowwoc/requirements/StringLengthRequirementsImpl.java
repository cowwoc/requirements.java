/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.Objects;
import java.util.Optional;

/**
 * Default implementation of StringLengthRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class StringLengthRequirementsImpl
	extends PrimitiveIntegerRequirementsImpl<StringLengthRequirements>
	implements StringLengthRequirements, PrimitiveIntegerRequirements<StringLengthRequirements>
{
	private final String string;

	/**
	 * Creates new StringLengthRequirementsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	StringLengthRequirementsImpl(String parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter.length(), name, exceptionOverride);
		this.string = parameter;
	}

	@Override
	public StringLengthRequirements isGreaterThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter >= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least %,d characters. It contained %,d characters.\n" +
				"Actual: \"%s\"", name, value, parameter, string));
	}

	@Override
	public StringLengthRequirements isGreaterThanOrEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter >= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) characters. It contained %,d characters.\n" +
				"Actual: \"%s\"", this.name, name, value, parameter, string));
	}

	@Override
	public StringLengthRequirements isGreaterThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter > value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain more than %,d characters. It contained %,d characters.\n" +
				"Actual: \"%s\"", name, value, parameter, string));
	}

	@Override
	public StringLengthRequirements isGreaterThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter > value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) characters. It contained " +
				"%,d characters: \"%s\"", this.name, name, value, parameter, string));
	}

	@Override
	public StringLengthRequirements isLessThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter <= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s may contain at most %,d characters. It contained %,d characters.\n" +
				"Actual: \"%s\"", name, value, parameter, string));
	}

	@Override
	public StringLengthRequirements isLessThanOrEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter <= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s may contain at most %s (%,d) characters. It contained " +
				"%,d characters: \"%s\"", this.name, name, value, parameter, string));
	}

	@Override
	public StringLengthRequirements isLessThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter < value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain less than %,d characters. It contained %,d characters.\n" +
				"Actual: \"%s\"", this.name, value, parameter, string));
	}

	@Override
	public StringLengthRequirements isLessThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter < value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format(
				"%s must contain less than %s (%,d) characters. It contained %,d characters.\n" +
				"Actual: \"%s\"", this.name, name, value, parameter, string));
	}

	@Override
	public StringLengthRequirements isNotPositive() throws IllegalArgumentException
	{
		return isZero();
	}

	@Override
	public StringLengthRequirements isPositive() throws IllegalArgumentException
	{
		if (parameter > 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least one character. It contained %,d characters.\n" +
				"Actual: \"%s\"", name, parameter, string));
	}

	@Override
	public StringLengthRequirements isNotZero() throws IllegalArgumentException
	{
		return isPositive();
	}

	@Override
	public StringLengthRequirements isZero() throws IllegalArgumentException
	{
		if (parameter == 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be empty. It contained %,d characters.\n" +
				"Actual: \"%s\"", name, parameter, string));
	}

	@Override
	public StringLengthRequirements isNotNegative() throws IllegalArgumentException
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public StringLengthRequirements isNegative() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s cannot have a negative length", name));
	}

	@Override
	public StringLengthRequirements isIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(range, "range").isNotNull();
		if (range.contains(parameter))
			return self;
		StringBuilder message = new StringBuilder(name + " must contain " + range);
		message.append(String.format(" characters. It contained %,d characters.\n" +
			"Actual: \"%s\"", parameter, string));
		return throwException(IllegalArgumentException.class, message.toString());
	}

	@Override
	public StringLengthRequirements isEqualTo(Integer value) throws IllegalArgumentException
	{
		if (Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %,d characters. It contained %,d characters.\n" +
				"Actual: \"%s\"", name, value, parameter, string));
	}

	@Override
	public StringLengthRequirements isEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		if (Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) characters. It contained %,d characters.\n" +
				"Actual: \"%s\"", this.name, name, value, parameter, string));
	}

	@Override
	public StringLengthRequirements isNotEqualTo(Integer value) throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not contain %,d characters, but did.\n" +
				"Actual: \"%s\"", name, value, string));
	}

	@Override
	public StringLengthRequirements isNotEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not contain %s (%,d) characters, but did.\n" +
				"Actual: \"%s\"", this.name, name, value, string));
	}
}
