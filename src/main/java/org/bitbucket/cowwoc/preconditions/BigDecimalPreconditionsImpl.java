/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.math.BigDecimal;

/**
 * Default implementation of BigDecimalPreconditions.
 * <p>
 * @author Gili Tzabari
 */
final class BigDecimalPreconditionsImpl
	extends NumberPreconditionsImpl<BigDecimalPreconditions, BigDecimal>
	implements BigDecimalPreconditions
{
	/**
	 * Creates new BigDecimalPreconditionsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	BigDecimalPreconditionsImpl(BigDecimal parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name);
	}

	@Override
	public BigDecimalPreconditions hasPrecisionIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(range, "range").isNotNull();
		if (range.contains(parameter.precision()))
			return this;
		return throwException(IllegalArgumentException.class,
			Ranges.getExceptionMessage(String.format("%s.precision()", name), parameter.precision(),
				range));
	}

	@Override
	public BigDecimalPreconditions hasScaleIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(range, "range").isNotNull();
		if (range.contains(parameter.scale()))
			return this;
		return throwException(IllegalArgumentException.class,
			Ranges.getExceptionMessage(String.format("%s.scale()", name), parameter.scale(), range));
	}

	@Override
	public BigDecimalPreconditions isZero() throws IllegalArgumentException
	{
		// Number.longValue() truncates the fractional portion, which we need to take into account
		if (parameter.signum() == 0)
			return self;
		return throwException(IllegalArgumentException.class, String.format("%s must be zero", name));
	}

	@Override
	public BigDecimalPreconditions isNotZero() throws IllegalArgumentException
	{
		// Number.longValue() truncates the fractional portion, which we need to take into account
		if (parameter.signum() != 0)
			return self;
		return throwException(IllegalArgumentException.class, String.format("%s may not be zero", name));
	}
}
