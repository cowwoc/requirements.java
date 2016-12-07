/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * An implementation of BigDecimalScaleVerifier that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpBigDecimalScaleVerifier implements BigDecimalScaleVerifier
{
	public static final NoOpBigDecimalScaleVerifier INSTANCE
		= new NoOpBigDecimalScaleVerifier();

	/**
	 * Prevent construction.
	 */
	private NoOpBigDecimalScaleVerifier()
	{
	}

	@Override
	public BigDecimalScaleVerifier withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	@Deprecated
	public BigDecimalScaleVerifier isNull()
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNegative()
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotNegative()
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotPositive()
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotZero()
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isPositive()
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isZero()
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isGreaterThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isGreaterThan(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isGreaterThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isGreaterThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isLessThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isLessThan(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isLessThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isLessThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isIn(Integer first, Integer last)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isIn(Collection<Integer> collection)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotNull()
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isolate(Consumer<BigDecimalScaleVerifier> consumer)
	{
		return this;
	}
}
