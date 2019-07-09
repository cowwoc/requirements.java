/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.InetAddressValidator;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
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
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public StringValidatorImpl(ApplicationScope scope, Configuration config, String name, String actual,
	                           List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected StringValidator getThis()
	{
		return this;
	}

	@Override
	protected StringValidator getNoOp()
	{
		return new StringValidatorNoOp(failures);
	}

	@Override
	public StringValidator isEmpty()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (!actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be empty.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public StringValidator isNotEmpty()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not be empty");
			failures.add(failure);
		}
		return this;
	}

	@Override
	public StringValidator trim()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		String trimmed = actual.trim();
		if (trimmed.equals(actual))
			return this;
		this.name = name + ".trim()";
		this.actual = trimmed;
		return this;
	}

	@Override
	public StringValidator isTrimmed()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		String trimmed = actual.trim();
		if (!trimmed.equals(actual))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not contain leading or trailing whitespace").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public InetAddressValidator asInetAddress()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return new InetAddressValidatorNoOp(failures);
		}
		// IPv4 must start with a digit. IPv6 must start with a colon.
		if (actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not be empty");
			failures.add(failure);
			return new InetAddressValidatorNoOp(failures);
		}

		char firstCharacter = actual.charAt(0);
		if (Character.digit(firstCharacter, 16) == -1 && (firstCharacter != ':'))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must contain a valid IP address or hostname format.").
				addContext("Actual", actual);
			failures.add(failure);
			return new InetAddressValidatorNoOp(failures);
		}
		InetAddress address;
		try
		{
			address = InetAddress.getByName(actual);
		}
		catch (UnknownHostException e)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must contain a valid IP address or hostname format.").
				setCause(e).
				addContext("Actual", actual);
			failures.add(failure);
			return new InetAddressValidatorNoOp(failures);
		}
		return new InetAddressValidatorImpl(scope, config, name, address, failures);
	}

	@Override
	public StringValidator asInetAddress(Consumer<InetAddressValidator> consumer)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asInetAddress());
		return this;
	}

	@Override
	public UriValidator asUri()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return new UriValidatorNoOp(failures);
		}
		try
		{
			URI uri = URI.create(actual);
			return new UriValidatorImpl(scope, config, name, uri, failures);
		}
		catch (IllegalArgumentException e)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " does not contain a valid URI format").
				setCause(e).
				addContext("Actual", actual);
			failures.add(failure);
			return new UriValidatorNoOp(failures);
		}
	}

	@Override
	public StringValidator asUri(Consumer<UriValidator> consumer)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asUri());
		return this;
	}

	@Override
	public UrlValidator asUrl()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return new UrlValidatorNoOp(failures);
		}
		try
		{
			URL url = new URL(actual);
			return new UrlValidatorImpl(scope, config, name, url, failures);
		}
		catch (MalformedURLException e)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " is not a valid URL").
				setCause(e).
				addContext("Actual", actual);
			failures.add(failure);
			return new UrlValidatorNoOp(failures);
		}
	}

	@Override
	public StringValidator asUrl(Consumer<UrlValidator> consumer)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asUrl());
		return this;
	}

	@Override
	public StringValidator startsWith(String prefix)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(prefix, "prefix").isNotNull();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (!actual.startsWith(prefix))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must start with \"" + prefix + "\".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public StringValidator doesNotStartWith(String prefix)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(prefix, "prefix").isNotNull();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.startsWith(prefix))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not start with \"" + prefix + "\".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public StringValidator endsWith(String suffix)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(suffix, "suffix").isNotNull();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (!actual.endsWith(suffix))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must end with \"" + suffix + "\".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public StringValidator doesNotEndWith(String suffix)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(suffix, "suffix").isNotNull();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.endsWith(suffix))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not end with \"" + suffix + "\".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public StringValidator contains(String expected)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (!actual.contains(expected))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must contain \"" + expected + "\".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public StringValidator doesNotContain(String value)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.contains(value))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not contain \"" + value + "\".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator length()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return new SizeValidatorNoOp(failures);
		}
		return new SizeValidatorImpl(scope, config, name, actual, name + ".length()", actual.length(),
			Pluralizer.CHARACTER, failures);
	}

	@Override
	public StringValidator length(Consumer<SizeValidator> consumer)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(length());
		return this;
	}
}