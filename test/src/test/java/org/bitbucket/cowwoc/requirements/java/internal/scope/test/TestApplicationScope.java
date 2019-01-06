/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope.test;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.GlobalConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.scope.AbstractApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.terminal.Terminal;
import org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding;

import java.util.function.Supplier;

/**
 * ApplicationScope for the test codebase.
 */
public final class TestApplicationScope extends AbstractApplicationScope
{
	private static final Supplier<Boolean> TRUE = () -> true;
	private static final Supplier<Boolean> FALSE = () -> false;
	private final TerminalEncoding terminalEncoding;
	private final Configuration defaultConfiguration = new Configuration();

	/**
	 * @param terminalEncoding the type of encoding that verifiers should output
	 * @throws NullPointerException if {@code terminalEncoding} is null
	 */
	public TestApplicationScope(TerminalEncoding terminalEncoding)
	{
		if (terminalEncoding == null)
			throw new NullPointerException("terminalEncoding may not be null");
		this.terminalEncoding = terminalEncoding;
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
	public Supplier<Configuration> getDefaultConfiguration()
	{
		return () -> defaultConfiguration;
	}

	@Override
	public Supplier<TerminalEncoding> getTerminalEncoding()
	{
		return () -> terminalEncoding;
	}

	@Override
	public Supplier<Boolean> isDiffEnabled()
	{
		return TRUE;
	}

	@Override
	public Supplier<Boolean> isLibraryRemovedFromStackTrace()
	{
		return FALSE;
	}

	@Override
	public void close()
	{
	}
}
