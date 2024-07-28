/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.validator;

import com.github.cowwoc.pouch.core.WrappedCheckedException;
import com.github.cowwoc.requirements.java.internal.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.message.StringMessages;
import com.github.cowwoc.requirements.java.internal.util.ObjectAndSize;
import com.github.cowwoc.requirements.java.validator.BigDecimalValidator;
import com.github.cowwoc.requirements.java.validator.BigIntegerValidator;
import com.github.cowwoc.requirements.java.validator.InetAddressValidator;
import com.github.cowwoc.requirements.java.validator.PathValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveBooleanValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveByteValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveCharacterValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveDoubleValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveFloatValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveLongValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveShortValidator;
import com.github.cowwoc.requirements.java.validator.StringValidator;
import com.github.cowwoc.requirements.java.validator.UriValidator;
import com.github.cowwoc.requirements.java.validator.UrlValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.validator.PrimitiveIntegerValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveUnsignedIntegerValidator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder.quoteName;

public final class StringValidatorImpl extends AbstractObjectValidator<StringValidator, String>
	implements StringValidator
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
	public StringValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<String> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public StringValidator isEmpty()
	{
		if (value.isNull())
			onNull();
		switch (value.test(String::isEmpty))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isEmpty(scope, this).toString());
		}
		return this;
	}

	@Override
	public StringValidator isNotEmpty()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !value.isEmpty()))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isNotEmpty(scope, this).toString());
		}
		return this;
	}

	@Override
	public StringValidator isBlank()
	{
		if (value.isNull())
			onNull();
		switch (value.test(String::isBlank))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.isBlank(scope, this).toString());
		}
		return this;
	}

	@Override
	public StringValidator isNotBlank()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !value.isBlank()))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.isNotBlank(scope, this).toString());
		}
		return this;
	}

	@Override
	public StringValidator trim()
	{
		return update(String::trim, "trim()");
	}

	/**
	 * Updates the value.
	 *
	 * @param operation the operation to apply on the value
	 * @param name      the name of the operation
	 */
	private StringValidator update(Function<String, String> operation, String name)
	{
		if (value.isNull())
			onNull();
		MaybeUndefined<String> oldValue = value;
		MaybeUndefined<String> newValue = oldValue.mapDefined(operation);
		if (!oldValue.equals(newValue))
			this.name += "." + name;
		value = newValue;
		return this;
	}

	@Override
	public StringValidator isTrimmed()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value ->
		{
			int length = value.length();
			if (length == 0)
				return true;
			boolean foundWhitespace = (value.charAt(0) & 0xff) <= ' ';
			if (!foundWhitespace && length > 1)
				foundWhitespace = (value.charAt(length - 1) & 0xff) <= ' ';
			return !foundWhitespace;
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.isTrimmed(scope, this).toString());
		}
		return this;
	}

	@Override
	public StringValidator strip()
	{
		return update(String::strip, "strip()");
	}

	@Override
	public StringValidator isStripped()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value ->
		{
			int length = value.length();
			if (length == 0)
				return true;
			int codepoint = value.codePointAt(0);
			boolean foundWhitespace = Character.isWhitespace(codepoint);
			if (!foundWhitespace && length > 1)
			{
				codepoint = value.codePointAt(length - 1);
				foundWhitespace = Character.isWhitespace(codepoint);
			}
			return !foundWhitespace;
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.isStripped(scope, this).toString());
		}
		return this;
	}

	@Override
	public PrimitiveByteValidator asPrimitiveByte()
	{
		if (value.isNull())
			onNull();
		return convertNotNull(Byte::valueOf, ignored ->
				quoteName(name) + " must be a number from " + Byte.MIN_VALUE + " to " + Byte.MAX_VALUE + ".",
			value -> new PrimitiveByteValidatorImpl(scope, configuration, name, value, context, failures));
	}

	/**
	 * Converts the value to a different type if it is defined and not null. If the value is undefined or null,
	 * no action is taken.
	 *
	 * @param <U>            the type to convert the value to
	 * @param <S>            the type of validator returned by {@code factory}
	 * @param mapper         the function to apply to the value if it is defined and not null. If the value is
	 *                       undefined or null, no action is taken.
	 * @param messageUpdater a function that transforms the exception message into a failure message
	 * @param factory        a function that returns a validator for the converted value
	 * @return the converted value
	 */
	private <U, S> S convertNotNull(Function<String, U> mapper, Function<String, String> messageUpdater,
		ValidatorFactory<U, S> factory)
	{
		try
		{
			return factory.apply(value.mapNotNull(mapper));
		}
		catch (IllegalArgumentException e)
		{
			MessageBuilder messageBuilder = new MessageBuilder(scope, this, messageUpdater.apply(e.getMessage()));
			value.ifDefined(value -> messageBuilder.withContext(value, "actual"));
			addIllegalArgumentException(messageBuilder.toString(), e.getCause());
			return factory.apply(MaybeUndefined.undefined());
		}
	}

	@Override
	public PrimitiveShortValidator asPrimitiveShort()
	{
		if (value.isNull())
			onNull();
		return convertNotNull(Short::valueOf, ignored ->
				quoteName(name) + " must be a number from " + Short.MIN_VALUE + " to " + Short.MAX_VALUE + ".",
			value -> new PrimitiveShortValidatorImpl(scope, configuration, name, value, context, failures));
	}

	@Override
	public PrimitiveIntegerValidator asPrimitiveInteger()
	{
		if (value.isNull())
			onNull();
		return convertNotNull(Integer::valueOf, ignored ->
				quoteName(name) + " must be a number from " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE + ".",
			value -> new PrimitiveIntegerValidatorImpl(scope, configuration, name, value, context, failures));
	}

	@Override
	public PrimitiveLongValidator asPrimitiveLong()
	{
		if (value.isNull())
			onNull();
		return convertNotNull(Long::valueOf, ignored ->
				quoteName(name) + " must be a number from " + Long.MIN_VALUE + " to " + Long.MAX_VALUE + ".",
			value -> new PrimitiveLongValidatorImpl(scope, configuration, name, value, context, failures));
	}

	@Override
	public PrimitiveFloatValidator asPrimitiveFloat()
	{
		if (value.isNull())
			onNull();
		return convertNotNull(Float::valueOf, ignored ->
				quoteName(name) + " must be a number from " + Float.MIN_VALUE + " to " + Float.MAX_VALUE + ".",
			value -> new PrimitiveFloatValidatorImpl(scope, configuration, name, value, context, failures));
	}

	@Override
	public PrimitiveDoubleValidator asPrimitiveDouble()
	{
		if (value.isNull())
			onNull();
		return convertNotNull(Double::valueOf, ignored ->
				quoteName(name) + " must be a number from " + Double.MIN_VALUE + " to " + Double.MAX_VALUE + ".",
			value -> new PrimitiveDoubleValidatorImpl(scope, configuration, name, value, context, failures));
	}

	@Override
	public PrimitiveBooleanValidator asPrimitiveBoolean()
	{
		if (value.isNull())
			onNull();
		return convertNotNull(Boolean::valueOf, ignored -> quoteName(name) + " must be true or false.",
			value -> new PrimitiveBooleanValidatorImpl(scope, configuration, name, value, context, failures));
	}

	@Override
	public PrimitiveCharacterValidator asPrimitiveCharacter()
	{
		if (value.isNull())
			onNull();
		return convertNotNull(value ->
		{
			PrimitiveUnsignedIntegerValidator validator = new JavaValidatorsImpl(scope, configuration).
				checkIf(value, name).length().isEqualTo(1);
			and(validator);
			if (validator.validationFailed())
				throw new IllegalArgumentException(quoteName(name) + " must be a character.");
			return value.charAt(0);
		}, message -> message, value ->
			new PrimitiveCharacterValidatorImpl(scope, configuration, name, value, context, failures));
	}

	@Override
	public BigIntegerValidator asBigInteger()
	{
		return convertNotNull(BigInteger::new, ignored -> quoteName(name) + " must be a whole number.",
			value -> new BigIntegerValidatorImpl(scope, configuration, name, value, context, failures));
	}

	@Override
	public BigDecimalValidator asBigDecimal()
	{
		return convertNotNull(BigDecimal::new, ignored -> quoteName(name) + " must be a floating-point number.",
			value -> new BigDecimalValidatorImpl(scope, configuration, name, value, context, failures));
	}

	@Override
	public PathValidator asPath()
	{
		return convertNotNull(Path::of, ignored -> quoteName(name) + " must be a path.",
			value -> new PathValidatorImpl(scope, configuration, name, value, context, failures));
	}

	@Override
	public UriValidator asUri()
	{
		return convertNotNull(URI::create, ignored -> quoteName(name) + " must be a URI.",
			value -> new UriValidatorImpl(scope, configuration, name, value, context, failures));
	}

	@Override
	public UrlValidator asUrl()
	{
		return convertNotNull(value -> WrappedCheckedException.wrap(() -> URI.create(value).toURL()),
			ignored -> quoteName(name) + " must be a URL.", value ->
				new UrlValidatorImpl(scope, configuration, name, value, context, failures));
	}

	@Override
	public InetAddressValidator asInetAddress()
	{
		return convertNotNull(this::parseInetAddress,
			ignored -> quoteName(name) + " must contain a valid IP address or hostname format.", value ->
				new InetAddressValidatorImpl(scope, configuration, name, value, context, failures));
	}

	private InetAddress parseInetAddress(String value)
	{
		// IPv4 must start with a digit. IPv6 must start with a colon.
		if (value.isEmpty())
			throw new IllegalArgumentException("value may not be empty");

		char firstCharacter = value.charAt(0);
		if (Character.digit(firstCharacter, 16) == -1 && (firstCharacter != ':'))
			throw new IllegalArgumentException("IPv4 must start with a digit. IPv6 must start with a colon.");

		InetAddress address;
		try
		{
			address = InetAddress.getByName(value);
			return address;
		}
		catch (UnknownHostException e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public StringValidator startsWith(String prefix)
	{
		scope.getInternalValidators().requireThat(prefix, "prefix").isNotNull();
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.startsWith(prefix)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.startsWith(scope, this, prefix).toString());
		}
		return this;
	}

	@Override
	public StringValidator doesNotStartWith(String prefix)
	{
		scope.getInternalValidators().requireThat(prefix, "prefix").isNotNull();
		if (value.isNull())
			onNull();
		switch (value.test(value -> !value.startsWith(prefix)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.doesNotStartWith(scope, this, prefix).toString());
		}
		return this;
	}

	@Override
	public StringValidator endsWith(String suffix)
	{
		scope.getInternalValidators().requireThat(suffix, "suffix").isNotNull();
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.endsWith(suffix)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.endsWith(scope, this, suffix).toString());
		}
		return this;
	}

	@Override
	public StringValidator doesNotEndWith(String suffix)
	{
		scope.getInternalValidators().requireThat(suffix, "suffix").isNotNull();
		if (value.isNull())
			onNull();
		switch (value.test(value -> !value.endsWith(suffix)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.doesNotEndWith(scope, this, suffix).toString());
		}
		return this;
	}

	@Override
	public StringValidator contains(String expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.contains(expected)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.contains(scope, this, expected).toString());
		}
		return this;
	}

	@Override
	public StringValidator doesNotContain(String unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		if (value.isNull())
			onNull();
		switch (value.test(value -> !value.contains(unwanted)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.doesNotContain(scope, this, unwanted).toString());
		}
		return this;
	}

	@Override
	public StringValidator matches(String regex)
	{
		scope.getInternalValidators().requireThat(regex, "regex").isNotNull();
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.matches(regex)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.matches(scope, this, regex).toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator length()
	{
		if (value.isNull())
			onNull();
		return new ObjectSizeValidatorImpl(scope, configuration, name,
			value.nullToUndefined().mapDefined(value -> new ObjectAndSize(value, value.length())),
			name + ".length()", Pluralizer.CHARACTER, context, failures);
	}
}