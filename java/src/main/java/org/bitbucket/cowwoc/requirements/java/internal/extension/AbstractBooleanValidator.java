/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleBooleanValidator;
import org.bitbucket.cowwoc.requirements.java.internal.ValidationFailureImpl;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Extensible implementation of ExtensibleBooleanValidator.
 *
 * @param <S> the type of validator returned by the methods
 */
public abstract class AbstractBooleanValidator<S> extends AbstractObjectValidator<S, Boolean>
	implements ExtensibleBooleanValidator<S>
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	protected AbstractBooleanValidator(ApplicationScope scope, Configuration config, String name,
	                                   Boolean actual, List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	public S isTrue()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (!actual)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be true");
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isFalse()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be false");
			failures.add(failure);
		}
		return getThis();
	}
}
