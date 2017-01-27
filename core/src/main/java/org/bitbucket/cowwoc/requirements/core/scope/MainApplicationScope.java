/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.scope;

import org.bitbucket.cowwoc.pouch.ConcurrentLazyReference;
import org.bitbucket.cowwoc.pouch.Reference;
import org.bitbucket.cowwoc.requirements.core.GlobalConfiguration;
import org.bitbucket.cowwoc.requirements.core.terminal.Terminal;
import org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding;

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
	public final Reference<TerminalEncoding> terminalEncoding;

	/**
	 * @param parent the parent scope
	 * @throws NullPointerException if {@code parent} is null
	 */
	public MainApplicationScope(JvmScope parent)
	{
		if (parent == null)
			throw new NullPointerException("parent may not be null");
		this.parent = parent;
		this.terminalEncoding = ConcurrentLazyReference.create(() ->
			parent.getTerminal().getEncoding());
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
		return terminalEncoding.getValue();
	}

	@Override
	public void close()
	{
	}
}
