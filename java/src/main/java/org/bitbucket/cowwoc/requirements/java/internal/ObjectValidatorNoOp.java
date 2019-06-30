/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ObjectValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * An ObjectValidator that ignores subsequent validations due to an incompatible type conversion.
 *
 * @param <T> the type of the value
 */
public final class ObjectValidatorNoOp<T> extends AbstractObjectValidatorNoOp<ObjectValidator<T>, T>
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	ObjectValidatorNoOp(ApplicationScope scope,
	                    Configuration config,
	                    List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}
}
