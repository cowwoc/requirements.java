/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.scope;

import java.util.Optional;
import org.bitbucket.cowwoc.pouch.ConcurrentLazyReference;
import org.bitbucket.cowwoc.pouch.Reference;
import org.bitbucket.cowwoc.requirements.Module;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.Verifiers;
import org.bitbucket.cowwoc.requirements.core.diff.DiffGenerator;
import org.bitbucket.cowwoc.requirements.guava.GuavaVerifiers;
import org.bitbucket.cowwoc.requirements.guava.impl.GuavaModule;

/**
 * ApplicationScope for the main and test codebases.
 *
 * @author Gili Tzabari
 */
public abstract class AbstractApplicationScope implements ApplicationScope
{
	private final Configuration defaultConfiguration = new Configuration();
	private final Reference<Verifiers> internalVerifier = ConcurrentLazyReference.create(() ->
		new Verifiers(this));
	private final Module guavaVerifiers = new GuavaModule();
	private final Reference<DiffGenerator> diffGenerator = ConcurrentLazyReference.create(() ->
		new DiffGenerator(this));

	@Override
	public DiffGenerator getDiffGenerator()
	{
		return diffGenerator.getValue();
	}

	@Override
	public Verifiers getInternalVerifier()
	{
		return internalVerifier.getValue();
	}

	@Override
	public Configuration getDefaultConfiguration()
	{
		return defaultConfiguration;
	}

	@Override
	public Optional<GuavaVerifiers> createGuavaVerifiers()
	{
		return guavaVerifiers.createVerifier(this).map(v -> (GuavaVerifiers) v);
	}
}
