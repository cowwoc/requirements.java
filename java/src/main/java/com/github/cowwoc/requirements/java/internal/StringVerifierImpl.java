/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.BigDecimalValidator;
import com.github.cowwoc.requirements.java.BigDecimalVerifier;
import com.github.cowwoc.requirements.java.BooleanValidator;
import com.github.cowwoc.requirements.java.BooleanVerifier;
import com.github.cowwoc.requirements.java.InetAddressValidator;
import com.github.cowwoc.requirements.java.InetAddressVerifier;
import com.github.cowwoc.requirements.java.IntegerValidator;
import com.github.cowwoc.requirements.java.IntegerVerifier;
import com.github.cowwoc.requirements.java.SizeValidator;
import com.github.cowwoc.requirements.java.SizeVerifier;
import com.github.cowwoc.requirements.java.StringValidator;
import com.github.cowwoc.requirements.java.StringVerifier;
import com.github.cowwoc.requirements.java.UriValidator;
import com.github.cowwoc.requirements.java.UriVerifier;
import com.github.cowwoc.requirements.java.UrlValidator;
import com.github.cowwoc.requirements.java.UrlVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectVerifier;

import java.util.function.Consumer;

/**
 * Default implementation of {@code StringVerifier}.
 */
public final class StringVerifierImpl
	extends AbstractObjectVerifier<StringVerifier, StringValidator, String>
	implements StringVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public StringVerifierImpl(StringValidator validator)
	{
		super(validator);
	}

	@Override
	protected StringVerifier getThis()
	{
		return this;
	}

	@Override
	public StringVerifier isEmpty()
	{
		validator.isEmpty();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public StringVerifier isNotEmpty()
	{
		validator.isNotEmpty();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public StringVerifier isBlank()
	{
		validator.isBlank();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public StringVerifier isNotBlank()
	{
		validator.isNotBlank();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public StringVerifier trim()
	{
		validator.trim();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public StringVerifier isTrimmed()
	{
		validator.isTrimmed();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public StringVerifier strip()
	{
		validator.strip();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public StringVerifier isStripped()
	{
		validator.isStripped();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public InetAddressVerifier asInetAddress()
	{
		InetAddressValidator newValidator = validator.asInetAddress();
		return validationResult(() -> new InetAddressVerifierImpl(newValidator));
	}

	@Override
	public StringVerifier asInetAddress(Consumer<InetAddressVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asInetAddress());
		return this;
	}

	@Override
	public UriVerifier asUri()
	{
		UriValidator newValidator = validator.asUri();
		return validationResult(() -> new UriVerifierImpl(newValidator));
	}

	@Override
	public StringVerifier asUri(Consumer<UriVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asUri());
		return this;
	}

	@Override
	public UrlVerifier asUrl()
	{
		UrlValidator newValidator = validator.asUrl();
		return validationResult(() -> new UrlVerifierImpl(newValidator));
	}

	@Override
	public StringVerifier asUrl(Consumer<UrlVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asUrl());
		return this;
	}

	@Override
	public BooleanVerifier asBoolean()
	{
		BooleanValidator newValidator = validator.asBoolean();
		return validationResult(() -> new BooleanVerifierImpl(newValidator));
	}

	@Override
	public StringVerifier asBoolean(Consumer<BooleanVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asBoolean());
		return this;
	}

	@Override
	public IntegerVerifier<Short> asShort()
	{
		IntegerValidator<Short> newValidator = validator.asShort();
		return validationResult(() -> new ShortVerifierImpl(newValidator));
	}

	@Override
	public StringVerifier asShort(Consumer<IntegerVerifier<Short>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asShort());
		return this;
	}

	@Override
	public IntegerVerifier<Integer> asInteger()
	{
		IntegerValidator<Integer> newValidator = validator.asInteger();
		return validationResult(() -> new IntegerVerifierImpl(newValidator));
	}

	@Override
	public StringVerifier asInteger(Consumer<IntegerVerifier<Integer>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asInteger());
		return this;
	}

	@Override
	public IntegerVerifier<Long> asLong()
	{
		IntegerValidator<Long> newValidator = validator.asLong();
		return validationResult(() -> new LongVerifierImpl(newValidator));
	}

	@Override
	public StringVerifier asLong(Consumer<IntegerVerifier<Long>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asLong());
		return this;
	}

	@Override
	public BigDecimalVerifier asBigDecimal()
	{
		BigDecimalValidator newValidator = validator.asBigDecimal();
		return validationResult(() -> new BigDecimalVerifierImpl(newValidator));
	}

	@Override
	public StringVerifier asBigDecimal(Consumer<BigDecimalVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asBigDecimal());
		return this;
	}

	@Override
	public StringVerifier startsWith(String prefix)
	{
		validator.startsWith(prefix);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public StringVerifier doesNotStartWith(String prefix)
	{
		validator.doesNotStartWith(prefix);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public StringVerifier endsWith(String suffix)
	{
		validator.endsWith(suffix);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public StringVerifier doesNotEndWith(String suffix)
	{
		validator.doesNotEndWith(suffix);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public StringVerifier contains(String expected)
	{
		validator.contains(expected);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public StringVerifier doesNotContain(String value)
	{
		validator.doesNotContain(value);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier length()
	{
		SizeValidator newValidator = validator.length();
		return validationResult(() -> new SizeVerifierImpl(newValidator));
	}

	@Override
	public StringVerifier length(Consumer<SizeVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(length());
		return this;
	}

	@Override
	@Deprecated
	public StringVerifier asString()
	{
		return super.asString();
	}
}