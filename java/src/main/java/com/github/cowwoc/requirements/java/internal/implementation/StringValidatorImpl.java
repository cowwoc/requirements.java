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
import java.util.List;
import java.util.Map;

public final class StringValidatorImpl extends AbstractObjectValidator<StringValidator, String>
	implements StringValidator
{
	private static final Pluralizer PLURALIZER = Pluralizer.CHARACTER;

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if any of the mandatory arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public StringValidatorImpl(ApplicationScope scope, Configuration configuration, String name, String value,
		Map<String, Object> context, List<ValidationFailure> failures)
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
		return this;
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
		return this;
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
		return this;
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
		return this;
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
		return this;
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
		return this;
	}

	@Override
	public PrimitiveByteValidator asPrimitiveByte()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a byte.").
					toString());
			return new PrimitiveByteValidatorImpl(scope, configuration, name, (byte) 0, context, failures);
		}
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveByteValidatorImpl(scope, configuration, name, (byte) 0, context, failures);
		}

		byte valueAsByte;
		try
		{
			valueAsByte = Byte.parseByte(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a byte.").
				withContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new PrimitiveByteValidatorImpl(scope, configuration, name, (byte) 0, context, failures);
		}
		return new PrimitiveByteValidatorImpl(scope, configuration, name, valueAsByte, context, failures);
	}

	@Override
	public PrimitiveShortValidator asPrimitiveShort()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a short.").
					toString());
			return new PrimitiveShortValidatorImpl(scope, configuration, name, (short) 0, context, failures);
		}
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveShortValidatorImpl(scope, configuration, name, (short) 0, context, failures);
		}

		short valueAsShort;
		try
		{
			valueAsShort = Short.parseShort(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a short.").
				withContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new PrimitiveShortValidatorImpl(scope, configuration, name, (short) 0, context, failures);
		}
		return new PrimitiveShortValidatorImpl(scope, configuration, name, valueAsShort, context, failures);
	}

	@Override
	public PrimitiveIntegerValidator asPrimitiveInteger()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a int.").
					toString());
			return new PrimitiveIntegerValidatorImpl(scope, configuration, name, 0, context, failures);
		}
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveIntegerValidatorImpl(scope, configuration, name, 0, context, failures);
		}

		int valueAsInt;
		try
		{
			valueAsInt = Integer.parseInt(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a int.").
				withContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new PrimitiveIntegerValidatorImpl(scope, configuration, name, 0, context, failures);
		}
		return new PrimitiveIntegerValidatorImpl(scope, configuration, name, valueAsInt, context, failures);
	}

	@Override
	public PrimitiveLongValidator asPrimitiveLong()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a long.").
					toString());
			return new PrimitiveLongValidatorImpl(scope, configuration, name, 0L, context, failures);
		}
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveLongValidatorImpl(scope, configuration, name, 0L, context, failures);
		}

		long valueAsLong;
		try
		{
			valueAsLong = Long.parseLong(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a long.").
				withContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new PrimitiveLongValidatorImpl(scope, configuration, name, 0L, context, failures);
		}
		return new PrimitiveLongValidatorImpl(scope, configuration, name, valueAsLong, context, failures);
	}

	@Override
	public PrimitiveFloatValidator asPrimitiveFloat()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a float.").
					toString());
			return new PrimitiveFloatValidatorImpl(scope, configuration, name, 0.0f, context, failures);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveFloatValidatorImpl(scope, configuration, name, 0.0f, context, failures);
		}

		float valueAsFloat;
		try
		{
			valueAsFloat = Float.parseFloat(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a float.").
				withContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new PrimitiveFloatValidatorImpl(scope, configuration, name, 0.0f, context, failures);
		}
		return new PrimitiveFloatValidatorImpl(scope, configuration, name, valueAsFloat, context, failures);
	}

	@Override
	public PrimitiveDoubleValidator asPrimitiveDouble()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a double.").
					toString());
			return new PrimitiveDoubleValidatorImpl(scope, configuration, name, 0.0, context, failures);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveDoubleValidatorImpl(scope, configuration, name, 0.0, context, failures);
		}

		double valueAsDouble;
		try
		{
			valueAsDouble = Double.parseDouble(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a double.").
				withContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new PrimitiveDoubleValidatorImpl(scope, configuration, name, 0.0, context, failures);
		}
		return new PrimitiveDoubleValidatorImpl(scope, configuration, name, valueAsDouble, context, failures);
	}

	@Override
	public PrimitiveBooleanValidator asPrimitiveBoolean()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a boolean.").
					toString());
			return new PrimitiveBooleanValidatorImpl(scope, configuration, name, false, context, failures);
		}

		return new PrimitiveBooleanValidatorImpl(scope, configuration, name, Boolean.parseBoolean(value), context, failures);
	}

	@Override
	public PrimitiveCharacterValidator asPrimitiveCharacter()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a char.").
					toString());
			return new PrimitiveCharacterValidatorImpl(scope, configuration, name, '\u0000', context, failures);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveCharacterValidatorImpl(scope, configuration, name, '\u0000', context, failures);
		}

		if (value.length() != 1)
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a char.").
					withContext(value, "Actual").
					toString());
			return new PrimitiveCharacterValidatorImpl(scope, configuration, name, '\u0000', context, failures);
		}
		return new PrimitiveCharacterValidatorImpl(scope, configuration, name, value.charAt(0), context, failures);
	}

	@Override
	public BigIntegerValidator asBigInteger()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a BigInteger.").
					toString());
			return new BigIntegerValidatorImpl(scope, configuration, name, null, context, failures);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new BigIntegerValidatorImpl(scope, configuration, name, null, context, failures);
		}

		BigInteger valueAsBigInteger;
		try
		{
			valueAsBigInteger = new BigInteger(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a BigInteger.").
				withContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new BigIntegerValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new BigIntegerValidatorImpl(scope, configuration, name, valueAsBigInteger, context, failures);
	}

	@Override
	public BigDecimalValidator asBigDecimal()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a BigDecimal.").
					toString());
			return new BigDecimalValidatorImpl(scope, configuration, name, null, context, failures);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new BigDecimalValidatorImpl(scope, configuration, name, null, context, failures);
		}

		BigDecimal valueAsBigDecimal;
		try
		{
			valueAsBigDecimal = new BigDecimal(value);
		}
		catch (NumberFormatException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a BigDecimal.").
				withContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new BigDecimalValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new BigDecimalValidatorImpl(scope, configuration, name, valueAsBigDecimal, context, failures);
	}

	@Override
	public PathValidator asPath()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a Path.").
					toString());
			return new PathValidatorImpl(scope, configuration, name, null, context, failures);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PathValidatorImpl(scope, configuration, name, null, context, failures);
		}

		Path valueAsPath;
		try
		{
			valueAsPath = Path.of(value);
		}
		catch (InvalidPathException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a Path.").
				withContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new PathValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new PathValidatorImpl(scope, configuration, name, valueAsPath, context, failures);
	}

	@Override
	public UriValidator asUri()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be convertible to a URI.").
					toString());
			return new UriValidatorImpl(scope, configuration, name, null, context, failures);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new UriValidatorImpl(scope, configuration, name, null, context, failures);
		}

		try
		{
			URI uri = URI.create(value);
			return new UriValidatorImpl(scope, configuration, name, uri, context, failures);
		}
		catch (IllegalArgumentException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a URI.").
				withContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new UriValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new UrlValidatorImpl(scope, configuration, name, null, context, failures);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new UrlValidatorImpl(scope, configuration, name, null, context, failures);
		}

		try
		{
			URL url = URI.create(value).toURL();
			return new UrlValidatorImpl(scope, configuration, name, url, context, failures);
		}
		catch (MalformedURLException e)
		{
			addFailure(new MessageBuilder(scope, this, name + " must be convertible to a URL.").
				withContext(value, "Actual").toString(), e, IllegalArgumentException::new);
			return new UrlValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new InetAddressValidatorImpl(scope, configuration, name, null, context, failures);
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new InetAddressValidatorImpl(scope, configuration, name, null, context, failures);
		}

		// IPv4 must start with a digit. IPv6 must start with a colon.
		if (value.isEmpty())
		{
			addIllegalArgumentException(
				CollectionMessages.isNotEmpty(scope, this, name).toString());
			return new InetAddressValidatorImpl(scope, configuration, name, null, context, failures);
		}

		char firstCharacter = value.charAt(0);
		if (Character.digit(firstCharacter, 16) == -1 && (firstCharacter != ':'))
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this,
					name + " must contain a valid IP address or hostname format.").
					withContext(value, "Actual").
					toString());
			return new InetAddressValidatorImpl(scope, configuration, name, null, context, failures);
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
				withContext(value, "Actual").
				toString(), e, IllegalArgumentException::new);
			return new InetAddressValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new InetAddressValidatorImpl(scope, configuration, name, address, context, failures);
	}

	@Override
	public StringValidator startsWith(String prefix)
	{
		scope.getInternalValidators().requireThat(prefix, "prefix").isNotNull();

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
					withContext(value, "Actual").
					toString());
		}
		return this;
	}

	@Override
	public StringValidator doesNotStartWith(String prefix)
	{
		scope.getInternalValidators().requireThat(prefix, "prefix").isNotNull();

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
					withContext(value, "Actual").
					toString());
		}
		return this;
	}

	@Override
	public StringValidator endsWith(String suffix)
	{
		scope.getInternalValidators().requireThat(suffix, "suffix").isNotNull();

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
					withContext(value, "Actual").
					toString());
		}
		return this;
	}

	@Override
	public StringValidator doesNotEndWith(String suffix)
	{
		scope.getInternalValidators().requireThat(suffix, "suffix").isNotNull();

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
					withContext(value, "Actual").
					toString());
		}
		return this;
	}

	@Override
	public StringValidator contains(String expected)
	{
		scope.getInternalValidators().requireThat(expected, "Expected").isNotNull();

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
					withContext(value, "Actual").
					toString());
		}
		return this;
	}

	@Override
	public StringValidator doesNotContain(String unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();

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
					withContext(value, "Actual").
					toString());
		}
		return this;
	}

	@Override
	public StringValidator matches(String regex)
	{
		scope.getInternalValidators().requireThat(regex, "regex").isNotNull();

		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this,
					name + " must match the regular expression \"" + regex + "\".").
					toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else if (!value.matches(regex))
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this,
					name + " must match the regular expression \"" + regex + "\".").
					withContext(value, "Actual").
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator length()
	{
		if (hasFailed())
		{
			return new PrimitiveUnsignedIntegerValidatorImpl(scope, configuration, name + ".length()",
				0, name, null, PLURALIZER, context, failures);
		}
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
			return new PrimitiveUnsignedIntegerValidatorImpl(scope, configuration, name + ".length()",
				0, name, null, PLURALIZER, context, failures);
		}
		return new PrimitiveUnsignedIntegerValidatorImpl(scope, configuration, name + ".length()",
			value.length(), name, value, PLURALIZER, context, failures);
	}
}