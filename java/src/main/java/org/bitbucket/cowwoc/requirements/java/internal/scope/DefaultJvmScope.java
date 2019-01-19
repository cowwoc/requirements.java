/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope;

import org.bitbucket.cowwoc.pouch.ConcurrentLazyFactory;
import org.bitbucket.cowwoc.pouch.ConcurrentLazyReference;
import org.bitbucket.cowwoc.pouch.Factory;
import org.bitbucket.cowwoc.pouch.Reference;
import org.bitbucket.cowwoc.requirements.java.GlobalConfigurable;
import org.bitbucket.cowwoc.requirements.java.GlobalConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.terminal.Terminal;
import org.bitbucket.cowwoc.requirements.natives.internal.terminal.NativeTerminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The default implementation of JvmScope.
 */
public final class DefaultJvmScope implements JvmScope
{
	public static final JvmScope INSTANCE = new DefaultJvmScope();
	private final boolean nativeLibraryLoaded;
	private final Factory<NativeTerminal> nativeTerminal = new ConcurrentLazyFactory<>()
	{
		@Override
		protected NativeTerminal createValue()
		{
			if (!nativeLibraryLoaded)
				return null;
			NativeTerminal result = new NativeTerminal();
			try
			{
				result.connect();
				return result;
			}
			catch (UnsatisfiedLinkError e)
			{
				terminalLog.warn("", e);
				return null;
			}
			catch (IOException e)
			{
				throw new UncheckedIOException(e);
			}
		}

		@Override
		protected void disposeValue(NativeTerminal value)
		{
			if (value == null)
				return;
			try
			{
				value.disconnect();
			}
			catch (IOException e)
			{
				terminalLog.error("", e);
			}
		}
	};
	private final Reference<Terminal> terminal =
		ConcurrentLazyReference.create(() -> new Terminal(nativeTerminal.getValue()));
	private final Reference<GlobalConfigurable> globalConfiguration =
		ConcurrentLazyReference.create(() -> new GlobalConfiguration(this));
	public final Thread shutdownHook;
	public final AtomicBoolean closed = new AtomicBoolean();
	private final Logger terminalLog = LoggerFactory.getLogger(NativeTerminal.class);

	public DefaultJvmScope()
	{
		boolean nativeLibraryLoaded;
		try
		{
			System.loadLibrary("requirements");
			nativeLibraryLoaded = true;
		}
		catch (UnsatisfiedLinkError e)
		{
			nativeLibraryLoaded = false;
			terminalLog.debug("Failed to load \"requirements\" native library. Please see " +
				"https://bitbucket.org/cowwoc/requirements/wiki/Deploying%20native%20libraries for more information.\n" +
				"\n" +
				"Relevant System Properties\n" +
				"--------------------------\n" +
				"java.library.path=" + System.getProperty("java.library.path") + "\n" +
				"user.dir=" + System.getProperty("user.dir"), e);
		}
		this.nativeLibraryLoaded = nativeLibraryLoaded;
		shutdownHook = new Thread(() ->
		{
			try
			{
				close();
			}
			catch (RuntimeException e)
			{
				terminalLog.error("Error closing scope", e);
			}
		});
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	}

	@Override
	public Terminal getTerminal()
	{
		return terminal.getValue();
	}

	@Override
	public GlobalConfigurable getGlobalConfiguration()
	{
		return globalConfiguration.getValue();
	}

	@Override
	public void close()
	{
		if (!closed.compareAndSet(false, true))
			return;
		nativeTerminal.close();
		try
		{
			Runtime.getRuntime().removeShutdownHook(shutdownHook);
		}
		catch (IllegalStateException unused)
		{
			// The JVM is already in the process of shutting down
		}
	}
}
