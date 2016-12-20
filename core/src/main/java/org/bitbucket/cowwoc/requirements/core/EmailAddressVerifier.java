/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.ext.StringBasedExtension;

/**
 * Verifies an email address.
 *
 * @author Gili Tzabari
 */
public interface EmailAddressVerifier
	extends StringBasedExtension<EmailAddressVerifier, String>, Verifier<EmailAddressVerifier>
{
}
