/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.scope;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.bitbucket.cowwoc.pouch.ConcurrentLazyFactory;
import org.bitbucket.cowwoc.pouch.ConcurrentLazyReference;
import org.bitbucket.cowwoc.pouch.Factory;
import org.bitbucket.cowwoc.pouch.Reference;
import org.bitbucket.cowwoc.requirements.core.GlobalConfiguration;
import org.bitbucket.cowwoc.requirements.core.terminal.NativeTerminal;
import org.bitbucket.cowwoc.requirements.core.terminal.Terminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default implementation of JvmScope.
 *
 * @author Gili Tzabari
 */
public final class DefaultJvmScope implements JvmScope
{
	public static final JvmScope INSTANCE = new DefaultJvmScope();
	private final Factory<NativeTerminal> nativeTerminal = new ConcurrentLazyFactory<NativeTerminal>()
	{
		@Override
		protected NativeTerminal createValue()
		{
			NativeTerminal result = new NativeTerminal();
			try
			{
				result.connect();
			}
			catch (IOException e)
			{
				throw new UncheckedIOException(e);
			}
			return result;
		}

		@Override
		protected void disposeValue(NativeTerminal value)
		{
			try
			{
				value.disconnect();
			}
			catch (IOException e)
			{
				log.error("", e);
			}
		}
	};
	private final Reference<Terminal> terminal = ConcurrentLazyReference.create(() ->
		new Terminal(nativeTerminal.getValue()));
	private final Reference<GlobalConfiguration> globalConfiguration = ConcurrentLazyReference.create(
		() -> new GlobalConfiguration(this));
	public final Thread shutdownHook;
	public final AtomicBoolean closed = new AtomicBoolean();
	private final Logger log = LoggerFactory.getLogger(DefaultJvmScope.class);

	public DefaultJvmScope()
	{
		try
		{
			System.loadLibrary("requirements");
		}
		catch (UnsatisfiedLinkError e)
		{
			log.warn("Failed to load \"requirements\" native library. Please see " +
				"https://bitbucket.org/cowwoc/requirements/wiki/Deploying%20native%20libraries for more " +
				"information.\n" +
				"java.library.path=" + System.getProperty("java.library.path") + "\n" +
				"user.dir=" + System.getProperty("user.dir"), e);
		}
		shutdownHook = new Thread(() ->
		{
			try
			{
				close();
			}
			catch (RuntimeException e)
			{
				log.error("Error closing scope", e);
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
	public GlobalConfiguration getGlobalConfiguration()
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
