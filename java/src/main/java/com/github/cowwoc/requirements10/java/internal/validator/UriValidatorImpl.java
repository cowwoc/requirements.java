/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.pouch.core.WrappedCheckedException;
import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.message.UriMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.validator.UriValidator;
import com.github.cowwoc.requirements10.java.validator.UrlValidator;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

public final class UriValidatorImpl extends AbstractObjectValidator<UriValidator, URI>
	implements UriValidator
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
	public UriValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<URI> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public UriValidator isAbsolute()
	{
		if (value.isNull())
			onNull();
		switch (value.test(URI::isAbsolute))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				UriMessages.isAbsolute(this).toString());
		}
		return this;
	}

	@Override
	public UrlValidator asUrl()
	{
		Throwable cause;
		MaybeUndefined<URL> newValue;
		try
		{
			newValue = value.nullToUndefined().mapDefined(value ->
			{
				try
				{
					return value.toURL();
				}
				catch (MalformedURLException | IllegalArgumentException e)
				{
					throw WrappedCheckedException.wrap(e);
				}
			});
			cause = null;
		}
		catch (WrappedCheckedException e)
		{
			newValue = MaybeUndefined.undefined();
			cause = e.getCause();
		}
		if (newValue.isUndefined())
			addIllegalArgumentException(UriMessages.asUrl(this).toString(), cause);
		return new UrlValidatorImpl(scope, configuration, name, newValue, context, failures);
	}
}