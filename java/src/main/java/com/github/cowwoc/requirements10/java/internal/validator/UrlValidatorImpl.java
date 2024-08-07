/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.pouch.core.WrappedCheckedException;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.message.UriMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.validator.UriValidator;
import com.github.cowwoc.requirements10.java.validator.UrlValidator;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public final class UrlValidatorImpl extends AbstractObjectValidator<UrlValidator, URL>
	implements UrlValidator
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
	public UrlValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<URL> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public UriValidator asUri()
	{
		Throwable cause;
		MaybeUndefined<URI> newValue;
		try
		{
			newValue = value.nullToUndefined().mapDefined(value ->
			{
				try
				{
					return value.toURI();
				}
				catch (URISyntaxException e)
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
		{
			addIllegalArgumentException(
				UriMessages.asUri(this).toString(), cause);
		}
		return new UriValidatorImpl(scope, configuration, name, newValue, context, failures);
	}
}