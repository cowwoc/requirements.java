/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.scope;

import com.github.cowwoc.requirements10.java.GlobalConfiguration;
import com.github.cowwoc.requirements10.java.terminal.TerminalEncoding;

import java.util.Set;

public final class TestGlobalConfiguration implements GlobalConfiguration
{
	private final TerminalEncoding terminalEncoding;

	/**
	 * @param terminalEncoding the terminal encoding that the test should use
	 * @throws NullPointerException if {@code terminalEncoding} is null
	 */
	public TestGlobalConfiguration(TerminalEncoding terminalEncoding)
	{
		if (terminalEncoding == null)
			throw new NullPointerException("terminalEncoding may not be null");
		this.terminalEncoding = terminalEncoding;
	}

	@Override
	public Set<TerminalEncoding> supportedTerminalEncodings()
	{
		return Set.of(terminalEncoding);
	}

	@Override
	public TerminalEncoding terminalEncoding()
	{
		return terminalEncoding;
	}

	@Override
	public TestGlobalConfiguration terminalEncoding(TerminalEncoding encoding)
	{
		if (encoding != terminalEncoding)
		{
			throw new UnsupportedOperationException("Test only supports one encoding: " + terminalEncoding + ".\n" +
				"actual: " + encoding);
		}
		return this;
	}
}