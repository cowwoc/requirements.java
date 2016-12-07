/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.usage;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import org.bitbucket.cowwoc.requirements.ComparableVerifier;

/**
 * Normally we shouldn't implement SPI interfaces directly, but since there is only one
 * implementation of this verifier (we don't support assertions) it is okay.
 *
 * @author Gili Tzabari
 */
public final class DurationRequirementsImpl implements DurationRequirements
{
	private final SingletonScope scope;
	private final Duration actual;
	private final String name;
	private final Configuration config;
	private final ComparableVerifier<Duration> asComparable;

	/**
	 * Creates new DurationRequirements.
	 * <p>
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config determines how to handle a requirements violation
	 * @throws NullPointerException     if {@code scope}, {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	DurationRequirementsImpl(SingletonScope scope, Duration actual, String name,
		Configuration config)
	{
		assert (scope != null): "scope may not be null";
		assert (name != null): "name may not be null";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asComparable = scope.getInternalVerifier().requireThat(actual, name).
			withContext(config.getContext()).
			withException(config.getException());
	}

	@Override
	public DurationRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new DurationRequirementsImpl(scope, actual, name, newConfig);
	}

	@Override
	public DurationRequirements addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new DurationRequirementsImpl(scope, actual, name, newConfig);
	}

	@Override
	public DurationRequirements withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new DurationRequirementsImpl(scope, actual, name, newConfig);
	}

	@Override
	public DurationRequirements isPositive()
	{
		if (actual.getNano() > 0 || actual.getSeconds() > 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be positive.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public DurationRequirements isGreaterThan(Duration value, String name)
	{
		asComparable.isGreaterThan(value, name);
		return this;
	}

	@Override
	public DurationRequirements isGreaterThan(Duration value)
	{
		asComparable.isGreaterThan(value);
		return this;
	}

	@Override
	public DurationRequirements isGreaterThanOrEqualTo(Duration value, String name)
	{
		asComparable.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public DurationRequirements isGreaterThanOrEqualTo(Duration value)
	{
		asComparable.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public DurationRequirements isLessThan(Duration value, String name)
	{
		asComparable.isLessThan(value, name);
		return this;
	}

	@Override
	public DurationRequirements isLessThan(Duration value)
	{
		asComparable.isLessThan(value);
		return this;
	}

	@Override
	public DurationRequirements isLessThanOrEqualTo(Duration value, String name)
	{
		asComparable.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public DurationRequirements isLessThanOrEqualTo(Duration value)
	{
		asComparable.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public DurationRequirements isIn(Duration first, Duration last)
	{
		asComparable.isIn(first, last);
		return this;
	}

	@Override
	public DurationRequirements isEqualTo(Duration value)
	{
		asComparable.isEqualTo(value);
		return this;
	}

	@Override
	public DurationRequirements isEqualTo(Duration value, String name)
	{
		asComparable.isEqualTo(value, name);
		return this;
	}

	@Override
	public DurationRequirements isNotEqualTo(Duration value)
	{
		asComparable.isNotEqualTo(value);
		return this;
	}

	@Override
	public DurationRequirements isNotEqualTo(Duration value, String name)
	{
		asComparable.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public DurationRequirements isIn(Collection<Duration> collection)
	{
		asComparable.isIn(collection);
		return this;
	}

	@Override
	public DurationRequirements isInstanceOf(Class<?> type)
	{
		asComparable.isInstanceOf(type);
		return this;
	}

	@Override
	public DurationRequirements isNull()
	{
		asComparable.isNull();
		return this;
	}

	@Override
	public DurationRequirements isNotNull()
	{
		asComparable.isNotNull();
		return this;
	}

	@Override
	public DurationRequirements isolate(Consumer<DurationRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
