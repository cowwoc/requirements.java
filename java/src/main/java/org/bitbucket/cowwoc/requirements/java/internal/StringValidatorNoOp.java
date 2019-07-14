/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.InetAddressValidator;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;
import org.bitbucket.cowwoc.requirements.java.StringValidator;
import org.bitbucket.cowwoc.requirements.java.UriValidator;
import org.bitbucket.cowwoc.requirements.java.UrlValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;

import java.util.List;
import java.util.function.Consumer;

/**
 * A StringValidator that ignores subsequent validations due to an incompatible type conversion.
 */
public final class StringValidatorNoOp extends AbstractObjectValidatorNoOp<StringValidator, String>
	implements StringValidator
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public StringValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected StringValidator getThis()
	{
		return this;
	}

	@Override
	public StringValidator startsWith(String prefix)
	{
		return this;
	}

	@Override
	public StringValidator doesNotStartWith(String prefix)
	{
		return this;
	}

	@Override
	public StringValidator endsWith(String suffix)
	{
		return this;
	}

	@Override
	public StringValidator doesNotEndWith(String suffix)
	{
		return this;
	}

	@Override
	public StringValidator contains(String expected)
	{
		return this;
	}

	@Override
	public StringValidator doesNotContain(String value)
	{
		return this;
	}

	@Override
	public StringValidator isEmpty()
	{
		return this;
	}

	@Override
	public StringValidator isNotEmpty()
	{
		return this;
	}

	@Override
	public SizeValidator length()
	{
		return new SizeValidatorNoOp(failures);
	}

	@Override
	public StringValidator length(Consumer<SizeValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public StringValidator trim()
	{
		return this;
	}

	@Override
	public StringValidator isTrimmed()
	{
		return this;
	}

	@Override
	public InetAddressValidator asInetAddress()
	{
		return new InetAddressValidatorNoOp(failures);
	}

	@Override
	public StringValidator asInetAddress(Consumer<InetAddressValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public UriValidator asUri()
	{
		return new UriValidatorNoOp(failures);
	}

	@Override
	public StringValidator asUri(Consumer<UriValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public UrlValidator asUrl()
	{
		return new UrlValidatorNoOp(failures);
	}

	@Override
	public StringValidator asUrl(Consumer<UrlValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public StringValidator asString()
	{
		return this;
	}
}
