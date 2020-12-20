/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.InetAddressValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;

import java.net.InetAddress;
import java.util.List;

/**
 * An InetAddressValidator that ignores subsequent validations due to an incompatible type conversion.
 */
public final class InetAddressValidatorNoOp
	extends AbstractObjectValidatorNoOp<InetAddressValidator, InetAddress>
	implements InetAddressValidator
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	InetAddressValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected InetAddressValidator getThis()
	{
		return this;
	}

	@Override
	public InetAddressValidator isIpV4()
	{
		return this;
	}

	@Override
	public InetAddressValidator isIpV6()
	{
		return this;
	}
}
