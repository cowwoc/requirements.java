/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.test.scope;

import io.github.cowwoc.requirements12.java.TerminalEncoding;
import io.github.cowwoc.requirements12.java.internal.scope.AbstractApplicationScope;
import io.github.cowwoc.requirements12.java.internal.scope.DefaultJvmScope;

import java.util.concurrent.atomic.AtomicBoolean;

import static io.github.cowwoc.requirements12.java.internal.scope.DefaultJvmScope.CLOSE_TIMEOUT;

/**
 * ApplicationScope for the test codebase.
 */
public final class TestApplicationScope extends AbstractApplicationScope
{
	private final AtomicBoolean closed = new AtomicBoolean();

	/**
	 * @param terminalEncoding the type of encoding that validators should output
	 * @throws NullPointerException if any of the arguments are null
	 */
	public TestApplicationScope(TerminalEncoding terminalEncoding)
	{
		super(DefaultJvmScope.INSTANCE, new TestGlobalConfiguration(terminalEncoding));
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