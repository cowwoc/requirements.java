/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ClassValidator;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Default implementation of {@code ClassValidator}.
 *
 * @param <T> the type of the class
 */
public final class ClassValidatorImpl<T> extends AbstractObjectValidator<ClassValidator<T>, Class<T>>
	implements ClassValidator<T>
{
	/**
	 * @param scope    the application configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code name}, {@code config} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public ClassValidatorImpl(ApplicationScope scope, String name, Class<T> actual, Configuration config,
	                          List<ValidationFailure> failures)
	{
		super(scope, name, actual, config, failures);
	}

	@Override
	public ClassValidator<T> isSupertypeOf(Class<?> type)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(type, "type").isNotNull();
		if (!actual.isAssignableFrom(type))
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " must be a supertype of " + type + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}
}