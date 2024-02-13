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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class UriValidatorImpl extends AbstractObjectValidator<UriValidator, URI>
	implements UriValidator
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
	public UriValidatorImpl(ApplicationScope scope, AbstractValidator<?> validator, String name, URI value)
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
	public UriValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		URI value)
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
	private UriValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		URI value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public UriValidator isAbsolute()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be an absolute.").toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!value.isAbsolute())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be absolute.").toString());
		}
		return this;
	}

	@Override
	public UrlValidator asUrl()
	{
		if (hasFailed())
			return new UrlValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
			return new UrlValidatorImpl(scope, this, name, null);
		}
		else
		{
			try
			{
				URL url = value.toURL();
				return new UrlValidatorImpl(scope, this, name, url);
			}
			catch (MalformedURLException | IllegalArgumentException e)
			{
				addFailure(new MessageBuilder(scope, this, name + " is not a valid URL.").
					putContext(value, "Actual").toString(), e, IllegalArgumentException::new);
				return new UrlValidatorImpl(scope, this, name, null);
			}
		}
	}
}