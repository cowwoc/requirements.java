/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope;

import org.bitbucket.cowwoc.requirements.java.internal.terminal.Terminal;
import org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding;

import java.util.Set;

/**
 * Default global configuration.
 */
public final class MainGlobalConfiguration extends AbstractGlobalConfiguration
{
	private final Terminal terminal;

	/**
	 * @param terminal the system configuration
	 * @throws AssertionError if {@code terminal} is null
	 */
	public MainGlobalConfiguration(Terminal terminal)
	{
		assert (terminal != null);
		this.terminal = terminal;
	}

	@Override
	public Set<TerminalEncoding> listTerminalEncodings()
	{
		return terminal.getSupportedTypes();
	}

	@Override
	public TerminalEncoding getTerminalEncoding()
	{
		return terminal.getEncoding();
	}

	@Override
	public GlobalConfiguration withDefaultTerminalEncoding()
	{
		terminal.useBestEncoding();
		return this;
	}

	@Override
	public GlobalConfiguration withTerminalEncoding(TerminalEncoding encoding)
	{
		if (encoding == null)
			throw new NullPointerException("encoding may not be null");
		terminal.setEncoding(encoding);
		return this;
	}

	@Override
	public String toString()
	{
		return "MainGlobalConfiguration[terminalEncoding=" + getTerminalEncoding() +
			", cleanStackTrace=" + isCleanStackTrace() + ", diffEnabled=" + isDiffEnabled() + "]";
	}
}
