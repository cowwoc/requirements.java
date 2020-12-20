/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.InetAddressValidator;
import com.github.cowwoc.requirements.java.StringValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.List;

/**
 * Default implementation of {@code InetAddressValidator}.
 */
public final class InetAddressValidatorImpl
	extends AbstractObjectValidator<InetAddressValidator, InetAddress>
	implements InetAddressValidator
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public InetAddressValidatorImpl(ApplicationScope scope, Configuration config, String name,
	                                InetAddress actual, List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected InetAddressValidator getThis()
	{
		return this;
	}

	@Override
	protected InetAddressValidator getNoOp()
	{
		return new InetAddressValidatorNoOp(getFailures());
	}

	@Override
	public InetAddressValidator isIpV4()
	{
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			return getNoOp();
		}
		if (!(actual instanceof Inet4Address))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be an IP v4 address.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public InetAddressValidator isIpV6()
	{
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			return getNoOp();
		}
		if (!(actual instanceof Inet6Address))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be an IP v6 address.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public StringValidator asString()
	{
		if (actual == null)
			return new StringValidatorImpl(scope, config, name, "null", getFailures());
		// InetAddress.toString() returns "<hostname>/<ip-address>", but this cannot be fed back into
		// InetAddress.getByName(String). Instead, we use InetAddress.getHostName() which returns the desired
		// format.
		String hostName = actual.getHostName();
		return new StringValidatorImpl(scope, config, name, hostName, getFailures());
	}
}
