/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.UriValidator;
import org.bitbucket.cowwoc.requirements.java.UrlValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;

import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

/**
 * A UrlValidator that ignores subsequent validations due to an incompatible type conversion.
 */
public final class UrlValidatorNoOp extends AbstractObjectValidatorNoOp<UrlValidator, URL>
	implements UrlValidator
{

	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public UrlValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected UrlValidator getThis()
	{
		return this;
	}

	@Override
	public UriValidator asUri()
	{
		return new UriValidatorNoOp(failures);
	}

	@Override
	public UrlValidator asUri(Consumer<UriValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}