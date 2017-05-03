/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.scope;

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
	public final TerminalEncoding terminalEncoding;
	public final boolean diffEnabled;
	public final boolean apiInStacktrace;

	/**
	 * @param parent the parent scope
	 * @throws NullPointerException if {@code parent} is null
	 */
	public MainApplicationScope(JvmScope parent)
	{
		if (parent == null)
			throw new NullPointerException("parent may not be null");
		this.parent = parent;
		GlobalConfiguration globalConfig = parent.getGlobalConfiguration();
		this.terminalEncoding = globalConfig.getTerminalEncoding();
		this.diffEnabled = globalConfig.isDiffEnabled();
		this.apiInStacktrace = globalConfig.isApiInStacktrace();
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
	public TerminalEncoding getTerminalEncoding()
	{
		return terminalEncoding;
	}

	@Override
	public boolean isDiffEnabled()
	{
		return diffEnabled;
	}

	@Override
	public boolean isApiInStacktrace()
	{
		return apiInStacktrace;
	}

	@Override
	public void close()
	{
	}
}
