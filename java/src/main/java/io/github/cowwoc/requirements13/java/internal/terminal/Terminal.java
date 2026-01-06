/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.terminal;

import io.github.cowwoc.pouch.core.ConcurrentLazyReference;
import io.github.cowwoc.pouch.core.Reference;
import io.github.cowwoc.requirements13.java.TerminalEncoding;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static io.github.cowwoc.requirements13.java.TerminalEncoding.NONE;
import static io.github.cowwoc.requirements13.java.TerminalEncoding.RGB_888_COLORS;
import static io.github.cowwoc.requirements13.java.TerminalEncoding.XTERM_16_COLORS;
import static io.github.cowwoc.requirements13.java.TerminalEncoding.XTERM_256_COLORS;
import static io.github.cowwoc.requirements13.java.TerminalEncoding.XTERM_8_COLORS;

/**
 * The terminal that the JVM is outputting to.
 */
public final class Terminal
{
	private final AtomicReference<TerminalEncoding> encoding = new AtomicReference<>();
	private final Reference<Set<TerminalEncoding>> supportedTypes =
		ConcurrentLazyReference.create(this::getSupportedEncodingsImpl);

	/**
	 * Creates a new instance.
	 */
	public Terminal()
	{
	}

	/**
	 * @return the ANSI escape codes supported by the terminal
	 */
	public Set<TerminalEncoding> getSupportedEncodings()
	{
		return supportedTypes.getValue();
	}

	/**
	 * @return the ANSI escape codes supported by the terminal
	 */
	private Set<TerminalEncoding> getSupportedEncodingsImpl()
	{
		return switch (OperatingSystem.detected().type)
		{
			case WINDOWS -> getSupportedEncodingsForWindows();
			case LINUX, MAC -> getSupportedEncodingsForLinuxOrMac();
		};
	}

	private Set<TerminalEncoding> getSupportedEncodingsForLinuxOrMac()
	{
		String term = System.getenv("TERM");
		if (term == null)
			return Set.of(NONE);
		// Following the approach set out in http://stackoverflow.com/a/39033815/14731, we don't attempt to
		// support all possible terminal encodings. Instead, we support mainstream encodings and require the
		// terminal to support or emulate them.
		Set<TerminalEncoding> result = EnumSet.of(NONE);
		switch (term)
		{
			case "term", "xterm" ->
			{
				// Used by older Linux deployments (e.g. routers)
				result.add(XTERM_8_COLORS);
			}
			case "xterm-16color" ->
			{
				// http://stackoverflow.com/a/10039347/14731
				result.add(XTERM_16_COLORS);
				result.add(XTERM_8_COLORS);
			}
			case "xterm-256color" ->
			{
				// Used by Linux and OSX 10.9+
				result.add(XTERM_256_COLORS);
				result.add(XTERM_16_COLORS);
				result.add(XTERM_8_COLORS);
			}
			default -> System.err.println("Unexpected TERM: " + term);
		}
		// There is no reliable way to detect RGB_888_COLORS support but we our best:
		// https://gist.github.com/XVilka/8346728#true-color-detection
		String colorterm = System.getenv("COLORTERM");
		if (colorterm == null)
			return result;
		if (colorterm.equals("truecolor") || colorterm.equals("24bit"))
			result.add(RGB_888_COLORS);
		return result;
	}

	private Set<TerminalEncoding> getSupportedEncodingsForWindows()
	{
		// WT_SESSION indicates that we are running in Windows Terminal.
		if (System.getenv("WT_SESSION") == null)
			return Set.of(NONE);
		return Set.of(NONE, XTERM_8_COLORS, XTERM_16_COLORS, XTERM_256_COLORS, RGB_888_COLORS);
	}

	/**
	 * Indicates the type of encoding that the terminal should use.
	 * <p>
	 * This feature can be used to force the use of ANSI colors even when their support is not detected.
	 *
	 * @param encoding the type of encoding that the terminal should use
	 * @param force    true if the encoding should be forced regardless of what the system supports
	 * @throws NullPointerException if {@code encoding} is null
	 * @see #useBestEncoding()
	 */
	private void setEncodingImpl(TerminalEncoding encoding, boolean force)
	{
		if (encoding == null)
			throw new NullPointerException("encoding may not be null");
		boolean connectedToStdout = isConnectedToStdout();
		if (!connectedToStdout && !force)
		{
			this.encoding.set(NONE);
			return;
		}
		if (!getSupportedEncodings().contains(encoding) && !force)
		{
			this.encoding.set(NONE);
			return;
		}
		this.encoding.set(encoding);
	}

	/**
	 * Indicates that validators should output the best encoding supported by the terminal.
	 *
	 * @see #setEncoding(TerminalEncoding)
	 */
	public void useBestEncoding()
	{
		Set<TerminalEncoding> supportedEncodings = getSupportedEncodings();
		List<TerminalEncoding> sortedEncodings = new ArrayList<>(supportedEncodings);
		sortedEncodings.sort(TerminalEncoding.sortByDecreasingRank());
		setEncodingImpl(sortedEncodings.getFirst(), false);
	}

	/**
	 * @return the encoding that the terminal should use (defaults to the best available encoding)
	 */
	public TerminalEncoding getEncoding()
	{
		TerminalEncoding result = encoding.get();
		if (result == null)
		{
			useBestEncoding();
			result = encoding.get();
		}
		return result;
	}

	/**
	 * Indicates the type of encoding that the terminal should use.
	 * <p>
	 * This feature can be used to force the use of ANSI colors even when their support is not detected.
	 *
	 * @param encoding the type of encoding that the terminal should use
	 * @throws NullPointerException if {@code encoding} is null
	 * @see #useBestEncoding()
	 */
	public void setEncoding(TerminalEncoding encoding)
	{
		setEncodingImpl(encoding, true);
	}

	/**
	 * @return true if stdout is connected to a terminal
	 */
	public boolean isConnectedToStdout()
	{
		// Reminder: System.console() returns null if stdin is redirected
		return System.console() != null;
	}
}