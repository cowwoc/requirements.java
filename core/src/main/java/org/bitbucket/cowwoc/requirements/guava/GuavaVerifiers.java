/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import org.bitbucket.cowwoc.requirements.core.Configurable;
import org.bitbucket.cowwoc.requirements.core.Configuration;

/**
 * A placeholder that is replaced by the requirements-guava module.
 *
 * @author Gili Tzabari
 */
public interface GuavaVerifiers extends Configurable
{
	@Override
	GuavaVerifiers addContext(String key, Object value);

	@Override
	GuavaVerifiers withDefaultException();

	@Override
	GuavaVerifiers withException(Class<? extends RuntimeException> exception);

	@Override
	GuavaVerifiers withAssertionsDisabled();

	@Override
	GuavaVerifiers withAssertionsEnabled();

	@Override
	GuavaVerifiers withConfiguration(Configuration configuration);

	@Override
	@SuppressWarnings("deprecation")
	boolean assertionsAreEnabled();
}
