/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.diff;

import java.io.IOException;
import static org.bitbucket.cowwoc.requirements.core.diff.OperatingSystem.Type.WINDOWS;
import static org.bitbucket.cowwoc.requirements.core.diff.TerminalType.NONE;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The terminal associated with the JVM.
 *
 * @author Gili Tzabari
 */
public final class Terminal
{
	private final TerminalType type;
	private final Logger log = LoggerFactory.getLogger(Terminal.class);

	/**
	 * A pointer to the state of the native code.
	 */
	private final long nativeData;

	/**
	 * @param scope the system configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	public Terminal(SingletonScope scope)
	{
		assert (scope != null): "scope may not be null";
		TerminalType requestedType = scope.getRequestedTerminalType().orElse(null);
		if (requestedType == NONE)
		{
			this.type = NONE;
			this.nativeData = 0;
			return;
		}

		boolean stdoutIsTerminal = detectStdout(loadLibrary());
		if (requestedType == null)
		{
			if (stdoutIsTerminal)
				requestedType = TerminalType.detected();
			else
			{
				log.warn("stdout not connected to a terminal. Setting type to NONE.");
				requestedType = NONE;
			}
		}

		long nativeData;
		try
		{
			nativeData = initialize(requestedType, stdoutIsTerminal);
		}
		catch (IOException | UnsatisfiedLinkError e)
		{
			log.error("Error invoking start()", e);
			nativeData = 0;
			requestedType = NONE;
		}
		this.type = requestedType;
		this.nativeData = nativeData;
		addShutdownHook();
	}

	/**
	 * Loads the native library.
	 *
	 * @return true on success
	 */
	private boolean loadLibrary()
	{
		try
		{
			System.loadLibrary("requirements");
			return true;
		}
		catch (UnsatisfiedLinkError e)
		{
			log.warn("Failed to load native library", e);
			return false;
		}
	}

	/**
	 * Attempts to detect if stdout is connected to a terminal, with and without using JNI.
	 *
	 * @param jniAvailable true if the native library is available
	 * @return true if stdout is connected to a terminal
	 */
	private boolean detectStdout(boolean jniAvailable)
	{
		try
		{
			if (jniAvailable)
				return stdoutIsTerminal();
		}
		catch (UnsatisfiedLinkError e)
		{
			log.error("Error invoking stdoutIsTerminal()", e);
		}
		switch (OperatingSystem.current().type)
		{
			case WINDOWS:
			{
				// No way to activate ANSI support without JNI
				return false;
			}
			case LINUX:
			case MAC:
			{
				// System.console() is not as accurate as isatty() in that it returns null when stdin
				// is redirected (which we don't care about) but this check is good enough for our case.
				return System.console() != null;
			}
			case UNKNOWN:
			default:
				throw new AssertionError(OperatingSystem.current().type.name());
		}
	}

	/**
	 * Initialize the terminal.
	 *
	 * @param requestedType    the requested terminal type
	 * @param stdoutIsTerminal true if stdout is connected to a terminal
	 * @return the address of the original terminal state
	 * @throws IOException          if an I/O error occurs
	 * @throws UnsatisfiedLinkError if the native method cannot be found
	 */
	private long initialize(TerminalType requestedType, boolean stdoutIsTerminal)
		throws IOException, UnsatisfiedLinkError
	{
		// For Windows, requestedType != NONE implies that JNI is available
		if (OperatingSystem.current().type == WINDOWS && requestedType != NONE && stdoutIsTerminal)
		{
			// No way to enable XTerm support if the terminal has been redirected
			return start();
		}
		return 0;
	}

	/**
	 * Invokes stop() on JVM shutdown.
	 */
	private void addShutdownHook()
	{
		if (nativeData == 0)
			return;
		Runtime.getRuntime().addShutdownHook(new Thread(() ->
		{
			try
			{
				stop(this.nativeData);
			}
			catch (IOException | UnsatisfiedLinkError e)
			{
				log.error("Error invoking stop()", e);
			}
		}));
	}

	/**
	 * @return the type of the terminal
	 */
	public TerminalType getType()
	{
		return type;
	}

	/**
	 * @return true if stdout is connected to the terminal
	 * @see http://stackoverflow.com/a/3650507/14731
	 */
	private native boolean stdoutIsTerminal();

	/**
	 * Initializes the terminal (enables Xterm support).
	 *
	 * @return the address of the original terminal state
	 * @throws IOException if an I/O error occurs
	 */
	private native long start() throws IOException;

	/**
	 * Restore the original terminal configuration.
	 *
	 * @param state the address of the original terminal state
	 * @throws IOException if an I/O error occurs
	 */
	private native void stop(long state) throws IOException;
}
