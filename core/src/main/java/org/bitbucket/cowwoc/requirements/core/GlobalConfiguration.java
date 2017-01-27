/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.Set;
import org.bitbucket.cowwoc.requirements.core.scope.JvmScope;
import org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding;

/**
 * The configuration shared by all verifiers.
 * <p>
 * This class is thread-safe.
 *
 * @author Gili Tzababri
 */
public final class GlobalConfiguration
{
	private final JvmScope scope;
	/**
	 * True if exceptions should contain a diff of the values being compared.
	 */
	private boolean diffEnabled = true;

	/**
	 * @param scope the system configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	public GlobalConfiguration(JvmScope scope)
	{
		assert (scope != null);
		this.scope = scope;
	}

	/**
	 * Indicates the terminal types that are available to the JVM.
	 *
	 * @return the list of available terminal types that may be used
	 * @see #withTerminalEncoding(TerminalEncoding)
	 * @see #withDefaultTerminalEncoding()
	 */
	public Set<TerminalEncoding> getAvailableTerminalEncodings()
	{
		return scope.getTerminal().getSupportedTypes();
	}

	/**
	 * @return the current terminal encoding (defaults to the auto-detected encoding)
	 */
	public TerminalEncoding getTerminalEncoding()
	{
		return scope.getTerminal().getEncoding();
	}

	/**
	 * Indicates the type of encoding that verifiers will output.
	 * <p>
	 * This feature can be used to force the use of ANSI colors even when their support is not
	 * detected.
	 *
	 * @param encoding the type of encoding that verifiers will output
	 * @return this
	 * @throws NullPointerException if {@code encoding} is null
	 * @see #withDefaultTerminalEncoding()
	 */
	public GlobalConfiguration withTerminalEncoding(TerminalEncoding encoding)
	{
		if (encoding == null)
			throw new NullPointerException("encoding may not be null");
		scope.getTerminal().setEncoding(encoding);
		return this;
	}

	/**
	 * Indicates that the terminal encoding should be auto-detected.
	 *
	 * @return this
	 * @see #withTerminalEncoding(TerminalEncoding)
	 */
	public GlobalConfiguration withDefaultTerminalEncoding()
	{
		scope.getTerminal().useBestEncoding();
		return this;
	}

	/**
	 * @return true if exceptions should show the difference between the actual and expected values
	 *         (true by default)
	 * @see #setDiffEnabled(boolean)
	 */
	public boolean isDiffEnabled()
	{
		return diffEnabled;
	}

	/**
	 * Indicates that exceptions should show the difference between the actual and expected values.
	 *
	 * @return this
	 */
	public GlobalConfiguration withDiff()
	{
		this.diffEnabled = true;
		return this;
	}

	/**
	 * Indicates that exceptions should not show the difference between the actual and expected
	 * values.
	 *
	 * @return this
	 */
	public GlobalConfiguration withoutDiff()
	{
		this.diffEnabled = false;
		return this;
	}
}
