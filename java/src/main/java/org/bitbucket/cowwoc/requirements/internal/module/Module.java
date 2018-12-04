/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.module;

import org.bitbucket.cowwoc.requirements.internal.java.scope.ApplicationScope;

import java.util.Optional;

/**
 * Information about a module.
 */
public interface Module
{
	/**
	 * Returns an new instance of the verifiers defined by this module.
	 *
	 * @param scope the application configuration
	 * @return {@code Optional.empty()} if the module is not loaded
	 */
	Optional<Object> createVerifier(ApplicationScope scope);
}
