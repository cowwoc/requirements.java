/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.terminal;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import org.bitbucket.cowwoc.pouch.ConcurrentLazyReference;
import org.bitbucket.cowwoc.pouch.Reference;
import static org.bitbucket.cowwoc.requirements.internal.core.terminal.OperatingSystem.Type.WINDOWS;
import org.bitbucket.cowwoc.requirements.internal.core.terminal.OperatingSystem.Version;
import org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.RGB_888COLOR;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.XTERM_16COLOR;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.XTERM_256COLOR;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.XTERM_8COLOR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The terminal associated with the JVM.
 *
 * @author Gili Tzabari
 */
public final class Terminal
{
	private final Reference<Set<TerminalEncoding>> supportedTypes = ConcurrentLazyReference.create(
		this::getSupportedTypesImpl);
	private final Reference<Boolean> connectedToStdout = ConcurrentLazyReference.create(
		this::isConnectedToStdoutImpl);
	private final AtomicReference<TerminalEncoding> encoding = new AtomicReference<>();
	private final Optional<NativeTerminal> nativeTerminal;
	private final Logger log = LoggerFactory.getLogger(Terminal.class);

	/**
	 * Creates a new Terminal.
	 *
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
		OperatingSystem os = OperatingSystem.detect();
		if (os.type == WINDOWS)
		{
			if (os.version.compareTo(new Version(10, 0, 10586)) >= 0)
			{
				// Windows 10.0.10586 added 16-bit color support:
				// http://www.nivot.org/blog/post/2016/02/04/Windows-10-TH2-%28v1511%29-Console-Host-Enhancements
				Set<TerminalEncoding> result = new HashSet<>(3);
				result.add(NONE);
				result.add(XTERM_16COLOR);
				if (os.version.compareTo(new Version(10, 0, 14931)) >= 0)
				{
					// build 14931 added 24-bit color support:
					// https://blogs.msdn.microsoft.com/commandline/2016/09/22/24-bit-color-in-the-windows-console/
					result.add(RGB_888COLOR);
				}
				return result;
			}
			return Collections.singleton(NONE);
		}
		String term = System.getenv("TERM");
		if (term == null)
			return Collections.singleton(NONE);
		// Following the approach set out in http://stackoverflow.com/a/39033815/14731, we don't
		// attempt to support all possible terminal types. Instead, we support mainstream types and
		// require the terminal to support or emulate them.
		switch (term)
		{
			case "xterm":
			{
				// Used by older Linux deployments (e.g. routers)
				return Collections.singleton(XTERM_8COLOR);
			}
			case "xterm-256color":
			{
				// Used by Linux and OSX 10.9+
				return Collections.singleton(XTERM_256COLOR);
			}
			default:
			{
				log.error("Unexpected TERM: " + term);
				return Collections.singleton(NONE);
			}
		}
	}

	/**
	 * Indicates the type of encoding that verifiers will output.
	 * <p>
	 * This feature can be used to force the use of ANSI colors even when their support is not
	 * detected.
	 *
	 * @param encoding the type of encoding that verifiers will output
	 * @throws NullPointerException if {@code encoding} is null
	 * @see #useBestEncoding()
	 */
	public void setEncoding(TerminalEncoding encoding)
	{
		if (encoding == null)
			throw new NullPointerException("encoding may not be null");
		OperatingSystem os = OperatingSystem.detect();
		if (os.type == WINDOWS)
		{
			if (getSupportedTypes().contains(encoding))
			{
				// Only Windows needs nativeSetEncoding() to be invoked
				if (nativeSetEncoding(encoding))
					this.encoding.set(encoding);
				else
					this.encoding.set(NONE);
			}
			else
			{
				// The user is forcing the use of an unsupported encoding
				this.encoding.set(encoding);
			}
		}
		else
			this.encoding.set(encoding);
	}

	/**
	 * Indicates the type of encoding that verifiers will output.
	 *
	 * @param encoding the type of encoding that verifiers will output (null if the best available
	 *                 encoding should be used)
	 * @return true on success
	 */
	private boolean nativeSetEncoding(TerminalEncoding encoding)
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
				log.error("", e);
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
		// See getSupportedTypesImpl() for an explanation of this algorithm
		OperatingSystem os = OperatingSystem.detect();
		if (os.type == WINDOWS)
		{
			if (os.version.compareTo(new Version(10, 0, 10586)) == 0)
			{
				this.encoding.set(XTERM_16COLOR);
				return;
			}
			// Build 10586 was the only one to support colors by default. Later builds required explicit
			// activation using native code.
			if (os.version.compareTo(new Version(10, 0, 14931)) >= 0 && nativeSetEncoding(RGB_888COLOR))
			{
				this.encoding.set(RGB_888COLOR);
				return;
			}
			this.encoding.set(NONE);
			return;
		}
		String term = System.getenv("TERM");
		if (term == null)
		{
			this.encoding.set(NONE);
			return;
		}
		switch (term)
		{
			case "xterm":
			{
				this.encoding.set(XTERM_8COLOR);
				return;
			}
			case "xterm-256color":
			{
				this.encoding.set(XTERM_256COLOR);
				return;
			}
			default:
			{
				log.error("Unknown terminal: " + term);
				this.encoding.set(NONE);
			}
		}
	}

	/**
	 * @return the encoding that verifiers will output (defaults to the best available encoding)
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
			return true;
		return nativeTerminal.map(terminal ->
		{
			try
			{
				return terminal.isConnectedToStdout();
			}
			catch (IOException e)
			{
				log.error("", e);
				return false;
			}
		}).orElse(false);
	}
}
