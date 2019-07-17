/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope;

import org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding;

import java.util.Set;

/**
 * Default global configuration.
 */
public final class MainGlobalConfiguration extends AbstractGlobalConfiguration
{
	private final JvmScope scope;

	/**
	 * @param scope the system configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	public MainGlobalConfiguration(JvmScope scope)
	{
		assert (scope != null);
		this.scope = scope;
	}

	@Override
	public Set<TerminalEncoding> listTerminalEncodings()
	{
		return scope.getTerminal().getSupportedTypes();
	}

	@Override
	public TerminalEncoding getTerminalEncoding()
	{
		return scope.getTerminal().getEncoding();
	}

	@Override
	public GlobalConfiguration withDefaultTerminalEncoding()
	{
		scope.getTerminal().useBestEncoding();
		return this;
	}

	@Override
	public GlobalConfiguration withTerminalEncoding(TerminalEncoding encoding)
	{
		if (encoding == null)
			throw new NullPointerException("encoding may not be null");
		scope.getTerminal().setEncoding(encoding);
		return this;
	}

	@Override
	public String toString()
	{
		return "MainGlobalConfiguration[terminalEncoding=" + getTerminalEncoding() +
			", cleanStackTrace=" + isCleanStackTrace() + ", diffEnabled=" + isDiffEnabled() + "]";
	}
}
