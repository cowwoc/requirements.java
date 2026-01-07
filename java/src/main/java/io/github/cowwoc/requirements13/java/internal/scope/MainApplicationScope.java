/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.scope;

import java.util.concurrent.atomic.AtomicBoolean;

import static io.github.cowwoc.requirements13.java.internal.scope.DefaultJvmScope.CLOSE_TIMEOUT;

/**
 * ApplicationScope for the main codebase.
 */
public final class MainApplicationScope extends AbstractApplicationScope
{
	/**
	 * The singleton instance.
	 */
	public static final MainApplicationScope INSTANCE = new MainApplicationScope(DefaultJvmScope.INSTANCE);
	private final AtomicBoolean closed = new AtomicBoolean();

	/**
	 * Creates a new application scope.
	 *
	 * @param parent the parent scope
	 * @throws NullPointerException if {@code parent} is null
	 */
	private MainApplicationScope(JvmScope parent)
	{
		super(parent, new MainGlobalConfiguration(parent.getTerminal()));
		parent.addChild(this);
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
		parent.removeChild(this);
		children.shutdown(CLOSE_TIMEOUT);
	}
}