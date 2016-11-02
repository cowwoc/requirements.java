/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.scope;

import java.util.Optional;
import org.bitbucket.cowwoc.pouch.ConcurrentLazyReference;
import org.bitbucket.cowwoc.pouch.Reference;
import org.bitbucket.cowwoc.requirements.diff.string.TerminalType;

/**
 * SingletonScope for the main codebase.
 *
 * @author Gili Tzabari
 */
public final class MainSingletonScope extends AbstractSingletonScope
{
	private final Reference<Optional<TerminalType>> requestedTerminalType = ConcurrentLazyReference.
		create(() ->
		{
			String property = System.getProperty("org.bitbucket.cowwoc.requirements.terminal");
			return Optional.ofNullable(property).map(TerminalType::valueOf);
		});

	@Override
	public Optional<TerminalType> getRequestedTerminalType()
	{
		return requestedTerminalType.getValue();
	}

	@Override
	public void close()
	{
	}
}
