/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ClassValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

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
	 * @param scope        the application configuration
	 * @param config       the instance configuration
	 * @param name         the name of the value
	 * @param actual       the actual value
	 * @param failures     the list of validation failures
	 * @param fatalFailure true if validation stopped as the result of a fatal failure
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is blank.
	 */
	public ClassValidatorImpl(ApplicationScope scope, Configuration config, String name, Class<T> actual,
		List<ValidationFailure> failures, boolean fatalFailure)
	{
		super(scope, config, name, actual, failures, fatalFailure);
	}

	@Override
	protected ClassValidator<T> getThis()
	{
		return this;
	}

	@Override
	public ClassValidator<T> isSupertypeOf(Class<?> type)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(type, "type").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (!actual.isAssignableFrom(type))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be a supertype of " + type + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public ClassValidator<T> isSubtypeOf(Class<?> type)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(type, "type").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (!type.isAssignableFrom(actual))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be a subtype of " + type + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}
}