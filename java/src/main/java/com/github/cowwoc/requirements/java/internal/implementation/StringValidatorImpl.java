/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ComparableMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.StringMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.type.BigDecimalValidator;
import com.github.cowwoc.requirements.java.type.BigIntegerValidator;
import com.github.cowwoc.requirements.java.type.InetAddressValidator;
import com.github.cowwoc.requirements.java.type.PathValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveBooleanValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveByteValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveCharacterValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveDoubleValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveFloatValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveIntegerValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveLongValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveShortValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveUnsignedIntegerValidator;
import com.github.cowwoc.requirements.java.type.StringValidator;
import com.github.cowwoc.requirements.java.type.UriValidator;
import com.github.cowwoc.requirements.java.type.UrlValidator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class StringValidatorImpl extends AbstractObjectValidator<StringValidator, String>
	implements StringValidator
{
	private static final Pluralizer PLURALIZER = Pluralizer.CHARACTER;

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
	public StringValidatorImpl(ApplicationScope scope, AbstractValidator<?> validator, String name,
		String value)
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
	public StringValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		String value)
	{
		this(scope, configuration, name, value, new HashMap<>(), new ArrayList<>());
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
	private StringValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		String value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	/**
	 * @param value a String
	 * @return true if the string does not contain any leading or trailing whitespace
	 */
	private static boolean isTrimmed(String value)
	{
		int length = value.length();
		if (length == 0)
			return true;
		boolean foundWhitedpace = (value.charAt(0) & 0xff) <= ' ';
		if (!foundWhitedpace && length > 1)
			foundWhitedpace = (value.charAt(length - 1) & 0xff) <= ' ';
		return !foundWhitedpace;
	}

	/**
	 * @param value a String
	 * @return true if the string does not contain any leading or trailing whitespace
	 */
	public static boolean isStripped(String value)
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
	}

	@Override
	public StringValidator isEmpty()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.isEmpty(scope, this, name, null, null, 0).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else if (!value.isEmpty())
		{
			addIllegalArgumentException(
				CollectionMessages.isEmpty(scope, this, name, value,
					name + ".length", value.length()).toString());
		}
		return self();
	}

	@Override
	public StringValidator isNotEmpty()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.isNotEmpty(scope, this, name).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else if (value.isEmpty())
		{
			addIllegalArgumentException(
				CollectionMessages.isNotEmpty(scope, this, name).toString());
		}
		return self();
	}

	@Override
	public StringValidator isBlank()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				StringMessages.isBlank(scope, this, name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else if (!value.isBlank())
		{
			addIllegalArgumentException(
				StringMessages.isBlank(scope, this, name, value).toString());
		}
		return self();
	}

	@Override
	public StringValidator isNotBlank()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				StringMessages.isNotBlank(scope, this, name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else if (value.isBlank())
		{
			addIllegalArgumentException(
				StringMessages.isNotBlank(scope, this, name, value).toString());
		}
		return self();
	}

	@Override
	public StringValidator trim()
	{
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else
			value = value.trim();
		name += ".trim()";
		return this;
	}

	@Override
	public StringValidator isTrimmed()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				StringMessages.isNotBlank(scope, this, name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else if (!isTrimmed(value))
		{
			addIllegalArgumentException(
				StringMessages.isNotBlank(scope, this, name, value).toString());
		}
		return self();
	}

	@Override
	public StringValidator strip()
	{
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else
			value = value.strip();
		name += ".strip()";
		return this;
	}

	@Override
	public StringValidator isStripped()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				StringMessages.isStripped(scope, this, name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else if (!isStripped(value))
		{
			addIllegalArgumentException(
				StringMessages.isStripped(scope, this, name, value).toString());
		}
		return self();
	}

	@Override
	public PrimitiveByteValidator asByte()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a byte.").
					toString());
			return new PrimitiveByteValidatorImpl(scope, this, name, (byte) 0);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveByteValidatorImpl(scope, this, name, (byte) 0);
		}

		byte valueAsByte;
		try
		{
			valueAsByte = Byte.parseByte(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a byte.").
				addContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new PrimitiveByteValidatorImpl(scope, this, name, (byte) 0);
		}
		return new PrimitiveByteValidatorImpl(scope, this, name, valueAsByte);
	}

	@Override
	public PrimitiveShortValidator asShort()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a short.").
					toString());
			return new PrimitiveShortValidatorImpl(scope, this, name, (short) 0);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveShortValidatorImpl(scope, this, name, (short) 0);
		}

		short valueAsShort;
		try
		{
			valueAsShort = Short.parseShort(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a short.").
				addContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new PrimitiveShortValidatorImpl(scope, this, name, (short) 0);
		}
		return new PrimitiveShortValidatorImpl(scope, this, name, valueAsShort);
	}

	@Override
	public PrimitiveIntegerValidator asInt()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a int.").
					toString());
			return new PrimitiveIntegerValidatorImpl(scope, this, name, 0);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveIntegerValidatorImpl(scope, this, name, 0);
		}

		int valueAsInt;
		try
		{
			valueAsInt = Integer.parseInt(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a int.").
				addContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new PrimitiveIntegerValidatorImpl(scope, this, name, 0);
		}
		return new PrimitiveIntegerValidatorImpl(scope, this, name, valueAsInt);
	}

	@Override
	public PrimitiveLongValidator asLong()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a long.").
					toString());
			return new PrimitiveLongValidatorImpl(scope, this, name, 0L);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveLongValidatorImpl(scope, this, name, 0L);
		}

		long valueAsLong;
		try
		{
			valueAsLong = Long.parseLong(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a long.").
				addContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new PrimitiveLongValidatorImpl(scope, this, name, 0L);
		}
		return new PrimitiveLongValidatorImpl(scope, this, name, valueAsLong);
	}

	@Override
	public PrimitiveFloatValidator asFloat()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a float.").
					toString());
			return new PrimitiveFloatValidatorImpl(scope, this, name, 0.0f);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveFloatValidatorImpl(scope, this, name, 0.0f);
		}

		float valueAsFloat;
		try
		{
			valueAsFloat = Float.parseFloat(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a float.").
				addContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new PrimitiveFloatValidatorImpl(scope, this, name, 0.0f);
		}
		return new PrimitiveFloatValidatorImpl(scope, this, name, valueAsFloat);
	}

	@Override
	public PrimitiveDoubleValidator asDouble()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a double.").
					toString());
			return new PrimitiveDoubleValidatorImpl(scope, this, name, 0.0f);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveDoubleValidatorImpl(scope, this, name, 0.0);
		}

		double valueAsDouble;
		try
		{
			valueAsDouble = Double.parseDouble(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a double.").
				addContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new PrimitiveDoubleValidatorImpl(scope, this, name, 0.0);
		}
		return new PrimitiveDoubleValidatorImpl(scope, this, name, valueAsDouble);
	}

	@Override
	public PrimitiveBooleanValidator asBoolean()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a boolean.").
					toString());
			return new PrimitiveBooleanValidatorImpl(scope, this, name, false);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveBooleanValidatorImpl(scope, this, name, false);
		}

		boolean valueAsBoolean = Boolean.parseBoolean(value);
		return new PrimitiveBooleanValidatorImpl(scope, this, name, valueAsBoolean);
	}

	@Override
	public PrimitiveCharacterValidator asChar()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a character.").
					toString());
			return new PrimitiveCharacterValidatorImpl(scope, this, name, '-');
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveCharacterValidatorImpl(scope, this, name, '-');
		}

		if (value.length() != 1)
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a character.").
					addContext(value, "Actual").
					toString());
			return new PrimitiveCharacterValidatorImpl(scope, this, name, '-');
		}
		return new PrimitiveCharacterValidatorImpl(scope, this, name, value.charAt(0));
	}

	@Override
	public BigIntegerValidator asBigInteger()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a BigInteger.").
					toString());
			return new BigIntegerValidatorImpl(scope, this, name, null);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new BigIntegerValidatorImpl(scope, this, name, null);
		}

		BigInteger valueAsBigInteger;
		try
		{
			valueAsBigInteger = new BigInteger(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a BigInteger.").
				addContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new BigIntegerValidatorImpl(scope, this, name, null);
		}
		return new BigIntegerValidatorImpl(scope, this, name, valueAsBigInteger);
	}

	@Override
	public BigDecimalValidator asBigDecimal()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a BigDecimal.").
					toString());
			return new BigDecimalValidatorImpl(scope, this, name, null);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new BigDecimalValidatorImpl(scope, this, name, null);
		}

		BigDecimal valueAsBigDecimal;
		try
		{
			valueAsBigDecimal = new BigDecimal(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a BigDecimal.").
				addContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new BigDecimalValidatorImpl(scope, this, name, null);
		}
		return new BigDecimalValidatorImpl(scope, this, name, valueAsBigDecimal);
	}

	@Override
	public PathValidator asPath()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a Path.").
					toString());
			return new PathValidatorImpl(scope, this, name, null);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PathValidatorImpl(scope, this, name, null);
		}

		Path valueAsPath;
		try
		{
			valueAsPath = Path.of(value);
		}
		catch (InvalidPathException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a Path.").
				addContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new PathValidatorImpl(scope, this, name, null);
		}
		return new PathValidatorImpl(scope, this, name, valueAsPath);
	}

	@Override
	public UriValidator asUri()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a URI.").
					toString());
			return new UriValidatorImpl(scope, this, name, null);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new UriValidatorImpl(scope, this, name, null);
		}

		try
		{
			URI uri = URI.create(value);
			return new UriValidatorImpl(scope, this, name, uri);
		}
		catch (IllegalArgumentException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a URI.").
				addContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new UriValidatorImpl(scope, this, name, null);
		}
	}

	@Override
	public UrlValidator asUrl()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a URL.").
					toString());
			return new UrlValidatorImpl(scope, this, name, null);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new UrlValidatorImpl(scope, this, name, null);
		}

		try
		{
			URL url = URI.create(value).toURL();
			return new UrlValidatorImpl(scope, this, name, url);
		}
		catch (MalformedURLException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a URL.").
				addContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new UrlValidatorImpl(scope, this, name, null);
		}
	}

	@Override
	public InetAddressValidator asInetAddress()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to an InetAddress.").
					toString());
			return new InetAddressValidatorImpl(scope, this, name, null);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new InetAddressValidatorImpl(scope, this, name, null);
		}

		// IPv4 must start with a digit. IPv6 must start with a colon.
		if (value.isEmpty())
		{
			addIllegalArgumentException(
				CollectionMessages.isNotEmpty(scope, this, name).toString());
			return new InetAddressValidatorImpl(scope, this, name, null);
		}

		char firstCharacter = value.charAt(0);
		if (Character.digit(firstCharacter, 16) == -1 && (firstCharacter != ':'))
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this,
					name + " must contain a valid IP address or hostname format.").
					addContext(value, "Actual").
					toString());
			return new InetAddressValidatorImpl(scope, this, name, null);
		}

		InetAddress address;
		try
		{
			address = InetAddress.getByName(value);
		}
		catch (UnknownHostException e)
		{
			addFailure(new MessageBuilder(scope, this,
				name + " must contain a valid IP address or hostname format.").
				addContext(value, "Actual").
				toString(), e, IllegalArgumentException::new);
			return new InetAddressValidatorImpl(scope, this, name, null);
		}
		return new InetAddressValidatorImpl(scope, this, name, address);
	}

	@Override
	public StringValidator startsWith(String prefix)
	{
		scope.getInternalValidator().requireThat("prefix", prefix).isNotNull();

		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this,
					name + " must start with \"" + prefix + "\".").
					toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else if (!value.startsWith(prefix))
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this,
					name + " must start with \"" + prefix + "\".").
					addContext(value, "Actual").
					toString());
		}
		return this;
	}

	@Override
	public StringValidator doesNotStartWith(String prefix)
	{
		scope.getInternalValidator().requireThat("prefix", prefix).isNotNull();

		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this,
					name + " may not start with \"" + prefix + "\".").
					toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else if (value.startsWith(prefix))
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this,
					name + " may not start with \"" + prefix + "\".").
					addContext(value, "Actual").
					toString());
		}
		return this;
	}

	@Override
	public StringValidator endsWith(String suffix)
	{
		scope.getInternalValidator().requireThat("suffix", suffix).isNotNull();

		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this,
					name + " must end with \"" + suffix + "\".").
					toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else if (!value.endsWith(suffix))
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this,
					name + " must end with \"" + suffix + "\".").
					addContext(value, "Actual").
					toString());
		}
		return this;
	}

	@Override
	public StringValidator doesNotEndWith(String suffix)
	{
		scope.getInternalValidator().requireThat("suffix", suffix).isNotNull();

		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this,
						name, null, "may not end with", null, "\"" + suffix + "\"").
					toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else if (value.endsWith(suffix))
		{
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this,
						name, null, "may not end with", null, "\"" + suffix + "\"").
					addContext(value, "Actual").
					toString());
		}
		return this;
	}

	@Override
	public StringValidator contains(String expected)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();

		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this,
					name + " must contain \"" + expected + "\".").
					toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else if (!value.contains(expected))
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this,
					name + " must contain \"" + expected + "\".").
					addContext(value, "Actual").
					toString());
		}
		return this;
	}

	@Override
	public StringValidator doesNotContain(String unwanted)
	{
		scope.getInternalValidator().requireThat("unwanted", unwanted).isNotNull();

		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this,
					name + " may not contain \"" + unwanted + "\".").
					toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else if (value.contains(unwanted))
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this,
					name + " may not contain \"" + unwanted + "\".").
					addContext(value, "Actual").
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator length()
	{
		if (hasFailed())
		{
			return new PrimitiveUnsignedIntegerValidatorImpl(scope, this, name + ".length()",
				0, null, PLURALIZER);
		}
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveUnsignedIntegerValidatorImpl(scope, this, name + ".length()",
				0, null, PLURALIZER);
		}
		return new PrimitiveUnsignedIntegerValidatorImpl(scope, this, name + ".length()",
			value.length(), value, PLURALIZER);
	}
}