/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.scope;

import org.bitbucket.cowwoc.pouch.ConcurrentLazyReference;
import org.bitbucket.cowwoc.pouch.Reference;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.Verifiers;
import org.bitbucket.cowwoc.requirements.guava.GuavaVerifiers;
import org.bitbucket.cowwoc.requirements.internal.core.diff.DiffGenerator;
import org.bitbucket.cowwoc.requirements.internal.core.util.Exceptions;
import org.bitbucket.cowwoc.requirements.internal.guava.impl.GuavaModule;
import org.bitbucket.cowwoc.requirements.internal.module.Module;

import java.util.Optional;

/**
 * ApplicationScope for the main and test codebases.
 */
public abstract class AbstractApplicationScope implements ApplicationScope
{
	private final Reference<Verifiers> internalVerifier = ConcurrentLazyReference.create(() ->
		new Verifiers(this));
	private final Module guavaVerifiers = new GuavaModule();
	private final Reference<DiffGenerator> diffGenerator = ConcurrentLazyReference.create(() ->
		new DiffGenerator(this));
	private final Exceptions exceptions = new Exceptions();
	protected final Configuration defaultConfiguration = new Configuration();

	@Override
	public DiffGenerator getDiffGenerator()
	{
		return diffGenerator.getValue();
	}

	@Override
	public Exceptions getExceptions()
	{
		return exceptions;
	}

	@Override
	public Verifiers getInternalVerifier()
	{
		return internalVerifier.getValue();
	}

	@Override
	public Optional<GuavaVerifiers> createGuavaVerifiers()
	{
		return guavaVerifiers.createVerifier(this).map(v -> (GuavaVerifiers) v);
	}
}
