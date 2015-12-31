/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.time.Year;
import java.util.function.Consumer;

/**
 * An implementation of YearPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpYearPreconditions implements YearPreconditions
{
	INSTANCE;

	@Override
	public YearPreconditions isGreaterThan(Year value, String name)
	{
		return this;
	}

	@Override
	public YearPreconditions isGreaterThan(Year value)
	{
		return this;
	}

	@Override
	public YearPreconditions isGreaterThanOrEqualTo(Year value, String name)
	{
		return this;
	}

	@Override
	public YearPreconditions isGreaterThanOrEqualTo(Year value)
	{
		return this;
	}

	@Override
	public YearPreconditions isIn(Range<Year> range)
	{
		return this;
	}

	@Override
	public YearPreconditions isLessThan(Year value, String name)
	{
		return this;
	}

	@Override
	public YearPreconditions isLessThan(Year value)
	{
		return this;
	}

	@Override
	public YearPreconditions isLessThanOrEqualTo(Year value, String name)
	{
		return this;
	}

	@Override
	public YearPreconditions isLessThanOrEqualTo(Year value)
	{
		return this;
	}

	@Override
	public NoOpYearPreconditions usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public YearPreconditions isEqualTo(Year value)
	{
		return this;
	}

	@Override
	public YearPreconditions isEqualTo(Year value, String name)
	{
		return this;
	}

	@Override
	public YearPreconditions isNotEqualTo(Year value)
	{
		return this;
	}

	@Override
	public YearPreconditions isNotEqualTo(Year value, String name)
	{
		return this;
	}

	@Override
	public YearPreconditions isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public YearPreconditions isNull()
	{
		return this;
	}

	@Override
	public YearPreconditions isNotNull()
	{
		return this;
	}

	@Override
	public YearPreconditions isolate(Consumer<YearPreconditions> consumer)
	{
		return this;
	}
}
