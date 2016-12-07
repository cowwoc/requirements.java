/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.BigDecimalPrecisionVerifier;

/**
 * An implementation of BigDecimalPrecisionVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpBigDecimalPrecisionVerifier implements BigDecimalPrecisionVerifier
{
	public static final NoOpBigDecimalPrecisionVerifier INSTANCE
		= new NoOpBigDecimalPrecisionVerifier();

	/**
	 * Prevent construction.
	 */
	private NoOpBigDecimalPrecisionVerifier()
	{
	}

	@Override
	public BigDecimalPrecisionVerifier withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isZero()
	{
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNotPositive()
	{
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNegative()
	{
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNull()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isNotNegative()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isNotZero()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isPositive()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isGreaterThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isGreaterThan(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isGreaterThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isGreaterThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isLessThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isLessThan(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isLessThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isLessThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isIn(Integer first, Integer last)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isNotEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isNotEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isIn(Collection<Integer> collection)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isNotNull()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isolate(Consumer<BigDecimalPrecisionVerifier> consumer)
	{
		return this;
	}

}
