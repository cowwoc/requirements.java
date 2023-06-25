/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.terminal;

import com.github.cowwoc.pouch.core.ConcurrentLazyReference;
import com.github.cowwoc.pouch.core.Reference;
import com.github.cowwoc.requirements.java.terminal.TerminalEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;
import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.RGB_888_COLORS;
import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.XTERM_16_COLORS;
import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.XTERM_256_COLORS;
import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.XTERM_8_COLORS;

/**
 * The terminal associated with the JVM.
 */
public final class Terminal
{
	private final AtomicReference<TerminalEncoding> encoding = new AtomicReference<>();
	private final Reference<Set<TerminalEncoding>> supportedTypes =
		ConcurrentLazyReference.create(this::getSupportedTypesImpl);
	private final Reference<Boolean> connectedToStdout =
		ConcurrentLazyReference.create(this::isConnectedToStdoutImpl);
	private final Logger log = LoggerFactory.getLogger(Terminal.class);

	/**
	 * Creates a new instance.
	 */
	public Terminal()
	{
	}

	/**
	 * @return the ANSI escape codes supported by the terminal
	 */
	public Set<TerminalEncoding> getSupportedTypes()
	{
		return supportedTypes.getValue();
	}

	/**
	 * @return the ANSI escape codes supported by the terminal
	 */
	private Set<TerminalEncoding> getSupportedTypesImpl()
	{
		OperatingSystem os = OperatingSystem.detected();
		return switch (os.type)
		{
			case WINDOWS -> getSupportedTypesForWindows(os);
			case LINUX, MAC -> getSupportedTypesForLinuxOrMac();
		};
	}

	private Set<TerminalEncoding> getSupportedTypesForLinuxOrMac()
	{
		String term = System.getenv("TERM");
		if (term == null)
			return Collections.singleton(NONE);
		// Following the approach set out in http://stackoverflow.com/a/39033815/14731, we don't attempt to
		// support all possible terminal types. Instead, we support mainstream types and require the terminal
		// to support or emulate them.
		Set<TerminalEncoding> result = new HashSet<>((int) Math.ceil(
			TerminalEncoding.values().length / 0.75));
		result.add(NONE);
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
			default -> log.error("Unexpected TERM: " + term);
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

	private Set<TerminalEncoding> getSupportedTypesForWindows(OperatingSystem os)
	{
		log.debug("Detected Windows {}", os.version);
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
		log.debug("setEncodingImpl({}, {})", encoding, force);
		boolean connectedToStdout = isConnectedToStdout();
		if (!connectedToStdout && !force)
		{
			log.debug("stdout was redirected. Falling back to {}", NONE);
			this.encoding.set(NONE);
			return;
		}
		if (!getSupportedTypes().contains(encoding))
		{
			log.debug("User forced the use of an unsupported encoding: {}", encoding);
			this.encoding.set(encoding);
			return;
		}
		this.encoding.set(encoding);
		log.debug("Setting encoding to {}", encoding);
	}

	/**
	 * Indicates that validators should output the best encoding supported by the terminal.
	 *
	 * @see #setEncoding(TerminalEncoding)
	 */
	public void useBestEncoding()
	{
		Set<TerminalEncoding> supportedTypes = getSupportedTypes();
		log.debug("supportedType: {}", supportedTypes);
		List<TerminalEncoding> sortedTypes = new ArrayList<>(supportedTypes);
		sortedTypes.sort(TerminalEncoding.sortByDecreasingRank());
		setEncodingImpl(sortedTypes.getFirst(), false);
	}

	/**
	 * @return the encoding that the terminal should use (defaults to the best available encoding)
	 */
	public TerminalEncoding getEncoding()
	{
		TerminalEncoding result = encoding.get();
		log.debug("encoding is {}", result);
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
	 * @return true if stdout is connected to the terminal
	 */
	public boolean isConnectedToStdout()
	{
		return connectedToStdout.getValue();
	}

	/**
	 * @return true if stdout is connected to the terminal
	 */
	private boolean isConnectedToStdoutImpl()
	{
		// System.console() is not as accurate as the native library in that it returns null when stdin
		// is redirected (which we don't care about). We try using System.console() and if we need more
		// information we send a follow-up query to the native library.
		if (System.console() != null)
		{
			log.debug("System.console() != null");
			return true;
		}
		return false;
	}
}