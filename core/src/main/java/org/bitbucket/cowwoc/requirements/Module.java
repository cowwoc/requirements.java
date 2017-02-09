/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Optional;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;

/**
 * Information about a module.
 *
 * @author Gili Tzabari
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
