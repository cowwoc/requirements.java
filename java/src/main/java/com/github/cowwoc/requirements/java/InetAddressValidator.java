/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

import java.net.InetAddress;

/**
 * Validates the requirements of an IP address or hostname.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectValidator}) imply {@link #isNotNull()}.
 */
public interface InetAddressValidator extends ExtensibleObjectValidator<InetAddressValidator, InetAddress>
{
	/**
	 * Ensures that the actual value is an IP v4 address.
	 *
	 * @return the updated validator
	 */
	InetAddressValidator isIpV4();

	/**
	 * Ensures that the actual value is an IP v6 address.
	 *
	 * @return the updated validator
	 */
	InetAddressValidator isIpV6();
}