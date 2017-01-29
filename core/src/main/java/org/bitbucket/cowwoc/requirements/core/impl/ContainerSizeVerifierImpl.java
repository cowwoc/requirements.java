/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Objects;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.CoreUnifiedVerifier;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;
import org.bitbucket.cowwoc.requirements.core.util.Exceptions;

/**
 * An implementation of PrimitiveIntegerVerifier for a container's size.
 *
 * @author Gili Tzabari
 */
public final class ContainerSizeVerifierImpl
	extends NumberCapabilitiesImpl<PrimitiveIntegerVerifier, Integer>
	implements PrimitiveIntegerVerifier
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
	public PrimitiveIntegerVerifier isGreaterThanOrEqualTo(Integer value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		if (actual >= value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain at least %,d %s.", name, value, pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isGreaterThanOrEqualTo(Integer value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual >= value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) %s.", this.name, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isGreaterThan(Integer value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		if (actual > value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain more than %,d %s.", name, value, pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isGreaterThan(Integer value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual > value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) %s.", this.name, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isLessThanOrEqualTo(Integer value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		if (actual <= value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s may not contain more than %,d %s.", name, value, pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isLessThanOrEqualTo(Integer value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual <= value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s may not contain more than %s (%,d) %s.", this.name, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isLessThan(Integer value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		if (actual < value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain less than %,d %s.", this.name, value, pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isLessThan(Integer value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual < value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain less than %s (%,d) %s.", this.name, name, value,
				pluralizer.nameOf(value), actual, pluralizer.nameOf(actual))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isNotPositive()
	{
		return isZero();
	}

	@Override
	public PrimitiveIntegerVerifier isPositive()
	{
		if (actual > 0)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain at least one %s.", name, pluralizer.nameOf(1))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isNotZero()
	{
		return isPositive();
	}

	@Override
	public PrimitiveIntegerVerifier isZero()
	{
		if (actual == 0)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isNotNegative()
	{
		// Always true
		return getThis();
	}

	@Deprecated
	@Override
	public PrimitiveIntegerVerifier isNegative()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s cannot have a negative length", name), null);
	}

	@Override
	public PrimitiveIntegerVerifier isBetween(Integer min, Integer max)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(min, "min").isNotNull();
		verifier.requireThat(max, "max").isNotNull().isGreaterThanOrEqualTo(min, "min");
		if (actual >= min && actual <= max)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain [%d, %d] %s.", name, min, max, pluralizer.nameOf(2))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isEqualTo(Integer value)
	{
		if (Objects.equals(actual, value))
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain %,d %s.", name, value, pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isEqualTo(Integer value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(actual, value))
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) %s.", this.name, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isNotEqualTo(Integer value)
	{
		if (!Objects.equals(actual, value))
			return getThis();
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s may not contain %,d %s.", name, value, pluralizer.nameOf(value))).
			addContext("Actual", container).
			build();
	}

	@Override
	public PrimitiveIntegerVerifier isNotEqualTo(Integer value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(actual, value))
			return getThis();
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s may not contain %s (%,d) %s.", this.name, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", container).
			build();
	}

	@Override
	@Deprecated
	public PrimitiveIntegerVerifier isNull()
	{
		return super.isNull();
	}
}
