/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.guava;

import com.github.cowwoc.requirements10.annotation.CheckReturnValue;
import com.github.cowwoc.requirements10.guava.internal.validator.GuavaValidatorsImpl;
import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.Validators;
import com.github.cowwoc.requirements10.java.internal.scope.MainApplicationScope;

import java.util.function.Function;

/**
 * Creates validators for the Guava API with an independent configuration.
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
public interface GuavaValidators
	extends Validators<GuavaValidators>, GuavaRequireThat, GuavaAssumeThat, GuavaCheckIf
{
	/**
	 * Creates a new instance using the default configuration.
	 *
	 * @return an instance of this interface
	 * @see Validators#configuration()
	 */
	static GuavaValidators newInstance()
	{
		return new GuavaValidatorsImpl(MainApplicationScope.INSTANCE, Configuration.DEFAULT);
	}

	@Override
	@CheckReturnValue
	GuavaValidators copy();
}