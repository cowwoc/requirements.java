/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.InetAddressValidator;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;
import org.bitbucket.cowwoc.requirements.java.StringValidator;
import org.bitbucket.cowwoc.requirements.java.UriValidator;
import org.bitbucket.cowwoc.requirements.java.UrlValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.Pluralizer;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.function.Consumer;

/**
 * Default implementation of {@code StringValidator}.
 */
public final class StringValidatorImpl extends AbstractObjectValidator<StringValidator, String>
	implements StringValidator
{
	/**
	 * @param scope    the application configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code name}, {@code config} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public StringValidatorImpl(ApplicationScope scope, String name, String actual, Configuration config,
	                           List<ValidationFailure> failures)
	{
		super(scope, name, actual, config, failures);
	}

	@Override
	public StringValidator isEmpty()
	{
		if (!actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " must be empty.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public StringValidator isNotEmpty()
	{
		if (actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " may not be empty");
			failures.add(failure);
		}
		return this;
	}

	@Override
	public StringValidator trim()
	{
		String trimmed = actual.trim();
		if (trimmed.equals(actual))
			return this;
		return new StringValidatorImpl(scope, name + ".trim()", trimmed, config, failures);
	}

	@Override
	public StringValidator isTrimmed()
	{
		String trimmed = actual.trim();
		if (!trimmed.equals(actual))
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " may not contain leading or trailing whitespace").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public InetAddressValidator asInetAddress()
	{
		// IPv4 must start with a digit. IPv6 must start with a colon.
		if (actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " may not be empty");
			failures.add(failure);
			return new InetAddressValidatorNoOp(scope, config, failures);
		}

		char firstCharacter = actual.charAt(0);
		if (Character.digit(firstCharacter, 16) == -1 && (firstCharacter != ':'))
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " must contain a valid IP address or hostname format.").
				addContext("Actual", actual);
			failures.add(failure);
			return new InetAddressValidatorNoOp(scope, config, failures);
		}
		InetAddress address;
		try
		{
			address = InetAddress.getByName(actual);
		}
		catch (UnknownHostException e)
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " must contain a valid IP address or hostname format.").
				setCause(e).
				addContext("Actual", actual);
			failures.add(failure);
			return new InetAddressValidatorNoOp(scope, config, failures);
		}
		return new InetAddressValidatorImpl(scope, name, address, config, failures);
	}

	@Override
	public StringValidator asInetAddress(Consumer<InetAddressValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asInetAddress());
		return this;
	}

	@Override
	public UriValidator asUri()
	{
		try
		{
			URI uri = URI.create(actual);
			return new UriValidatorImpl(scope, name, uri, config, failures);
		}
		catch (IllegalArgumentException e)
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " does not contain a valid URI format").
				setCause(e).
				addContext("Actual", actual);
			failures.add(failure);
			return new UriValidatorNoOp(scope, config, failures);
		}
	}

	@Override
	public StringValidator asUri(Consumer<UriValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asUri());
		return this;
	}

	@Override
	public UrlValidator asUrl()
	{
		try
		{
			URL url = new URL(actual);
			return new UrlValidatorImpl(scope, name, url, config, failures);
		}
		catch (MalformedURLException e)
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " is not a valid URL").
				setCause(e).
				addContext("Actual", actual);
			failures.add(failure);
			return new UrlValidatorNoOp(scope, config, failures);
		}
	}

	@Override
	public StringValidator asUrl(Consumer<UrlValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asUrl());
		return this;
	}

	@Override
	public StringValidator startsWith(String prefix)
	{
		if (!actual.startsWith(prefix))
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " must start with \"" + prefix + "\".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public StringValidator doesNotStartWith(String prefix)
	{
		if (actual.startsWith(prefix))
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " may not start with \"" + prefix + "\".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public StringValidator endsWith(String suffix)
	{
		if (!actual.endsWith(suffix))
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " must end with \"" + suffix + "\".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public StringValidator doesNotEndWith(String suffix)
	{
		if (actual.endsWith(suffix))
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " may not end with \"" + suffix + "\".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public StringValidator contains(String expected)
	{
		if (!actual.contains(expected))
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " must contain \"" + expected + "\".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public StringValidator doesNotContain(String value)
	{
		if (!actual.contains(value))
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " may not contain \"" + value + "\".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator length()
	{
		return new SizeValidatorImpl(scope, name, actual, name + ".length()", actual.length(),
			Pluralizer.CHARACTER, config, failures);
	}

	@Override
	public StringValidator length(Consumer<SizeValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(length());
		return this;
	}
}