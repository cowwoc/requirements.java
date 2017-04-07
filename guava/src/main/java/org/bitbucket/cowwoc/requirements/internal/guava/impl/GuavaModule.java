/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.guava.impl;

import org.bitbucket.cowwoc.requirements.internal.guava.impl.GuavaVerifiersImpl;
import java.util.Optional;
import org.bitbucket.cowwoc.requirements.internal.module.Module;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;

/**
 * Information about the Guava module.
 *
 * @author Gili Tzabaris
 */
public final class GuavaModule implements Module
{
	@Override
	public Optional<Object> createVerifier(ApplicationScope scope)
	{
		return Optional.of(new GuavaVerifiersImpl(scope, new Configuration()));
	}
}
