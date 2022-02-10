/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.natives.internal.util.scope;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ThreadConfiguration;
import com.github.cowwoc.requirements.java.internal.scope.AbstractApplicationScope;
import com.github.cowwoc.requirements.java.internal.scope.DefaultThreadConfiguration;
import com.github.cowwoc.requirements.java.internal.scope.GlobalConfiguration;
import com.github.cowwoc.requirements.java.internal.scope.MainConfiguration;
import com.github.cowwoc.requirements.natives.terminal.TerminalEncoding;

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

	/**
	 * @param terminalEncoding the type of encoding that verifiers should output
	 * @param terminalWidth    the terminal width that the test should use
	 * @throws NullPointerException     if {@code terminalEncoding} is null
	 * @throws IllegalArgumentException if {@code terminalWidth} is zero or negative
	 */
	public TestApplicationScope(TerminalEncoding terminalEncoding, int terminalWidth)
	{
		this.globalConfiguration = new TestGlobalConfiguration(terminalEncoding, terminalWidth);
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
	public Supplier<Configuration> getDefaultConfiguration()
	{
		return () -> defaultConfiguration;
	}

	@Override
	public void close()
	{
	}
}
