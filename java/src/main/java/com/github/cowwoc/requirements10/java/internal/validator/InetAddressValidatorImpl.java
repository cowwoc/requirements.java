/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.message.InetAddressMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.ValidationTarget;
import com.github.cowwoc.requirements10.java.validator.InetAddressValidator;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class InetAddressValidatorImpl extends AbstractObjectValidator<InetAddressValidator, InetAddress>
	implements InetAddressValidator
{
	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         the value being validated
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public InetAddressValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<InetAddress> value, Map<String, Optional<Object>> context,
		List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public InetAddressValidator isIpV4()
	{
		if (value.validationFailed(v -> v instanceof Inet4Address))
		{
			failOnNull();
			addIllegalArgumentException(
				InetAddressMessages.isIpAddressFailed(this, "IP v4").toString());
		}
		return this;
	}

	@Override
	public InetAddressValidator isIpV6()
	{
		if (value.validationFailed(v -> v instanceof Inet6Address))
		{
			failOnNull();
			addIllegalArgumentException(
				InetAddressMessages.isIpAddressFailed(this, "IP v6").toString());
		}
		return this;
	}
}