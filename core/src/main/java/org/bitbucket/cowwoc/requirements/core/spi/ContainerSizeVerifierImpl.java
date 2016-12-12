/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.UnifiedVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.util.Exceptions;

/**
 * Default implementation of ContainerSizeVerifier.
 *
 * @author Gili Tzabari
 */
public final class ContainerSizeVerifierImpl implements ContainerSizeVerifier
{
	private final SingletonScope scope;
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
	 * @param scope         the system configuration
	 * @param container     the container
	 * @param size          the size of the container
	 * @param containerName the name of the container
	 * @param sizeName      the name of the container's size
	 * @param pluralizer    returns the singular or plural form of the container's element type
	 * @param config        the instance configuration
	 * @throws AssertionError if {@code scope}, {@code container}, {@code name} or {@code config} are
	 *                        null; if {@code name} is empty
	 */
	public ContainerSizeVerifierImpl(SingletonScope scope, Object container, int size,
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
	public ContainerSizeVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new ContainerSizeVerifierImpl(scope, container, size, containerName, sizeName,
			pluralizer, newConfig);
	}

	@Override
	public ContainerSizeVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new ContainerSizeVerifierImpl(scope, container, size, containerName, sizeName,
			pluralizer, newConfig);
	}

	@Override
	public ContainerSizeVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new ContainerSizeVerifierImpl(scope, container, size, containerName, sizeName,
			pluralizer, newConfig);
	}

	@Override
	public ContainerSizeVerifier isGreaterThanOrEqualTo(Integer value)
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
	public ContainerSizeVerifier isGreaterThanOrEqualTo(Integer value, String name)
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
	public ContainerSizeVerifier isGreaterThan(Integer value)
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
	public ContainerSizeVerifier isGreaterThan(Integer value, String name)
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
	public ContainerSizeVerifier isLessThanOrEqualTo(Integer value)
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
	public ContainerSizeVerifier isLessThanOrEqualTo(Integer value, String name)
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
	public ContainerSizeVerifier isLessThan(Integer value)
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
	public ContainerSizeVerifier isLessThan(Integer value, String name)
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
	public ContainerSizeVerifier isNotPositive()
	{
		return isZero();
	}

	@Override
	public ContainerSizeVerifier isPositive()
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
	public ContainerSizeVerifier isNotZero()
	{
		return isPositive();
	}

	@Override
	public ContainerSizeVerifier isZero()
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
	public ContainerSizeVerifier isNotNegative()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public ContainerSizeVerifier isNegative()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s cannot have a negative length", sizeName), null);
	}

	@Override
	public ContainerSizeVerifier isIn(Integer first, Integer last)
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
	public ContainerSizeVerifier isEqualTo(Integer value)
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
	public ContainerSizeVerifier isEqualTo(Integer value, String name)
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
	public ContainerSizeVerifier isNotEqualTo(Integer value)
	{
		if (!Objects.equals(size, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not contain %,d %s.", sizeName, value, pluralizer.nameOf(value))).
			addContext("Actual", container).
			build();
	}

	@Override
	public ContainerSizeVerifier isNotEqualTo(Integer value, String name)
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
	public ContainerSizeVerifier isNull()
	{
		asInt.isNull();
		return this;
	}

	@Override
	public ContainerSizeVerifier isNotNull()
	{
		asInt.isNotNull();
		return this;
	}

	@Override
	public ContainerSizeVerifier isIn(Collection<Integer> collection)
	{
		asInt.isIn(collection);
		return this;
	}

	@Override
	public ContainerSizeVerifier isInstanceOf(Class<?> type)
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
	public ContainerSizeVerifier isolate(Consumer<ContainerSizeVerifier> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
