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
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of UriRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class UriRequirementsImpl implements UriRequirements
{
	private final URI parameter;
	private final String name;
	private final Configuration config;
	private final ObjectRequirements<URI> asObject;

	/**
	 * Creates new UriRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	UriRequirementsImpl(URI parameter, String name, Configuration config) throws AssertionError
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectRequirementsImpl<>(this.parameter, name, config);
	}

	@Override
	public UriRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new UriRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public UriRequirements addContext(String key, Object value)
		throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		return new UriRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public UriRequirements withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new UriRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public UriRequirements isAbsolute() throws IllegalArgumentException
	{
		if (parameter.isAbsolute())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be absolute.", name)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public UriRequirements isNotNull() throws NullPointerException
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public UriRequirements isNull() throws IllegalArgumentException
	{
		asObject.isNull();
		return this;
	}

	@Override
	public UriRequirements isIn(Collection<URI> collection)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public UriRequirements isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public UriRequirements isNotEqualTo(URI value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public UriRequirements isNotEqualTo(URI value) throws IllegalArgumentException
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public UriRequirements isEqualTo(URI value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public UriRequirements isEqualTo(URI value) throws IllegalArgumentException
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
