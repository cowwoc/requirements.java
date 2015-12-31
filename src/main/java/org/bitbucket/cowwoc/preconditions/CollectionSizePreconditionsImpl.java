/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * Default implementation of CollectionSizePreconditions.
 * <p>
 * @author Gili Tzabari
 */
final class CollectionSizePreconditionsImpl
	extends PrimitiveIntegerPreconditionsImpl<CollectionSizePreconditions>
	implements CollectionSizePreconditions
{
	private final Collection<?> collection;

	/**
	 * Creates new CollectionSizePreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} or {@code exceptionOverride} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	CollectionSizePreconditionsImpl(Collection<?> parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter.size(), name, exceptionOverride);
		this.collection = parameter;
	}

	@Override
	public CollectionSizePreconditions isGreaterThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter >= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least %,d elements. It contained %,d elements.\n" +
				"Actual: %s", name, value, parameter, collection));
	}

	@Override
	public CollectionSizePreconditions isGreaterThanOrEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter >= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) elements. It contained %,d elements.\n" +
				"Actual: %s", this.name, name, value, parameter, collection));
	}

	@Override
	public CollectionSizePreconditions isGreaterThan(Integer value) throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter > value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain more than %,d elements. It contained %,d elements.\n" +
				"Actual: %s", name, value, parameter, collection));
	}

	@Override
	public CollectionSizePreconditions isGreaterThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter > value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) elements. It contained %,d elements.\n" +
				"Actual: %s", this.name, name, value, parameter, collection));
	}

	@Override
	public CollectionSizePreconditions isLessThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter <= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s may contain at most %,d elements. It contained %,d elements.\n" +
				"Actual: %s", name, value, parameter, collection));
	}

	@Override
	public CollectionSizePreconditions isLessThanOrEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter <= value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s may contain at most %,d (%s) elements. It contained %,d elements.\n" +
				"Actual: %s", this.name, value, name, parameter, collection));
	}

	@Override
	public CollectionSizePreconditions isLessThan(Integer value) throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (parameter < value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain less than %,d elements. It contained %,d elements.\n" +
				"Actual: %s", name, value, parameter, collection));
	}

	@Override
	public CollectionSizePreconditions isLessThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter < value)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain less than %,d (%s) elements. It contained %,d elements.\n" +
				"Actual: %s", this.name, value, name, parameter, collection));
	}

	@Override
	public CollectionSizePreconditions isNotPositive() throws IllegalArgumentException
	{
		return isZero();
	}

	@Override
	public CollectionSizePreconditions isPositive() throws IllegalArgumentException
	{
		if (parameter > 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain at least one entry. It contained %,d elements.\n" +
				"Actual: %s", name, parameter, collection));
	}

	@Override
	public CollectionSizePreconditions isNotZero() throws IllegalArgumentException
	{
		return isPositive();
	}

	@Override
	public CollectionSizePreconditions isZero() throws IllegalArgumentException
	{
		if (parameter == 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be empty. It contained %,d elements.\n" +
				"Actual: %s", name, parameter, collection));
	}

	@Override
	public CollectionSizePreconditions isNotNegative() throws IllegalArgumentException
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public CollectionSizePreconditions isNegative() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never have a negative size", name));
	}

	@Override
	public CollectionSizePreconditions isIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(range, "range").isNotNull();
		if (range.contains(parameter))
			return self;
		StringBuilder message = new StringBuilder(name + " must contain ");
		Ranges.appendRange(range, message);
		message.append(String.format(" elements. It contained %,d elements.\n" +
			"Actual: %s", parameter, collection));
		return throwException(IllegalArgumentException.class, message.toString());
	}

	@Override
	public CollectionSizePreconditions isEqualTo(Integer value) throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %,d elements. It contained %,d elements.\n" +
				"Actual: %s", name, value, parameter, collection));
	}

	@Override
	public CollectionSizePreconditions isEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) elements. It contained %,d elements.\n" +
				"Actual: %s", this.name, name, value, parameter, collection));
	}

	@Override
	public CollectionSizePreconditions isNotEqualTo(Integer value) throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		if (!Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not contain %,d elements, but did.\n" +
				"Actual: %s", name, value, collection));
	}

	@Override
	public CollectionSizePreconditions isNotEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Preconditions.requireThat(value, "value").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(parameter, value))
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not contain %s (%,d) elements, but did. It contained %s", this.name,
				name, value, collection));
	}
}
