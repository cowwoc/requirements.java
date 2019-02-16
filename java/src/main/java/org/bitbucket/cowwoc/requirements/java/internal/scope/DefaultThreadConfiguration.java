/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope;

import org.bitbucket.cowwoc.requirements.java.internal.util.Maps;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Configures the behavior of all verifiers invoked by the current thread.
 * <p>
 * This class is immutable.
 */
public final class DefaultThreadConfiguration implements ThreadConfiguration
{
	private final Map<String, Object> context;

	/**
	 * Creates a new configuration with an empty context.
	 */
	public DefaultThreadConfiguration()
	{
		this.context = new HashMap<>();
	}

	/**
	 * Copies a configuration.
	 *
	 * @param context the map to append to the exception message
	 * @throws AssertionError if any of the arguments are null
	 */
	private DefaultThreadConfiguration(Map<String, Object> context)
	{
		assert (context != null) : "context may not be null";
		this.context = Maps.unmodifiable(context);
	}

	@Override
	public Map<String, Object> getContext()
	{
		return Collections.unmodifiableMap(context);
	}

	@Override
	public ThreadConfiguration putContext(String name, Object value)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		context.put(name, value);
		return this;
	}

	@Override
	public ThreadConfiguration removeContext(String name)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		context.remove(name);
		return this;
	}

	@Override
	public String toString()
	{
		return "MainThreadConfiguration[context=" + context + "]";
	}
}
