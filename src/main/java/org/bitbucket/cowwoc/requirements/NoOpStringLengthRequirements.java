/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An implementation of StringLengthRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpStringLengthRequirements implements StringLengthRequirements
{
	public static final NoOpStringLengthRequirements INSTANCE = new NoOpStringLengthRequirements();

	/**
	 * Prevent construction.
	 */
	private NoOpStringLengthRequirements()
	{
	}

	@Override
	public StringLengthRequirements withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public StringLengthRequirements withContext(Map<String, Object> context)
	{
		return this;
	}

	@Override
	@Deprecated
	public StringLengthRequirements isNull()
	{
		return this;
	}

	@Override
	public StringLengthRequirements isNegative()
	{
		return this;
	}

	@Override
	public StringLengthRequirements isNotNegative()
	{
		return this;
	}

	@Override
	public StringLengthRequirements isNotPositive()
	{
		return this;
	}

	@Override
	public StringLengthRequirements isNotZero()
	{
		return this;
	}

	@Override
	public StringLengthRequirements isPositive()
	{
		return this;
	}

	@Override
	public StringLengthRequirements isZero()
	{
		return this;
	}

	@Override
	public StringLengthRequirements isGreaterThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public StringLengthRequirements isGreaterThan(Integer value)
	{
		return this;
	}

	@Override
	public StringLengthRequirements isGreaterThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public StringLengthRequirements isGreaterThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public StringLengthRequirements isLessThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public StringLengthRequirements isLessThan(Integer value)
	{
		return this;
	}

	@Override
	public StringLengthRequirements isLessThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public StringLengthRequirements isLessThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public StringLengthRequirements isIn(Range<Integer> range)
	{
		return this;
	}

	@Override
	public StringLengthRequirements isEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public StringLengthRequirements isEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public StringLengthRequirements isNotEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public StringLengthRequirements isNotEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public StringLengthRequirements isIn(Collection<Integer> collection)
	{
		return this;
	}

	@Override
	public StringLengthRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public StringLengthRequirements isNotNull()
	{
		return this;
	}

	@Override
	public StringLengthRequirements isolate(Consumer<StringLengthRequirements> consumer)
	{
		return this;
	}
}
