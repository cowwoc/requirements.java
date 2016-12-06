/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of UriRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class UriRequirementsImpl implements UriRequirements
{
	private final SingletonScope scope;
	private final URI actual;
	private final String name;
	private final Configuration config;
	private final ObjectRequirements<URI> asObject;

	/**
	 * Creates new UriRequirementsImpl.
	 * <p>
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	UriRequirementsImpl(SingletonScope scope, URI actual, String name, Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectRequirementsImpl<>(scope, this.actual, name, config);
	}

	@Override
	public UriRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new UriRequirementsImpl(scope, actual, name, newConfig);
	}

	@Override
	public UriRequirements addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new UriRequirementsImpl(scope, actual, name, newConfig);
	}

	@Override
	public UriRequirements withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new UriRequirementsImpl(scope, actual, name, newConfig);
	}

	@Override
	public UriRequirements isAbsolute()
	{
		if (actual.isAbsolute())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be absolute.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public UriRequirements isNotNull()
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public UriRequirements isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public UriRequirements isIn(Collection<URI> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public UriRequirements isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public UriRequirements isNotEqualTo(URI value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public UriRequirements isNotEqualTo(URI value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public UriRequirements isEqualTo(URI value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public UriRequirements isEqualTo(URI value)
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public UriRequirements isolate(Consumer<UriRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
