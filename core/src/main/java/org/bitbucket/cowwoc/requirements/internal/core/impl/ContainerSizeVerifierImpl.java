/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import java.util.Objects;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.core.CoreVerifiers;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.util.ExceptionBuilder;

/**
 * An implementation of PrimitiveNumberVerifier for a container's size.
 *
 * @author Gili Tzabari
 */
public final class ContainerSizeVerifierImpl
	extends NumberCapabilitiesImpl<PrimitiveNumberVerifier<Integer>, Integer>
	implements ContainerSizeVerifier
{
	private final Object container;
	private final String containerName;
	private final Pluralizer pluralizer;

	/**
	 * Creates new ContainerSizeVerifierImpl.
	 *
	 * @param scope         the application configuration
	 * @param container     the container
	 * @param size          the size of the container
	 * @param containerName the name of the container
	 * @param sizeName      the name of the container's size
	 * @param pluralizer    returns the singular or plural form of the container's element type
	 * @param config        the instance configuration
	 * @throws AssertionError if {@code scope}, {@code container}, {@code name} or {@code config} are
	 *                        null; if {@code name} is empty
	 */
	public ContainerSizeVerifierImpl(ApplicationScope scope, Object container, int size,
		String containerName, String sizeName, Pluralizer pluralizer, Configuration config)
	{
		super(scope, size, sizeName, config);
		assert (container != null): "container may not be null";
		assert (containerName != null): "containerName may not be null";
		assert (!containerName.isEmpty()): "containerName may not be empty";
		assert (pluralizer != null): "pluralizer may not be null";
		this.container = container;
		this.containerName = containerName;
		this.pluralizer = pluralizer;
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isGreaterThanOrEqualTo(Integer value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
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
	public PrimitiveNumberVerifier<Integer> isGreaterThanOrEqualTo(Integer value, String name)
	{
		CoreVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
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
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
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
	public PrimitiveNumberVerifier<Integer> isGreaterThan(Integer value, String name)
	{
		CoreVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
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
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
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
	public PrimitiveNumberVerifier<Integer> isLessThanOrEqualTo(Integer value, String name)
	{
		CoreVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
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
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
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
	public PrimitiveNumberVerifier<Integer> isLessThan(Integer value, String name)
	{
		CoreVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
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
	public PrimitiveNumberVerifier<Integer> isBetween(Integer min, Integer max)
	{
		CoreVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat(min, "min").isNotNull();
		verifier.requireThat(max, "max").isNotNull().isGreaterThanOrEqualTo(min, "min");
		if (actual >= min && actual <= max)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain [%d, %d] %s.", containerName, min, max, pluralizer.nameOf(2))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isEqualTo(Integer value)
	{
		if (Objects.equals(actual, value))
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain %,d %s.", containerName, value, pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isEqualTo(Integer value, String name)
	{
		CoreVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(actual, value))
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) %s.", this.containerName, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isNotEqualTo(Integer value)
	{
		if (!Objects.equals(actual, value))
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not contain %,d %s.", containerName, value, pluralizer.nameOf(value))).
			addContext("Actual", container).
			build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isNotEqualTo(Integer value, String name)
	{
		CoreVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(actual, value))
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not contain %s (%,d) %s.", this.containerName, name, value,
				pluralizer.nameOf(value))).
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
