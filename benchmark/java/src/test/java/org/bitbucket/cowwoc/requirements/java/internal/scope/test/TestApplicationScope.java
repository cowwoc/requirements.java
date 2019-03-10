/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope.test;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.scope.AbstractApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.DefaultThreadConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.scope.GlobalConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.scope.MainConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ThreadConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.terminal.Terminal;
import org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding;

import java.util.function.Supplier;

/**
 * ApplicationScope for the test codebase.
 */
public final class TestApplicationScope extends AbstractApplicationScope
{
	private final Configuration defaultConfiguration = new MainConfiguration();
	private final GlobalConfiguration globalConfiguration;
	private final ThreadLocal<ThreadConfiguration> threadConfiguration =
		ThreadLocal.withInitial(DefaultThreadConfiguration::new);

	/**
	 * @param terminalEncoding the type of encoding that verifiers should output
	 * @throws NullPointerException if {@code terminalEncoding} is null
	 */
	public TestApplicationScope(TerminalEncoding terminalEncoding)
	{
		this.globalConfiguration = new TestGlobalConfiguration(terminalEncoding);
	}

	@Override
	public GlobalConfiguration getGlobalConfiguration()
	{
		return globalConfiguration;
	}

	@Override
	public Supplier<ThreadConfiguration> getThreadConfiguration()
	{
		return threadConfiguration::get;
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
	public void close()
	{
	}
}
