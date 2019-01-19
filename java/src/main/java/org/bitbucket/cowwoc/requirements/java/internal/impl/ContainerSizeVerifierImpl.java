/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.java.JavaVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.secrets.SecretConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.secrets.SharedSecrets;
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
	private final SecretConfiguration secretConfiguration = SharedSecrets.INSTANCE.secretConfiguration;
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
	 *                        null. If {@code name} is empty.
	 */
	public ContainerSizeVerifierImpl(ApplicationScope scope, String containerName, Object container,
	                                 String sizeName, int size, Pluralizer pluralizer, Configuration config)
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
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		if (actual >= value)
			return getThis();
		String valueAsString = secretConfiguration.toString(config, value);
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			containerName + " must contain at least " + valueAsString + " " + pluralizer.nameOf(value) + ".").
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isGreaterThanOrEqualTo(Integer value, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		verifier.requireThat(value, "value").isNotNull();
		if (actual >= value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			containerName + " must contain at least " + name + " " + pluralizer.nameOf(value) + ".").
			addContext("Actual", actual).
			addContext("Minimum", value);
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
		String valueAsString = secretConfiguration.toString(config, value);
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			containerName + " must contain more than " + valueAsString + " " + pluralizer.nameOf(value)).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isGreaterThan(Integer value, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		verifier.requireThat(value, "value").isNotNull();
		if (actual > value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			containerName + " must contain more than " + name + " " + pluralizer.nameOf(value) + ".").
			addContext("Actual", actual).
			addContext("Exclusive minimum", value);
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
		String valueAsString = secretConfiguration.toString(config, value);
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			containerName + " may not contain more than " + valueAsString + " " + pluralizer.nameOf(value) + ".").
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isLessThanOrEqualTo(Integer value, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		verifier.requireThat(value, "value").isNotNull();
		if (actual <= value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			containerName + " may not contain more than " + name + " " + pluralizer.nameOf(value) + ".").
			addContext("Actual", actual).
			addContext("Maximum", value);
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
		String valueAsString = secretConfiguration.toString(config, value);
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			containerName + " must contain less than " + valueAsString + " " + pluralizer.nameOf(value) + ".").
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isLessThan(Integer value, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		verifier.requireThat(value, "value").isNotNull();
		if (actual < value)
			return getThis();
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			containerName + " must contain less than " + name + " " + pluralizer.nameOf(value) + ".").
			addContext("Actual", actual).
			addContext("Exclusive maximum", value);
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
			containerName + " must contain at least one " + pluralizer.nameOf(1) + ".").
			addContext("Actual", actual);
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
			containerName + " must be empty.").
			addContext("Actual", container);
		throw eb.build();
	}

	@Deprecated
	@Override
	public PrimitiveNumberVerifier<Integer> isNotNegative()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " can never be negative", null).
			build();
	}

	@Deprecated
	@Override
	public PrimitiveNumberVerifier<Integer> isNegative()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " can never be negative", null).
			build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isBetween(Integer startInclusive, Integer endExclusive)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(startInclusive, "startInclusive").isNotNull();
		verifier.requireThat(endExclusive, "endExclusive").isNotNull().
			isGreaterThanOrEqualTo(startInclusive, "startInclusive");
		if (actual >= startInclusive && actual < endExclusive)
			return getThis();
		String startAsString = secretConfiguration.toString(config, startInclusive);
		String endAsString = secretConfiguration.toString(config, endExclusive);
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			containerName + " must contain [" + startAsString + ", " + endAsString + ") " + pluralizer.nameOf(2) +
				".").
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isBetweenClosed(Integer startInclusive, Integer endInclusive)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(startInclusive, "startInclusive").isNotNull();
		verifier.requireThat(endInclusive, "endInclusive").isNotNull().
			isGreaterThanOrEqualTo(startInclusive, "startInclusive");
		if (actual >= startInclusive && actual <= endInclusive)
			return getThis();
		String startAsString = secretConfiguration.toString(config, startInclusive);
		String endAsString = secretConfiguration.toString(config, endInclusive);
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			containerName + " must contain [" + startAsString + ", " + endAsString + "] " + pluralizer.nameOf(2) +
				".").
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isEqualTo(Object expected)
	{
		scope.getInternalVerifier().requireThat(expected, "expected").isInstanceOf(Integer.class);
		if (!(expected instanceof Integer))
			return super.isEqualTo(expected);
		if (Objects.equals(actual, expected))
			return getThis();
		int expectedAsInt = (Integer) expected;
		String expectedAsString = secretConfiguration.toString(config, expected);
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			containerName + " must contain " + expectedAsString + " " + pluralizer.nameOf(expectedAsInt) + ".").
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isEqualTo(Object expected, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		verifier.requireThat(expected, "expected").isInstanceOf(Integer.class);
		if (Objects.equals(actual, expected))
			return getThis();
		int expectedAsInt = (Integer) expected;
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			containerName + " must contain " + name + " " + pluralizer.nameOf(expectedAsInt) + ".").
			addContext("Actual", actual).
			addContext("Expected", expected);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isNotEqualTo(Object value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isInstanceOf(Integer.class);
		if (!(value instanceof Integer))
		{
			String valueAsString = secretConfiguration.toString(config, value);
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
				name + " can never be equal to " + valueAsString, null).
				build();
		}
		if (!Objects.equals(actual, value))
			return getThis();
		int valueAsInt = (Integer) value;
		String valueAsString = secretConfiguration.toString(config, value);
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			containerName + " may not contain " + valueAsString + " " + pluralizer.nameOf(valueAsInt) + ".").
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isNotEqualTo(Object value, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		verifier.requireThat(value, "value").isInstanceOf(Integer.class);
		if (!(value instanceof Integer))
		{
			String valueAsString = secretConfiguration.toString(config, value);
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
				name + " can never be equal to " + valueAsString, null).
				build();
		}
		if (!Objects.equals(actual, value))
			return getThis();
		int valueAsInt = (Integer) value;
		ExceptionBuilder eb = new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			containerName + " may not contain " + name + " " + pluralizer.nameOf(valueAsInt) + ".").
			addContext("Actual", actual).
			addContext("Unwanted", value);
		if (actual > 0)
			eb.addContext(containerName, container);
		throw eb.build();
	}

	@Override
	@Deprecated
	public PrimitiveNumberVerifier<Integer> isNull()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " can never be null", null).
			build();
	}
}
