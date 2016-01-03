/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.time.Year;
import java.util.Optional;

/**
 * Default implementation of YearPreconditions.
 * <p>
 * @author Gili Tzabari
 */
final class YearPreconditionsImpl extends AbstractObjectPreconditions<YearPreconditions, Year>
	implements YearPreconditions
{
	/**
	 * Creates new YearPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} or {@code exceptionOverride} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	YearPreconditionsImpl(Year parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public YearPreconditions isIn(Range<Year> range)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(range, "range").isNotNull();
		if (range.contains(parameter))
			return self;
		return throwException(IllegalArgumentException.class,
			Ranges.getExceptionMessage(name, parameter, range));
	}

	@Override
	public YearPreconditions isLessThan(Year value, String name) throws IllegalArgumentException
	{
		if (parameter.compareTo(value) < 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be less than %s.\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, value, parameter));
	}

	@Override
	public YearPreconditions isLessThan(Year value) throws IllegalArgumentException
	{
		if (parameter.compareTo(value) < 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be less than %s.\n" +
				"Actual: %s", name, value, parameter));
	}

	@Override
	public YearPreconditions isLessThanOrEqualTo(Year value, String name)
		throws IllegalArgumentException
	{
		if (parameter.compareTo(value) <= 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be less than or equal to %s.\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, value, parameter));
	}

	@Override
	public YearPreconditions isLessThanOrEqualTo(Year value) throws IllegalArgumentException
	{
		if (parameter.compareTo(value) <= 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be less than or equal to %s.\n" +
				"Actual: %s", name, value, parameter));
	}

	@Override
	public YearPreconditions isGreaterThan(Year value, String name) throws IllegalArgumentException
	{
		if (parameter.compareTo(value) > 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be greater than %s.\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, value, parameter));
	}

	@Override
	public YearPreconditions isGreaterThan(Year value) throws IllegalArgumentException
	{
		if (parameter.compareTo(value) > 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be less than %s.\n" +
				"Actual: %s", name, value, parameter));
	}

	@Override
	public YearPreconditions isGreaterThanOrEqualTo(Year value, String name)
		throws IllegalArgumentException
	{
		if (parameter.compareTo(value) >= 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be greater than or equal to %s.\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, value, parameter));
	}

	@Override
	public YearPreconditions isGreaterThanOrEqualTo(Year value) throws IllegalArgumentException
	{
		if (parameter.compareTo(value) >= 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be greater than or equal to %s.\n" +
				"Actual: %s", name, value, parameter));
	}

	@Override
	protected YearPreconditions valueOf(Year parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
	{
		if (exceptionOverride.equals(this.exceptionOverride))
			return this;
		return new YearPreconditionsImpl(parameter, name, exceptionOverride);
	}
}
