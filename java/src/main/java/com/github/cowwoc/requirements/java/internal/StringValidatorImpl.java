/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.BigDecimalValidator;
import com.github.cowwoc.requirements.java.BooleanValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.InetAddressValidator;
import com.github.cowwoc.requirements.java.IntegerValidator;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.SizeValidator;
import com.github.cowwoc.requirements.java.StringValidator;
import com.github.cowwoc.requirements.java.UriValidator;
import com.github.cowwoc.requirements.java.UrlValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;

import java.math.BigDecimal;
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
	 * Creates a StringValidatorImpl with existing validation failures.
	 *
	 * @param scope        the application configuration
	 * @param config       the instance configuration
	 * @param name         the name of the value
	 * @param actual       the actual value
	 * @param failures     the list of validation failures
	 * @param fatalFailure true if validation stopped as the result of a fatal failure
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is blank.
	 */
	public StringValidatorImpl(ApplicationScope scope, Configuration config, String name, String actual,
		List<ValidationFailure> failures, boolean fatalFailure)
	{
		super(scope, config, name, actual, failures, fatalFailure);
	}

	@Override
	protected StringValidator getThis()
	{
		return this;
	}

	@Override
	public StringValidator isEmpty()
	{
		if (fatalFailure)
			return this;
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (!actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be empty.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public StringValidator isNotEmpty()
	{
		if (fatalFailure)
			return this;
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not be empty");
			addFailure(failure);
		}
		return this;
	}

	@Override
	public StringValidator isBlank()
	{
		if (fatalFailure)
			return this;
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (!actual.isBlank())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be empty or contain only white space codepoints.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public StringValidator isNotBlank()
	{
		if (fatalFailure)
			return this;
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (actual.isBlank())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not be empty or contain only white space codepoints.");
			addFailure(failure);
		}
		return this;
	}

	@Override
	public StringValidator trim()
	{
		if (fatalFailure)
			return this;
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
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
		if (fatalFailure)
			return this;
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		String trimmed = actual.trim();
		if (!trimmed.equals(actual))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not contain leading or trailing white space").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public StringValidator strip()
	{
		if (fatalFailure)
			return this;
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		String stripped = actual.strip();
		if (stripped.equals(actual))
			return this;
		this.name = name + ".strip()";
		this.actual = stripped;
		return this;
	}

	@Override
	public StringValidator isStripped()
	{
		if (fatalFailure)
			return this;
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		String stripped = actual.strip();
		if (!stripped.equals(actual))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not contain leading or trailing white space").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public InetAddressValidator asInetAddress()
	{
		if (fatalFailure)
			return new InetAddressValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		if (actual == null)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " must be an InetAddress.").
				addContext("Actual", this.actual);
			addFailure(failure);
			fatalFailure = true;
			return new InetAddressValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		}
		// IPv4 must start with a digit. IPv6 must start with a colon.
		if (actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not be empty");
			addFailure(failure);
			fatalFailure = true;
			return new InetAddressValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		}

		char firstCharacter = actual.charAt(0);
		if (Character.digit(firstCharacter, 16) == -1 && (firstCharacter != ':'))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must contain a valid IP address or hostname format.").
				addContext("Actual", actual);
			addFailure(failure);
			fatalFailure = true;
			return new InetAddressValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		}
		InetAddress address;
		try
		{
			address = InetAddress.getByName(actual);
		}
		catch (UnknownHostException e)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must contain a valid IP address or hostname format.").
				setCause(e).
				addContext("Actual", actual);
			addFailure(failure);
			fatalFailure = true;
			return new InetAddressValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		}
		return new InetAddressValidatorImpl(scope, config, name, address, getFailures(), fatalFailure);
	}

	@Override
	public StringValidator asInetAddress(Consumer<InetAddressValidator> consumer)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asInetAddress());
		return this;
	}

	@Override
	public UriValidator asUri()
	{
		if (fatalFailure)
			return new UriValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		if (actual == null)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " must be a URI.").
				addContext("Actual", this.actual);
			addFailure(failure);
			fatalFailure = true;
			return new UriValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		}
		try
		{
			URI uri = URI.create(actual);
			return new UriValidatorImpl(scope, config, name, uri, getFailures(), fatalFailure);
		}
		catch (IllegalArgumentException e)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " does not contain a valid URI format").
				setCause(e).
				addContext("Actual", actual);
			addFailure(failure);
			fatalFailure = true;
			return new UriValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		}
	}

	@Override
	public StringValidator asUri(Consumer<UriValidator> consumer)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asUri());
		return this;
	}

	@Override
	public UrlValidator asUrl()
	{
		if (fatalFailure)
			return new UrlValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		if (actual == null)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " must be a URL.").
				addContext("Actual", null);
			addFailure(failure);
			fatalFailure = true;
			return new UrlValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		}
		try
		{
			URL url = new URL(actual);
			return new UrlValidatorImpl(scope, config, name, url, getFailures(), fatalFailure);
		}
		catch (MalformedURLException e)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " is not a valid URL").
				setCause(e).
				addContext("Actual", actual);
			addFailure(failure);
			return new UrlValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		}
	}

	@Override
	public StringValidator asUrl(Consumer<UrlValidator> consumer)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asUrl());
		return this;
	}

	@Override
	public BooleanValidator asBoolean()
	{
		if (fatalFailure)
			return new BooleanValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		if (actual == null)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " must be a Boolean.").
				addContext("Actual", this.actual);
			addFailure(failure);
			fatalFailure = true;
			return new BooleanValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		}
		Boolean actualBoolean = Boolean.parseBoolean(actual);
		return new BooleanValidatorImpl(scope, config, name, actualBoolean, getFailures(), fatalFailure);
	}

	@Override
	public StringValidator asBoolean(Consumer<BooleanValidator> consumer)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asBoolean());
		return this;
	}

	@Override
	public IntegerValidator<Short> asShort()
	{
		if (fatalFailure)
			return new ShortValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		if (actual == null)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " must be a Short.").
				addContext("Actual", this.actual);
			addFailure(failure);
			fatalFailure = true;
			return new ShortValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		}
		Short actualShort = Short.parseShort(actual);
		return new ShortValidatorImpl(scope, config, name, actualShort, getFailures(), fatalFailure);
	}

	@Override
	public StringValidator asShort(Consumer<IntegerValidator<Short>> consumer)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asShort());
		return this;
	}

	@Override
	public IntegerValidator<Integer> asInteger()
	{
		if (fatalFailure)
			return new IntegerValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		if (actual == null)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " must be a Integer.").
				addContext("Actual", this.actual);
			addFailure(failure);
			fatalFailure = true;
			return new IntegerValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		}
		Integer actualInteger = Integer.parseInt(actual);
		return new IntegerValidatorImpl(scope, config, name, actualInteger, getFailures(), fatalFailure);
	}

	@Override
	public StringValidator asInteger(Consumer<IntegerValidator<Integer>> consumer)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asInteger());
		return this;
	}

	@Override
	public IntegerValidator<Long> asLong()
	{
		if (fatalFailure)
			return new LongValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		if (actual == null)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " must be a Long.").
				addContext("Actual", this.actual);
			addFailure(failure);
			fatalFailure = true;
			return new LongValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		}
		Long actualLong = Long.parseLong(actual);
		return new LongValidatorImpl(scope, config, name, actualLong, getFailures(), fatalFailure);
	}

	@Override
	public StringValidator asLong(Consumer<IntegerValidator<Long>> consumer)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asLong());
		return this;
	}

	@Override
	public BigDecimalValidator asBigDecimal()
	{
		if (fatalFailure)
			return new BigDecimalValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		if (actual == null)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " must be a BigDecimal.").
				addContext("Actual", this.actual);
			addFailure(failure);
			fatalFailure = true;
			return new BigDecimalValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		}
		BigDecimal actualBigDecimal = new BigDecimal(actual);
		return new BigDecimalValidatorImpl(scope, config, name, actualBigDecimal, getFailures(), fatalFailure);
	}

	@Override
	public StringValidator asBigDecimal(Consumer<BigDecimalValidator> consumer)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asBigDecimal());
		return this;
	}

	@Override
	public StringValidator startsWith(String prefix)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(prefix, "prefix").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (!actual.startsWith(prefix))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must start with \"" + prefix + "\".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public StringValidator doesNotStartWith(String prefix)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(prefix, "prefix").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (actual.startsWith(prefix))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not start with \"" + prefix + "\".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public StringValidator endsWith(String suffix)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(suffix, "suffix").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (!actual.endsWith(suffix))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must end with \"" + suffix + "\".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public StringValidator doesNotEndWith(String suffix)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(suffix, "suffix").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (actual.endsWith(suffix))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not end with \"" + suffix + "\".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public StringValidator contains(String expected)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (!actual.contains(expected))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must contain \"" + expected + "\".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public StringValidator doesNotContain(String value)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (actual.contains(value))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not contain \"" + value + "\".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public SizeValidator length()
	{
		if (fatalFailure)
		{
			return new SizeValidatorImpl(scope, config, name, List.of(), name + ".length()", 0,
				Pluralizer.CHARACTER, getFailures(), fatalFailure);
		}
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return new SizeValidatorImpl(scope, config, name, List.of(), name + ".length()", 0,
				Pluralizer.CHARACTER, getFailures(), fatalFailure);
		}
		return new SizeValidatorImpl(scope, config, name, actual, name + ".length()", actual.length(),
			Pluralizer.CHARACTER, getFailures(), fatalFailure);
	}

	@Override
	public StringValidator length(Consumer<SizeValidator> consumer)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(length());
		return this;
	}

	@Override
	@Deprecated
	public StringValidator asString()
	{
		return super.asString();
	}
}