/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.jackson;

import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.jackson.internal.implementation.JacksonValidatorsImpl;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.Validators;
import com.github.cowwoc.requirements.java.internal.scope.MainApplicationScope;

import java.util.function.Function;

/**
 * Creates validators for the Jackson API with an independent configuration.
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
 * {@code isGreaterThan(value, null)}) are thrown by all validators and cannot be configured. Exceptions that
 * are thrown in response to the value failing a validation check, e.g. {@code isGreaterThan(5)} on a value
 * of 0, are thrown by {@code requireThat()} and {@code assumeThat()} but are recorded by {@code checkIf()}
 * without being thrown. The type of thrown exceptions is configurable using
 * {@link ConfigurationUpdater#exceptionTransformer(Function)}.
 */
public interface JacksonValidators
	extends Validators<JacksonValidators>, JacksonRequireThat, JacksonAssumeThat, JacksonCheckIf
{
	/**
	 * Creates a new instance using the default configuration.
	 *
	 * @return an instance of this interface
	 * @see Validators#configuration()
	 */
	static JacksonValidators newInstance()
	{
		return new JacksonValidatorsImpl(MainApplicationScope.INSTANCE, Configuration.DEFAULT);
	}

	@Override
	@CheckReturnValue
	JacksonValidators copy();
}