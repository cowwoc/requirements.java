/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.UriValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.UrlValidator;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;

import java.net.URI;
import java.util.List;
import java.util.function.Consumer;

/**
 * A UriValidator that ignores subsequent validations due to an incompatible type conversion.
 */
public final class UriValidatorNoOp extends AbstractObjectValidatorNoOp<UriValidator, URI>
	implements UriValidator
{

	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	UriValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected UriValidator getThis()
	{
		return this;
	}

	@Override
	public UriValidator isAbsolute()
	{
		return this;
	}

	@Override
	public UrlValidator asUrl()
	{
		return new UrlValidatorNoOp(failures);
	}

	@Override
	public UriValidator asUrl(Consumer<UrlValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asUrl());
		return this;
	}
}