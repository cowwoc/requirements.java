/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.net.InetAddress;
import org.bitbucket.cowwoc.requirements.core.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.core.spi.ObjectVerifierSpi;

/**
 * Verifies an email address.
 *
 * @author Gili Tzabari
 */
public interface IpAddressVerifier
	extends ObjectVerifierSpi<IpAddressVerifier, InetAddress>, Isolatable<IpAddressVerifier>
{
}
