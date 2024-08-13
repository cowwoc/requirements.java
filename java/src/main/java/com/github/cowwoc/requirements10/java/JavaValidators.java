/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java;

import com.github.cowwoc.requirements10.annotation.CheckReturnValue;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.scope.MainApplicationScope;
import com.github.cowwoc.requirements10.java.internal.validator.JavaValidatorsImpl;

/**
 * Creates validators for the Java API with an independent configuration.
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