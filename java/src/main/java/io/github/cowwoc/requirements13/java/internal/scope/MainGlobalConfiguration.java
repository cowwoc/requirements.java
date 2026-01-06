/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.scope;

import io.github.cowwoc.requirements13.java.GlobalConfiguration;
import io.github.cowwoc.requirements13.java.TerminalEncoding;
import io.github.cowwoc.requirements13.java.internal.terminal.Terminal;

import java.util.Set;

/**
 * Default global configuration.
 */
public final class MainGlobalConfiguration implements GlobalConfiguration
{
	private final Terminal terminal;

	/**
	 * @param terminal the system configuration
	 * @throws AssertionError if {@code terminal} is null
	 */
	public MainGlobalConfiguration(Terminal terminal)
	{
		assert terminal != null;
		this.terminal = terminal;
	}

	@Override
	public Set<TerminalEncoding> supportedTerminalEncodings()
	{
		return terminal.getSupportedEncodings();
	}

	@Override
	public TerminalEncoding terminalEncoding()
	{
		return terminal.getEncoding();
	}

	@Override
	public GlobalConfiguration terminalEncoding(TerminalEncoding encoding)
	{
		if (encoding == null)
			throw new NullPointerException("encoding may not be null");
		terminal.setEncoding(encoding);
		return this;
	}

	@Override
	public String toString()
	{
		return "MainGlobalConfiguration[supportedTerminalEncodings=" + supportedTerminalEncodings() +
			", terminalEncoding=" + terminalEncoding() + "]";
	}
}