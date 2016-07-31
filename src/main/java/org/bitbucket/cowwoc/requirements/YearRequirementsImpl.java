/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.time.Year;
import java.util.Objects;

/**
 * Default implementation of YearRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class YearRequirementsImpl extends AbstractObjectRequirements<YearRequirements, Year>
	implements YearRequirements
{
	/**
	 * Creates new YearRequirementsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	YearRequirementsImpl(Year parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public YearRequirements isIn(Range<Year> range)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(range, "range").isNotNull();
		if (range.contains(parameter))
			return self;
		return throwException(IllegalArgumentException.class,
			Ranges.getExceptionMessage(name, parameter, range));
	}

	@Override
	public YearRequirements isLessThan(Year value, String name) throws IllegalArgumentException
	{
		if (parameter.compareTo(value) < 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be less than %s.\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, value, parameter));
	}

	@Override
	public YearRequirements isLessThan(Year value) throws IllegalArgumentException
	{
		if (parameter.compareTo(value) < 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be less than %s.\n" +
				"Actual: %s", name, value, parameter));
	}

	@Override
	public YearRequirements isLessThanOrEqualTo(Year value, String name)
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
	public YearRequirements isLessThanOrEqualTo(Year value) throws IllegalArgumentException
	{
		if (parameter.compareTo(value) <= 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be less than or equal to %s.\n" +
				"Actual: %s", name, value, parameter));
	}

	@Override
	public YearRequirements isGreaterThan(Year value, String name) throws IllegalArgumentException
	{
		if (parameter.compareTo(value) > 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be greater than %s.\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, value, parameter));
	}

	@Override
	public YearRequirements isGreaterThan(Year value) throws IllegalArgumentException
	{
		if (parameter.compareTo(value) > 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be less than %s.\n" +
				"Actual: %s", name, value, parameter));
	}

	@Override
	public YearRequirements isGreaterThanOrEqualTo(Year value, String name)
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
	public YearRequirements isGreaterThanOrEqualTo(Year value) throws IllegalArgumentException
	{
		if (parameter.compareTo(value) >= 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be greater than or equal to %s.\n" +
				"Actual: %s", name, value, parameter));
	}

	@Override
	public YearRequirements usingException(Class<? extends RuntimeException> exceptionOverride)
	{
		if (Objects.equals(exceptionOverride, this.exceptionOverride))
			return this;
		return new YearRequirementsImpl(parameter, name, exceptionOverride);
	}
}
