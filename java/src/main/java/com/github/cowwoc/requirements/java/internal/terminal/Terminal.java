/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.terminal;

import com.github.cowwoc.pouch.core.ConcurrentLazyReference;
import com.github.cowwoc.pouch.core.Reference;
import com.github.cowwoc.requirements.natives.internal.terminal.NativeTerminal;
import com.github.cowwoc.requirements.natives.internal.util.OperatingSystem;
import com.github.cowwoc.requirements.natives.internal.util.OperatingSystem.Version;
import com.github.cowwoc.requirements.natives.terminal.TerminalEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.cowwoc.requirements.natives.internal.util.OperatingSystem.Type.WINDOWS;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.RGB_888_COLORS;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.XTERM_16_COLORS;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.XTERM_256_COLORS;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.XTERM_8_COLORS;

/**
 * The terminal associated with the JVM.
 */
public final class Terminal
{
	private final Reference<Set<TerminalEncoding>> supportedTypes =
		ConcurrentLazyReference.create(this::getSupportedTypesImpl);
	private final Reference<Boolean> connectedToStdout =
		ConcurrentLazyReference.create(this::isConnectedToStdoutImpl);
	private final AtomicReference<TerminalEncoding> encoding = new AtomicReference<>();
	private final AtomicReference<Integer> width = new AtomicReference<>();
	private final Optional<NativeTerminal> nativeTerminal;
	private final Logger log = LoggerFactory.getLogger(Terminal.class);

	/**
	 * @param nativeTerminal the native terminal (null if unavailable)
	 */
	public Terminal(NativeTerminal nativeTerminal)
	{
		this.nativeTerminal = Optional.ofNullable(nativeTerminal);
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
		switch (os.type)
		{
			case WINDOWS ->
			{
				log.debug("Detected Windows {}", os.version);
				if (os.version.compareTo(new Version(10, 0, 10586)) >= 0)
				{
					// Windows 10.0.10586 added 16-bit color support:
					// http://www.nivot.org/blog/post/2016/02/04/Windows-10-TH2-%28v1511%29-Console-Host-Enhancements
					Set<TerminalEncoding> result = new HashSet<>((int) Math.ceil(4 / 0.75));
					result.add(NONE);
					result.add(XTERM_8_COLORS);
					result.add(XTERM_16_COLORS);
					if (os.version.compareTo(new Version(10, 0, 14931)) >= 0)
					{
						// build 14931 added 24-bit color support:
						// https://blogs.msdn.microsoft.com/commandline/2016/09/22/24-bit-color-in-the-windows-console/
						result.add(RGB_888_COLORS);
					}
					log.debug("Returning {}", result);
					return result;
				}
				return Collections.singleton(NONE);
			}
			case LINUX, MAC ->
			{
				String term = System.getenv("TERM");
				if (term == null)
					return Collections.singleton(NONE);
				// Following the approach set out in http://stackoverflow.com/a/39033815/14731, we don't attempt to
				// support all possible terminal types. Instead, we support mainstream types and require the terminal
				// to support or emulate them.
				Set<TerminalEncoding> result = new HashSet<>((int) Math.ceil(TerminalEncoding.values().length / 0.75));
				result.add(NONE);
				switch (term)
				{
					case "xterm":
					{
						// Used by older Linux deployments (e.g. routers)
						result.add(XTERM_8_COLORS);
						break;
					}
					case "xterm-16color":
					{
						// http://stackoverflow.com/a/10039347/14731
						result.add(XTERM_16_COLORS);
						result.add(XTERM_8_COLORS);
						break;
					}
					case "xterm-256color":
					{
						// Used by Linux and OSX 10.9+
						result.add(XTERM_256_COLORS);
						result.add(XTERM_16_COLORS);
						result.add(XTERM_8_COLORS);
						break;
					}
					default:
					{
						log.error("Unexpected TERM: " + term);
						break;
					}
				}
				// There is no reliable way to detect RGB_888_COLORS support but we our best:
				// https://gist.github.com/XVilka/8346728#true-color-detection
				String colorterm = System.getenv("COLORTERM");
				if (colorterm != null)
				{
					switch (colorterm)
					{
						case "truecolor":
						case "24bit":
						{
							result.add(RGB_888_COLORS);
							break;
						}
					}
				}
				return result;
			}
			default ->
				throw new IllegalArgumentException("Unsupported OS type: " + os.type);
		}
	}

