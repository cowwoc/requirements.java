/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
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
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	UriRequirementsImpl(URI parameter, String name,
		Configuration config)
	{
		assert (name != null);
		assert (config != null);
		this.parameter = parameter;
		this.name = name + ".length()";
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
	public UriRequirements withContext(Map<String, Object> context)
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be absolute.", name),
			"Actual", parameter);
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
