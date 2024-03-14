/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.java.internal.implementation.JavaValidatorsImpl;
import com.github.cowwoc.requirements.java.internal.scope.MainApplicationScope;

import java.util.function.Function;

/**
 * Creates validators for the Java API with an independent configuration.
 * <p>
 * There are three kinds of validators:
 * <ul>
 *   <li>{@code requireThat()} for method preconditions.</li>
 *   <li>{@code assumeThat()} for class invariants, and method postconditions.</li>
 *   <li>{@code checkIf()} for method preconditions, class invariants and method postconditions.</li>
 * </ul>
 * <p>
 * {@code requireThat()} and {@code assumeThat()} throw an exception on the first validation failure,
 * while {@code checkIf()} collects multiple validation failures before throwing an exception at the end.
 * {@code checkIf()} is more flexible than the others, but its syntax is more verbose.
 * <p>
 * Exceptions that are thrown in response to invalid method arguments (e.g.
 * {@code isGreaterThan(null, value)}) are thrown by all validators and cannot be configured. Exceptions that
 * are thrown in response to the value failing a validation check, e.g. {@code isGreaterThan(5)} on a value
 * of 0, are thrown by {@code requireThat()} and {@code assumeThat()} but are recorded by {@code checkIf()}
 * without being thrown. The type of thrown exceptions is configurable using
 * {@link ConfigurationUpdater#exceptionTransformer(Function)}.
 */
public interface JavaValidators
	extends Validators<JavaValidators>, JavaRequireThat, JavaAssumeThat, JavaCheckIf
{
	/**
	 * Creates a new instance using the default configuration.
	 *
	 * @return an instance of this interface
	 * @see Validators#configuration()
	 */
	static JavaValidators newInstance()
	{
		return new JavaValidatorsImpl(MainApplicationScope.INSTANCE, Configuration.DEFAULT);
	}

	/**
	 * Creates a new instance of this validator with an independent configuration.
	 *
	 * @param configuration the configuration to use for new validators
	 * @return an instance of this interface
	 * @see Validators#configuration()
	 */
	static JavaValidators newInstance(Configuration configuration)
	{
		return new JavaValidatorsImpl(MainApplicationScope.INSTANCE, configuration);
	}

	/**
	 * Returns a new factory instance with an independent configuration. This method is commonly used to inherit
	 * and update contextual information from the original factory before passing it into a nested operation.
	 * For example:
	 * <p>
	 * {@snippet :
	 * JavaValidators copy = validators.copy();
	 * copy.context().put(json.toString(), "json");
	 * nestedOperation(copy);
	 *}
	 *
	 * @return a copy of this factory
	 */
	@CheckReturnValue
	JavaValidators copy();
}