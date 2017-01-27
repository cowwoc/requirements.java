/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.scope;

import org.bitbucket.cowwoc.pouch.ConcurrentLazyReference;
import org.bitbucket.cowwoc.pouch.Reference;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.CoreUnifiedVerifier;
import org.bitbucket.cowwoc.requirements.core.diff.DiffGenerator;

/**
 * ApplicationScope for the main and test codebases.
 *
 * @author Gili Tzabari
 */
public abstract class AbstractApplicationScope implements ApplicationScope
{
	private final Configuration defaultConfiguration = new Configuration(this);
	private final Reference<CoreUnifiedVerifier> internalVerifier = ConcurrentLazyReference.create(
		() -> new CoreUnifiedVerifier(this, getDefaultConfiguration(),
			CoreUnifiedVerifier.classAssertionsAreEnabled()));
	private final Reference<DiffGenerator> diffGenerator = ConcurrentLazyReference.create(() ->
		new DiffGenerator(this));

	@Override
	public DiffGenerator getDiffGenerator()
	{
		return diffGenerator.getValue();
	}

	@Override
	public CoreUnifiedVerifier getInternalVerifier()
	{
		return internalVerifier.getValue();
	}

	@Override
	public Configuration getDefaultConfiguration()
	{
		return defaultConfiguration;
	}
}
