/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import java.io.IOException;
import static org.bitbucket.cowwoc.requirements.diff.string.OperatingSystem.Type.WINDOWS;
import static org.bitbucket.cowwoc.requirements.diff.string.TerminalType.NONE;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
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

		try
		{
			System.loadLibrary("requirements");
		}
		catch (UnsatisfiedLinkError e)
		{
			log.warn("Failed to load native library. Setting terminal type to NONE.", e);
			this.type = NONE;
			this.nativeData = 0;
			return;
		}
		boolean stdoutIsTerminal = stdoutIsTerminal();

		if (requestedType == null)
		{
			if (stdoutIsTerminal)
				requestedType = TerminalType.detected();
			else
				requestedType = NONE;
		}

		long nativeData;
		if (OperatingSystem.current().type == WINDOWS && requestedType != NONE && stdoutIsTerminal)
		{
			// No need/way to enable XTerm support if the console has been redirected
			try
			{
				nativeData = start();
			}
			catch (IOException unused)
			{
				// An error occurred, disable XTERM support
				requestedType = NONE;
				nativeData = 0;
			}
		}
		else
			nativeData = 0;
		this.type = requestedType;
		this.nativeData = nativeData;

		if (nativeData != 0)
		{
			Runtime.getRuntime().addShutdownHook(new Thread(() ->
			{
				try
				{
					stop(this.nativeData);
				}
				catch (IOException | RuntimeException e)
				{
					Logger log = LoggerFactory.getLogger(DiffGenerator.class);
					log.error("Failed to restore console state", e);
				}
			}));
		}
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
	 * Initializes the console (enables Xterm support).
	 *
	 * @return the original console state
	 * @throws IOException if an I/O error occurs
	 */
	private native long start() throws IOException;

	/**
	 * Restore the original console configuration.
	 *
	 * @param state the original console state
	 * @throws IOException if an I/O error occurs
	 */
	private native void stop(long state) throws IOException;
}
