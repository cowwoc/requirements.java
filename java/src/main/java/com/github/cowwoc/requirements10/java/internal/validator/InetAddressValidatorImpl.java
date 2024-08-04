/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.message.InetAddressMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.validator.InetAddressValidator;
import com.github.cowwoc.requirements10.java.validator.StringValidator;

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
	 * @param value         the value
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public InetAddressValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<InetAddress> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public InetAddressValidator isIpV4()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value instanceof Inet4Address))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				InetAddressMessages.isIpAddress(this, "IP v4").toString());
		}
		return this;
	}

	@Override
	public InetAddressValidator isIpV6()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value instanceof Inet6Address))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				InetAddressMessages.isIpAddress(this, "IP v6").toString());
		}
		return this;
	}

	@Override
	public StringValidator asString()
	{
		if (value.isNull())
			onNull();
		// InetAddress.toString() returns "<hostname>/<ip-address>", but this cannot be fed back into
		// InetAddress.getByName(String). Instead, we use InetAddress.getHostName() which returns the desired
		// format.
		return new StringValidatorImpl(scope, configuration, name,
			value.nullToUndefined().mapDefined(InetAddress::getHostName), context, failures);
	}
}