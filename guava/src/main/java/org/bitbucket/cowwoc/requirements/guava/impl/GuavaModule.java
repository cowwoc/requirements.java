/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava.impl;

import java.util.Optional;
import org.bitbucket.cowwoc.requirements.Module;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;

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
		return Optional.of(new GuavaVerifiersImpl(scope, scope.getDefaultConfiguration()));
	}
}
