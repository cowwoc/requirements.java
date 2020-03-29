/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.secrets;

import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * @see JavaSecrets
 */
public interface SecretRequirements
{
	/**
	 * Creates a new {@code JavaRequirements}.
	 *
	 * @param scope the application configuration
	 * @return a new {@code JavaRequirements}
	 */
	JavaRequirements create(ApplicationScope scope);
}
