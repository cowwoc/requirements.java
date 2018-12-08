/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.java.JavaVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;
import org.bitbucket.cowwoc.requirements.java.internal.util.Pluralizer;

import java.util.Objects;

/**
 * An implementation of PrimitiveNumberVerifier for a container's size.
 */
public final class ContainerSizeVerifierImpl
	extends NumberCapabilitiesImpl<PrimitiveNumberVerifier<Integer>, Integer>
	implements ContainerSizeVerifier
{
	private final String containerName;
	private final Object container;
	private final Pluralizer pluralizer;

	/**
	 * @param scope         the application configuration
	 * @param containerName the name of the container
	 * @param container     the container
	 * @param sizeName      the name of the container's size
	 * @param size          the size of the container
	 * @param pluralizer    returns the singular or plural form of the container's element type
	 * @param config        the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name}, {@code container} or {@code config} are
	 *                        null; if {@code name} is empty
	 */
	public ContainerSizeVerifierImpl(ApplicationScope scope, String containerName, Object container, String sizeName, int size,
	                                 Pluralizer pluralizer, Configuration config)
	{
		super(scope, sizeName, size, config);
		assert (containerName != null) : "containerName may not be null";
		assert (!containerName.isEmpty()) : "containerName may not be empty";
		assert (container != null) : "container may not be null";
		assert (pluralizer != null) : "pluralizer may not be null";
		this.containerName = containerName;
		this.container = container;
		this.pluralizer = pluralizer;
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isGreaterThanOrEqualTo(Integer value)
	{
		scope.getInternalVerifier().requireThat("value", value).isNotNull();
		if (actual >= value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain at least %,d %s.", containerName, value,
				pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isGreaterThanOrEqualTo(String name, Integer value)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		verifier.requireThat("value", value).isNotNull();
		if (actual >= value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) %s.", this.containerName, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isGreaterThan(Integer value)
	{
		scope.getInternalVerifier().requireThat("value", value).isNotNull();
		if (actual > value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain more than %,d %s.", containerName, value,
				pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isGreaterThan(String name, Integer value)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		verifier.requireThat("value", value).isNotNull();
		if (actual > value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) %s.", this.containerName, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isLessThanOrEqualTo(Integer value)
	{
		scope.getInternalVerifier().requireThat("value", value).isNotNull();
		if (actual <= value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not contain more than %,d %s.", containerName, value,
				pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isLessThanOrEqualTo(String name, Integer value)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		verifier.requireThat("value", value).isNotNull();
		if (actual <= value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not contain more than %s (%,d) %s.", this.containerName, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isLessThan(Integer value)
	{
		scope.getInternalVerifier().requireThat("value", value).isNotNull();
		if (actual < value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain less than %,d %s.", this.containerName, value,
				pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isLessThan(String name, Integer value)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		verifier.requireThat("value", value).isNotNull();
		if (actual < value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain less than %s (%,d) %s.", this.containerName, name, value,
				pluralizer.nameOf(value), actual, pluralizer.nameOf(actual))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isNotPositive()
	{
		return isZero();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isPositive()
	{
		if (actual > 0)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain at least one %s.", containerName, pluralizer.nameOf(1))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isNotZero()
	{
		return isPositive();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isZero()
	{
		if (actual == 0)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be empty.", containerName)).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Deprecated
	@Override
	public PrimitiveNumberVerifier<Integer> isNotNegative()
	{
		// Always true
		return getThis();
	}

	@Deprecated
	@Override
	public PrimitiveNumberVerifier<Integer> isNegative()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be negative", name), null).
			build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isBetween(Integer startInclusive, Integer endExclusive)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat("startInclusive", startInclusive).isNotNull();
		verifier.requireThat("endExclusive", endExclusive).isNotNull().
			isGreaterThanOrEqualTo("startInclusive", startInclusive);
		if (actual >= startInclusive && actual < endExclusive)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain [%d, %d) %s.", containerName, startInclusive, endExclusive,
				pluralizer.nameOf(2))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isBetweenClosed(Integer startInclusive,
	                                                        Integer endInclusive)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat("startInclusive", startInclusive).isNotNull();
		verifier.requireThat("endInclusive", endInclusive).isNotNull().
			isGreaterThanOrEqualTo("startInclusive", startInclusive);
		if (actual >= startInclusive && actual <= endInclusive)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain [%d, %d] %s.", containerName, startInclusive, endInclusive,
				pluralizer.nameOf(2))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isEqualTo(Object expected)
	{
		scope.getInternalVerifier().requireThat("expected", expected).isInstanceOf(Integer.class);
		if (Objects.equals(actual, expected))
			return getThis();
		int expectedAsInt = (Integer) expected;
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain %,d %s.", containerName, expected,
				pluralizer.nameOf(expectedAsInt))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isEqualTo(String name, Object expected)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		verifier.requireThat("expected", expected).isInstanceOf(Integer.class);
		if (Objects.equals(actual, expected))
			return getThis();
		int expectedAsInt = (Integer) expected;
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) %s.", this.containerName, name, expected,
				pluralizer.nameOf(expectedAsInt))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isNotEqualTo(Object value)
	{
		scope.getInternalVerifier().requireThat("value", value).isInstanceOf(Integer.class);
		if (!Objects.equals(actual, value))
			return getThis();
		int valueAsInt = (Integer) value;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not contain %,d %s.", containerName, value,
				pluralizer.nameOf(valueAsInt))).
			addContext("Actual", container).
			build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isNotEqualTo(String name, Object value)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		verifier.requireThat("value", value).isInstanceOf(Integer.class);
		if (!Objects.equals(actual, value))
			return getThis();
		int valueAsInt = (Integer) value;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not contain %s (%,d) %s.", this.containerName, name, value,
				pluralizer.nameOf(valueAsInt))).
			addContext("Actual", container).
			build();
	}

	@Override
	@Deprecated
	public PrimitiveNumberVerifier<Integer> isNull()
	{
		return super.isNull();
	}
}
