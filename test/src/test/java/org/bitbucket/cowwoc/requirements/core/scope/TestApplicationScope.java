/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.scope;

import org.bitbucket.cowwoc.pouch.ConcurrentLazyReference;
import org.bitbucket.cowwoc.pouch.ConstantReference;
import org.bitbucket.cowwoc.pouch.Reference;
import org.bitbucket.cowwoc.requirements.core.GlobalConfiguration;
import org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding;
import org.bitbucket.cowwoc.requirements.internal.core.scope.AbstractApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.scope.JvmScope;
import org.bitbucket.cowwoc.requirements.internal.core.terminal.Terminal;

/**
 * SingletonScope for the test codebase.
 *
 * @author Gili Tzabari
 */
public final class TestApplicationScope extends AbstractApplicationScope
{
	private final Reference<TerminalEncoding> terminalEncoding;

	/**
	 * Creates a TestApplicationScope that uses the best available terminal encoding.
	 *
	 * @param parent the parent scope
	 */
	public TestApplicationScope(JvmScope parent)
	{
		this.terminalEncoding = ConcurrentLazyReference.create(() ->
			parent.getTerminal().getEncoding());
	}

	/**
	 * @param terminalEncoding the type of encoding that verifiers should output
	 * @throws NullPointerException if {@code terminalEncoding} is null
	 */
	public TestApplicationScope(TerminalEncoding terminalEncoding)
	{
		this.terminalEncoding = new ConstantReference<>(terminalEncoding);
	}

	@Override
	public GlobalConfiguration getGlobalConfiguration()
	{
		throw new UnsupportedOperationException("Unit tests don't have access to JvmScope (and " +
			"by extension GlobalConfiguration) because they need to run in isolation of each other");
	}

	@Override
	public Terminal getTerminal()
	{
		throw new UnsupportedOperationException("Unit tests don't have access to JvmScope (and " +
			"by extension Terminal) because they need to run in isolation of each other");
	}

	@Override
	public TerminalEncoding getTerminalEncoding()
	{
		return terminalEncoding.getValue();
	}

	@Override
	public boolean isDiffEnabled()
	{
		return true;
	}

	@Override
	public boolean isApiInStacktrace()
	{
		return true;
	}

	@Override
	public void close()
	{
	}
}
