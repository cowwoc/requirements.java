/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.net.URI;
import org.bitbucket.cowwoc.requirements.core.ext.ObjectVerifierExtension;

/**
 * Verifies a {@code URI} parameter.
 *
 * @author Gili Tzabari
 */
public interface UriVerifier
	extends ObjectVerifierExtension<UriVerifier, URI>, Verifier<UriVerifier>
{
	/**
	 * Ensures that the actual value is absolute.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is not absolute
	 */
	UriVerifier isAbsolute();
}
