/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.core.spi.StringVerifierSpi;

/**
 * Verifies a {@link String} parameter.
 *
 * @author Gili Tzabari
 */
public interface StringVerifier
	extends StringVerifierSpi<StringVerifier, String>, Isolatable<StringVerifier>
{
}
