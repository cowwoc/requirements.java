/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.Collection;
import java.util.Objects;

/**
 * Default implementation of CollectionSizeRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class CollectionSizeRequirementsImpl
	extends PrimitiveIntegerRequirementsImpl<CollectionSizeRequirements>
	implements CollectionSizeRequirements
{
	private final Collection<?> collection;

	/**
	 * Creates new CollectionSizeRequirementsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	CollectionSizeRequirementsImpl(Collection<?> parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter.size(), name, exceptionOverride);
		this.collection = parameter;
	}

	@Override
	public CollectionSizeRequirements usingException(
		Class<? extends RuntimeException> exceptionOverride)
	{
		if (Objects.equals(exceptionOverride, this.exceptionOverride))
			return this;
		return new CollectionSizeRequirementsImpl(collection, name, exceptionOverride);
	}

	@Override
	public CollectionSizeRequirements isGreaterThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter >= value)
			return self;

		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least %,d %s. It contained %,d %s.\n" +
				"Actual: %s", name, value, getSingleOrPlural(value), parameter, getSingleOrPlural(parameter),
				collection));
	}

	/**
	 * @param amount an amount
	 * @return "element" or "elements" depending on whether {@code amount} is plural
	 */
	private String getSingleOrPlural(int amount)
	{
		if (amount == 1)
			return "element";
		return "elements";
	}

	@Override
	public CollectionSizeRequirements isGreaterThanOrEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter >= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) %s. It contained %,d %s.\n" +
				"Actual: %s", this.name, name, value, getSingleOrPlural(value), parameter,
				getSingleOrPlural(parameter), collection));
	}

	@Override
	public CollectionSizeRequirements isGreaterThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter > value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain more than %,d %s. It contained %,d %s.\n" +
				"Actual: %s", name, value, getSingleOrPlural(value), parameter, getSingleOrPlural(parameter),
				collection));
	}

	@Override
	public CollectionSizeRequirements isGreaterThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter > value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) %s. It contained %,d %s.\n" +
				"Actual: %s", this.name, name, value, getSingleOrPlural(value), parameter,
				getSingleOrPlural(parameter), collection));
	}

	@Override
	public CollectionSizeRequirements isLessThanOrEqualTo(Integer value)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter <= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s may contain at most %,d %s. It contained %,d %s.\n" +
				"Actual: %s", name, value, getSingleOrPlural(value), parameter, getSingleOrPlural(parameter),
				collection));
	}

	@Override
	public CollectionSizeRequirements isLessThanOrEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter <= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s may contain at most %,d (%s) %s. It contained %,d %s.\n" +
				"Actual: %s", this.name, value, name, getSingleOrPlural(value), parameter,
				getSingleOrPlural(parameter), collection));
	}

	@Override
	public CollectionSizeRequirements isLessThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter < value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain less than %,d %s. It contained %,d %s.\n" +
				"Actual: %s", name, getSingleOrPlural(value), parameter, getSingleOrPlural(parameter),
				collection));
	}

	@Override
	public CollectionSizeRequirements isLessThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter < value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain less than %,d (%s) %s. It contained %,d %s.\n" +
				"Actual: %s", this.name, value, name, getSingleOrPlural(value), parameter,
				getSingleOrPlural(parameter), collection));
	}

	@Override
	public CollectionSizeRequirements isNotPositive() throws IllegalArgumentException
	{
		return isZero();
	}

	@Override
	public CollectionSizeRequirements isPositive() throws IllegalArgumentException
	{
		if (parameter > 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least one element. It contained %,d %s.\n" +
				"Actual: %s", name, parameter, getSingleOrPlural(parameter), collection));
	}

	@Override
	public CollectionSizeRequirements isNotZero() throws IllegalArgumentException
	{
		return isPositive();
	}

	@Override
	public CollectionSizeRequirements isZero() throws IllegalArgumentException
	{
		if (parameter == 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be empty. It contained %,d %s.\n" +
				"Actual: %s", name, parameter, getSingleOrPlural(parameter), collection));
	}

	@Override
	public CollectionSizeRequirements isNotNegative() throws IllegalArgumentException
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public CollectionSizeRequirements isNegative() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never have a negative size", name));
	}

	@Override
	public CollectionSizeRequirements isIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(range, "range").isNotNull();
		if (range.contains(parameter))
			return self;
		StringBuilder message = new StringBuilder(name + " must contain " + range);
		message.append(String.format(" elements. It contained %,d %s.\n" +
			"Actual: %s", parameter, getSingleOrPlural(parameter), collection));
		return throwException(IllegalArgumentException.class, message.toString());
	}

	@Override
	public CollectionSizeRequirements isEqualTo(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %,d %s. It contained %,d %s.\n" +
				"Actual: %s", name, value, getSingleOrPlural(value), parameter, getSingleOrPlural(parameter),
				collection));
	}

	@Override
	public CollectionSizeRequirements isEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) %s. It contained %,d %s.\n" +
				"Actual: %s", this.name, name, value, getSingleOrPlural(value), parameter,
				getSingleOrPlural(parameter), collection));
	}

	@Override
	public CollectionSizeRequirements isNotEqualTo(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (!Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not contain %,d %s, but did.\n" +
				"Actual: %s", name, value, getSingleOrPlural(value), collection));
	}

	@Override
	public CollectionSizeRequirements isNotEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not contain %s (%,d) %s, but did. It contained %s", this.name,
				name, value, getSingleOrPlural(value), collection));
	}
}
