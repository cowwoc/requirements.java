/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ThreadConfiguration;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * ApplicationScope for the main codebase.
 */
public final class MainApplicationScope extends AbstractApplicationScope
{
	/**
	 * The singleton instance.
	 */
	public static final MainApplicationScope INSTANCE = new MainApplicationScope(DefaultJvmScope.INSTANCE);
	/**
	 * The parent scope.
	 */
	public final JvmScope parent;
	/**
	 * The global configuration.
	 */
	public final GlobalConfiguration globalConfiguration;
	/**
	 * The default configuration.
	 */
	public final Supplier<Configuration> defaultConfigurationSupplier;

	/**
	 * @param parent the parent scope
	 * @throws NullPointerException if {@code parent} is null
	 */
	public MainApplicationScope(JvmScope parent)
	{
		if (parent == null)
			throw new NullPointerException("parent may not be null");
		this.parent = parent;
		this.globalConfiguration = parent.getGlobalConfiguration();

		// Try to reuse the previously-returned configuration object
		AtomicReference<Configuration> defaultConfiguration = new AtomicReference<>(new MainConfiguration());
		this.defaultConfigurationSupplier = () ->
		{
			Configuration result = defaultConfiguration.get();
			if (globalConfiguration.isCleanStackTrace())
				result = result.withCleanStackTrace();
			else
				result = result.withoutCleanStackTrace();
			if (globalConfiguration.isDiffEnabled())
				result = result.withDiff();
			else
				result = result.withoutDiff();
			defaultConfiguration.setOpaque(result);
			return result;
		};
	}

	@Override
	public GlobalConfiguration getGlobalConfiguration()
	{
		return parent.getGlobalConfiguration();
	}

	@Override
	public Supplier<ThreadConfiguration> getThreadConfiguration()
	{
		return parent.getThreadConfiguration();
	}

	@Override
	public Supplier<Configuration> getDefaultConfiguration()
	{
		return defaultConfigurationSupplier;
	}

	@Override
	public void close()
	{
	}
}
