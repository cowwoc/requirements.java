/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.scope;

import com.github.cowwoc.requirements10.java.GlobalConfiguration;
import com.github.cowwoc.requirements10.java.internal.terminal.Terminal;
import com.github.cowwoc.requirements10.java.terminal.TerminalEncoding;

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
		assert (terminal != null);
		this.terminal = terminal;
	}

	@Override
	public Set<TerminalEncoding> supportedTerminalEncodings()
	{
		return terminal.getSupportedTypes();
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