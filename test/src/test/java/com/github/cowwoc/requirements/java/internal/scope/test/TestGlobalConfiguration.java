/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope.test;

import com.github.cowwoc.requirements.java.internal.scope.AbstractGlobalConfiguration;
import com.github.cowwoc.requirements.java.internal.scope.GlobalConfiguration;
import com.github.cowwoc.requirements.natives.terminal.TerminalEncoding;

import java.util.Collections;
import java.util.Set;

public final class TestGlobalConfiguration extends AbstractGlobalConfiguration
{
	private final TerminalEncoding terminalEncoding;
	private final int terminalWidth;

	/**
	 * Equivalent to
	 * {@link #TestGlobalConfiguration(TerminalEncoding, int) TestGlobalConfiguration(terminalEncoding, 80)}.
	 *
	 * @param terminalEncoding the terminal encoding that the test should use
	 * @throws NullPointerException if {@code terminalEncoding} is null
	 */
	public TestGlobalConfiguration(TerminalEncoding terminalEncoding)
	{
		if (terminalEncoding == null)
			throw new NullPointerException("terminalEncoding may not be null");
		this.terminalEncoding = terminalEncoding;
		this.terminalWidth = 80;
	}

	/**
	 * @param terminalEncoding the terminal encoding that the test should use
	 * @param terminalWidth    the terminal width that the test should use
	 * @throws NullPointerException     if {@code terminalEncoding} is null
	 * @throws IllegalArgumentException if {@code terminalWidth} is zero or negative
	 */
	public TestGlobalConfiguration(TerminalEncoding terminalEncoding, int terminalWidth)
	{
		if (terminalEncoding == null)
			throw new NullPointerException("terminalEncoding may not be null");
		if (terminalWidth <= 0)
			throw new IllegalArgumentException("terminalWidth must be positive");
		this.terminalEncoding = terminalEncoding;
		this.terminalWidth = terminalWidth;
	}

	@Override
	public Set<TerminalEncoding> listTerminalEncodings()
	{
		return Collections.singleton(terminalEncoding);
	}

	@Override
	public TerminalEncoding getTerminalEncoding()
	{
		return terminalEncoding;
	}

	@Override
	public TestGlobalConfiguration withDefaultTerminalEncoding()
	{
		return this;
	}

	@Override
	public TestGlobalConfiguration withTerminalEncoding(TerminalEncoding encoding)
	{
		if (encoding != terminalEncoding)
		{
			throw new UnsupportedOperationException("Test only supports one encoding: " + terminalEncoding + ".\n" +
				"Actual: " + encoding);
		}
		return this;
	}

	@Override
	public int getTerminalWidth()
	{
		return terminalWidth;
	}

	@Override
	public GlobalConfiguration withDefaultTerminalWidth()
	{
		return this;
	}

	@Override
	public TestGlobalConfiguration withTerminalWidth(int width)
	{
		if (width != terminalWidth)
		{
			throw new UnsupportedOperationException("Test only supports one width: " + terminalWidth + ".\n" +
				"Actual: " + width);
		}
		return this;
	}
}
