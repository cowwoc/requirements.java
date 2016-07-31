/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.Map;
import java.util.Objects;

/**
 * Default implementation of MapSizeRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class MapSizeRequirementsImpl
	extends PrimitiveIntegerRequirementsImpl<MapSizeRequirements>
	implements MapSizeRequirements
{
	private final Map<?, ?> map;

	/**
	 * Creates new MapSizeRequirementsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	MapSizeRequirementsImpl(Map<?, ?> parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter.size(), name, exceptionOverride);
		this.map = parameter;
	}

	@Override
	public MapSizeRequirements usingException(
		Class<? extends RuntimeException> exceptionOverride)
	{
		if (Objects.equals(exceptionOverride, this.exceptionOverride))
			return this;
		return new MapSizeRequirementsImpl(map, name, exceptionOverride);
	}

	@Override
	public MapSizeRequirements isGreaterThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter >= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least %,d entries. It contained %,d entries.\n" +
				"Actual: %s", name, value, parameter, map));
	}

	@Override
	public MapSizeRequirements isGreaterThanOrEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter >= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) entries. It contained %,d entries.\n" +
				"Actual: %s", this.name, name, value, parameter, map));
	}

	@Override
	public MapSizeRequirements isGreaterThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter > value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain more than %,d entries. It contained %,d entries.\n" +
				"Actual: %s", name, value, parameter, map));
	}

	@Override
	public MapSizeRequirements isGreaterThan(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter > value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) entries. It contained %,d entries.\n" +
				"Actual: %s", this.name, name, value, parameter, map));
	}

	@Override
	public MapSizeRequirements isLessThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter <= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s may contain at most %,d entries. It contained %,d entries.\n" +
				"Actual: %s", name, value, parameter, map));
	}

	@Override
	public MapSizeRequirements isLessThanOrEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter <= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s may contain at most %s (%,d) entries. It contained %,d entries.\n" +
				"Actual: %s", this.name, name, value, parameter, map));
	}

	@Override
	public MapSizeRequirements isLessThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter < value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain less than %,d entries. It contained %,d entries.\n" +
				"Actual: %s", name, value, parameter, map));
	}

	@Override
	public MapSizeRequirements isLessThan(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter < value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain less than %s (%,d) entries. It contained %,d entries.\n" +
				"Actual: %s", this.name, name, value, parameter, map));
	}

	@Override
	public MapSizeRequirements isNotPositive() throws IllegalArgumentException
	{
		return isZero();
	}

	@Override
	public MapSizeRequirements isPositive() throws IllegalArgumentException
	{
		if (parameter > 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least one entry. It contained %,d entries.\n" +
				"Actual: %s", name, parameter, map));
	}

	@Override
	public MapSizeRequirements isNotZero() throws IllegalArgumentException
	{
		return isPositive();
	}

	@Override
	public MapSizeRequirements isZero() throws IllegalArgumentException
	{
		if (parameter == 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be empty. It contained %,d entries.\n" +
				"Actual: %s", name, parameter, map));
	}

	@Override
	public MapSizeRequirements isNotNegative() throws IllegalArgumentException
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public MapSizeRequirements isNegative() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never have a negative size", name));
	}

	@Override
	public MapSizeRequirements isIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(range, "range").isNotNull();
		if (range.contains(parameter))
			return self;
		StringBuilder message = new StringBuilder(name + " must contain " + range);
		message.append(String.format(" entries. It contained %,d entries.\n" +
			"Actual: %s", parameter, map));
		return throwException(IllegalArgumentException.class, message.toString());
	}

	@Override
	public MapSizeRequirements isEqualTo(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %,d entries. It contained %,d entries.\n" +
				"Actual: %s", name, value, parameter, map));
	}

	@Override
	public MapSizeRequirements isEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) entries. It contained %,d entries.\n" +
				"Actual: %s", this.name, name, value, parameter, map));
	}

	@Override
	public MapSizeRequirements isNotEqualTo(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (!Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not contain %,d entries, but did.\n" +
				"Actual: %s", name, value, map));
	}

	@Override
	public MapSizeRequirements isNotEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not contain %s (%,d) entries, but did.\n" +
				"Actual: %s", this.name, name, value, map));
	}
}
