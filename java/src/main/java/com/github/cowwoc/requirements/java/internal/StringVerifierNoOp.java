/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.BooleanVerifier;
import com.github.cowwoc.requirements.java.InetAddressVerifier;
import com.github.cowwoc.requirements.java.SizeVerifier;
import com.github.cowwoc.requirements.java.StringVerifier;
import com.github.cowwoc.requirements.java.UriVerifier;
import com.github.cowwoc.requirements.java.UrlVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

import java.util.function.Consumer;

/**
 * A {@code StringVerifier} that does nothing.
 */
public final class StringVerifierNoOp
	extends AbstractObjectVerifierNoOp<StringVerifier, String>
	implements StringVerifier
{
	private static final StringVerifierNoOp INSTANCE = new StringVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static StringVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private StringVerifierNoOp()
	{
	}

	@Override
	protected StringVerifier getThis()
	{
		return this;
	}

	@Override
	public StringVerifier doesNotEndWith(String suffix)
	{
		return this;
	}

	@Override
	public StringVerifier doesNotStartWith(String prefix)
	{
		return this;
	}

	@Override
	public StringVerifier endsWith(String suffix)
	{
		return this;
	}

	@Override
	public StringVerifier contains(String expected)
	{
		return this;
	}

	@Override
	public StringVerifier doesNotContain(String value)
	{
		return this;
	}

	@Override
	public InetAddressVerifier asInetAddress()
	{
		return InetAddressVerifierNoOp.getInstance();
	}

	@Override
	public StringVerifier asInetAddress(Consumer<InetAddressVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public UriVerifier asUri()
	{
		return UriVerifierNoOp.getInstance();
	}

	@Override
	public StringVerifier asUri(Consumer<UriVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public UrlVerifier asUrl()
	{
		return UrlVerifierNoOp.getInstance();
	}

	@Override
	public StringVerifier asUrl(Consumer<UrlVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public BooleanVerifier asBoolean()
	{
		return BooleanVerifierNoOp.getInstance();
	}

	@Override
	public StringVerifier asBoolean(Consumer<BooleanVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public StringVerifier isEmpty()
	{
		return this;
	}

	@Override
	public StringVerifier isNotEmpty()
	{
		return this;
	}

	@Override
	public StringVerifier isBlank()
	{
		return this;
	}

	@Override
	public StringVerifier isNotBlank()
	{
		return this;
	}

	@Override
	public StringVerifier startsWith(String prefix)
	{
		return this;
	}

	@Override
	public StringVerifier trim()
	{
		return this;
	}

	@Override
	public StringVerifier isTrimmed()
	{
		return this;
	}

	@Override
	public SizeVerifier length()
	{
		return SizeVerifierNoOp.getInstance();
	}

	@Override
	public StringVerifier length(Consumer<SizeVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	@Deprecated
	public StringVerifier asString()
	{
		return this;
	}
}
