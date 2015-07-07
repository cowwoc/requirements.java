/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Default implementation of MapSizePreconditions.
 * <p>
 * @author Gili Tzabari
 */
final class MapSizePreconditionsImpl
	extends PrimitiveIntegerPreconditionsImpl<MapSizePreconditions>
	implements MapSizePreconditions, PrimitiveIntegerPreconditions<MapSizePreconditions>
{
	private final Map<?, ?> map;

	/**
	 * Creates new MapSizePreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	MapSizePreconditionsImpl(Map<?, ?> parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter.size(), name, exceptionOverride);
		this.map = parameter;
	}

	@Override
	public MapSizePreconditions isGreaterThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter >= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least %,d entries. It contained %,d entries.\n" +
				"Actual  : %s", name, value, parameter, map));
	}

	@Override
	public MapSizePreconditions isGreaterThanOrEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull();
		if (parameter >= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least %,d (%s) entries. It contained %,d entries.\n" +
				"Actual  : %s", this.name, value, name, parameter, map));
	}

	@Override
	public MapSizePreconditions isGreaterThan(Integer value) throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter > value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain more than %,d entries. It contained %,d entries.\n" +
				"Actual  : %s", name, value, parameter, map));
	}

	@Override
	public MapSizePreconditions isGreaterThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull();
		if (parameter > value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain more than %,d (%s) entries. It contained %,d entries.\n" +
				"Actual  : %s", this.name, value, name, parameter, map));
	}

	@Override
	public MapSizePreconditions isLessThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter <= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s may contain at most %,d entries. It contained %,d entries.\n" +
				"Actual  : %s", name, value, parameter, map));
	}

	@Override
	public MapSizePreconditions isLessThanOrEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull();
		if (parameter <= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s may contain at most %,d (%s) entries. It contained %,d entries.\n" +
				"Actual  : %s", this.name, value, name, parameter, map));
	}

	@Override
	public MapSizePreconditions isLessThan(Integer value) throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter < value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain less than %,d entries. It contained %,d entries.\n" +
				"Actual  : %s", name, value, parameter, map));
	}

	@Override
	public MapSizePreconditions isLessThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull();
		if (parameter < value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain less than %,d (%s) entries. It contained %,d entries.\n" +
				"Actual  : %s", this.name, value, name, parameter, map));
	}

	@Override
	public MapSizePreconditions isNotPositive() throws IllegalArgumentException
	{
		return isZero();
	}

	@Override
	public MapSizePreconditions isPositive() throws IllegalArgumentException
	{
		if (parameter > 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least one entry. It contained %,d entries.\n" +
				"Actual  : %s", name, parameter, map));
	}

	@Override
	public MapSizePreconditions isNotZero() throws IllegalArgumentException
	{
		return isPositive();
	}

	@Override
	public MapSizePreconditions isZero() throws IllegalArgumentException
	{
		if (parameter == 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be empty. It contained %,d entries.\n" +
				"Actual  : %s", name, parameter, map));
	}

	@Override
	public MapSizePreconditions isNotNegative() throws IllegalArgumentException
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public MapSizePreconditions isNegative() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never have a negative size", name));
	}

	@Override
	public MapSizePreconditions isIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(range, "range").isNotNull();
		if (range.contains(parameter))
			return self;
		StringBuilder message = new StringBuilder(name + " must contain ");
		Ranges.appendRange(range, message);
		message.append(String.format(" entries. It contained %,d entries.\n" +
			"Actual  : %s", parameter, map));
		return throwException(IllegalArgumentException.class, message.toString());
	}

	@Override
	public MapSizePreconditions isEqualTo(Integer value) throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %,d entries. It contained %,d entries.\n" +
				"Actual  : %s", name, value, parameter, map));
	}

	@Override
	public MapSizePreconditions isEqualTo(Integer value, String name) throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull();
		if (Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %,d entries (%s). It contained %,d entries.\n" +
				"Actual  : %s", this.name, value, name, parameter, map));
	}

	@Override
	public MapSizePreconditions isNotEqualTo(Integer value) throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (!Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not contain %,d entries, but did.\n" +
				"Actual  : %s", name, value, map));
	}

	@Override
	public MapSizePreconditions isNotEqualTo(Integer value, String name) throws
		IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull();
		if (!Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not contain %,d entries (%s), but did.\n" +
				"Actual  : %s", this.name, value, name, map));
	}
}
