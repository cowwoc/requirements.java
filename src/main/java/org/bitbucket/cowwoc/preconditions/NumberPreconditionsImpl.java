/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.util.Optional;

/**
 * Default implementation of NumberPreconditions.
 * <p>
 * @param <S> the type of preconditions that was instantiated
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
class NumberPreconditionsImpl<S extends NumberPreconditions<S, T>, T extends Number & Comparable<? super T>>
	extends AbstractObjectPreconditions<S, T>
	implements NumberPreconditions<S, T>
{
	/**
	 * Creates new NumberPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	NumberPreconditionsImpl(T parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public S isIn(Range<T> range)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(range, "range").isNotNull();
		if (range.contains(parameter))
			return self;
		return throwException(IllegalArgumentException.class,
			Ranges.getExceptionMessage(name, parameter, range));
	}

	@Override
	public S isNegative() throws IllegalArgumentException
	{
		if (parameter.longValue() < 0L)
			return self;
		return throwException(IllegalArgumentException.class, String.format("%s must be negative.\n" +
			"Actual  : %s", name, parameter));
	}

	@Override
	public S isNotNegative() throws IllegalArgumentException
	{
		if (parameter.longValue() >= 0L)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not be negative.\n" +
				"Actual  : %s", name, parameter));
	}

	@Override
	public S isZero() throws IllegalArgumentException
	{
		if (parameter.longValue() == 0L)
			return self;
		return throwException(IllegalArgumentException.class, String.format("%s must be zero.\n" +
			"Actual  : %s", name, parameter));
	}

	@Override
	public S isNotZero() throws IllegalArgumentException
	{
		if (parameter.longValue() != 0L)
			return self;
		return throwException(IllegalArgumentException.class, String.format("%s may not be zero", name));
	}

	@Override
	public S isPositive() throws IllegalArgumentException
	{
		if (parameter.longValue() > 0L)
			return self;
		return throwException(IllegalArgumentException.class, String.format("%s must be positive.\n" +
			"Actual  : %s", name, parameter));
	}

	@Override
	public S isNotPositive() throws IllegalArgumentException
	{
		if (parameter.longValue() <= 0L)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not be positive.\n" +
				"Actual  : %s", name, parameter));
	}

	@Override
	public S isLessThan(T value, String name) throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull();
		if (parameter.compareTo(value) < 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%d) must be less than %s (%d)", this.name, parameter.longValue(), name,
				value.longValue()));
	}

	@Override
	public S isLessThan(T value) throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter.compareTo(value) < 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%d) must be less than %d", this.name, parameter.longValue(),
				value.longValue()));
	}

	@Override
	public S isLessThanOrEqualTo(T value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull();
		if (parameter.compareTo(value) <= 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%d) must be less than or equal to %s (%d)", this.name,
				parameter.longValue(), name, value.longValue()));
	}

	@Override
	public S isLessThanOrEqualTo(T value)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter.compareTo(value) <= 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%d) must be less than or equal to %d", name, parameter.longValue(),
				value.longValue()));
	}

	@Override
	public S isGreaterThan(T value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull();
		if (parameter.compareTo(value) > 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%d) must be greater than %s (%d)", this.name, parameter.longValue(), name,
				value.longValue()));
	}

	@Override
	public S isGreaterThan(T value)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter.compareTo(value) > 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%d) must be greater than %d", name, parameter.longValue(),
				value.longValue()));
	}

	@Override
	public S isGreaterThanOrEqualTo(T value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull();
		if (parameter.compareTo(value) >= 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%d) must be greater than or equal to %s (%d)", this.name,
				parameter.longValue(), name, value.longValue()));
	}

	@Override
	public S isGreaterThanOrEqualTo(T value)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter.compareTo(value) >= 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%d) must be greater than or equal to %d", name, parameter.longValue(),
				value.longValue()));
	}

	@Override
	protected S valueOf(T parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
	{
		@SuppressWarnings("unchecked")
		S result = (S) new NumberPreconditionsImpl<>(parameter, name, exceptionOverride);
		return result;
	}
}
