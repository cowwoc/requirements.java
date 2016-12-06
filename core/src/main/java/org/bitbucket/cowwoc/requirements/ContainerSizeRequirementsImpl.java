/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import org.bitbucket.cowwoc.requirements.spi.ExceptionBuilder;
import org.bitbucket.cowwoc.requirements.util.Exceptions;

/**
 * Default implementation of ContainerSizeRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class ContainerSizeRequirementsImpl implements ContainerSizeRequirements
{
	private final SingletonScope scope;
	private final Object container;
	private final int size;
	private final String containerName;
	private final String sizeName;
	private final Pluralizer pluralizer;
	private final Configuration config;
	private final PrimitiveIntegerRequirements asInt;

	/**
	 * Creates new StringLengthRequirementsImpl.
	 * <p>
	 * @param scope         the system configuration
	 * @param container     the container
	 * @param size          the size of the container
	 * @param containerName the name of the container
	 * @param sizeName      the name of the container's size
	 * @param pluralizer    returns the singular or plural form of the container's element type
	 * @param config        the instance configuration
	 * @throws AssertionError if {@code scope}, {@code actual}, {@code name} or {@code config} are
	 *                        null; if {@code name} is empty
	 */
	ContainerSizeRequirementsImpl(SingletonScope scope, Object container, int size,
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
		this.asInt = new PrimitiveIntegerRequirementsImpl(scope, size, sizeName, config);
	}

	@Override
	public ContainerSizeRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new ContainerSizeRequirementsImpl(scope, container, size, containerName, sizeName,
			pluralizer, newConfig);
	}

	@Override
	public ContainerSizeRequirements addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new ContainerSizeRequirementsImpl(scope, container, size, containerName, sizeName,
			pluralizer, newConfig);
	}

	@Override
	public ContainerSizeRequirements withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new ContainerSizeRequirementsImpl(scope, container, size, containerName, sizeName,
			pluralizer, newConfig);
	}

	@Override
	public ContainerSizeRequirements isGreaterThanOrEqualTo(Integer value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		if (size >= value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least %,d %s.", sizeName, value, pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public ContainerSizeRequirements isGreaterThanOrEqualTo(Integer value, String name)
	{
		UnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (size >= value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) %s.", this.sizeName, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public ContainerSizeRequirements isGreaterThan(Integer value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		if (size > value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain more than %,d %s.", sizeName, value, pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public ContainerSizeRequirements isGreaterThan(Integer value, String name)
	{
		UnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (size > value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) %s.", this.sizeName, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public ContainerSizeRequirements isLessThanOrEqualTo(Integer value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		if (size <= value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain more than %,d %s.", sizeName, value,
				pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public ContainerSizeRequirements isLessThanOrEqualTo(Integer value, String name)
	{
		UnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (size <= value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain more than %s (%,d) %s.", this.sizeName, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public ContainerSizeRequirements isLessThan(Integer value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		if (size < value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain less than %,d %s.", this.sizeName, value,
				pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public ContainerSizeRequirements isLessThan(Integer value, String name)
	{
		UnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (size < value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain less than %s (%,d) %s.", this.sizeName, name, value,
				pluralizer.nameOf(value), size, pluralizer.nameOf(size))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public ContainerSizeRequirements isNotPositive()
	{
		return isZero();
	}

	@Override
	public ContainerSizeRequirements isPositive()
	{
		if (size > 0)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least one %s.", sizeName, pluralizer.nameOf(1))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public ContainerSizeRequirements isNotZero()
	{
		return isPositive();
	}

	@Override
	public ContainerSizeRequirements isZero()
	{
		if (size == 0)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be empty.", sizeName)).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public ContainerSizeRequirements isNotNegative()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public ContainerSizeRequirements isNegative()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s cannot have a negative length", sizeName), null);
	}

	@Override
	public ContainerSizeRequirements isIn(Integer first, Integer last)
	{
		UnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(first, "first").isNotNull();
		verifier.requireThat(last, "last").isNotNull().isGreaterThanOrEqualTo(first, "first");
		if (size >= first && size <= last)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain [%d, %d] characters.", sizeName, first, last)).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public ContainerSizeRequirements isEqualTo(Integer value)
	{
		if (Objects.equals(size, value))
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain %,d %s.", sizeName, value, pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public ContainerSizeRequirements isEqualTo(Integer value, String name)
	{
		UnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(size, value))
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) %s.", this.sizeName, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", size);
		if (size > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public ContainerSizeRequirements isNotEqualTo(Integer value)
	{
		if (!Objects.equals(size, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not contain %,d %s.", sizeName, value, pluralizer.nameOf(value))).
			addContext("Actual", container).
			build();
	}

	@Override
	public ContainerSizeRequirements isNotEqualTo(Integer value, String name)
	{
		UnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(size, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not contain %s (%,d) %s.", this.sizeName, name, value,
				pluralizer.nameOf(value))).
			addContext("Actual", container).
			build();
	}

	@Override
	@Deprecated
	public ContainerSizeRequirements isNull()
	{
		asInt.isNull();
		return this;
	}

	@Override
	public ContainerSizeRequirements isNotNull()
	{
		asInt.isNotNull();
		return this;
	}

	@Override
	public ContainerSizeRequirements isIn(Collection<Integer> collection)
	{
		asInt.isIn(collection);
		return this;
	}

	@Override
	public ContainerSizeRequirements isInstanceOf(Class<?> type)
	{
		asInt.isInstanceOf(type);
		return this;
	}

	@Override
	public ContainerSizeRequirements isolate(Consumer<ContainerSizeRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
