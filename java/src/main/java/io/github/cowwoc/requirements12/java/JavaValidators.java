/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java;

import io.github.cowwoc.requirements12.java.internal.Configuration;
import io.github.cowwoc.requirements12.java.internal.scope.MainApplicationScope;
import io.github.cowwoc.requirements12.java.internal.validator.JavaValidatorsImpl;
import io.github.cowwoc.requirements12.annotation.CheckReturnValue;

/**
 * Creates validators for the Java API with an independent configuration.
 * <p>
 * A factory that creates different types of validators.
 * <p>
 * There are three kinds of validators:
 * <ul>
 *   <li>{@code requireThat()} for method preconditions.</li>
 *   <li>{@code assert that()} for class invariants, and method postconditions.</li>
 *   <li>{@code checkIf()} for returning multiple validation failures.</li>
 * </ul>
 */
public interface JavaValidators
	extends Validators<JavaValidators>, JavaRequireThat, JavaAssertThat, JavaCheckIf
{
	/**
	 * Creates a new instance using the default configuration.
	 *
	 * @return an instance of this interface
	 */
	static JavaValidators newInstance()
	{
		return new JavaValidatorsImpl(MainApplicationScope.INSTANCE, Configuration.DEFAULT);
	}

	@Override
	@CheckReturnValue
	JavaValidators copy();
}