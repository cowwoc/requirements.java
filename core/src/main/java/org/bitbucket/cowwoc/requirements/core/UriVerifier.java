/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.net.URI;
import org.bitbucket.cowwoc.requirements.core.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.core.spi.ObjectVerifierSpi;

/**
 * Interface needed for Requirements.assertThat().
 * <p>
 * @author Gili Tzabari
 */
public interface UriVerifier
	extends ObjectVerifierSpi<UriVerifier, URI>, Isolatable<UriVerifier>
{
	/**
	 * Ensures that the actual value is absolute.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is not absolute
	 */
	UriVerifier isAbsolute();
}
