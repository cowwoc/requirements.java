/*
 * Copyright (c) 2013 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

import java.net.InetAddress;

/**
 * Verifies the requirements of an IP address or hostname.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectVerifier}) imply {@link #isNotNull()}.
 */
public interface InetAddressVerifier extends ExtensibleObjectVerifier<InetAddressVerifier, InetAddress>
{
	/**
	 * Ensures that the actual value is an IP v4 address.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if actual value is not a IP v4 address
	 */
	InetAddressVerifier isIpV4();

	/**
	 * Ensures that the actual value is an IP v6 address.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if actual value is not a IP v6 address
	 */
	InetAddressVerifier isIpV6();
}
