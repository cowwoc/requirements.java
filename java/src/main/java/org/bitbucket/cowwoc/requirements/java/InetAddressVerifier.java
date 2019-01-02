/*
 * Copyright (c) 2013 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.capabilities.ObjectCapabilities;

import java.net.InetAddress;

/**
 * Verifies the requirements of an IP address or hostname.
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