	/**
	 * Indicates the type of encoding that the terminal should use.
	 * <p>
	 * This feature can be used to force the use of ANSI colors even when their support is not
	 * detected.
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
	 * Indicates the type of encoding that the terminal should use.
	 * <p>
	 * This feature can be used to force the use of ANSI colors even when their support is not
	 * detected.
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
		OperatingSystem os = OperatingSystem.detected();
		if (os.type == WINDOWS)
		{
			// Only Windows needs nativeSetEncoding() to be invoked
			if (nativeSetEncoding(encoding, force) || force)
			{
				log.debug("Setting encoding to {}", encoding);
				this.encoding.set(encoding);
			}
			else
			{
				log.debug("nativeSetEncoding() failed. Falling back to {}", NONE);
				this.encoding.set(NONE);
			}
		}
		this.encoding.set(encoding);
		log.debug("Setting encoding to {} without native interaction", encoding);
	}

	/**
	 * Indicates the type of encoding that the terminal should use.
	 *
	 * @param encoding the type of encoding that the terminal should use (null if the best available encoding
	 *                 should be used)
	 * @param force    true if the encoding should be forced regardless of what the system supports
	 * @return true on success
	 */
	private boolean nativeSetEncoding(TerminalEncoding encoding, boolean force)
	{
		return nativeTerminal.map(terminal ->
		{
			try
			{
				terminal.setEncoding(encoding);
				return true;
			}
			catch (IOException e)
			{
				if (force)
					log.debug("", e);
				else
					log.warn("", e);
				return false;
			}
		}).orElse(false);
	}

	/**
	 * Indicates that verifiers should output the best encoding supported by the terminal.
	 *
	 * @see #setEncoding(TerminalEncoding)
	 */
	public void useBestEncoding()
	{
		Set<TerminalEncoding> supportedTypes = getSupportedTypes();
		log.debug("supportedType: {}", supportedTypes);
		List<TerminalEncoding> sortedTypes = new ArrayList<>(supportedTypes);
		sortedTypes.sort(TerminalEncoding.sortByDecreasingRank());
		setEncodingImpl(sortedTypes.get(0), false);
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
		return nativeTerminal.map(terminal ->
		{
			try
			{
				boolean result = terminal.isConnectedToStdout();
				log.debug("Returning {}", result);
				return result;
			}
			catch (IOException e)
			{
				log.error("", e);
				return false;
			}
		}).orElseGet(() ->
		{
			log.debug("NativeTerminal is not available");
			return false;
		});
	}

	/**
	 * Indicates the width that the terminal should use.
	 * <p>
	 * This feature can be used to override the default terminal width when it cannot be auto-detected.
	 *
	 * @param width the width that the terminal should use
	 * @throws IllegalArgumentException if {@code width} is zero or negative
	 * @see #useBestWidth()
	 */
	public void setWidth(int width)
	{
		if (width <= 0)
			throw new IllegalArgumentException("width must be positive");
		this.width.set(width);
	}

	/**
	 * Returns the terminal width.
	 *
	 * @return the terminal width in characters
	 */
	private int nativeGetWidth()
	{
		return nativeTerminal.map(terminal ->
		{
			if (isConnectedToStdout())
			{
				try
				{
					return terminal.getWidth();
				}
				catch (IOException e)
				{
					log.warn("", e);
				}
			}
			return 0;
		}).orElse(0);
	}

	/**
	 * Indicates that verifiers should use the best width supported by the terminal. If the width cannot be
	 * auto-detected, a value of {@code 80} is used.
	 *
	 * @see #setWidth(int)
	 */
	public void useBestWidth()
	{
		int detectedWidth = nativeGetWidth();
		log.debug("detectedWidth: {}", detectedWidth);
		if (detectedWidth == 0)
			detectedWidth = 80;
		setWidth(detectedWidth);
	}

	/**
	 * @return the width of the terminal in characters (defaults to the auto-detected width)
	 */
	public int getWidth()
	{
		Integer result = width.get();
		log.debug("encoding is {}", result);
		if (result == null)
		{
			useBestWidth();
			result = width.get();
		}
		return result;
	}
}
