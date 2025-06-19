/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.internal.scope;

import io.github.cowwoc.pouch.core.AbstractScope;
import io.github.cowwoc.requirements12.java.internal.terminal.Terminal;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The default implementation of JvmScope.
 * <p>
 * This class ensures that globals, such as slf4j, are initialized in a thread-safe manner.
 */
public final class DefaultJvmScope extends AbstractScope
	implements JvmScope
{
	/**
	 * The singleton instance.
	 */
	public static final DefaultJvmScope INSTANCE = new DefaultJvmScope();
	/**
	 * The maximum amount of time to wait for child scopes to close.
	 */
	public static final Duration CLOSE_TIMEOUT = Duration.ofSeconds(10);
	private final Terminal terminal = new Terminal();
	private final AtomicBoolean closed = new AtomicBoolean();

	private DefaultJvmScope()
	{
	}

	@Override
	public Terminal getTerminal()
	{
		return terminal;
	}

	@Override
	public boolean isClosed()
	{
		return closed.get();
	}

	@Override
	public void close()
	{
		if (!closed.compareAndSet(false, true))
			return;
		children.shutdown(CLOSE_TIMEOUT);
	}
}