/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.GlobalConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.terminal.Terminal;
import org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding;

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
		Configuration defaultConfiguration = new Configuration();
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
	public Supplier<TerminalEncoding> getTerminalEncoding()
	{
		return globalConfiguration::getTerminalEncoding;
	}

	@Override
	public Supplier<Boolean> isDiffEnabled()
	{
		return globalConfiguration::isDiffEnabled;
	}

	@Override
	public Supplier<Boolean> isLibraryRemovedFromStacktrace()
	{
		return globalConfiguration::isLibraryRemovedFromStacktrace;
	}

	@Override
	public void close()
	{
	}
}
