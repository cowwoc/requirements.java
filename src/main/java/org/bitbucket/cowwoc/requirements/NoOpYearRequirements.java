/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.time.Year;
import java.util.function.Consumer;

/**
 * An implementation of YearRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpYearRequirements implements YearRequirements
{
	INSTANCE;

	@Override
	public YearRequirements isGreaterThan(Year value, String name)
	{
		return this;
	}

	@Override
	public YearRequirements isGreaterThan(Year value)
	{
		return this;
	}

	@Override
	public YearRequirements isGreaterThanOrEqualTo(Year value, String name)
	{
		return this;
	}

	@Override
	public YearRequirements isGreaterThanOrEqualTo(Year value)
	{
		return this;
	}

	@Override
	public YearRequirements isIn(Range<Year> range)
	{
		return this;
	}

	@Override
	public YearRequirements isLessThan(Year value, String name)
	{
		return this;
	}

	@Override
	public YearRequirements isLessThan(Year value)
	{
		return this;
	}

	@Override
	public YearRequirements isLessThanOrEqualTo(Year value, String name)
	{
		return this;
	}

	@Override
	public YearRequirements isLessThanOrEqualTo(Year value)
	{
		return this;
	}

	@Override
	public NoOpYearRequirements usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public YearRequirements isEqualTo(Year value)
	{
		return this;
	}

	@Override
	public YearRequirements isEqualTo(Year value, String name)
	{
		return this;
	}

	@Override
	public YearRequirements isNotEqualTo(Year value)
	{
		return this;
	}

	@Override
	public YearRequirements isNotEqualTo(Year value, String name)
	{
		return this;
	}

	@Override
	public YearRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public YearRequirements isNull()
	{
		return this;
	}

	@Override
	public YearRequirements isNotNull()
	{
		return this;
	}

	@Override
	public YearRequirements isolate(Consumer<YearRequirements> consumer)
	{
		return this;
	}
}
