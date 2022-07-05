/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ThreadConfiguration;
import com.github.cowwoc.requirements.java.internal.util.Exceptions;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Configures the behavior of all verifiers invoked by the current thread.
 */
public final class DefaultThreadConfiguration implements ThreadConfiguration
{
	private final Map<String, Object> context;
	private final Exceptions exceptions;
	private final Configuration validatorConfiguration;

	/**
	 * Creates a new configuration.
	 *
	 * @param scope the application configuration
	 * @throws NullPointerException if {@code scope} is null
	 */
	public DefaultThreadConfiguration(ApplicationScope scope)
	{
		this.context = new LinkedHashMap<>();
		this.exceptions = scope.getExceptions();
		this.validatorConfiguration = scope.getDefaultConfiguration().get();
	}

	@Override
	public Map<String, Object> getContext()
	{
		return Collections.unmodifiableMap(context);
	}

	@Override
	public ThreadConfiguration withContext(String name, Object value)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		context.put(name, value);
		return this;
	}

	@Override
	public ThreadConfiguration withoutContext(String name)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		context.remove(name);
		return this;
	}

	@Override
	public ThreadConfiguration withoutAnyContext()
	{
		context.clear();
		return this;
	}

	@Override
	public String getContextMessage(String message)
	{
		return exceptions.getContextMessage(context, validatorConfiguration, message, List.of());
	}

	@Override
	public String toString()
	{
		return "DefaultThreadConfiguration[context=" + context + "]";
	}
}