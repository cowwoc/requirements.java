/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.InetAddressValidator;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;

public final class InetAddressValidatorImpl extends AbstractObjectValidator<InetAddressValidator, InetAddress>
	implements InetAddressValidator
{
	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public InetAddressValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		InetAddress value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public InetAddressValidator isIpV4()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be an IP v4 address.").toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Inet4Address))
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be an IP v4 address.").toString());
		}
		return this;
	}

	@Override
	public InetAddressValidator isIpV6()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be an IP v6 address.").toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Inet6Address))
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be an IP v6 address.").toString());
		}
		return this;
	}

	@Override
	public StringValidatorImpl asString()
	{
		if (value == null)
		{
			return super.asString();
		}
		// InetAddress.toString() returns "<hostname>/<ip-address>", but this cannot be fed back into
		// InetAddress.getByName(String). Instead, we use InetAddress.getHostName() which returns the desired
		// format.
		String hostName = value.getHostName();
		return new StringValidatorImpl(scope, configuration, name, hostName, context, failures);
	}
}