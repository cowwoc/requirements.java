/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.terminal.Terminal;

import java.util.function.Supplier;

/**
 * ApplicationScope for the main codebase.
 */
public final class MainApplicationScope extends AbstractApplicationScope
{
	public static final MainApplicationScope INSTANCE = new MainApplicationScope(DefaultJvmScope.INSTANCE);
	public final JvmScope parent;
	public final GlobalConfiguration globalConfiguration;
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
		Configuration defaultConfiguration = new MainConfiguration();
		this.defaultConfigurationSupplier = () ->
		{
			Configuration result = defaultConfiguration;
			if (!globalConfiguration.isDiffEnabled())
				result = result.withoutDiff();
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
	public Terminal getTerminal()
	{
		return parent.getTerminal();
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
