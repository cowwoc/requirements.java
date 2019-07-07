/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.InetAddressValidator;
import org.bitbucket.cowwoc.requirements.java.StringValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;

import java.net.InetAddress;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

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
	public InetAddressValidatorNoOp(List<ValidationFailure> failures)
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

	@Override
	public InetAddressValidator isEqualTo(Object expected)
	{
		return this;
	}

	@Override
	public InetAddressValidator isEqualTo(Object expected, String name)
	{
		return this;
	}

	@Override
	public InetAddressValidator isNotEqualTo(Object other)
	{
		return this;
	}

	@Override
	public InetAddressValidator isNotEqualTo(Object other, String name)
	{
		return this;
	}

	@Override
	public InetAddressValidator isSameObjectAs(Object expected, String name)
	{
		return this;
	}

	@Override
	public InetAddressValidator isNotSameObjectAs(Object other, String name)
	{
		return this;
	}

	@Override
	public InetAddressValidator isOneOf(Collection<? super InetAddress> collection)
	{
		return this;
	}

	@Override
	public InetAddressValidator isNotOneOf(Collection<? super InetAddress> collection)
	{
		return this;
	}

	@Override
	public InetAddressValidator isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public InetAddressValidator isNotInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public InetAddressValidator isNull()
	{
		return this;
	}

	@Override
	public InetAddressValidator isNotNull()
	{
		return this;
	}

	@Override
	public StringValidator asString()
	{
		return new StringValidatorNoOp(failures);
	}

	@Override
	public InetAddressValidator asString(Consumer<StringValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
