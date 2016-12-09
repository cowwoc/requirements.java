/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.core.spi.StringBasedSpi;

/**
 * Verifies an email address.
 *
 * @author Gili Tzabari
 */
public interface EmailAddressVerifier
	extends StringBasedSpi<EmailAddressVerifier, String>,
	Isolatable<EmailAddressVerifier>
{
}
