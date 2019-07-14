/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveArrayValidator;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.Collection;

/**
 * Default implementation of {@code PrimitiveArrayValidator}.
 *
 * @param <E> the type of elements in the array
 * @param <A> the type of the array
 */
public final class PrimitiveArrayValidatorImpl<E, A>
	extends AbstractArrayValidator<PrimitiveArrayValidator<E, A>, E, A>
	implements PrimitiveArrayValidator<E, A>
{
	/**
	 * @param scope              the application configuration
	 * @param config             the instance configuration
	 * @param name               the name of the value
	 * @param actual             the actual value
	 * @param actualAsCollection the {@code Collection} representation of the array
	 * @throws AssertionError if {@code scope}, {@code config} or {@code name} are null. If {@code name} is
	 *                        empty.
	 */
	public PrimitiveArrayValidatorImpl(ApplicationScope scope, Configuration config, String name,
	                                   A actual, Collection<E> actualAsCollection)
	{
		super(scope, config, name, actual, actualAsCollection, NO_FAILURES);
	}

	@Override
	protected PrimitiveArrayValidator<E, A> getThis()
	{
		return this;
	}

	@Override
	protected PrimitiveArrayValidator<E, A> getNoOp()
	{
		return new PrimitiveArrayValidatorNoOp<>(getFailures());
	}
}
