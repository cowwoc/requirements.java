/*
 * Copyright 2015 Gili Tzabaro.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.core.BigDecimalScaleVerifier;
import org.bitbucket.cowwoc.requirements.core.BigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of BigDecimalVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public enum NoOpBigDecimalVerifier implements BigDecimalVerifier
{
	INSTANCE;

	@Override
	public BigDecimalVerifier withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier precision()
	{
		return NoOpBigDecimalPrecisionVerifier.INSTANCE;
	}

	@Override
	public BigDecimalScaleVerifier scale()
	{
		return NoOpBigDecimalScaleVerifier.INSTANCE;
	}

	@Override
	public BigDecimalVerifier isNegative()
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isNotNegative()
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isNotPositive()
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isNotZero()
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isPositive()
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isZero()
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isGreaterThan(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isGreaterThan(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isGreaterThanOrEqualTo(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isGreaterThanOrEqualTo(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isLessThan(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isLessThan(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isLessThanOrEqualTo(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isLessThanOrEqualTo(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isIn(BigDecimal first, BigDecimal last)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isEqualTo(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isEqualTo(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isNotEqualTo(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isNotEqualTo(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isIn(Collection<BigDecimal> collection)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isNull()
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return NoOpStringVerifier.INSTANCE;
	}

	@Override
	public BigDecimalVerifier isolate(Consumer<BigDecimalVerifier> consumer)
	{
		return this;
	}
}
