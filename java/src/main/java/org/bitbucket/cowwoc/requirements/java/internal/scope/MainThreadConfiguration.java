/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope;

import org.bitbucket.cowwoc.requirements.java.internal.util.Maps;
import org.bitbucket.cowwoc.requirements.java.internal.util.Objects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Configures the behavior of all verifiers invoked by the current thread.
 * <p>
 * This class is immutable.
 */
public final class MainThreadConfiguration implements ThreadConfiguration
{
	private final Map<String, Object> context;

	/**
	 * Creates a new configuration with an empty context.
	 */
	public MainThreadConfiguration()
	{
		this.context = Collections.emptyMap();
	}

	/**
	 * Copies a configuration.
	 *
	 * @param context the map to append to the exception message
	 * @throws AssertionError if any of the arguments are null
	 */
	private MainThreadConfiguration(Map<String, Object> context)
	{
		assert (context != null) : "context may not be null";
		this.context = Maps.unmodifiable(context);
	}

	@Override
	public ThreadConfiguration putContext(String name, Object value)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		Object oldValue = context.get(name);
		if (Objects.equals(oldValue, value))
			return this;
		Map<String, Object> newContext = new HashMap<>((int) Math.ceil((context.size() + 1) / 0.75));
		newContext.putAll(context);
		newContext.put(name, value);
		return new MainThreadConfiguration(newContext);
	}

	@Override
	public ThreadConfiguration removeContext(String name)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		if (!context.containsKey(name))
			return this;
		Map<String, Object> newContext = new HashMap<>((int) Math.ceil((context.size() - 1) / 0.75));
		for (Entry<String, Object> entry : context.entrySet())
			if (!entry.getKey().equals(name))
				newContext.put(entry.getKey(), entry.getValue());
		return new MainThreadConfiguration(newContext);
	}

	@Override
	public GlobalConfiguration getGlobalConfiguration()
	{
		return DefaultJvmScope.INSTANCE.getGlobalConfiguration();
	}

	@Override
	public String toString()
	{
		return "MainThreadConfiguration[context=" + context + "]";
	}
}
