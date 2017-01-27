/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.net.InetAddress;
import org.bitbucket.cowwoc.requirements.core.capabilities.ObjectCapabilities;

/**
 * Verifies an IP address or hostname.
 *
 * @author Gili Tzabari
 */
public interface InetAddressVerifier extends ObjectCapabilities<InetAddressVerifier, InetAddress>
{
	/**
	 * Ensures that the actual value is an IP v4 address.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is not a IP v4 address
	 */
	InetAddressVerifier isIpV4();

	/**
	 * Ensures that the actual value is an IP v6 address.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is not a IP v6 address
	 */
	InetAddressVerifier isIpV6();
}
