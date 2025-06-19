/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.jackson;

import io.github.cowwoc.requirements12.jackson.internal.validator.JacksonValidatorsImpl;
import io.github.cowwoc.requirements12.java.Validators;
import io.github.cowwoc.requirements12.java.internal.Configuration;
import io.github.cowwoc.requirements12.java.internal.scope.MainApplicationScope;
import io.github.cowwoc.requirements12.annotation.CheckReturnValue;

/**
 * Creates validators for the Jackson API with an independent configuration.
 * <p>
 * A factory that creates different types of validators.
 * <p>
 * There are three kinds of validators:
 * <ul>
 *   <li>{@code requireThat()} for method preconditions.</li>
 *   <li>{@code assertThat()} for class invariants, and method postconditions.</li>
 *   <li>{@code checkIf()} for returning multiple validation failures.</li>
 * </ul>
 */
public interface JacksonValidators
	extends Validators<JacksonValidators>, JacksonRequireThat, JacksonAssertThat, JacksonCheckIf
{
	/**
	 * Creates a new instance using the default configuration.
	 *
	 * @return an instance of this interface
	 */
	static JacksonValidators newInstance()
	{
		return new JacksonValidatorsImpl(MainApplicationScope.INSTANCE, Configuration.DEFAULT);
	}

	@Override
	@CheckReturnValue
	JacksonValidators copy();
}