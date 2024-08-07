/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

import java.net.InetAddress;

/**
 * Validates the state of an {@code InetAddress}.
 */
public interface InetAddressValidator extends
	ValidatorComponent<InetAddressValidator, InetAddress>,
	ObjectComponent<InetAddressValidator, InetAddress>
{
	/**
	 * Ensures that the value is an IP v4 address.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if value is not an IP v4 address
	 */
	InetAddressValidator isIpV4();

	/**
	 * Ensures that the actual value is an IP v6 address.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if value is not an IP v6 address
	 */
	InetAddressValidator isIpV6();
}