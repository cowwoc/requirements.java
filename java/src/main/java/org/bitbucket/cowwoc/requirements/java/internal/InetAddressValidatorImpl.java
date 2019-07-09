/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.InetAddressValidator;
import org.bitbucket.cowwoc.requirements.java.StringValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

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
		return new InetAddressValidatorNoOp(failures);
	}

	@Override
	public InetAddressValidator isIpV4()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (!(actual instanceof Inet4Address))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be an IP v4 address.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public InetAddressValidator isIpV6()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (!(actual instanceof Inet6Address))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be an IP v6 address.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public StringValidator asString()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return new StringValidatorNoOp(failures);
		}
		// InetAddress.toString() returns "<hostname>/<ip-address>", but this cannot be fed back into
		// InetAddress.getByName(String). Instead, we use InetAddress.getHostName() which returns the desired
		// format.
		String hostName = actual.getHostName();
		return new StringValidatorImpl(scope, config, hostName, name, failures);
	}
}
