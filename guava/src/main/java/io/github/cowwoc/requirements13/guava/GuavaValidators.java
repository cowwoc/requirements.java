/*
 * Copyright (c) 2024 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.guava;

import io.github.cowwoc.requirements13.annotation.CheckReturnValue;
import io.github.cowwoc.requirements13.guava.internal.validator.GuavaValidatorsImpl;
import io.github.cowwoc.requirements13.java.Validators;
import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.scope.MainApplicationScope;

/**
 * Creates validators for the Guava API with an independent configuration.
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
public interface GuavaValidators
	extends Validators<GuavaValidators>, GuavaRequireThat, GuavaAssertThat, GuavaCheckIf
{
	/**
	 * Creates a new instance using the default configuration.
	 *
	 * @return an instance of this interface
	 */
	static GuavaValidators newInstance()
	{
		return new GuavaValidatorsImpl(MainApplicationScope.INSTANCE, Configuration.DEFAULT);
	}

	@Override
	@CheckReturnValue
	GuavaValidators copy();
}