/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.scope;

import java.util.function.Supplier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.GlobalConfiguration;
import org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding;
import org.bitbucket.cowwoc.requirements.internal.core.terminal.Terminal;

/**
 * ApplicationScope for the main codebase.
 *
 * @author Gili Tzabari
 */
public final class MainApplicationScope extends AbstractApplicationScope
{
	public static final MainApplicationScope INSTANCE = new MainApplicationScope(
		DefaultJvmScope.INSTANCE);
	public final JvmScope parent;
	public final GlobalConfiguration globalConfiguration;

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
		return () ->
		{
			Configuration result = defaultConfiguration;
			if (!globalConfiguration.isDiffEnabled())
				result = result.withoutDiff();
			return result;
		};
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
	public Supplier<Boolean> isApiInStacktrace()
	{
		return globalConfiguration::isApiInStacktrace;
	}

	@Override
	public void close()
	{
	}
}
