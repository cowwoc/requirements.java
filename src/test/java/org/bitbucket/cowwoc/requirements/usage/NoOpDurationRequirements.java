/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.usage;

import com.google.common.collect.Range;
import java.time.Duration;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An implementation of DurationRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
public enum NoOpDurationRequirements implements DurationRequirements
{
	INSTANCE;

	@Override
	public DurationRequirements isPositive()
	{
		return this;
	}

	@Override
	public DurationRequirements isGreaterThan(Duration value, String name)
	{
		return this;
	}

	@Override
	public DurationRequirements isGreaterThan(Duration value)
	{
		return this;
	}

	@Override
	public DurationRequirements isGreaterThanOrEqualTo(Duration value, String name)
	{
		return this;
	}

	@Override
	public DurationRequirements isGreaterThanOrEqualTo(Duration value)
	{
		return this;
	}

	@Override
	public DurationRequirements isLessThan(Duration value, String name)
	{
		return this;
	}

	@Override
	public DurationRequirements isLessThan(Duration value)
	{
		return this;
	}

	@Override
	public DurationRequirements isLessThanOrEqualTo(Duration value, String name)
	{
		return this;
	}

	@Override
	public DurationRequirements isLessThanOrEqualTo(Duration value)
	{
		return this;
	}

	@Override
	public DurationRequirements isIn(Range<Duration> range)
	{
		return this;
	}

	@Override
	public DurationRequirements withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public DurationRequirements withContext(Map<String, Object> context)
	{
		return this;
	}

	@Override
	public DurationRequirements isEqualTo(Duration value)
	{
		return this;
	}

	@Override
	public DurationRequirements isEqualTo(Duration value, String name)
	{
		return this;
	}

	@Override
	public DurationRequirements isNotEqualTo(Duration value)
	{
		return this;
	}

	@Override
	public DurationRequirements isNotEqualTo(Duration value, String name)
	{
		return this;
	}

	@Override
	public DurationRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public DurationRequirements isNull()
	{
		return this;
	}

	@Override
	public DurationRequirements isNotNull()
	{
		return this;
	}

	@Override
	public DurationRequirements isolate(Consumer<DurationRequirements> consumer)
	{
		return this;
	}
}