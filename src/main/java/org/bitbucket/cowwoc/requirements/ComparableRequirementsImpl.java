/*
 * Copyright 2016 Gili.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Objects;

/**
 * Default implementation of ComparableRequirements.
 * <p>
 * @param <T> the type of objects that the parameter may be compared to
 * @author Gili Tzabari
 */
final class ComparableRequirementsImpl<T extends Comparable<? super T>>
	extends AbstractObjectRequirements<ComparableRequirements<T>, T>
	implements ComparableRequirements<T>
{
	/**
	 * Creates new ComparableRequirementsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	ComparableRequirementsImpl(T parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public ComparableRequirements<T> usingException(
		Class<? extends RuntimeException> exceptionOverride)
	{
		if (Objects.equals(exceptionOverride, this.exceptionOverride))
			return self;
		return new ComparableRequirementsImpl<>(parameter, name, exceptionOverride);
	}

	@Override
	public ComparableRequirements<T> isLessThan(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int actual = parameter.compareTo(value);
		if (actual < 0)
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%s) must be less than %s (%s)\n" +
				"Actual: %d", this.name, parameter, name, value, actual));
	}

	@Override
	public ComparableRequirements<T> isLessThan(T value)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		int actual = parameter.compareTo(value);
		if (actual < 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%s) must be less than %s\n" +
				"Actual: %d", this.name, parameter, value, actual));
	}

	@Override
	public ComparableRequirements<T> isLessThanOrEqualTo(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int actual = parameter.compareTo(value);
		if (actual <= 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%s) must be less than or equal to %s (%s)\n" +
				"Actual: %d", this.name, parameter, name, value, actual));
	}

	@Override
	public ComparableRequirements<T> isLessThanOrEqualTo(T value)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		int actual = parameter.compareTo(value);
		if (actual <= 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%s) must be less than or equal to %s\n" +
				"Actual: %d", name, parameter, value, actual));
	}

	@Override
	public ComparableRequirements<T> isGreaterThan(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int actual = parameter.compareTo(value);
		if (actual > 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%s) must be greater than %s (%s)\n" +
				"Actual: %d", this.name, parameter, name, value, actual));
	}

	@Override
	public ComparableRequirements<T> isGreaterThan(T value)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		int actual = parameter.compareTo(value);
		if (actual > 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%s) must be greater than %s\n" +
				"Actual: %d", name, parameter, value, actual));
	}

	@Override
	public ComparableRequirements<T> isGreaterThanOrEqualTo(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int actual = parameter.compareTo(value);
		if (actual >= 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%s) must be greater than or equal to %s (%s)\n" +
				"Actual: %d", this.name, parameter, name, value, actual));
	}

	@Override
	public ComparableRequirements<T> isGreaterThanOrEqualTo(T value)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		int actual = parameter.compareTo(value);
		if (actual >= 0)
			return self;
		return throwException(IllegalArgumentException.class,
			String.format("%s (%s) must be greater than or equal to %s\n" +
				"Actual: %d", name, parameter, value, actual));
	}
}
