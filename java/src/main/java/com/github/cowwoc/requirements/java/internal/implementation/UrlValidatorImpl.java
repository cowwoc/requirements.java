/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.UriValidator;
import com.github.cowwoc.requirements.java.type.UrlValidator;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class UrlValidatorImpl extends AbstractObjectValidator<UrlValidator, URL>
	implements UrlValidator
{
	/**
	 * Creates a new validator as a result of a validation.
	 *
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public UrlValidatorImpl(ApplicationScope scope, AbstractValidator<?> validator, String name, URL value)
	{
		this(scope, validator.configuration(), name, value, validator.context, validator.failures);
	}

	/**
	 * Creates a new validator.
	 *
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public UrlValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		URL value)
	{
		this(scope, configuration, name, value, HashMap.newHashMap(2), new ArrayList<>(1));
	}

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	private UrlValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		URL value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public UriValidator asUri()
	{
		if (hasFailed())
			return new UriValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
			return new UriValidatorImpl(scope, this, name, null);
		}
		else
		{
			try
			{
				URI uri = value.toURI();
				return new UriValidatorImpl(scope, this, name, uri);
			}
			catch (URISyntaxException e)
			{
				addFailure(new MessageBuilder(scope, this, name + " is not a valid URI.").
					putContext(value, "Actual").toString(), e, IllegalArgumentException::new);
				return new UriValidatorImpl(scope, this, name, null);
			}
		}
	}
}