/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.guava.internal.secrets;

import com.github.cowwoc.requirements.guava.GuavaRequirements;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * @see GuavaSecrets
 */
public interface SecretRequirements
{
	/**
	 * Creates a new {@code GuavaRequirements}.
	 *
	 * @param scope the application configuration
	 * @return a new {@code GuavaRequirements}
	 */
	GuavaRequirements create(ApplicationScope scope);
}
