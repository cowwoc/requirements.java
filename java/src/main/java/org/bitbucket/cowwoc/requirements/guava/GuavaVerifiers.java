/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import org.bitbucket.cowwoc.requirements.java.Configurable;
import org.bitbucket.cowwoc.requirements.java.Configuration;

import java.util.function.Function;

/**
 * A placeholder that is replaced by the requirements-guava module.
 */
public interface GuavaVerifiers extends Configurable
{
	@Override
	GuavaVerifiers addContext(String name, Object value);

	@Override
	GuavaVerifiers withDefaultException();

	@Override
	GuavaVerifiers withException(Class<? extends RuntimeException> exception);

	@Override
	GuavaVerifiers withAssertionsDisabled();

	@Override
	GuavaVerifiers withAssertionsEnabled();

	@Override
	GuavaVerifiers withDiff();

	@Override
	GuavaVerifiers withoutDiff();

	@Override
	<T> GuavaVerifiers withStringConverter(Class<T> type, Function<T, String> converter);

	@Override
	<T> GuavaVerifiers withoutStringConverter(Class<T> type);

	@Override
	GuavaVerifiers withConfiguration(Configuration configuration);

	@Override
	boolean assertionsAreEnabled();
}
