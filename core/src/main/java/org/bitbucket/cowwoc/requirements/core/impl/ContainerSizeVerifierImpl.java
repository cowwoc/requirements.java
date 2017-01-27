/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.CoreUnifiedVerifier;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;
import org.bitbucket.cowwoc.requirements.core.util.Exceptions;

/**
 * An implementation of PrimitiveIntegerVerifier for a container's size.
 *
 * @author Gili Tzabari
 */
public final class ContainerSizeVerifierImpl implements PrimitiveIntegerVerifier
{
	private final ApplicationScope scope;
	private final Object container;
	private final int size;
	private final String containerName;
	private final String sizeName;
	private final Pluralizer pluralizer;
	private final Configuration config;
	private final PrimitiveIntegerVerifier asInt;

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
		assert (scope != null): "scope may not be null";
		assert (container != null): "container may not be null";
		assert (sizeName != null): "name may not be null";
		assert (!sizeName.isEmpty()): "name may not be empty";
		assert (pluralizer != null): "pluralizer may not be null";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.container = container;
		this.size = size;
		this.containerName = containerName;
		this.sizeName = sizeName;
		this.pluralizer = pluralizer;
		this.config = config;
		this.asInt = new PrimitiveIntegerVerifierImpl(scope, size, sizeName, config);
	}

	@Override
	public PrimitiveIntegerVerifier isGreaterThanOrEqualTo(Integer value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		if (size >= value)
			return this;
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain at least %,d %s.", sizeName, value, pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isGreaterThanOrEqualTo(Integer value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (size >= value)
			return this;
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) %s.", this.sizeName, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isGreaterThan(Integer value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		if (size > value)
			return this;
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain more than %,d %s.", sizeName, value, pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isGreaterThan(Integer value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (size > value)
			return this;
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) %s.", this.sizeName, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isLessThanOrEqualTo(Integer value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		if (size <= value)
			return this;
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s may not contain more than %,d %s.", sizeName, value,
				pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isLessThanOrEqualTo(Integer value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (size <= value)
			return this;
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s may not contain more than %s (%,d) %s.", this.sizeName, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isLessThan(Integer value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		if (size < value)
			return this;
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain less than %,d %s.", this.sizeName, value,
				pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isLessThan(Integer value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (size < value)
			return this;
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain less than %s (%,d) %s.", this.sizeName, name, value,
				pluralizer.nameOf(value), size, pluralizer.nameOf(size))).
			addContext("Actual", size);
		if (size > 0)
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
		if (size > 0)
			return this;
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain at least one %s.", sizeName, pluralizer.nameOf(1))).
			addContext("Actual", size);
		if (size > 0)
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
		if (size == 0)
			return this;
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be empty.", sizeName)).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isNotNegative()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public PrimitiveIntegerVerifier isNegative()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s cannot have a negative length", sizeName), null);
	}

	@Override
	public PrimitiveIntegerVerifier isBetween(Integer min, Integer max)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(min, "min").isNotNull();
		verifier.requireThat(max, "max").isNotNull().isGreaterThanOrEqualTo(min, "min");
		if (size >= min && size <= max)
			return this;
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain [%d, %d] %s.", sizeName, min, max, pluralizer.nameOf(2))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isEqualTo(Integer value)
	{
		if (Objects.equals(size, value))
			return this;
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain %,d %s.", sizeName, value, pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isEqualTo(Integer value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(size, value))
			return this;
		ExceptionBuilder eb = new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) %s.", this.sizeName, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveIntegerVerifier isNotEqualTo(Integer value)
	{
		if (!Objects.equals(size, value))
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s may not contain %,d %s.", sizeName, value, pluralizer.nameOf(value))).
			addContext("Actual", container).
			build();
	}

	@Override
	public PrimitiveIntegerVerifier isNotEqualTo(Integer value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(size, value))
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s may not contain %s (%,d) %s.", this.sizeName, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", container).
			build();
	}

	@Override
	@Deprecated
	public PrimitiveIntegerVerifier isNull()
	{
		asInt.isNull();
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNotNull()
	{
		asInt.isNotNull();
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isIn(Collection<Integer> collection)
	{
		asInt.isIn(collection);
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isInstanceOf(Class<?> type)
	{
		asInt.isInstanceOf(type);
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(scope, String.valueOf(size), sizeName, config);
	}

	@Override
	public PrimitiveIntegerVerifier asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
		return this;
	}

	@Override
	public Optional<Integer> getActualIfPresent()
	{
		return Optional.of(size);
	}

	@Override
	public Integer getActual()
	{
		return size;
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public PrimitiveIntegerVerifier configuration(Consumer<Configuration> consumer)
	{
		consumer.accept(config);
		return this;
	}
}
